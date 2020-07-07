package util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;



import dao.StudioDao;

/*
 * rewrite from course instructions 
 * rewrite from scratch to understand completely.
 * @author: Crystal Hansen 
 * @Date 2020 05 27
 * macbookpro
 * */
public class SpringFactory {
	
	//this file will create the connection to the DAO class with the application context model
	private static ApplicationContext ctx;
	
	private static StudioDao studioDao;

	
	

	
	public static StudioDao getStudioDao() {
		
		if(ctx==null) {
			ctx =  new ClassPathXmlApplicationContext("spring-config.xml");
		}
		studioDao = (StudioDao) ctx.getBean("studioDao");
		
		
		return studioDao;
	}

	
	
	//In the dao tests we had to call a transaction manager and its Template to implement the database call. 
	// similar to the transactional formats of .net
	
	// all dao have a been to instanciate in this class to access through the bean manager
	
	
	
	

}
