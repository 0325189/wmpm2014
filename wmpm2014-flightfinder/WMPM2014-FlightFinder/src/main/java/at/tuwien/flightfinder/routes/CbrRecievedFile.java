package at.tuwien.flightfinder.routes;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
import org.apache.camel.model.dataformat.CsvDataFormat;
import org.apache.camel.model.dataformat.XStreamDataFormat;
import org.apache.camel.spi.DataFormat;
import org.apache.commons.csv.writer.CSVConfig;
import org.springframework.stereotype.Component;

@Component
public class CbrRecievedFile extends RouteBuilder{
	@Override
	public void configure() throws Exception {	

		DataFormat bindy = new BindyCsvDataFormat("at.tuwien.flightfinder.beans");

		
		from("activemq:fileOffers").
		choice().
			when(header("CamelFileName").endsWith(".xml")).
				log("XML file found on CBR: ${header.CamelFileName}").
				split().tokenizeXML("Flight").streaming().
				log("XML file ${header.CamelFileName} has been splitted.").
				to("activemq:Offers").endChoice().
			when(header("CamelFileName").regex("^.*(csv|csl)$")).
				log("CSV file found on CBR: ${header.CamelFileName}").
				unmarshal(bindy).
				marshal().
				xstream().
				to("xslt:file:mojTest/xslt.xsl").
				split().tokenizeXML("Flight").
				//to("file:mojTest?fileName=test.xml").endChoice().
				log("${body}").
				log("Message has been pushed to OffersQueue").
				to("activemq:Offers").endChoice().
			otherwise().to("activemq:badMessage")
		.end();

	}
}
