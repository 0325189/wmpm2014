package at.tuwien.flightfinder.beans;

import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import twitter4j.Twitter;
import at.tuwien.flightfinder.dao.FlightofferDAO;
import at.tuwien.flightfinder.pojo.Flightoffer;

/**
 * @author Sanjin Becirovic
 * This processor pulls all of the "Todays" flight offers from the database, sorts them based on price, 
 * and add the best flight offer to the exchange in form of String body, which is then to be posted to Twitter 
 * 
 */

public class MarketingProcessor implements Processor{
	
	public void process(Exchange exchange)
	{
		FlightofferDAO foDAO = new FlightofferDAO();
		
		//Get Todays Single Best Flight Offer from db
		/*Flightoffer todaysSingleBestFlightOffer = foDAO.getTodaysSingleBestFlightoffer();
		
		String todaysSingleBestFlightOfferString = "FROM: " + todaysSingleBestFlightOffer.getNameOrigin() + " "+
												   "TO: " + todaysSingleBestFlightOffer.getNameDestination() + " "+
												   "CLASS: " + todaysSingleBestFlightOffer.getFlightClass() + " "+
												   "FOR ONLY EUR:" + todaysSingleBestFlightOffer.getPrice();*/
		
		//exchange.getOut().setBody(todaysSingleBestFlightOfferString);
		
		
	}

}
