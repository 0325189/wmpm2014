package at.tuwien.flightfinder.dao;


import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import at.tuwien.flightfinder.pojo.Airport;
import at.tuwien.flightfinder.pojo.Subscriber;
/**
 * Subscriber DAO with basic CRUD methods
 * @author Ivan Gusljesevic
 */
@Component
public class SubscriberDAO extends BaseDAO{
	public void addSubscriber(Subscriber subscriber){
		Transaction trns = null;
	    Session session = getSession();
	    try {
	        trns = session.beginTransaction();
	        session.save(subscriber);
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
	public void deleteSubscriber(int subscriberId){
		 Transaction trns = null;
	        Session session = getSession();
	        try {
	            trns = session.beginTransaction();
	            Subscriber subscriber = (Subscriber) session.load(Subscriber.class, new Integer(subscriberId));
	            session.delete(subscriber);
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
	public void updateFlightoffer(Subscriber subscriber){
	    Transaction trns = null;
        Session session = getSession();
        try {
            trns = session.beginTransaction();
            session.update(subscriber);
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
	public List<Subscriber> getAllFlightsoffers(){
		 List<Subscriber> flightoffers = new ArrayList<Subscriber>();
	        Transaction trns = null;
	        Session session = getSession();
	        try {
	            trns = session.beginTransaction();
	            flightoffers = session.createQuery("from Subscriber").list();
	        } catch (RuntimeException e) {
	            e.printStackTrace();
	        } finally {
	            session.flush();
	            session.close();
	        }
	        return flightoffers;
	}
	public Subscriber getSubscriberById(int offerId){
		 Subscriber subscriber = null;
	        Transaction trns = null;
	        Session session = getSession();
	        try {
	            trns = session.beginTransaction();
	            String queryString = "from Subscriber where id = :id";
	            Query query = session.createQuery(queryString);
	            query.setInteger("id", offerId);
	            subscriber = (Subscriber) query.uniqueResult();
	        } catch (RuntimeException e) {
	            e.printStackTrace();
	        } finally {
	            session.flush();
	            session.close();
	        }
	        return subscriber;
	}
	public List<Subscriber> getSubscriberByOrignAirport(String iataCode){
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
	      
	        return airport.getSubscribers();
	}

}
