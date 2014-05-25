package at.tuwien.flightfinder.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.*;

import at.tuwien.flightfinder.pojo.Flightoffer;
import at.tuwien.flightfinder.util.HibernateUtil;


public class FlightofferDAO {
	
	public void addFlightoffer(Flightoffer flightoffer){
		Transaction trns = null;
	    Session session = HibernateUtil.getSessionFactory().openSession();
	    try {
	        trns = session.beginTransaction();
	        session.save(flightoffer);
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
	public void deleteFlightoffer(int offerId){
		 Transaction trns = null;
	        Session session = HibernateUtil.getSessionFactory().openSession();
	        try {
	            trns = session.beginTransaction();
	            Flightoffer flightoffer = (Flightoffer) session.load(Flightoffer.class, new Integer(offerId));
	            session.delete(flightoffer);
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
	public void updateFlightoffer(Flightoffer flightoffer){
	    Transaction trns = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            trns = session.beginTransaction();
            session.update(flightoffer);
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
	public List<Flightoffer> getAllFlightsoffers(){
		 List<Flightoffer> flightoffers = new ArrayList<Flightoffer>();
	        Transaction trns = null;
	        Session session = HibernateUtil.getSessionFactory().openSession();
	        try {
	            trns = session.beginTransaction();
	            flightoffers = session.createQuery("from Flightoffer").list();
	        } catch (RuntimeException e) {
	            e.printStackTrace();
	        } finally {
	            session.flush();
	            session.close();
	        }
	        return flightoffers;
	}
	public Flightoffer getFlightofferById(int offerId){
		 Flightoffer offer = null;
	        Transaction trns = null;
	        Session session = HibernateUtil.getSessionFactory().openSession();
	        try {
	            trns = session.beginTransaction();
	            String queryString = "from Flightoffer where id = :id";
	            Query query = session.createQuery(queryString);
	            query.setInteger("id", offerId);
	            offer = (Flightoffer) query.uniqueResult();
	        } catch (RuntimeException e) {
	            e.printStackTrace();
	        } finally {
	            session.flush();
	            session.close();
	        }
	        return offer;
	}

}
