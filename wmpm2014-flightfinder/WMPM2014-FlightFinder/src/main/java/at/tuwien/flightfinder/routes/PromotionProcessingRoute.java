package at.tuwien.flightfinder.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
import org.apache.camel.component.hazelcast.*;



@Component
public class PromotionProcessingRoute extends RouteBuilder {
	
	@Override
	public void configure() throws Exception {
				
		//missing pollInterval configuration??
		fromF("hazelcast:seda:promotionQueue?pollInterval=1000", HazelcastConstants.QUEUE_PREFIX)
		   .log("An offer has been pulled from HazelcastQueue.");
		  
	}

}
