package at.tuwien.flightfinder.beans;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import at.tuwien.flightfinder.pojo.Airport;
import at.tuwien.flightfinder.pojo.Flightoffer;




public class PrintListFlightoffer implements Processor {
	public void process(Exchange exchange) throws Exception {


		List<Flightoffer> foList = (List<Flightoffer>) exchange.getIn().getBody();
		
		for (Flightoffer fo : foList)
			System.out.println(fo.toString());

	}


}


