package at.tuwien.flightfinder.beans;


import java.io.StringWriter;
import java.util.ArrayList;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class JSONStream implements Processor {

	public void process(Exchange exchange) throws Exception {
		System.out.println("Found at: " + exchange.getIn().getHeader("CamelFileName"));
		ArrayList<String> list = exchange.getIn().getBody(ArrayList.class);
		String listString = "";

		for (String s : list)
		{
		    listString += s + "\n";
		}
		
		exchange.getOut().setBody(listString);

		

	}


}
