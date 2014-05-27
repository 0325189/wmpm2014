package at.tuwien.flightfinder.beans;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.camel.*;
import org.apache.camel.builder.xml.XPathBuilder;

import at.tuwien.flightfinder.dao.AirportDAO;
import at.tuwien.flightfinder.pojo.Airport;

public class EuropeanFlightsFilter implements Predicate {

	@Override
	public boolean matches(Exchange exchange) {
		
		boolean IataCodeFound = false;
		String origin = XPathBuilder.xpath("//IATACodeOrigin").evaluate(exchange, String.class);
		String trimedOrigin = origin.substring(origin.indexOf('>')+1,origin.lastIndexOf('<'));
		//System.out.println("What is my origin: "+trimedOrigin);

		// boolean matches = XPathBuilder.xpath("Origin").matches(exchange);

		AirportDAO airportDao1 = new AirportDAO();
		List<Airport> airports = airportDao1.getAllAirports();
		
		for(Airport a : airports)
		{
			String iataCode = a.getIataCode();
			if (trimedOrigin.equals(iataCode))
			{
				System.out.println("The destination " + trimedOrigin + " has been accepted by the filter!");
				IataCodeFound = true;
			}
			
		}
		return IataCodeFound;
	}

}
