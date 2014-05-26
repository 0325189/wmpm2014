package at.tuwien.flightfinder.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
import org.apache.camel.component.hazelcast.*;

import at.tuwien.flightfinder.beans.EuropeanFlightsFilter;
import at.tuwien.flightfinder.beans.OffersEnricher;


@Component
public class OfferProcessingRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {

		from("activemq:Offers").
		log("${body}").
		log("Message has been pulled from Offers queue").
		filter(new EuropeanFlightsFilter()).
		process(new OffersEnricher()).

		//to("activemq:FilterOffer").
		log("${body}").
		log("Filtered messages beeing pushed to FilterOffer Queue").
		//.to("activemq:EnrichedOffer")
		to("file:mojTest?fileName=test.xml");


		//.setHeader(HazelcastConstants.OPERATION, constant(HazelcastConstants.PUT_OPERATION))
		//.toF("hazelcast:seda:promotionQueue", HazelcastConstants.QUEUE_PREFIX);

	}

}
