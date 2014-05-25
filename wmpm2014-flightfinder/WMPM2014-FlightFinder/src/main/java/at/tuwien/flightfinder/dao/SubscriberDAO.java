package at.tuwien.flightfinder.dao;


import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import at.tuwien.flightfinder.pojo.Airport;
import at.tuwien.flightfinder.pojo.Subscriber;
import at.tuwien.flightfinder.util.HibernateUtil;

public class SubscriberDAO {
	public void addSubscriber(Subscriber subscriber){
		Transaction trns = null;
	    Session session = HibernateUtil.getSessionFactory().openSession();
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
	        Session session = HibernateUtil.getSessionFactory().openSession();
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
        Session session = HibernateUtil.getSessionFactory().openSession();
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
	        Session session = HibernateUtil.getSessionFactory().openSession();
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
	        Session session = HibernateUtil.getSessionFactory().openSession();
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
	        Session session = HibernateUtil.getSessionFactory().openSession();
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
