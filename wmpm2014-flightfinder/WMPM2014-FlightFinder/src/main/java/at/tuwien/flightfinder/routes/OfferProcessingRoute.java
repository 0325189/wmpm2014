package at.tuwien.flightfinder.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import at.tuwien.flightfinder.beans.Archive;
import at.tuwien.flightfinder.beans.EuropeanFlightsFilter;
import at.tuwien.flightfinder.beans.OffersEnricher;
import at.tuwien.flightfinder.pojo.Flightoffer;


@Component
public class OfferProcessingRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {

		from("activemq:Offers").
		//log("${body}").
		routeId("Route-OfferProcess").
		log("Message has been pulled from Offers queue").split(body()).
		log("------${body}").
		//filter().xpath("//Flight").
		filter().method(Flightoffer.class, "isEuropean").
		log("Message has been filtered and is being pushed to ernicher");
		
//		process(new OffersEnricher()).
//		log("Message has been eriched with hotels and is being pushed to enricher").
//		wireTap("file://offers_archive?fileName=${date:now:yyyy-MM-dd}.xml&fileExist=Append").
//		bean(new Archive()).
//		log("Message has been stored using WireTap");
	
	}

}
