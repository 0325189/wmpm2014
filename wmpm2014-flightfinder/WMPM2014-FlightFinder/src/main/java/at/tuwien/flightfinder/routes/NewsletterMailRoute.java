package at.tuwien.flightfinder.routes;


import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import at.tuwien.flightfinder.beans.EnrichWithSubscribers;
import at.tuwien.flightfinder.pojo.Flightoffer;
import at.tuwien.flightfinder.dao.FlightofferDAO;



/**
 * This is CamelRoute that pulls the best flight offers once a day from the DB and send them to a list of subscribers.
 *
 */
@Component
public class NewsletterMailRoute extends RouteBuilder {

	@Autowired
	EnrichWithSubscribers enrichWithSubscribers;
	@Autowired
	FlightofferDAO flightOfferDAO;
	@Override
	public void configure() throws Exception {

		from("timer:newsletter?period=60000"). //can be set to specific time "time=yyyy-MM-dd HH:mm:ss" or just set the period to one day "period=86400000"
		routeId("Route-Newsletter").
		log("--------------------timer fired..--------------------------------").
		bean(flightOfferDAO , "getTodaysFlightoffers").id("flightOfferBean").
		split(body()).
		process(enrichWithSubscribers).
		to("velocity:file:data/newsletter.vm").id("velocityTemplate").

		to("smtp://188.40.32.121?username=workflow@seferovic.net&password=workflowpassword&contentType=text/html").
		log("-------------------FINISHED--------------------------------------");
			

	}

}
