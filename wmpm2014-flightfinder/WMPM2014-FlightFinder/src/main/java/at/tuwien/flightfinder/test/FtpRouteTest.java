package at.tuwien.flightfinder.test;

import org.apache.camel.CamelExecutionException;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.processor.validation.PredicateValidationException;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.apache.log4j.Logger;
import org.junit.Test;

import at.tuwien.flightfinder.pojo.Flightoffer;
import at.tuwien.flightfinder.routes.FtpRouteConfig;
import at.tuwien.flightfinder.routes.NewsletterMailRoute;

public class FtpRouteTest extends CamelTestSupport {

	private static final Logger logger = Logger.getLogger(NewsletterTest.class);

	@Override
	protected RouteBuilder createRouteBuilder() throws Exception 
	{
		return new FtpRouteConfig();
	}




	//Tests
	@Test
	public void testFtpRoute() throws Exception
	{
		context.getRouteDefinitions().get(0).adviceWith(context, new AdviceWithRouteBuilder() {

			@Override
			public void configure() throws Exception {
				//consumes from this endpoint
				replaceFromWith("seda:ftpTest");
				interceptSendToEndpoint("activemq:*")
				.skipSendToOriginalEndpoint()
				.to("mock:activemq");

			}
		});

		context.start();

        
		MockEndpoint activemq= getMockEndpoint("mock:activemq");
		activemq.expectedMessageCount(1);
		activemq.expectedBodiesReceived("FTP Test");
		template.sendBodyAndHeader("seda:ftpTest", "FTP Test", "CamelFileName", "offer.xml");
		assertMockEndpointsSatisfied();

		context.stop();
	}
	@Test
	public void testFtpRouteUnsupertedFileName() throws Exception
	{
		context.getRouteDefinitions().get(0).adviceWith(context, new AdviceWithRouteBuilder() {

			@Override
			public void configure() throws Exception {
				//consumes from this endpoint
				replaceFromWith("seda:ftpTest");
				interceptSendToEndpoint("activemq:*")
				.skipSendToOriginalEndpoint()
				.to("mock:activemq");

			}
		});

		
		context.start();
		MockEndpoint activemq= getMockEndpoint("mock:activemq");
		activemq.expectedMessageCount(0);
		template.sendBodyAndHeader("seda:ftpTest", "FTP Test", "CamelFileName", "öffer.xml");
		
		activemq.assertIsSatisfied();
		context.stop();

		


	}

}
