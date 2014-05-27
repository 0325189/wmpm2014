package at.tuwien.flightfinder.beans;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import org.apache.camel.*;
import org.apache.camel.builder.xml.XPathBuilder;

import at.tuwien.flightfinder.dao.AirportDAO;
import at.tuwien.flightfinder.pojo.Airport;

public class EuropeanFlightsFilter implements Predicate {

	ArrayList<String> europeanFlightList = new ArrayList();

	@Override
	public boolean matches(Exchange exchange) {
		Scanner sc;
		
		//populate the database with european IATA codes
		Airport airport1 = new Airport("");
		AirportDAO airportDao1 = new AirportDAO();
		
		try 
		{
			sc = new Scanner(new File("mojTest/IATAEuropeanCodes.csv"));
			sc.useDelimiter("\n");
			
			while(sc.hasNext())
			{
				europeanFlightList.add(sc.nextLine());
			}
			sc.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}


		String origin = XPathBuilder.xpath("//IATACodeOrigin").evaluate(exchange, String.class);
		String trimedOrigin = origin.substring(origin.indexOf('>')+1,origin.lastIndexOf('<'));
		//System.out.println("What is my origin: "+trimedOrigin);

		// boolean matches = XPathBuilder.xpath("Origin").matches(exchange);

		if (europeanFlightList.contains(trimedOrigin)){
			System.out.println("The destination " + trimedOrigin + " has been accepted by the filter!");

			return true;
		}

		return false;
	}

}
