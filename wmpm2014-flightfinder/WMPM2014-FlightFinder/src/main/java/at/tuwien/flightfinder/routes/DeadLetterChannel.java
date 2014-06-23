package at.tuwien.flightfinder.routes;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
import org.apache.log4j.Logger;

/*
 * Just put 
 * 		errorHandler(deadLetterChannel(DeadLetterChannel.DLCH).useOriginalMessage());
 * before "from(..)." of your route.. 
 */

@Component
public class DeadLetterChannel extends RouteBuilder {

	private static final Logger log = Logger.getLogger(DeadLetterChannel.class);
	public static final String DLCH = "activemq:dead-letter";
	
	@Override
	public void configure() throws Exception {
		
		from(DLCH).log(LoggingLevel.INFO, "Message in dead letter queue");
	}
}
