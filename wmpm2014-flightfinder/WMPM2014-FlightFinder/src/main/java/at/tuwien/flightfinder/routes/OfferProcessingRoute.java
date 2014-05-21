package at.tuwien.flightfinder.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import at.tuwien.flightfinder.beans.EuropeanFlightsFilter;


@Component
public class OfferProcessingRoute extends RouteBuilder {

	EuropeanFlightsFilter FlightFinderFilteringRules = new EuropeanFlightsFilter();
	
	@Override
	public void configure() throws Exception {
		
		from("activemq:Offers")
		.filter(FlightFinderFilteringRules)
		
		.to("activemq:FilterOffer");
		
	}

}
