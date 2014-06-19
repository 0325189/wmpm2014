package at.tuwien.flightfinder.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.facebook.FacebookComponent;
import org.apache.camel.component.facebook.config.FacebookConfiguration;
import org.apache.camel.component.twitter.TwitterComponent;
import org.springframework.stereotype.Component;

import at.tuwien.flightfinder.beans.MarketingProcessor;

@Component
public class TwitterFacebookRoute extends RouteBuilder{

	//twitter oAuth properties 
	private String twitterConsumerKey = "hIYlzKUmLbuU89ZcpweGu0v4k";
	private String twitterConsumerSecret = "UP823D2If4WzOdsvBL9IQe3PjHHFm20eB2gTTJ7oXRjCDhqaLj";
	private String twitterAccessToken = "359320158-jHPvE5UWTybd5vXv0N3WmKxyI38SU1ABVReeMHpH";
	private String twitterAccessTokenSecret = "Qfe9Tn9rqo1UUKzeAHxnJ1QXbu6r1GCt7pvTOLNUWBT9A";
	private String twitterEndpoint = "twitter://timeline/user";
	
	//fb oAuth properties
	private String userId = "&userId=519619911476769";
	private String oAuthAppId = "&oAuthAppId=505613226207091";
	private String oAuthAppSecret = "&oAuthAppSecret=50a4083b2dd5d425734cb1004b839f4a";
	private String oAuthAccessToken = "&oAuthAccessToken=CAACEdEose0cBAIPZCm6QHZAIJqZAaP5xp1ECsrLLuiKYRo7inPsk26d9UBtZC6BSxaJTSDTkZCjTZBZA9uW8wRLmvzLXlWSxbZACmYcYtk87W50160BYVqAQOqMbz2JzTxZBBcMBQrdQixzYdrFtObHH9Khmd3mT5en59E3faZCj2JH5fRDAZCtaSthyWDZBWZAuhp80ZD";
	private String facebookEndpoint = "facebook://postStatusMessage?inBody=message" + userId + oAuthAppId + oAuthAppSecret + oAuthAccessToken;

	@Override
	public void configure() throws Exception
	{
		TwitterComponent tc  = getContext().getComponent("twitter", TwitterComponent.class);
		tc.setAccessToken(twitterAccessToken);
        tc.setAccessTokenSecret(twitterAccessTokenSecret);
        tc.setConsumerKey(twitterConsumerKey);
        tc.setConsumerSecret(twitterConsumerSecret);
        
        try
        {
        	from("timer:socialMarketing?period=100000").process(new MarketingProcessor()).multicast().parallelProcessing().to(facebookEndpoint, twitterEndpoint).to("log:Succesful!!!!");
        }
        catch (Exception ex)
        {
        	//Do something
        }
   	
   	}

}
