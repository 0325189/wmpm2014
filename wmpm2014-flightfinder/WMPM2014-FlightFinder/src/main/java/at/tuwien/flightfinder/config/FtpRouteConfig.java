package at.tuwien.flightfinder.config;

import org.apache.camel.builder.RouteBuilder;



public class FtpRouteConfig extends RouteBuilder{
	@Override
	public void configure() throws Exception {
		from("ftp://www92.world4you.com?username=ftp6291381_workflow&password=workflow2014?noop=true");
	}


}
