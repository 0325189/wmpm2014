package at.tuwien.flightfinder.beans;


import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class HttpRouteLogger implements Processor {

	public void process(Exchange exchange) throws Exception {
		System.out.println("Found at: " + exchange.getIn().getHeader("CamelFileName"));
		System.out.println(exchange.getIn().getBody(String.class));

	}


}
