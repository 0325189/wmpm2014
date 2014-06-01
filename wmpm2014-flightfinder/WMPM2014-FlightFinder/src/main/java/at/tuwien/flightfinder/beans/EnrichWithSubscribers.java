package at.tuwien.flightfinder.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import at.tuwien.flightfinder.dao.AirportDAO;
import at.tuwien.flightfinder.dao.FlightofferDAO;
import at.tuwien.flightfinder.dao.SubscriberDAO;
import at.tuwien.flightfinder.pojo.Airport;
import at.tuwien.flightfinder.pojo.FlightClass;
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

	private static final Logger logger = LoggerFactory.getLogger(EnrichWithSubscribers.class);

	public void process(Exchange exchange) throws Exception {

		Airport vie = new Airport("VIE");
		Airport cdg = new Airport("CDG");
		Airport jfk = new Airport("JFK");

		Flightoffer fo1 = new Flightoffer(vie, cdg,
				"Vienna", "New York", "Lufthansa",
				"DF234", new Date(), "someID", 99, FlightClass.Business);

		Flightoffer fo2 = new Flightoffer(jfk, cdg,
				"New York", "Paris", "Lufthansa",
				"DF123", new Date(), "someID", 199, FlightClass.Economy);

		Flightoffer fo3 = new Flightoffer(cdg, vie,
				"Paris", "Vienna", "Lufthansa",
				"AF345", new Date(), "someID", 150, FlightClass.Business);
		
		List<Flightoffer> flightofferList = new ArrayList<Flightoffer>();
		flightofferList.add(fo1);
		flightofferList.add(fo2);
		flightofferList.add(fo3);
		
		
		/////////////////------please change to your email address here(gmail gets blocked for some reason!)-------////////////////////
		Subscriber su1 = new Subscriber("none@none.com", "Peter Parker", vie);
		Subscriber su2 = new Subscriber("none@none.com", "Green Goblin", cdg);
		Subscriber su3 = new Subscriber("none@none.com", "Maryjane Watson", cdg);

		
		List<Subscriber> subscriberList = new ArrayList<Subscriber>();
		subscriberList.add(su1);
		subscriberList.add(su2);
		subscriberList.add(su3);

		
		
		//Create a String with addresses of all Subscribers
		String emailList = "";
		for(Subscriber item:subscriberList){
			emailList += item.getEmail()+" ; ";
		}
		//The last semi-colon has to be cut out
		emailList.trim();
		emailList = emailList.substring(0, emailList.length()-3);

		
///////////////////////////////////-----Preparation for the working solution------////////////////////////////////////		
//		FlightofferDAO foDAO = new FlightofferDAO();
//		SubscriberDAO suDAO = new SubscriberDAO();
//		AirportDAO apDAO = new AirportDAO();
		
//		Flightoffer fo = foDAO.getFlightofferById(4);
//		String iata = fo.getFromAirport().getIataCode();
//
//		List<Subscriber> subscriberList = suDAO.getSubscriberByOrignAirport(iata);
		
		
		

/**
 * Header includes allSubscribers and body includes the list of flight offers.
 * The body includes a list of all flight offers that will be displayed on the newsletter.
 */
		Map<String, Object> mailHeader = new HashMap<String, Object>();
		mailHeader.put("To", emailList);
		mailHeader.put("From", "FlightFinder <workflow@seferovic.net>");
		mailHeader.put("Subject", "FlightFinder -- Best offres of the day!");
		
		exchange.getOut().setHeaders(mailHeader);
		exchange.getOut().setBody(flightofferList);

	}	
}

