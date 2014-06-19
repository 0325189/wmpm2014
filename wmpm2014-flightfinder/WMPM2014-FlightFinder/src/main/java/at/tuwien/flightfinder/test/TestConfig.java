package at.tuwien.flightfinder.test;

import org.apache.camel.component.properties.PropertiesComponent;
import org.apache.camel.spring.javaconfig.CamelConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import twitter4j.internal.logging.Logger;

@Configuration
@Profile("testing")
@ComponentScan("at.tuwien.flightfinder")
public class TestConfig extends CamelConfiguration {
	
	private static final Logger logger = Logger.getLogger(TestConfig.class);
	
	public TestConfig() {
		logger.debug("TestConfig created...");
	}
	
	@Bean
	public static PropertiesComponent properties() throws Exception {
		PropertiesComponent prop = new PropertiesComponent();
		String[] loc = {"classpath:data.properties"};
		prop.setLocations(loc);
		return prop;
	}

}
