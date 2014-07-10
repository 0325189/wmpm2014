package at.tuwien.flightfinder.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import at.tuwien.flightfinder.beans.Archive;
import at.tuwien.flightfinder.beans.PrintFlightoffer;
import at.tuwien.flightfinder.dao.FlightofferDAO;
import at.tuwien.flightfinder.pojo.Flightoffer;


@Component
public class OfferProcessingRoute extends RouteBuilder {

	@Autowired
	Archive archive;
	@Autowired
	FlightofferDAO flightOfferDAO;
	@Override
	public void configure() throws Exception {

		from("activemq:Offers").
		routeId("Route-OfferProcess").
		log("Message has been pulled from Offers queue").
		filter().method(flightOfferDAO, "lookupEuropeanIata").
		process(new PrintFlightoffer()).
		log("Message has been filtered and is being pushed to ernicher").
		
//		process(new OffersEnricher()).
//		log("Message has been eriched with hotels and is being pushed to enricher").
//		wireTap("file://offers_archive?fileName=${date:now:yyyy-MM-dd}.xml&fileExist=Append").
		bean(archive).
		log("Message has been stored using WireTap");
	
	}

}
