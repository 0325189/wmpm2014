package at.tuwien.flightfinder.beans;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import at.tuwien.flightfinder.pojo.Airport;
import at.tuwien.flightfinder.pojo.Flightoffer;




public class PrintFlightoffer implements Processor {
	public void process(Exchange exchange) throws Exception {


		Flightoffer fo = exchange.getIn().getBody(Flightoffer.class);
		System.out.println("----Class Type: "+exchange.getIn().getBody().getClass());
	

		Long id = fo.getId();
		String fromAirport = fo.getFromAirport().getFromIataCode();
		String toAirport = fo.getToAirport().getToIataCode();
		String nameOrigin = fo.getNameOrigin();
		String nameDestination = fo.getNameDestination();
		String airCompany = fo.getAirCompany();
		String flightDate = fo.getFlightClass().toString();
		String ticketId = fo.getTicketId();
		int price = fo.getPrice();
		String flightClass = fo.getFlightDate().toString();


		System.out.println("id: "+id+"\nfromAirport: "+fromAirport+"\ntoAirport: "+toAirport+"\nnameOrigin: "+nameOrigin+"\nnameDestination: "+nameDestination+"\nairCompany: "+airCompany+
				"\nflightDate: "+flightDate+"\nticketId: "+ticketId+"\nprice: "+price+"\nflightClass: "+flightClass);


	}
}

