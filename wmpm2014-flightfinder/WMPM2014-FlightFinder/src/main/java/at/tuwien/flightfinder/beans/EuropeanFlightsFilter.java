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
		Scanner sc;

		try {
			sc = new Scanner(new File("mojTest/IATAEuropeanCodes.csv"));
			sc.useDelimiter("\n");
			while(sc.hasNext()){
				europeanFlightList.add(sc.nextLine());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}


		String origin = XPathBuilder.xpath("//IATACodeOrigin").evaluate(exchange, String.class);
		String trimedOrigin = origin.substring(origin.indexOf('>')+1,origin.lastIndexOf('<'));
		//System.out.println("What is my origin: "+trimedOrigin);

		// boolean matches = XPathBuilder.xpath("Origin").matches(exchange);

		if (europeanFlightList.contains(trimedOrigin)){
			System.out.println("The destination "+trimedOrigin+" has been accepted by the filter!");
			return true;
		}

		return false;
	}

}
