package at.tuwien.flightfinder.beans;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.xml.XPathBuilder;

import java.text.SimpleDateFormat;
import java.util.Date;

import at.tuwien.flightfinder.dao.AirportDAO;
import at.tuwien.flightfinder.dao.FlightofferDAO;
import at.tuwien.flightfinder.pojo.Airport;
import at.tuwien.flightfinder.pojo.FlightClass;
import at.tuwien.flightfinder.pojo.Flightoffer;

public class Archive implements Processor {
	/**
	 * xml flightoffer message parsing and persist to db
	 * @author Ivan Gusljesevic
	 */
	@Override
	public void process(Exchange exchange) throws Exception {

		Flightoffer flightOffer = (Flightoffer) exchange.getIn().getBody();
		FlightofferDAO flightOfferDAO = new FlightofferDAO();
		flightOfferDAO.addFlightoffer(flightOffer);

	}

}
