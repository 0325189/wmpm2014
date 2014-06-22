package at.tuwien.flightfinder.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.xmljson.XmlJsonDataFormat;
import org.springframework.stereotype.Component;

import at.tuwien.flightfinder.beans.JSONStream;

@Component
public class HttpRouteConfig extends RouteBuilder {
	XmlJsonDataFormat xmlJsonFormat = new XmlJsonDataFormat();
	@Override
	public void configure() throws Exception {	
		from("stream:url?url=http://www.tesla-gui.at/http/offers.json&groupLines=100").
		routeId("Route-HTTP").
		process(new JSONStream())
		.unmarshal(xmlJsonFormat)
		.log("Found at: ${body}")
		.to("activemq:fileOffers");
	}

}
