package at.tuwien.flightfinder.beans;

import java.util.ArrayList;
import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;

import at.tuwien.flightfinder.pojo.Flightoffer;
import at.tuwien.flightfinder.pojo.Hotel;

public class HotelAggregationStrategy implements AggregationStrategy {
	
	List<Hotel> hotelList = new ArrayList<Hotel>();
	
	@Override
	public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
		List<Flightoffer> foList = (List<Flightoffer>)oldExchange.getIn().getBody();
		List<Hotel> hotelList = (List<Hotel>) newExchange.getIn().getBody();
		
		for(Flightoffer fo : foList)
		{
			for(Hotel item : hotelList)
			{
				if (fo.getToIataCode().equals(item.getDestinationAirport().getIataCode()))
					fo.getHotels().add(item);
			}
		}
       
        oldExchange.getOut().setBody(foList);
        
		return oldExchange;
	}

}
