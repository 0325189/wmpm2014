package at.tuwien.flightfinder.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.xmljson.XmlJsonDataFormat;
import org.springframework.stereotype.Component;

import at.tuwien.flightfinder.beans.HttpRouteLogger;

@Component
public class HttpRouteConfig extends RouteBuilder {
	XmlJsonDataFormat xmlJsonFormat = new XmlJsonDataFormat();
	@Override
	public void configure() throws Exception {	
		from("stream:url?url=http://www.tesla-gui.at/http/offers.json")
		//.unmarshal(xmlJsonFormat)
		.process(new HttpRouteLogger())
		.to("activemq:fileOffers");
	}

}
