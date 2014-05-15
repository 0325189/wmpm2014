package at.tuwien.flightfinder.routes;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class CbrRecievedFile extends RouteBuilder{
	@Override
	public void configure() throws Exception {	
		from("activemq:fileOffers")
		.choice()
		.when(header("CamelFileName").endsWith(".xml")).log("XML file found on CBR: ${body}").split().tokenizeXML("Event", "Header").to("activemq:Offers").endChoice() 
		.when(header("CamelFileName").regex("^.*(csv|csl)$")).log("CVS file found on CBR: ${body}").split(body()).log("Message from splitter: ").to("activemq:Offers").endChoice()
		.otherwise().to("activemq:badMessage")
		.end();

	}
}

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