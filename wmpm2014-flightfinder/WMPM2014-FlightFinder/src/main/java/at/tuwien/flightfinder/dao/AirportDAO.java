package at.tuwien.flightfinder.dao;



import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import at.tuwien.flightfinder.pojo.Airport;
import at.tuwien.flightfinder.util.HibernateUtil;
/**
 * Airport DAO with basic CRUD methods and getAirportByIataCode method
 * which gives possibility to select Airport based on IATA code
 * @author Ivan Gusljesevic
 */
public class AirportDAO {
	
	public void addAirport(Airport airport){
		Transaction trns = null;
	    Session session = HibernateUtil.getSessionFactory().openSession();
	    try {
	        trns = session.beginTransaction();
	        session.save(airport);
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
	public void deleteAirport(int airportId){
		 Transaction trns = null;
	        Session session = HibernateUtil.getSessionFactory().openSession();
	        try {
	            trns = session.beginTransaction();
	            Airport airport = (Airport) session.load(Airport.class, new Integer(airportId));
	            session.delete(airport);
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
	public void updateAirport(Airport airport){
	    Transaction trns = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            trns = session.beginTransaction();
            session.update(airport);
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
	public List<Airport> getAllAirports(){
		 List<Airport> airports = new ArrayList<Airport>();
	        Transaction trns = null;
	        Session session = HibernateUtil.getSessionFactory().openSession();
	        try {
	            trns = session.beginTransaction();
	            airports = session.createQuery("from Airport").list();
	        } catch (RuntimeException e) {
	            e.printStackTrace();
	        } finally {
	            session.flush();
	            session.close();
	        }
	        return airports;
	}
	public Airport getAirportById(int airportId){
		 Airport airport = null;
	        Transaction trns = null;
	        Session session = HibernateUtil.getSessionFactory().openSession();
	        try {
	            trns = session.beginTransaction();
	            String queryString = "from Airport where id = :id";
	            Query query = session.createQuery(queryString);
	            query.setInteger("id",airportId);
	            airport = (Airport) query.uniqueResult();
	        } catch (RuntimeException e) {
	            e.printStackTrace();
	        } finally {
	            session.flush();
	            session.close();
	        }
	        return airport;
	}
	public Airport getAirportByIataCode(String iataCode){
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
	        return airport;
	}

}
