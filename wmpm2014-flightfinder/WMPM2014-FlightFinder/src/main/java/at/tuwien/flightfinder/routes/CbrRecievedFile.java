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
				log("CVS file found on CBR: ${header.CamelFileName}").
				unmarshal(bindy).
				marshal().
				xstream().
				to("xslt:file:mojTest/xslt.xsl").
				split().tokenizeXML("Flight").
				//to("file:mojTest?fileName=test.xml").endChoice().
				to("activemq:Offers").endChoice().
			otherwise().to("activemq:badMessage")
		.end();

	}
}

////Some comments here
//public void doHandleCsvData(List<List<String>> csvData)
//{
// // do magic here
//}

//<route>
//<!-- poll every 10 seconds -->
//<from uri="file:///some/path/to/pickup/csvfiles?delete=true&amp;consumer.delay=10000" />
//<unmarshal><csv /></unmarshal>
//<to uri="bean:myCsvHandler?method=doHandleCsvData" />
//</route>


//Pre-process for XML
//List<List<String>> data = (List<List<String>>) exchange.getIn().getBody();
//for (List<String> line : data) {
//    LOG.debug(String.format("%s has an IQ of %s and is currently %s",
//                            line.get(0), line.get(1), line.get(2)));
//}

//XML Sample
//from(SU_NAME)
//.choice()
//    .when(STATUS_IS_OK)
//        .to("xslt:xsl/RemoveNode.xsl")
//        .split().tokenizeXML("Event", "Header")
//            .to(XP_NAME)
//        .end() /* <-- explicitly end the split here, that should help */
//    .otherwise()
//        .dynamicRouter(method(router, "slip"))
// .end(); 


//.when(body().contains("Camel"))