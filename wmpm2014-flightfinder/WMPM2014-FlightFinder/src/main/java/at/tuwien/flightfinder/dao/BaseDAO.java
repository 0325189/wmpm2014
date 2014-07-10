package at.tuwien.flightfinder.dao;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class BaseDAO {
	 @Resource(name="sessionFactory")
     protected SessionFactory sessionFactory;

     public void setSessionFactory(SessionFactory sessionFactory) {
            this.sessionFactory = sessionFactory;
     }
    
     protected Session getSession(){
            return sessionFactory.openSession();
     }     
}
