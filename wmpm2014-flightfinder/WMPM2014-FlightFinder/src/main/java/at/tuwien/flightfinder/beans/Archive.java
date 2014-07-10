package at.tuwien.flightfinder.beans;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.xml.XPathBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

import at.tuwien.flightfinder.dao.AirportDAO;
import at.tuwien.flightfinder.dao.FlightofferDAO;
import at.tuwien.flightfinder.pojo.Airport;
import at.tuwien.flightfinder.pojo.FlightClass;
import at.tuwien.flightfinder.pojo.Flightoffer;
@Component
public class Archive implements Processor {
	/**
	 * xml flightoffer message parsing and persist to db
	 * @author Ivan Gusljesevic
	 */
	@Autowired
	FlightofferDAO flightOfferDAO;
	@Override
	public void process(Exchange exchange) throws Exception {

		Flightoffer flightOffer = (Flightoffer) exchange.getIn().getBody();
		
		flightOfferDAO.addFlightoffer(flightOffer);

	}

}
