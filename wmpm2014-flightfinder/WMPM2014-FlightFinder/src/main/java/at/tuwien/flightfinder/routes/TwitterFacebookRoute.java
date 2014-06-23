package at.tuwien.flightfinder.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.twitter.TwitterComponent;
import org.springframework.stereotype.Component;




import twitter4j.TwitterException;
import facebook4j.FacebookException;
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
		private String oAuthAccessToken = "&oAuthAccessToken=CAACEdEose0cBAIdEDDtc5546tvQhOz6Tn7ANkiuEzlTUGvOoytkK2aqyc8UOGKohKlxhnWImIXirauZAabRdXjpeHfZBObNy62pA4f8zWyZBpeDHvTaQSIvlQDYWZBaxKlJaDNjwPZC3P5rksGx1rLgxEtuQdNIFM2Mf6tRirQ1OqsaOfiYjSr9EMh7pK0vIZD";
		private String facebookEndpoint = "facebook://postStatusMessage?inBody=message" + userId + oAuthAppId + oAuthAppSecret + oAuthAccessToken;
		
	@Override
	public void configure() throws Exception
	{
		TwitterComponent tc  = getContext().getComponent("twitter", TwitterComponent.class);
		tc.setAccessToken(twitterAccessToken);
        tc.setAccessTokenSecret(twitterAccessTokenSecret);
        tc.setConsumerKey(twitterConsumerKey);
        tc.setConsumerSecret(twitterConsumerSecret);
   
        onException(FacebookException.class).redeliveryDelay(1000).maximumRedeliveries(3).continued(true);
        onException(TwitterException.class).redeliveryDelay(1000).maximumRedeliveries(3).continued(true);
        
        from("timer:socialMarketing?period=100000")
        .process(new MarketingProcessor())
        .multicast()
        .parallelProcessing()
        .to(facebookEndpoint, twitterEndpoint)
        .log("Succesful!!!!");
        
   	}
}
