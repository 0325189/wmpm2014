package SampleApp;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class SampleFlightLogger implements Processor {

	public void process(Exchange exchange) throws Exception {
		
		System.out.println("Found at: " + exchange.getIn().getHeader("CamelFileName"));
		System.out.println(exchange.getIn().getBody(String.class));
		
		
		
	}

}
