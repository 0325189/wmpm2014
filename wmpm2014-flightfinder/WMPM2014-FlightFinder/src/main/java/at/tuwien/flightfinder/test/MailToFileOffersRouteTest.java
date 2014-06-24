package at.tuwien.flightfinder.test;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;

import org.apache.camel.CamelContext;
import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Producer;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
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
import at.tuwien.flightfinder.routes.MailToFileOffersRoute;
import at.tuwien.flightfinder.test.config.TestConfig;


@RunWith(CamelSpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = {TestConfig.class, MailToFileOffersRouteTest.SpecificTestConfig.class},
        loader = CamelSpringDelegatingTestContextLoader.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("testing")
public class MailToFileOffersRouteTest extends CamelTestSupport {

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
        private MailToFileOffersRoute MailToFileOffersRoute;

        @Bean
        public RouteBuilder route() {
            return MailToFileOffersRoute;
        }
    }
	
	
	@Test  
	public void testMailRoute() throws Exception
	{
		 camelContext.getRouteDefinitions().get(0).adviceWith(context, new AdviceWithRouteBuilder() {
				
				@Override
				public void configure() throws Exception {
					
					interceptSendToEndpoint("activemq:fileOffers")
					.skipSendToOriginalEndpoint()
					.to("mock:test");
					
				}
			});
		 
		// create an exchange with a normal body and attachment to be produced as email  
	     Endpoint endpoint = camelContext.getEndpoint("smtp://188.40.32.121?username=workflow@seferovic.net&password=workflowpassword");  
	     
	     // create the exchange with the mail message   
	     Exchange exchange = endpoint.createExchange();  
	     Message in = exchange.getIn();  
	     in.setBody("Test Flight Offer with CSV Attachment");  
	     in.addAttachment("FlightFinder_FlightsDetailsInfo_v4.csv", new DataHandler(new FileDataSource("src/data/FlightFinder_FlightsDetailsInfo_v4.csv")));  
	     in.addAttachment("FlightFinder_FlightsDetailsInfo_v2.xml", new DataHandler(new FileDataSource("src/data/FlightFinder_FlightsDetailsInfo_v2.xml")));  
	     
	     Map<String, Object> map = new HashMap<String, Object>();
	     map.put("To", "workflow@seferovic.net");
	     map.put("From", "workflow@seferovic.net");
	     map.put("Subject", "jUnit MailRouteTest");
	     
	     // set headers for the message and at the same time for the eMail message
	     in.setHeaders(map);	     
	     
	     // create a producer that can produce the exchange (= send the mail)  
	     Producer producer = endpoint.createProducer();  
	     
	     // start the producer  
	     producer.start();  
	     
	     // and let it go (processes the exchange by sending the email)  
	     producer.process(exchange);  
	     
	     // need some time for the mail to arrive on the inbox (consumed and sent to the mock)  
	     Thread.sleep(5000);
	     
	     producer.stop();  
		 
	     camelContext.start();

	     MockEndpoint mailTest = getMockEndpoint("mock:test");
	 		
//	 	 mailTest.expectedHeaderReceived("CamelFileName", "FlightFinder_FlightsDetailsInfo_v4.csv");
//	 	 mailTest.expectedHeaderReceived("CamelFileName", "FlightFinder_FlightsDetailsInfo_v2.xml");
	 	 mailTest.expectedMessageCount(2);
	 	 mailTest.expectedHeaderValuesReceivedInAnyOrder("CamelFileName", "FlightFinder_FlightsDetailsInfo_v4.csv","FlightFinder_FlightsDetailsInfo_v2.xml");
		 
		 assertMockEndpointsSatisfied();
	}
}
