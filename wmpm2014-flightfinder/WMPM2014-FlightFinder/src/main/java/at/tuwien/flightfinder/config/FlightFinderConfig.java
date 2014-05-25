package at.tuwien.flightfinder.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.camel.component.ActiveMQComponent;
import org.apache.camel.CamelContext;
import org.apache.camel.component.hazelcast.HazelcastComponent;
import org.apache.camel.spring.SpringCamelContext;
import org.apache.camel.spring.javaconfig.CamelConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

/**
 * This is where SpringCamelContext is configured WITHOUT any Spring XML stuff :)
 * ComponentScan will scan for all classes annotated with "Component" in the defined 
 * package. Every class containing a Camel route should be annotated and extend RouteBuilder
 * 
 */

@Configuration
@ComponentScan("at.tuwien.flightfinder")
public class FlightFinderConfig extends CamelConfiguration {
	
	/**
    * Returns the CamelContext which support Spring
    */
	@Override
	protected CamelContext createCamelContext() throws Exception {
		return new SpringCamelContext(getApplicationContext());
	}
	
	@Override
    protected void setupCamelContext(CamelContext camelContext) throws Exception {
		
		// Set up CamelContext
		
		// Add ActiveMQ component
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("vm://localhost");
		ActiveMQComponent activeMQcomp = new ActiveMQComponent();
		activeMQcomp.setConnectionFactory(connectionFactory);
		camelContext.addComponent("activemq", activeMQcomp);
				
		Config cfgHazelcast = new Config();
		HazelcastInstance hzInstance = Hazelcast.newHazelcastInstance(cfgHazelcast);
		HazelcastComponent hzComponent = new HazelcastComponent();
		hzComponent.setHazelcastInstance(hzInstance);
		camelContext.addComponent("hazelcast", hzComponent);
    
    }

}
