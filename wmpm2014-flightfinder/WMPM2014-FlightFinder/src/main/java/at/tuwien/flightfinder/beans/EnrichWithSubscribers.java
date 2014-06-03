package at.tuwien.flightfinder.beans;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;

import at.tuwien.flightfinder.dao.AirportDAO;
import at.tuwien.flightfinder.dao.FlightofferDAO;
import at.tuwien.flightfinder.dao.SubscriberDAO;
import at.tuwien.flightfinder.pojo.Airport;
import at.tuwien.flightfinder.pojo.Flightoffer;
import at.tuwien.flightfinder.pojo.Subscriber;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This method pulls sorted flight offers according to its price once a day from database. The output of the class is a message that holds a list of offers
 * in its body and format header with a list of subscribers ready to be processed by the camel smtp-endpoint.
 *
 */
public class EnrichWithSubscribers implements  Processor {

	//ProducerTemplate
	ProducerTemplate producer;

	public void setProducer(ProducerTemplate producer) {
		this.producer = producer;
	}

	Comparator<Flightoffer> priceComparator = new Comparator<Flightoffer>() {
		@Override
		public int compare(Flightoffer fo1, Flightoffer fo2) {
			return ((Integer)fo1.getPrice()).compareTo((Integer)fo2.getPrice());
		}
	};
	
	private static final Logger logger = LoggerFactory.getLogger(EnrichWithSubscribers.class);

	public void process(Exchange exchange) {

		FlightofferDAO foDAO = new FlightofferDAO();
		SubscriberDAO suDAO = new SubscriberDAO();
		AirportDAO apDAO = new AirportDAO();

		List <Airport> airportList = apDAO.getAllAirports();
		//Airport a = apDAO.getAirportByIataCode("VIE");

		for(Airport a: airportList){
			//Get all flightOffers from today and the list of subscribers to the specific IATA code(Airport)
			String iata = a.getIataCode();
			List<Flightoffer> flightofferList = foDAO.getTodaysFlightoffersByDepAirport(iata);

			//Sort the list of Flight offers
			Collections.sort(flightofferList, priceComparator);

			//Get the list of subscribers
			List<Subscriber> subscriberList = suDAO.getSubscriberByOrignAirport(iata);


			//Trim the list to consider only the first 5 best offer
			if(flightofferList.size()>4){
				flightofferList.subList(0, 4).clear();
			}

			//Create a String with addresses of all Subscribers
			String emailList = "";
			for(Subscriber item:subscriberList){
				emailList += item.getEmail()+" ; ";
			}

			/**
			 * Header includes allSubscribers and body includes the list of flight offers.
			 * The body includes a list of all flight offers that will be displayed on the newsletter.
			 */
			if(flightofferList.isEmpty() || emailList.isEmpty()){
				logger.info("There are no Flightoffers or Subscribers for the Airport: "+iata+". No Email will be sent!");
			}else{

				//The last semi-colon has to be cut out
				emailList.trim();
				emailList = emailList.substring(0, emailList.length()-3);

				Map<String, Object> mailHeader = new HashMap<String, Object>();
				mailHeader.put("To", emailList);
				mailHeader.put("From", "FlightFinder <workflow@seferovic.net>");
				mailHeader.put("Subject", "FlightFinder -- Best offres of the day!");

				exchange.getOut().setHeaders(mailHeader);
				exchange.getOut().setBody(flightofferList);
				
			}	

		}//ForLoop



	}//Method
}






