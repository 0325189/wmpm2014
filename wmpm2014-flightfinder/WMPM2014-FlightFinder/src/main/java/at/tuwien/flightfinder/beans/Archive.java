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
		
		Flightoffer flightOffer = new Flightoffer();
		
		System.out.println(exchange.getIn().getBody());
					
		
		String trimedFlightNumber;
		
		String trimedAirCompany;
		
		
		Airport airportOrigin;
		
		String trimedNameOrigin;
		
		Airport airportDestination;
		
		String trimedNameDestination;
		

		String trimedFlightDate;
		

		String trimedNameClass;
		

		String trimedTicketId;

		String trimedPrice;
		try {
			//FlightNumber
			String flightNumber = XPathBuilder.xpath("//FlightNumber")
					.evaluate(exchange, String.class);
			trimedFlightNumber = flightNumber.substring(
					flightNumber.indexOf('>') + 1,
					flightNumber.lastIndexOf('<'));
			//AirCompany 
			String airCompany = XPathBuilder.xpath("//AirCompany").evaluate(
					exchange, String.class);
			trimedAirCompany = airCompany.substring(
					airCompany.indexOf('>') + 1, airCompany.lastIndexOf('<'));
			//IATACodeOrigin
			String iataCodeOrigin = XPathBuilder.xpath("//IATACodeOrigin")
					.evaluate(exchange, String.class);
			String trimedIATACodeOrigin = iataCodeOrigin.substring(
					iataCodeOrigin.indexOf('>') + 1,
					iataCodeOrigin.lastIndexOf('<'));
			AirportDAO airportDao = new AirportDAO();
			airportOrigin = airportDao
					.getAirportByIataCode(trimedIATACodeOrigin);
			//NameOrigin
			String nameOrigin = XPathBuilder.xpath("//NameOrigin").evaluate(
					exchange, String.class);
			trimedNameOrigin = nameOrigin.substring(
					nameOrigin.indexOf('>') + 1, nameOrigin.lastIndexOf('<'));
			//IATACodeDestination
			String iataCodeDestination = XPathBuilder.xpath(
					"//IATACodeDestination").evaluate(exchange, String.class);
			String trimedIATACodeDestination = iataCodeDestination.substring(
					iataCodeDestination.indexOf('>') + 1,
					iataCodeDestination.lastIndexOf('<'));
			airportDestination = airportDao
					.getAirportByIataCode(trimedIATACodeDestination);
			//NameDestination
			String nameDestination = XPathBuilder.xpath("//Destination")
					.evaluate(exchange, String.class);
			trimedNameDestination = nameDestination.substring(
					nameDestination.indexOf('>') + 1,
					nameDestination.lastIndexOf('<'));
			//FlightDate
			String flightDate = XPathBuilder.xpath("//FlightDate").evaluate(
					exchange, String.class);
			trimedFlightDate = flightDate.substring(
					flightDate.indexOf('>') + 1, flightDate.lastIndexOf('<'));
			//Class
			String nameClass = XPathBuilder.xpath("//Class").evaluate(exchange,
					String.class);
			trimedNameClass = nameClass.substring(nameClass.indexOf('>') + 1,
					nameClass.lastIndexOf('<'));
			//TicketID
			String ticketId = XPathBuilder.xpath("//TicketID").evaluate(
					exchange, String.class);
			trimedTicketId = ticketId.substring(ticketId.indexOf('>') + 1,
					ticketId.lastIndexOf('<'));
			//Price
			String price = XPathBuilder.xpath("//Price").evaluate(exchange,
					String.class);
			trimedPrice = price.substring(price.indexOf('>') + 1,
					price.lastIndexOf('<'));
		
		
		
		
		Date trimedFlightDate2 = new SimpleDateFormat("yyyyMMdd").parse(trimedFlightDate);
		int price2 = Integer.parseInt(trimedPrice);
		
		if (trimedNameClass.startsWith("B")){
			flightOffer.setFlightClass(FlightClass.Business);
		}
		else if (trimedNameClass.startsWith("E")){
			flightOffer.setFlightClass(FlightClass.Economy);
		}
	
		
		flightOffer.setFlightNumber(trimedFlightNumber);
		flightOffer.setAirCompany(trimedAirCompany);
		flightOffer.setFromAirport(airportOrigin);
		flightOffer.setNameOrigin(trimedNameOrigin);
		flightOffer.setToAirport(airportDestination);
		flightOffer.setNameDestination(trimedNameDestination);
		flightOffer.setFlightDate(trimedFlightDate2);
		flightOffer.setTicketId(trimedTicketId);
		flightOffer.setPrice(price2);
		
		FlightofferDAO flightOfferDAO = new FlightofferDAO();
		flightOfferDAO.addFlightoffer(flightOffer);
		
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
}
