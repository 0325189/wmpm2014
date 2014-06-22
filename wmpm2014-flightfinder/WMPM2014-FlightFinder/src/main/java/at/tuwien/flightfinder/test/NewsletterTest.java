
package at.tuwien.flightfinder.test;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
import at.tuwien.flightfinder.routes.NewsletterMailRoute;

@RunWith(CamelSpringJUnit4ClassRunner.class)
@ContextConfiguration(
		classes = {TestConfig.class, NewsletterTest.SpecificTestConfig.class},
		loader = CamelSpringDelegatingTestContextLoader.class
		)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("testing")
public class NewsletterTest extends CamelTestSupport {

	private static final Logger logger = Logger.getLogger(NewsletterTest.class);

	@Autowired
	private NewsletterMailRoute newsletterMailRoute;

	@Autowired
	private CamelContext camelContext;

	@Configuration
	public static class SpecificTestConfig extends SingleRouteCamelConfiguration
	{
		@Autowired
		private NewsletterMailRoute newsletterMailRoute;

		@Bean
		public RouteBuilder route()
		{
			return newsletterMailRoute;
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
		return newsletterMailRoute;
	}

	@Override
	protected CamelContext createCamelContext() throws Exception
	{
		return camelContext;
	}


	//Tests
	@Test
	public void testSendingNewsletterToEmail() throws Exception
	{
		context.getRouteDefinitions().get(0).adviceWith(context, new AdviceWithRouteBuilder() {

			@Override
			public void configure() throws Exception {
				//consumes from this endpoint
				replaceFromWith("velocity:file:mojTest/newsletter.vm");
				interceptSendToEndpoint("smtp:*")
				.skipSendToOriginalEndpoint()
				.to("mock:newsletter");

			}
		});

		context.start();

		getMockEndpoint("mock:newsletter").expectedMessageCount(1);

		String body = "Haloworld Body";

		Map<String, Object> mailHeader = new HashMap<String, Object>();
		mailHeader.put("To", "martin.skakala@alanova.at");
		mailHeader.put("From", "FlightFinder <workflow@seferovic.net>");
		mailHeader.put("Subject", "FlightFinder -- Best offres of the day!");

		template.setDefaultEndpointUri("mock:newsletter");
		template.sendBodyAndHeader("mock:newsletter",body,mailHeader);

		assertMockEndpointsSatisfied();

		context.stop();
	}

}
