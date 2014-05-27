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
		AirportDAO airportDao1 = new AirportDAO();
	
		try {
			if(airportDao1.getAirportByIataCode(trimedOrigin)!=null){
				System.out.println("The destination " + trimedOrigin + " has been accepted by the filter!");
				IataCodeFound = true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return IataCodeFound;
	}
}
