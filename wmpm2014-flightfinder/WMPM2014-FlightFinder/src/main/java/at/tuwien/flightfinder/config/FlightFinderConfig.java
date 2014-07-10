package at.tuwien.flightfinder.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.camel.component.ActiveMQComponent;
import org.apache.camel.CamelContext;
import org.apache.camel.component.twitter.TwitterComponent;
import org.apache.camel.component.twitter.TwitterConfiguration;
import org.apache.camel.spring.SpringCamelContext;
import org.apache.camel.spring.javaconfig.CamelConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * This is where SpringCamelContext is configured WITHOUT any Spring XML stuff :)
 * ComponentScan will scan for all classes annotated with "Component" in the defined 
 * package. Every class containing a Camel route should be annotated and extend RouteBuilder
 */

@Configuration
@Profile("production")
@PropertySource(value = {"classpath:facebook4j.properties", "classpath:twitter4j.properties"})
@ComponentScan("at.tuwien.flightfinder")
@EnableTransactionManagement
@ImportResource({ "classpath:spring-config.xml" })
public class FlightFinderConfig extends CamelConfiguration {
	
	@Autowired
    Environment prop;
	
	private String twitterEndpoint = "twitter://timeline/user";

	/**
    * Returns the CamelContext which support Spring
    */
	@Override
	protected CamelContext createCamelContext() throws Exception {
		return new SpringCamelContext(getApplicationContext());
	}
	
	@Override
    protected void setupCamelContext(CamelContext camelContext) throws Exception {
		
		// THERE IS NO OTHER WAY TO 
		
		// Set up CamelContext
		// Add ActiveMQ component
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("vm://localhost");
		ActiveMQComponent activeMQcomp = new ActiveMQComponent();
		activeMQcomp.setConnectionFactory(connectionFactory);
		camelContext.addComponent("activemq", activeMQcomp);
		
		System.out.println("Twitter Proper:" + prop.getProperty("FlightFinder_oauth.accessToken"));
		
		TwitterComponent twitter = camelContext.getComponent("twitter", TwitterComponent.class);
		
		twitter.setAccessToken(prop.getProperty("FlightFinder_oauth.accessToken"));
		twitter.setAccessTokenSecret(prop.getProperty("FlightFinder_oauth.accessTokenSecret"));
		twitter.setConsumerKey(prop.getProperty("FlightFinder_oauth.consumerKey"));
		twitter.setConsumerSecret(prop.getProperty("FlightFinder_oauth.consumerSecret"));
    
    }
	


}
