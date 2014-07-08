package at.tuwien.flightfinder.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import at.tuwien.flightfinder.beans.FtpRouteLogger;

@Component
public class FtpRouteConfig extends RouteBuilder{
	
	@Override
	public void configure() throws Exception {	
		//from("file:mojTest?fileName=FlightFinder_FlightsDetailsInfo_v4MAC.csv&noop=true").
		from("ftp://ftp6291381_workflow@www92.world4you.com?password=workflow2014&consumer.delay=60000&charset=ISO-8859-1").
		routeId("Route-FTP").
		log("Downloading File: ${header.CamelFileName} from the FTP Server").
		//what about routing the faulty file to dead queue?? 
		validate(header("CamelFileName").regex("^[^ÜüÄäÖö]+$")).
		log("The file ${header.CamelFileName} has been validated for german special character").
		to("activemq:fileOffers").
		log("${header.CamelFileName} has been pushed to the fileOffers queue!");
	}
}
