package at.tuwien.flightfinder.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
import org.apache.camel.component.hazelcast.*;

import at.tuwien.flightfinder.beans.Archive;
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
		wireTap("file://offers_archive?fileName=${date:now:yyyy-MM-dd}.xml&fileExist=Append").
		bean(new Archive()).
		log("Message has been stored using WireTap");
	
	}

}
