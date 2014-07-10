package at.tuwien.flightfinder.beans;

import java.util.List;

import javax.annotation.Resource;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import twitter4j.Twitter;
import at.tuwien.flightfinder.dao.FlightofferDAO;
import at.tuwien.flightfinder.pojo.Flightoffer;

/**
 * @author Sanjin Becirovic
 * This processor pulls all of the "Todays" flight offers from the database, sorts them based on price, 
 * and add the best flight offer to the exchange in form of String body, which is then to be posted to Twitter 
 * 
 */
@Component
public class MarketingProcessor implements Processor{
	@Autowired
	FlightofferDAO flightOfferDAO;
	
	public void process(Exchange exchange)
	{
		
		//Get Todays Single Best Flight Offer from db
		Flightoffer todaysSingleBestFlightOffer = flightOfferDAO.getTodaysBestFlightoffer();
		
		String todaysSingleBestFlightOfferString = "From: " + todaysSingleBestFlightOffer.getNameOrigin() + "; "+
												   "To: " + todaysSingleBestFlightOffer.getNameDestination() + "; "+
												   "Class: " + todaysSingleBestFlightOffer.getFlightClass() + "; "+
												   "Price: " + todaysSingleBestFlightOffer.getPrice();
		
		exchange.getOut().setBody(todaysSingleBestFlightOfferString);
	}

}
