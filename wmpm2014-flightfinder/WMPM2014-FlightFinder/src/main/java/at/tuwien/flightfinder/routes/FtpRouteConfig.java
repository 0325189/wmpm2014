package at.tuwien.flightfinder.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import at.tuwien.flightfinder.beans.FtpRouteLogger;

@Component
public class FtpRouteConfig extends RouteBuilder{
	
	@Override
	public void configure() throws Exception {	
		from("ftp://ftp6291381_workflow@www92.world4you.com?password=workflow2014&consumer.delay=60000")
		.log("Downloading File: ${header.CamelFileName} from the FTP Server")
		.validate(header("CamelFileName").regex("^.*(csv|csl)$"))
		.log("Found Message: ${body}").to("activemq:fileOffers").log("${body}has been pushed to the fileOffers queue!");
		//.process(new FtpRouteLogger())
	}


}
