package at.tuwien.flightfinder.beans;

import java.util.ArrayList;

import org.apache.camel.*;
import org.apache.camel.builder.xml.XPathBuilder;

import static org.apache.camel.builder.PredicateBuilder.not;

public class EuropeanFlightsFilter implements Predicate {

	ArrayList<String> europeanFlightList = new ArrayList();
	
	@Override
	public boolean matches(Exchange exchange) {
		
		 	String origin = XPathBuilder.xpath("//Origin").evaluate(exchange, String.class);
		 	europeanFlightList.add("VIE");
		 	
		 	System.out.println(origin);
		 	
			// boolean matches = XPathBuilder.xpath("Origin").matches(exchange);
			
			if (europeanFlightList.contains(origin)){
				System.out.println(origin);
				return true;
			}
				
		return false;
	}
		
}
