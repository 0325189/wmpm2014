package at.tuwien.flightfinder.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.*;

import at.tuwien.flightfinder.pojo.Airport;
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
	public List<Flightoffer> getTodaysFlightoffersByDepAirport(String iataCode){
		 List<Flightoffer> flightoffers = new ArrayList<Flightoffer>();
		 Airport airport = null; 
		 Transaction trns = null;
	        Session session = HibernateUtil.getSessionFactory().openSession();
	        try {
	            trns = session.beginTransaction();
	            String queryString = "from Airport where iataCode = :id";
	            Query query = session.createQuery(queryString);
	            query.setString("id", iataCode);
	            airport = (Airport) query.uniqueResult();
	            String queryString2 = "from Flightoffer where FROM_ID = :id and INSERTDATE = :insertdate ";
	            Query query2 = session.createQuery(queryString2);
	            query2.setDate("insertdate", new Date());
	            query2.setInteger("id", (int) airport.getId());
	            flightoffers = query2.list();
	        } catch (RuntimeException e) {
	            e.printStackTrace();
	        } finally {
	            session.flush();
	            session.close();
	        }
	        return flightoffers;
	}

}
