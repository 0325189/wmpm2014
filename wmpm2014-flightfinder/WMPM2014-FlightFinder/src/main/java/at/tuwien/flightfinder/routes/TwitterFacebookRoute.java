package at.tuwien.flightfinder.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.facebook.FacebookComponent;
import org.apache.camel.component.facebook.config.FacebookConfiguration;
import org.apache.camel.component.twitter.TwitterComponent;
import org.springframework.stereotype.Component;

import at.tuwien.flightfinder.beans.MarketingProcessor;

@Component
public class TwitterFacebookRoute extends RouteBuilder{

	private String facebookEndpoint = "facebook://postStatusMessage?inBody=message&userId=519619911476769" +
        "&oAuthAppId=505613226207091" +
        "&oAuthAppSecret=50a4083b2dd5d425734cb1004b839f4a" +
        "&oAuthAccessToken=CAACEdEose0cBANjgTnxDvHuvzy5iSS3z3lONZCUyrV19GZArW8sMfZAxBrZBtGqRdjOM7Y2EfyF9KSaSip682lqDQh4rJRZBFQqt6VLtZCbqvANMriSvsNRyomJHtMXag48xvlVZAAhypGF2BclQJX7ZBXdaZC0ZAk1XZA3oVHBvw1oYkTB0i9ev8baIwZAN9ERCUNwZD";
	
	private String twitterEndpoint = "twitter://timeline/user?consumerKey=hIYlzKUmLbuU89ZcpweGu0v4k" +
            "&consumerSecret=UP823D2If4WzOdsvBL9IQe3PjHHFm20eB2gTTJ7oXRjCDhqaLj" +
            "&accessToken=359320158-jHPvE5UWTybd5vXv0N3WmKxyI38SU1ABVReeMHpH" +
            "&accessTokenSecret=Qfe9Tn9rqo1UUKzeAHxnJ1QXbu6r1GCt7pvTOLNUWBT9A";
	
	@Override
	public void configure() throws Exception
	{
       from("timer:socialMarketing?period=100000").process(new MarketingProcessor()).multicast().parallelProcessing().to(facebookEndpoint, twitterEndpoint).to("log:Succesful!!!!");
    }

}
