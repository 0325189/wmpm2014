package at.tuwien.flightfinder.config;

import org.apache.camel.CamelContext;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.spring.SpringCamelContext;
import org.apache.camel.spring.javaconfig.CamelConfiguration;
import org.apache.camel.spring.javaconfig.Main;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlightFinderConfig extends CamelConfiguration implements InitializingBean {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		new Main().run(args);
		CamelContext context = new DefaultCamelContext();
		context.addRoutes(new FtpRouteConfig());
	}
	
	/**
    * Returns the CamelContext which support Spring
    */
	protected CamelContext createCamelContext() throws Exception {
		return new SpringCamelContext(getApplicationContext());
	}
	
    protected void setupCamelContext(CamelContext camelContext) throws Exception {
        // nothing here :( yet :)
    }
	
	public void afterPropertiesSet() throws Exception {
        // just to make Spring happy do nothing here
    }

}
