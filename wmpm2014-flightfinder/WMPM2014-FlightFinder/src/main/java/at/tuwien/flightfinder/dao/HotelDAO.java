package at.tuwien.flightfinder.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import at.tuwien.flightfinder.pojo.Airport;
import at.tuwien.flightfinder.pojo.Hotel;
/**
 * Hotel DAO with basic CRUD methods
 * @author Ivan Gusljesevic
 */
@Component
public class HotelDAO extends BaseDAO{
	
	public void addHotel(Hotel hotel){
		Transaction trns = null;
	    Session session = getSession();
	    try {
	        trns = session.beginTransaction();
	        session.save(hotel);
	        session.getTransaction().commit();
	    } catch (RuntimeException e) {
	        if (trns != null) {
	            trns.rollback();
	        }
	        e.printStackTrace();
	    } finally {
	        session.flush();
	        session.close();
	    }
	}
	public void deleteHotel(int hotelId){
		Transaction trns = null;
        Session session =getSession();
        try {
            trns = session.beginTransaction();
            Hotel hotel= (Hotel) session.load(Hotel.class, new Integer(hotelId));
            session.delete(hotel);
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            if (trns != null) {
                trns.rollback();
            }
            e.printStackTrace();
        } finally {
            session.flush();
            session.close();
        }
		
	}
	public void updateHotel(Hotel hotel){
		Transaction trns = null;
        Session session = getSession();
        try {
            trns = session.beginTransaction();
            session.update(hotel);
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            if (trns != null) {
                trns.rollback();
            }
            e.printStackTrace();
        } finally {
            session.flush();
            session.close();
        }
	}
	public List<Hotel> getAllHotels(){
		 List<Hotel> hotels = new ArrayList<Hotel>();
	        Transaction trns = null;
	        Session session = getSession();
	        try {
	            trns = session.beginTransaction();
	            hotels = session.createQuery("from Hotel").list();
	        } catch (RuntimeException e) {
	            e.printStackTrace();
	        } finally {
	            session.flush();
	            session.close();
	        }
	        return hotels;
	}
	public Hotel getHotelById(int hotelId){
		 Hotel hotel = null;
	        Transaction trns = null;
	        Session session = getSession();
	        try {
	            trns = session.beginTransaction();
	            String queryString = "from Hotel where id = :id";
	            Query query = session.createQuery(queryString);
	            query.setInteger("id", hotelId);
	            hotel = (Hotel) query.uniqueResult();
	        } catch (RuntimeException e) {
	            e.printStackTrace();
	        } finally {
	            session.flush();
	            session.close();
	        }
	        return hotel;
	}
	public List<Hotel> getHotelByDestAirport(String iataCode){
		 Airport airport = null;
	        Transaction trns = null;
	        Session session = getSession();
	        try {
	            trns = session.beginTransaction();
	            String queryString = "from Airport where iataCode = :id";
	            Query query = session.createQuery(queryString);
	            query.setString("id", iataCode);
	            airport = (Airport) query.uniqueResult();
	            
	        } catch (RuntimeException e) {
	            e.printStackTrace();
	        } finally {
	            session.flush();
	            session.close();
	        }
	      
	        return airport.getHotels();
	}

}
