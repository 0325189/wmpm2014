package at.tuwien.flightfinder.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;


@Component
public class MailToFileOffersRoute extends RouteBuilder {

	
            public void configure() {
               
            	/**
            	 * Offer Gathering per eMail should connect to a server by using IMAP(s) and poll every 2 sec for new messages
            	 * 
            	 * from("pop3://james@mymailserver.com?password=secret&consumer.delay=1000")
    				.to("log:email")
    				// use the SplitAttachmentsExpression which will split the message per attachment
    				.split(new SplitAttachmentsExpression())
        			// each message going to this mock has a single attachment
        			.to("mock:split")
    				.end();
            	 */
            }
          
}
