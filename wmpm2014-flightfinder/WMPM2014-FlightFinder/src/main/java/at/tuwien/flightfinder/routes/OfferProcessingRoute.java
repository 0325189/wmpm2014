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
		log("Message has been filtered and is being pushed to ernicher").
		process(new OffersEnricher()).
		log("Message has been eriched with hotels and is being pushed to enricher").
		to("file:mojTest?fileName=test.xml"). //just for testing purpose!
		//.wireTap
		log("Message has been stored using WireTap");
		//.setHeader(HazelcastConstants.OPERATION, constant(HazelcastConstants.PUT_OPERATION))
		//.toF("hazelcast:seda:promotionQueue", HazelcastConstants.QUEUE_PREFIX);
		//log("Message has been pushed into Hazelcast");
	}

}
