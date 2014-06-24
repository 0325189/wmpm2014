package at.tuwien.flightfinder.test;


import java.util.Date;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.spring.javaconfig.SingleRouteCamelConfiguration;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.apache.camel.test.spring.CamelSpringDelegatingTestContextLoader;
import org.apache.camel.test.spring.CamelSpringJUnit4ClassRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import at.tuwien.flightfinder.pojo.Airport;
import at.tuwien.flightfinder.pojo.FlightClass;
import at.tuwien.flightfinder.pojo.Flightoffer;
import at.tuwien.flightfinder.routes.TwitterFacebookRoute;
import at.tuwien.flightfinder.test.config.TestConfig;


@RunWith(CamelSpringJUnit4ClassRunner.class)
@ContextConfiguration(
		classes = {TestConfig.class, TwitterFacebookRouteTest.SpecificTestConfig.class},
        loader = CamelSpringDelegatingTestContextLoader.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("testing")
public class TwitterFacebookRouteTest extends CamelTestSupport {
	
	@Autowired
    private CamelContext camelContext;
	
	@Override
    public boolean isUseAdviceWith() {
        return true;
    }
	
	@Override
    protected CamelContext createCamelContext() throws Exception {
        return camelContext;
    }
	
	@Configuration
    public static class SpecificTestConfig extends SingleRouteCamelConfiguration {
       
		@Autowired
        private TwitterFacebookRoute TwitterFacebookRoute;

        @Bean
        public RouteBuilder route() {
            return TwitterFacebookRoute;
        }
    }
	
	
	@Test
	public void testSocialMarketing() throws Exception
	{
		camelContext.getRouteDefinitions().get(0).adviceWith(context, new AdviceWithRouteBuilder() {
			
			@Override
			public void configure() throws Exception {
				replaceFromWith("seda:test");
				weaveById("MarketingProcessor").remove();
				interceptSendToEndpoint("twitter:*")
				.skipSendToOriginalEndpoint()
				.to("mock:twitter");
				interceptSendToEndpoint("facebook:*")
				.skipSendToOriginalEndpoint()
				.to("mock:facebook");
			}
		});
		
		camelContext.start();
		
		Airport fromAirport = new Airport("MAD");
		Airport toAirport = new Airport("CDG");
		
		
		Flightoffer fo = new Flightoffer();
		fo.setFromAirport(fromAirport);
		fo.setToAirport(toAirport);
		fo.setAirCompany("Lufthansa");
		fo.setFlightClass(FlightClass.Business);
		fo.setPrice(99);
		fo.setTicketId("12345");
		fo.setNameDestination("Paris");
		fo.setNameOrigin("Madrid");
		fo.setFlightDate(new Date());
		

		template.sendBody("seda:test", fo);
	
		getMockEndpoint("mock:twitter").expectedMessageCount(1);
		getMockEndpoint("mock:twitter").expectedBodiesReceived(fo);
		
		getMockEndpoint("mock:facebook").expectedMessageCount(1);
		getMockEndpoint("mock:facebook").expectedBodiesReceived(fo);
		
		assertMockEndpointsSatisfied();
		
		context.stop();
	}
}
