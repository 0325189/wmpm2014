package at.tuwien.flightfinder.test;

import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.apache.log4j.Logger;
import org.junit.Test;

import at.tuwien.flightfinder.routes.HttpRouteConfig;


public class HttpRouteTest extends CamelTestSupport {

	private static final Logger logger = Logger.getLogger(NewsletterTest.class);

	@Override
	protected RouteBuilder createRouteBuilder() throws Exception 
	{
		return new HttpRouteConfig();
	}




	//Tests
	@Test
	public void testHttpRoute() throws Exception
	{
		context.getRouteDefinitions().get(0).adviceWith(context, new AdviceWithRouteBuilder() {

			@Override
			public void configure() throws Exception {
				//consumes from this endpoint
				replaceFromWith("seda:httpTest");
				weaveById("jsonStream").remove();
				interceptSendToEndpoint("activemq:*")
				.skipSendToOriginalEndpoint()
				.to("mock:activemq");

			}
		});

		context.start();

        
		MockEndpoint activemq= getMockEndpoint("mock:activemq");
		activemq.expectedMessageCount(1);
		template.sendBodyAndHeader("seda:httpTest", "{"
  +"\"FlightsDetailsInfo\": {"
    +"\"Flight\": ["
 +" {"
    +"\"FlightNumber\":\"GW937\","
    +"\"AirCompany\":\"Germanwings\","
    +"\"IATACodeOrigin\":\"BJS\","
    +"\"NameOrigin\":\"Beijing\","
    +"\"IATACodeDestination\":\"LHR\","
    +"\"Destination\":\"London\","
    +"\"FlightDate\":20140510,"
    +"\"Class\":\"Economy\","
    +"\"TicketID\":\"GW93720140510E70\","
    +"\"Price\":105"
  +"},"
  +"{"
    +"\"FlightNumber\":\"AA254\","
    +"\"AirCompany\":\"AustriaAirlines\","
    +"\"IATACodeOrigin\":\"BJS\","
    +"\"NameOrigin\":\"Beijing\","
    +"\"IATACodeDestination\":\"LGW\","
    +"\"Destination\":\"London\","
	+"\"FlightDate\":20140510,"
	+"\"Class\":\"Business\","
    +"\"TicketID\":\"AA25420140510B20\","
    +"\"Price\":320"
  +"}"
+"]"
  +"}"
+"}", "CamelFileName", "offer.json");
		assertMockEndpointsSatisfied();

		context.stop();
	}
	@Test
	public void testHttpRouteUnsupertedFileName() throws Exception
	{
		context.getRouteDefinitions().get(0).adviceWith(context, new AdviceWithRouteBuilder() {

			@Override
			public void configure() throws Exception {
				//consumes from this endpoint
				replaceFromWith("seda:httpTest");
				weaveById("jsonStream").remove();
				interceptSendToEndpoint("activemq:*")
				.skipSendToOriginalEndpoint()
				.to("mock:activemq");

			}
		});

		
		context.start();
		MockEndpoint activemq= getMockEndpoint("mock:activemq");
		activemq.expectedMessageCount(0);
		template.sendBodyAndHeader("seda:httpTest", "http not json ", "CamelFileName", "öffer.xml");
		
		activemq.assertIsSatisfied();
		context.stop();

		


	}
}
