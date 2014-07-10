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
import at.tuwien.flightfinder.dao.HotelDAO;
import at.tuwien.flightfinder.dao.SubscriberDAO;
import at.tuwien.flightfinder.pojo.Airport;
import at.tuwien.flightfinder.pojo.Flightoffer;
import at.tuwien.flightfinder.pojo.Hotel;
import at.tuwien.flightfinder.pojo.Subscriber;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * This method pulls sorted flight offers according to its price once a day from database. The output of the class is a message that holds a list of offers
 * in its body and format header with a list of subscribers ready to be processed by the camel smtp-endpoint.
 *
 */
/**
 * @author ivan
 *
 */
@Component
public class EnrichWithSubscribers implements  Processor {

	@Autowired
	HotelDAO hotelDao;
	@Autowired
	SubscriberDAO suDAO;
	private static final Logger logger = LoggerFactory.getLogger(EnrichWithSubscribers.class);

	public void process(Exchange exchange) {

		List<Flightoffer> offers = (List<Flightoffer>) exchange.getIn().getBody();

		//Trim the list to consider only the first 5 best offer
		if(offers.size()>4){
			offers.subList(4, offers.size()-1).clear();
		}
		

		String iata = offers.get(0).getFromIataCode();


		//Get the list of subscribers
		List<Subscriber> subscriberList = suDAO.getSubscriberByOrignAirport(iata);

		//Create a String with addresses of all Subscribers
		String emailList = "";
		for(Subscriber item:subscriberList){
			emailList += item.getEmail()+" ; ";
		}

		//DefaultMessage message = new DefaultMessage(); 

		logger.info("------------------MailingList: "+emailList+"----------------");


		if(offers.isEmpty() || emailList.isEmpty()){
			logger.info("There are no Flightoffers or Subscribers for the Airport: "+iata+". No Email will be sent!");
		}else{

			//The last semi-colon has to be cut out
			emailList.trim();
			emailList = emailList.substring(0, emailList.length()-3);

			Map<String, Object> mailHeader = new HashMap<String, Object>();
			mailHeader.put("To", emailList);
			mailHeader.put("From", "FlightFinder <workflow@seferovic.net>");
			mailHeader.put("Subject", "FlightFinder -- Best offres of the day!");

			//sends the body of the message to Velocity-template for transformation
			exchange.getIn().setHeaders(mailHeader);
			exchange.getIn().setBody(offers);

		}	
	}

}







