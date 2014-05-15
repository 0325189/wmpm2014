package at.tuwien.flightfinder.beans;

import java.io.FileOutputStream;
import java.util.Map;

import javax.activation.DataHandler;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class IncommingMailProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		
		String body = exchange.getIn().getBody(String.class);
		
	    Map<String, DataHandler> attachments = exchange.getIn().getAttachments();
	     if (attachments.size() > 0) {
	         for (String name : attachments.keySet()) {
	        	 exchange.getOut().setHeader("attachmentName", name);
	        	 
	         }
	     }
	    // read the attachments from the in exchange putting them back on the out 
	    exchange.getOut().setAttachments(attachments);
	    
	    // resetting the body on out exchange   
	    exchange.getOut().setBody(body); 
	
		//System.out.println(exchange.getIn().getBody(String.class));

	}

}
