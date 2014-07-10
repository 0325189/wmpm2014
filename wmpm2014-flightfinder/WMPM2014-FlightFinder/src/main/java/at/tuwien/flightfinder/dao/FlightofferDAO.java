package at.tuwien.flightfinder.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.camel.Header;
import org.hibernate.*;
import org.springframework.stereotype.Component;

import at.tuwien.flightfinder.pojo.Airport;
import at.tuwien.flightfinder.pojo.Flightoffer;

/**
 * Flightoffer DAO with basic CRUD methods
 * @author Ivan Gusljesevic
 */
@Component
public class FlightofferDAO extends BaseDAO{

	public void addFlightoffer(Flightoffer flightoffer){
		Transaction trns = null;
		Session session = getSession();
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
		Session session = getSession();
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
		Session session = getSession();
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
		Session session = getSession();
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
		Session session = getSession();
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
		Session session = getSession();
		try {
			trns = session.beginTransaction();
			String queryString = "from Airport where iataCode = :id";
			Query query = session.createQuery(queryString);
			query.setString("id", iataCode);
			airport = (Airport) query.uniqueResult();
			String queryString2 = "from Flightoffer where FROM_ID = :id and INSERTDATE >= :insertdate ";
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
	public boolean lookupEuropeanIata(@Header ("fromIataCode") String iataCode){
		System.out.println(iataCode);
		boolean flag = false;
		Airport airport = null; 
		Transaction trns = null;
		Session session = getSession();
		try {
			trns = session.beginTransaction();
			String queryString = "from Airport where iataCode = :id";
			Query query = session.createQuery(queryString);
			query.setString("id", iataCode);
			airport = (Airport) query.uniqueResult();
			if(airport != null){
				flag = true;
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
		} finally {
			session.flush();
			session.close();
		}
		return flag;
	}

	public ArrayList<ArrayList<Flightoffer>> getTodaysFlightoffers(){

		List<Flightoffer> flightOffersList = new ArrayList<Flightoffer>();
		ArrayList<Flightoffer> temp = new ArrayList<Flightoffer>();

		HashMap<String,ArrayList<Flightoffer>> temp_storage = new HashMap<String,ArrayList<Flightoffer>>();


		ArrayList<ArrayList<Flightoffer>> flightoffers = new ArrayList<ArrayList<Flightoffer>>();

		Airport airport = null; 
		Transaction trns = null;
		Session session = getSession();
		try {
			trns = session.beginTransaction();
			String queryString2 = "from Flightoffer offer where offer.insertDate >= current_date() order by offer.fromIataCode, offer.price asc";
			Query query2 = session.createQuery(queryString2);
			flightOffersList = query2.list();



			for (Flightoffer offer : flightOffersList) {

				if(temp_storage.isEmpty())
				{
					ArrayList temp_list = new ArrayList<Flightoffer>();

					temp_list.add(offer);
					temp_storage.put(offer.getFromIataCode(), temp_list);
				}
				else if(!temp_storage.containsKey(offer.getFromIataCode())) {
					ArrayList temp_list = new ArrayList<Flightoffer>();

					temp_list.add(offer);
					temp_storage.put(offer.getFromIataCode(), temp_list);
				}
				else if(temp_storage.containsKey(offer.getFromIataCode())) {
					ArrayList temp_list = new ArrayList<Flightoffer>();

					temp_list = temp_storage.get(offer.getFromIataCode());
					temp_list.add(offer);
					temp_storage.put(offer.getFromIataCode(), temp_list);

				}

			}

			for(String key : temp_storage.keySet()){
				flightoffers.add(temp_storage.get(key));
			}

		} catch (RuntimeException e) {
			e.printStackTrace();
		} finally {
			session.flush();
			session.close();
		}
		return flightoffers;
	}

	public Flightoffer getTodaysBestFlightoffer(){
		Flightoffer flightoffer = new Flightoffer();
		Airport airport = null; 
		Transaction trns = null;
		Session session = getSession();
		try {
			trns = session.beginTransaction();
			String queryString2 = "from Flightoffer offer where offer.insertDate >= current_date() order by offer.price asc";
			Query query2 = session.createQuery(queryString2);
			flightoffer = (Flightoffer) query2.list().get(0);
		} catch (RuntimeException e) {
			e.printStackTrace();
		} finally {
			session.flush();
			session.close();
		}
		return flightoffer;
	}
}
