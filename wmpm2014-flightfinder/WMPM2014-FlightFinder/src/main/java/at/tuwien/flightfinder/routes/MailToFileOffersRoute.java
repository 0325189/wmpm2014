package at.tuwien.flightfinder.routes;

import java.io.FileOutputStream;
import java.util.Map;

import javax.activation.DataHandler;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mail.SplitAttachmentsExpression;
import org.springframework.stereotype.Component;

import at.tuwien.flightfinder.beans.IncommingMailProcessor;


/**
 * MailToFileOffersRoute
 * Mail server	: 88.198.149.250
 * Username 	: workflow@seferovic.net
 * Password		: workflowpassword
 * 
 * This route will connect to an eMail server by using IMAP, get attachments,
 * split them and put them into FileOffers queue
 * 
 * @author sanjin becirovic
 */

@Component
public class MailToFileOffersRoute extends RouteBuilder {

//			@Override
//            public void configure() throws Exception {
//				from("imap://188.40.32.121?username=workflow@seferovic.net&password=workflowpassword&delete=false&unseen=false&consumer.delay=60000").
//				routeId("Route-Mail").
//				split(new SplitAttachmentsExpression()).process(new IncommingMailProcessor()).to("activemq:fileOffers").end();
				
			@Override
	        public void configure() throws Exception {
				from("imap://188.40.32.121?username=workflow@seferovic.net&password=workflowpassword&delete=false&unseen=false&consumer.delay=60000")
				.routeId("Route-Mail")
				.split(new SplitAttachmentsExpression())
				.choice()
					.when(header("CamelFileName").endsWith(".xml")).log("XML file").endChoice()
					.when(header("CamelFileName").endsWith(".csv")).log("CSV file").endChoice()
					.otherwise().log("invalid file ext").endChoice();
				
//				process(new IncommingMailProcessor()).to("activemq:fileOffers").end();
		}
			
}
			
		
         
