package bll;

import java.util.List;


import dao.StudioDao;

import entity.Studio;

/**
 * 
 * 
 * @author Crystal Hansen
 * May 29 2020
 */
public class StudioManager {


	private StudioDao studioDao = util.SpringFactory.getStudioDao();
//	private StudioBookingDao studioBookingDao = util.SpringFactory.getStudioBookingDao();
	
	public Studio getSingleStudio(int studioId){
		return studioDao.getStudioByStudioId(studioId);
	}
	public List<Studio> listStudios(){
		
		return studioDao.getStudios();
		
		
	}
	
	public int deleteStudio(Studio studio){
		int success = 1;
		try {
			studioDao.deleteStudio(studio);
		}catch (Exception e){
			success=0;
			System.out.print("failed.");
		}
		
		return success;
		
	}
	
	public int createStudio(Studio studio){
		int success = 1;
		try {
		studioDao.addStudio(studio);
		}catch (Exception e){
			success=0;
			System.out.print("failed.");
		}
		
		return success;
	}
	
	public int updateStudio(Studio studio){
		int success = 1;
		try {
		studioDao.updateStudio(studio);
		}catch (Exception e){
			success=0;
			System.out.print("failed.");
		}
		
		return success;
	}
	
//	public List<StudioBooking> getStudioBookings(int studioId) {
//		return studioBookingDao.getStudioBookings(studioId);
//		
//	}
	
	
}
