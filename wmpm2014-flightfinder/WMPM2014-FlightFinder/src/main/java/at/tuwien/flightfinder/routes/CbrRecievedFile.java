package at.tuwien.flightfinder.routes;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
import org.apache.camel.model.dataformat.CsvDataFormat;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.apache.camel.model.dataformat.XStreamDataFormat;
import org.apache.camel.spi.DataFormat;
import org.apache.commons.csv.writer.CSVConfig;
import org.springframework.stereotype.Component;

import at.tuwien.flightfinder.beans.PrintFlightoffer;

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
		split().tokenizeXML("Flight").unmarshal(jaxb).process(new PrintFlightoffer()).
		log("XML file ${header.CamelFileName} has been splitted.").
		to("activemq:OffersXml").
		log("-------------Finished XML---------").
		endChoice().

		when(header("CamelFileName").regex("^.*(csv|csl)$")).
		log("CVS file found on CBR: ${header.CamelFileName}").
		split(body().tokenize(";")).streaming().log("---before---${body}").
		log("CSV file ${header.CamelFileName} has been splitted.").
		unmarshal(bindy).log("---after---${body}").
		log("CSV file ${header.CamelFileName} has been unmarshaled into Flighfinder POJO.").
		to("activemq:Offers").
		log("-------------Finished CSV---------").endChoice().
		otherwise().to("activemq:badMessage")
		.end();
		

	}
}
