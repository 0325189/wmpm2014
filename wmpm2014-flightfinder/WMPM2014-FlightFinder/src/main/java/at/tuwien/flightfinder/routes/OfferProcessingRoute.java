package at.tuwien.flightfinder.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
import org.apache.camel.component.hazelcast.*;

import at.tuwien.flightfinder.beans.EuropeanFlightsFilter;


@Component
public class OfferProcessingRoute extends RouteBuilder {
	
	@Override
	public void configure() throws Exception {
		
		from("activemq:Offers")
		.log("${body}").
		log("Message has been pulled from file offers queue").
		filter(new EuropeanFlightsFilter())
		
		.to("activemq:FilterOffer")
		///some code
		.setHeader(HazelcastConstants.OPERATION, constant(HazelcastConstants.PUT_OPERATION))
		.toF("hazelcast:seda:promotionQueue", HazelcastConstants.QUEUE_PREFIX);
	}

}
