package at.tuwien.flightfinder.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.camel.component.ActiveMQComponent;
import org.apache.camel.CamelContext;
import org.apache.camel.spring.SpringCamelContext;
import org.apache.camel.spring.javaconfig.CamelConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * This is where SpringCamelContext is configured WITHOUT any Spring XML stuff :)
 * ComponentScan will scan for all classes annotated with "Component" in the defined 
 * package. Every class containing a Camel route should be annotated and extend RouteBuilder
 * 
 * @author seferovic
 */

@Configuration
@ComponentScan("at.tuwien.flightfinder.routes")
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
		
		// TODO: hazelcast
    
    }

}
