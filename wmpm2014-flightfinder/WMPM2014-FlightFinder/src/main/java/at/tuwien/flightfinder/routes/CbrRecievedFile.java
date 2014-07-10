package at.tuwien.flightfinder.routes;

import java.util.List;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.apache.camel.spi.DataFormat;
import org.springframework.stereotype.Component;

import at.tuwien.flightfinder.beans.PrintFlightoffer;
import at.tuwien.flightfinder.pojo.Flightoffer;




@Component
public class CbrRecievedFile extends RouteBuilder{
	@Override
	public void configure() throws Exception {	

		DataFormat bindy = new BindyCsvDataFormat("at.tuwien.flightfinder.pojo");
		DataFormat jaxb = new JaxbDataFormat("at.tuwien.flightfinder.pojo");

		from("activemq:fileOffers").
		routeId("Route-CBR").
		choice().
		when(header("CamelFileName").regex("^.*(xml)$")).
		log("XML file found on CBR: ${header.CamelFileName}").
		split().tokenizeXML("Flight").log("---before---${body}").unmarshal(jaxb).setHeader("iataCode", simple("${in.body.fromIataCode}")).process(new PrintFlightoffer()).
		log("XML file ${header.CamelFileName} has been splitted.").
		to("activemq:Offers").
		log("-------------Finished XML---------").
		endChoice().

		when(header("CamelFileName").regex("^.*(csv|csl)$")).
		log("CVS file found on CBR: ${header.CamelFileName}").
		split(body().tokenize(";")).log("---before---${body}").
		log("CSV file ${header.CamelFileName} has been splitted.").
		unmarshal(bindy).process(new Processor() {


			/*The Bindy component returned is a List of Map objects. Each Map within the list contains
			the model objects that were marshalled out of each line of the CSV. The reason behind
			this is that each line can correspond to more than one object.
			When we simply expect one object to be returned per line we need to use the processor specified in the 
			camel Bindy documentation.
			 */

			@Override
			public void process(Exchange exchange) throws Exception{
				List<Map<String, Object>> unmarshaledModels = (List<Map<String, Object>>) exchange.getIn().getBody();
				Message msg = exchange.getIn();
				for (Map<String, Object> model : unmarshaledModels) {
					for (String className : model.keySet()) {
						Object obj = model.get(className);
						msg.setBody(obj);
					}
				}
				exchange.setOut(msg);

			}
		}).
		log("CSV file ${header.CamelFileName} has been unmarshaled into Flighfinder POJO.").
		
		
		//setHeader("iataCode").simple("${body.fromIataCode}")  --> if not working try this!
		to("activemq:Offers").setHeader("iataCode", simple("${body.fromIataCode}")).process(new PrintFlightoffer()).
		log("-------------Finished CSV---------").endChoice().
		otherwise().to("activemq:badMessage")
		.end();

	}
}
