
package at.tuwien.flightfinder.test;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
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
import at.tuwien.flightfinder.dao.FlightofferDAO;
import at.tuwien.flightfinder.pojo.Airport;
import at.tuwien.flightfinder.pojo.FlightClass;
import at.tuwien.flightfinder.pojo.Flightoffer;
import at.tuwien.flightfinder.routes.NewsletterMailRoute;


public class NewsletterTest extends CamelTestSupport {

	private static final Logger logger = Logger.getLogger(NewsletterTest.class);

	@Override
	protected RouteBuilder createRouteBuilder() throws Exception 
	{
		return new NewsletterMailRoute();
	}



	//Tests
	@Test
	public void testSendingNewsletterToEmail() throws Exception
	{
		context.getRouteDefinitions().get(0).adviceWith(context, new AdviceWithRouteBuilder() {

			@Override
			public void configure() throws Exception {
				replaceFromWith("seda:newsletterStart");
				//skip velocity template and bean that pulls from DB
				weaveById("flightOfferBean").remove();
				weaveById("velocityTemplate").remove();
				interceptSendToEndpoint("smtp:*").skipSendToOriginalEndpoint().to("mock:newsletter");

			}
		});

		context.start();
		
		//Mock Flightoffers
		Flightoffer fo1 = new Flightoffer( new Airport("MAD"), new Airport("CDG"), "Madrid", "Paris", "Lufthansa", "2439823GDS", new Date(), "123", 234, FlightClass.Economy);
		Flightoffer fo2 = new Flightoffer( new Airport("MAD"), new Airport("VIE"), "Madrid", "Vienna", "Lufthansa", "2439823GDS", new Date(), "322", 234, FlightClass.Economy);

		Flightoffer fo3 = new Flightoffer( new Airport("MAD"), new Airport("CDG"), "Madrid", "Paris", "Lufthansa", "2439823GDS", new Date(), "123", 234, FlightClass.Economy);
		Flightoffer fo4 = new Flightoffer( new Airport("MAD"), new Airport("VIE"), "Madrid", "Vienna", "Lufthansa", "2439823GDS", new Date(), "342", 234, FlightClass.Economy);

		List<Flightoffer> list1 = new ArrayList<Flightoffer>();
		List<Flightoffer> list2 = new ArrayList<Flightoffer>();
		list1.add(fo1);
		list1.add(fo2);
		list2.add(fo3);
		list2.add(fo4);

		List<List<Flightoffer>> listOfList = new ArrayList<List<Flightoffer>>();
		listOfList.add(list1);
		listOfList.add(list2);

		//Mock Header
		Map<String, Object> mailHeader = new HashMap<String, Object>();
		mailHeader.put("To", "e1128233@student.tuwien.ac.at");
		mailHeader.put("From", "FlightFinder <workflow@seferovic.net>");
		mailHeader.put("Subject", "FlightFinder -- Best offres of the day!");
		
		//Send body and header to the route
		template.sendBodyAndHeaders("seda:newsletterStart", listOfList, mailHeader);

		//check if asserted
		getMockEndpoint("mock:newsletter").expectedMessageCount(2);
		MockEndpoint newsletter = getMockEndpoint("mock:newsletter");
		newsletter.expectedBodiesReceived(listOfList);
		newsletter.expectedHeaderReceived("Subject", "FlightFinder -- Best offres of the day!");
		newsletter.expectedHeaderReceived("From", "FlightFinder <workflow@seferovic.net>");
		newsletter.expectedHeaderReceived("To", "martin.skakala@alanova.at");

		
		
		assertMockEndpointsSatisfied();
		
		context.stop();
	}

}
