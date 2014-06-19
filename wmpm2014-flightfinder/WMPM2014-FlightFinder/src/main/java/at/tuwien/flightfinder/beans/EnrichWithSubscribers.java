package at.tuwien.flightfinder.beans;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.impl.DefaultMessage;

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
/**
 * @author ivan
 *
 */
public class EnrichWithSubscribers implements  Processor {
/*
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
*/
	private static final Logger logger = LoggerFactory.getLogger(EnrichWithSubscribers.class);

	public void process(Exchange exchange) {

		List<Flightoffer> offer = (List<Flightoffer>) exchange.getIn().getBody();
		
		for (Flightoffer flightoffer : offer) {
			System.out.println(flightoffer.getNameOrigin());
		}
	/*	FlightofferDAO foDAO = new FlightofferDAO();
		SubscriberDAO suDAO = new SubscriberDAO();
		AirportDAO apDAO = new AirportDAO();

		List <Airport> airportList = apDAO.getAllAirports();
//		Airport vie = apDAO.getAirportByIataCode("VIE");
//		Airport mad = apDAO.getAirportByIataCode("MAD");
//		List <Airport> airportList = new ArrayList<Airport>();
//		airportList.add(vie);
//		airportList.add(mad);



		for(Airport a: airportList){
			System.out.println("-------------Begin of the iteration--------------");
			//Get all flightOffers from today and the list of subscribers to the specific IATA code(Airport)
			String iata = a.getIataCode();
			List<Flightoffer> flightofferList = foDAO.getTodaysFlightoffersByDepAirport(iata);
			System.out.println("The size of flightofferList: "+flightofferList.size());
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

			//DefaultMessage message = new DefaultMessage(); 

			logger.info("------------------MailingList: "+emailList+"----------------");

			*//**
			 * Header includes allSubscribers and body includes the list of flight offers.
			 * The body includes a list of all flight offers that will be displayed on the newsletter.
			 *//*
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

//				exchange.getIn().setHeaders(mailHeader);
//				exchange.getIn().setBody(flightofferList);
				
				//sends the body of the message to Velocity-template for transformation
				ProducerTemplate template = exchange.getContext().createProducerTemplate();
				String newsletterBody = (String)template.requestBody("velocity:file:mojTest/newsletter.vm", flightofferList); 
				exchange.getIn().setHeaders(mailHeader);
				exchange.getIn().setBody(newsletterBody);
				
//				message.setHeaders(mailHeader);
//				message.setBody(flightofferList);
//				exchange.setIn(message); 
				
				//sends the whole exchange as email iteratively
				logger.info("SENDING EMAIL");
				template.send("smtp://188.40.32.121?username=workflow@seferovic.net&password=workflowpassword&contentType=text/html",exchange); //sends multiple messages
			}	
		}//ForLoop

*/

	}//Method
}






