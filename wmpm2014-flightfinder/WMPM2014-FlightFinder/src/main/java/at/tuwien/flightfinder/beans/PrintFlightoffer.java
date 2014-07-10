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
		System.out.println(fo.toString());



	}


}


