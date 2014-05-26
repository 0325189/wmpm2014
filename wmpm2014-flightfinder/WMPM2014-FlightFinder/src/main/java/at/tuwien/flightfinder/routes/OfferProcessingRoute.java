package at.tuwien.flightfinder.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
import org.apache.camel.component.hazelcast.*;

import at.tuwien.flightfinder.beans.EuropeanFlightsFilter;


@Component
public class OfferProcessingRoute extends RouteBuilder {

	//EuropeanFlightsFilter FlightFinderFilteringRules = new EuropeanFlightsFilter();

	@Override
	public void configure() throws Exception {

		from("activemq:Offers").
		log("${body}").
		log("Message has been pulled from Offers queue").
		filter(new EuropeanFlightsFilter()).
		to("activemq:FilterOffer").
		log("${body}").
		log("Filtered messages beeing pushed to FilterOffer Queue")
//		.pollEnrich("activemq:Offers",new AggregationStrategy(){
//			public Exchange aggregate(Exchange oldExchange,Exchange newExchange) {
//				if (newExchange == null) {
//					return oldExchange;
//				}
//
//				String fromQueue = oldExchange.getIn()
//						.getBody(String.class);
//				String fromDB = newExchange.getIn()
//						.getBody(String.class);
//				//find the airport destination in the old message, check the parser in filter class
//				//retrieve getFlightofferById(int offerId) and save it(I need FlightofferID for that, how to get it??)
//				//use getI
//				String body = fromQueue + "\n" + fromDB;
//				oldExchange.getIn().setBody(body);
//				return oldExchange;
//			}
//		})
		//.to("activemq:EnrichedOffer")
		//.wireTap("jms:orderAudit")//use hibernate instead of jms
		.setHeader(HazelcastConstants.OPERATION, constant(HazelcastConstants.PUT_OPERATION))
		.toF("hazelcast:seda:promotionQueue", HazelcastConstants.QUEUE_PREFIX);
	}

}
