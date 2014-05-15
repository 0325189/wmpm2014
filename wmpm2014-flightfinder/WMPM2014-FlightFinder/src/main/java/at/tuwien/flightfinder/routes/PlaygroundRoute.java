package at.tuwien.flightfinder.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

/**
 * PlaygroundRoute is an example class how to annotate a class that contains a route for Camel.
 * Class needs to extend RouteBuilder and therefore needs a configure() method 
 * where the route rule MUST be written using Java DSL ( from().to().end() )
 *  
 * TODO: Make a copy of this class/file and play around with Camel components
 * @author seferovic
 *
 */
@Component
public class PlaygroundRoute extends RouteBuilder {

	
            public void configure() {
                // you can configure the route rule with Java DSL here
            	from("file:src/data?noop=true").
            		 	bean(new SomeBean()).
            		 	//to("file://target/test?noop=true");
            		 	to("activemq:testQueue");
            	
            	from("activemq:testQueue").
            		bean(new SomeOtherBean()).
            		to("file:src/data/output?noop=true");
               
            }
            
            public static class SomeBean {

                public void someMethod(String body) {
                    System.out.println("Received: " + body);
                }

            }
            
            public static class SomeOtherBean {

                public void someMethod(String body) {
                    System.out.println("Received from QUEUE: " + body);
                }

            }

                
}
