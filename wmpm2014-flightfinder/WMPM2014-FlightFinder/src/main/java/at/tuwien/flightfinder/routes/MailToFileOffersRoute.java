package at.tuwien.flightfinder.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

/**
 * MailToFileOffersRoute
 * Mail server	: 88.198.149.250
 * Username 	: workflow@seferovic.net
 * Password		: workflowpassword
 * 
 * This route will connect to an eMail server by using IMAP, get attachments,
 * split them and put them into FileOffers queue
 * 
 * @author seferovic
 */

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
