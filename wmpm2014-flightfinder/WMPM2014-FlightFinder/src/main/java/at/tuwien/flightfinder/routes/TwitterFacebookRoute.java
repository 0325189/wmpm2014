package at.tuwien.flightfinder.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.facebook.FacebookComponent;
import org.apache.camel.component.facebook.config.FacebookConfiguration;
import org.apache.camel.component.twitter.TwitterComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import twitter4j.TwitterException;
import facebook4j.FacebookException;
import at.tuwien.flightfinder.beans.MarketingProcessor;

@Component
public class TwitterFacebookRoute extends RouteBuilder{

	@Autowired
    Environment prop;
	
	private String twitterEndpoint = "twitter://timeline/user";
	private String facebookEndpoint = "facebook://postStatusMessage?inBody=message";

	@Override
	public void configure() throws Exception
	{
        loadFacebookKeys();
        
        //onException(FacebookException.class).redeliveryDelay(60000).maximumRedeliveries(3).continued(true);
        //onException(TwitterException.class).redeliveryDelay(60000).maximumRedeliveries(3).continued(true);
        
        	from("timer:socialMarketing?period=86400000"). //can be set to specific time "time=yyyy-MM-dd HH:mm:ss" or just set the period to one day "period=86400000"	
        	routeId("Route-Social").
        	log("timer fired..").
        	process(new MarketingProcessor()).id("MarketingProcessor").
        	multicast().parallelProcessing().
        	to(facebookEndpoint, twitterEndpoint).
        	to("log:Succesful!!!!");
      
   	}
	
	private void loadFacebookKeys() {
		facebookEndpoint += "&userId=" + prop.getProperty("FlightFinder_userId");
		facebookEndpoint += "&oAuthAppId=" + prop.getProperty("FlightFinder_oAuthAppId");
		facebookEndpoint += "&oAuthAppSecret=" + prop.getProperty("FlightFinder_oAuthAppSecret");
		facebookEndpoint += "&oAuthAccessToken=" + prop.getProperty("FlightFinder_oAuthAccessToken");

    }
	

}
