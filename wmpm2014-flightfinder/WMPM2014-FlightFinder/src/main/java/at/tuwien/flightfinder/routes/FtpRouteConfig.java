package at.tuwien.flightfinder.routes;

import org.apache.camel.builder.RouteBuilder;

import at.tuwien.flightfinder.config.FtpRouteLogger;


public class FtpRouteConfig extends RouteBuilder{
	@Override
	public void configure() throws Exception {	
		from("ftp://ftp6291381_workflow@www92.world4you.com?password=workflow2014&consumer.delay=60000")
		.validate(header("CamelFileName").regex("^.*(csv|csl)$"))
		.process(new FtpRouteLogger()).to("activemq:fileOffers");
	}


}
