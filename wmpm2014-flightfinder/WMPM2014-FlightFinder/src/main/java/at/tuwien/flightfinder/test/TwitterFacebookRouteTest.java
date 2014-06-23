package at.tuwien.flightfinder.test;


import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.spring.javaconfig.SingleRouteCamelConfiguration;
import org.apache.camel.test.spring.*;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.apache.camel.test.spring.CamelSpringDelegatingTestContextLoader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import at.tuwien.flightfinder.dao.AirportDAO;
import at.tuwien.flightfinder.pojo.Airport;
import at.tuwien.flightfinder.pojo.FlightClass;
import at.tuwien.flightfinder.pojo.Flightoffer;
import at.tuwien.flightfinder.routes.TwitterFacebookRoute;

@RunWith(CamelSpringJUnit4ClassRunner.class)
@ContextConfiguration(
		classes = {TestConfig.class, TwitterFacebookRouteTest.SpecificTestConfig.class},
		loader = CamelSpringDelegatingTestContextLoader.class
		)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("testing")
public class TwitterFacebookRouteTest extends CamelTestSupport {

	private static final Logger logger = Logger.getLogger(TwitterFacebookRouteTest.class);
	
	@Autowired
	private TwitterFacebookRoute twitterFacebookRoute;
	
	@Autowired
	private CamelContext camelContext;
	
	@Configuration
	public static class SpecificTestConfig extends SingleRouteCamelConfiguration
	{
		@Autowired
		private TwitterFacebookRoute twitterFacebookRoute;
		
		@Bean
		public RouteBuilder route()
		{
			return twitterFacebookRoute;
		}
	}
	
	@Override
	public boolean isUseAdviceWith()
	{
		return true;
	}
	
	@Override
	protected RouteBuilder createRouteBuilder() throws Exception 
	{
		return twitterFacebookRoute;
	}
	
	@Override
	protected CamelContext createCamelContext() throws Exception
	{
		return camelContext;
	}
	
	
	//Tests
	@Test
	public void testSendingFlightOfferToFacebook() throws Exception
	{
		context.getRouteDefinitions().get(0).adviceWith(context, new AdviceWithRouteBuilder() {
			
			@Override
			public void configure() throws Exception {
				interceptSendToEndpoint("facebook:*")
				.skipSendToOriginalEndpoint()
				.to("mock:facebook");
				
			}
		});
		
		context.start();
		
		getMockEndpoint("mock:facebook").expectedMessageCount(1);
		//getMockEndpoint("mock:facebook").expectedBodiesReceived("");
		
		assertMockEndpointsSatisfied();
		
		context.stop();
	}
}
