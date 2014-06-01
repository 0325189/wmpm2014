package at.tuwien.flightfinder.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import at.tuwien.flightfinder.beans.EnrichWithSubscribers;

/**
 * This is CamelRoute that pulls the best flight offers once a day from the DB and send them to a list of subscribers.
 *
 */
@Component
public class NewsletterMailRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {

		
		from("timer:newsletter?period=100000"). //can be set to specific time "time=yyyy-MM-dd HH:mm:ss" or just set the period to one day "period=86400000"
		process(new EnrichWithSubscribers()).
		to("velocity:file:mojTest/newsletter.vm").
		//to("file:mojTest?fileName=emailNewsletter.html"). //Just for testing
		to("smtp://188.40.32.121?username=workflow@seferovic.net&password=workflowpassword&contentType=text/html").
		log("--------FINISHED-------");
			

	}

}
