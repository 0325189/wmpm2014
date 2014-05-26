package at.tuwien.flightfinder.beans;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

import org.apache.camel.*;
import org.apache.camel.builder.xml.XPathBuilder;

import static org.apache.camel.builder.PredicateBuilder.not;

public class EuropeanFlightsFilter implements Predicate {


	ArrayList<String> europeanFlightList = new ArrayList();

	@Override
	public boolean matches(Exchange exchange) {

		Scanner scanner;
		try 
		{
			scanner = new Scanner(new File("mojTest/IATAEuropeanCodes.csv"));
			scanner.useDelimiter("/n");

			while (scanner.hasNext())
			{
				europeanFlightList.add(scanner.next());
				System.out.println(scanner.next());
			}

			scanner.close();

		} 
		catch (FileNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String origin = XPathBuilder.xpath("//Origin").evaluate(exchange, String.class);

		System.out.println(origin);

		// boolean matches = XPathBuilder.xpath("Origin").matches(exchange);

		if (europeanFlightList.contains(origin)){
			System.out.println(origin);
			return true;
		}

		return false;
	}

}
