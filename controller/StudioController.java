package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import java.awt.Dimension;
import java.awt.image.BufferedImage;

import java.io.FilenameFilter;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.ImageInputStream;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;




import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.richfaces.json.JSONArray;
import org.richfaces.json.JSONException;
import org.richfaces.json.JSONObject;

import com.google.gson.Gson;

import bll.StudioManager;

import entity.Studio;


public class StudioController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	RequestDispatcher dispatcher = null;
	StudioManager studioManager= null;
	private Gson gson = new Gson();
	public StudioController() {
		studioManager = new StudioManager();
		
		
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter("action");
		
		if(action == null) {
			action = "LIST";
		}
		
		switch(action) {
			
			case "LIST":
				listStudio(request, response);
				break;
			//methods coded for jsp page receipt rather than json message through ajax	
			case "EDIT":
				getSingleStudio(request, response);
				break;
			//methods coded for jsp page receipt rather than json message through ajax	
			case "DELETE":
				deleteStudio(request, response);
				break;
				
			case "BOOK":
				listStudioBooking(request, response);
				break;
			//the nugget we're working on.
			case "GALLERY":
				listStudioImages(request,response);
				break;
			//methods coded for jsp page receipt rather than json message through ajax
			//when using date calendar from java rather than front end scripts
			case "PREV":
				listLastMonthAvailability( request, response);
				break;
				
		    //methods coded for jsp page receipt rather than json message through ajax
			//when using date calendar from java rather than front end scripts
			case "NEXT":
				listNextMonthAvailability( request, response);
				break;
			
			default:
				listStudio(request, response);
				break;
				
		}
		
	}

	private void deleteStudio(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//studioId on form but 'id' parameter in links
		String id = request.getParameter("id");
		Studio studio = studioManager.getSingleStudio(Integer.parseInt(id));
		if(studioManager.deleteStudio(studio)==0) {
			request.setAttribute("NOTIFICATION", "Studio Deleted Successfully!");
		}
		//forward the http request
		listStudio(request, response);
	}

	private void getSingleStudio(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		//studioId on form but 'id' parameter in links
		String id = request.getParameter("id");
		
		Studio theStudio = studioManager.getSingleStudio(Integer.parseInt(id));
		//java to jsp where declare a jstl component at top of page
		request.setAttribute("studio", theStudio);
		
		dispatcher = request.getRequestDispatcher("/views/studio-form.jsp");
		
		dispatcher.forward(request, response);
	}
	//list images
	private void listStudioImages(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		//studioId on form but 'id' parameter in links
		String id = request.getParameter("id");
		JSONArray theStudioImages = new JSONArray(); 
		
		String studioPath = "studio"+id;
		

		String path = this.getClass().getClassLoader().getResource("").getPath();
		String fullPath = URLDecoder.decode(path, "UTF-8");
		String pathArr[] = fullPath.split("/WEB-INF/classes/");
		System.out.println(fullPath);
		System.out.println(pathArr[0]);
		
		fullPath = pathArr[0];
		String reponsePath = "";
		// to read a file from webcontent
		
		
		reponsePath = new File(fullPath).getPath() + File.separatorChar + "images" + File.separatorChar + studioPath + File.separatorChar +"576" ;
		
		
		File folder = new File(reponsePath);
		File[] listOfFiles = folder.listFiles();
		System.out.println(listOfFiles.length);
		for (int i = 0; i < listOfFiles.length; i++) {
			JSONObject studioImages = new JSONObject();
		  if (listOfFiles[i].isFile()) {
			  try {
				  studioImages.put("id",i);
				  studioImages.put("image",listOfFiles[i].getName());
				  String imageFileName = listOfFiles[i].getName();
						  
				  BufferedImage bimg = ImageIO.read(listOfFiles[i]);
					int width          = bimg.getWidth();
					int height         = bimg.getHeight();
				  studioImages.put("width", width);
				  studioImages.put("height", height);
				theStudioImages.put(i,studioImages);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    System.out.println("File: " + listOfFiles[i].getName());
		  } else if (listOfFiles[i].isDirectory()) {
		    System.out.println("Directory: " + listOfFiles[i].getName());
		  }
		}
		
		
		
		System.out.println(theStudioImages.toString());
		 PrintWriter writer = response.getWriter();
	      
	        writer.println(theStudioImages);
	        writer.flush();
		
// jstl code for jsp page		
//		request.setAttribute("studio", theStudioImages);
//		
//		dispatcher = request.getRequestDispatcher("/views/studio-form.jsp");
//		
//		dispatcher.forward(request, response);
	}
	//show calendar studio view
	private void listStudioBooking(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, NumberFormatException{
		
		//studioId on form but 'id' parameter in links
		String id = request.getParameter("id");
		Studio studio = studioManager.getSingleStudio(Integer.parseInt(id));
		
		JSONArray ja = new JSONArray(); 
		System.out.println(studio);
		System.out.println("listStudioBooking id::"+ id);
//	     List<StudioBooking> theBookings = studioManager.getStudioBookings(Integer.parseInt(id));
	     
//	     for(StudioBooking s : theBookings) {
//				System.out.println(s.getStudio().getStudioId());
				  JSONObject bklistObj =new JSONObject();
			        try {
			        	bklistObj.put("studioId", studio.getStudioId());
			        	bklistObj.put("name",studio.getStudioName());
			        	bklistObj.put("description",studio.getStudioDescription());
			        	bklistObj.put("charge",studio.getStudioCharge());
			        	bklistObj.put("accessories", studio.getStudioAccessories());			        	
			        	bklistObj.put("studiosize",studio.getStudioSizeSq());
			        	bklistObj.put("studiofolder",studio.getStudiofolder());
			        	bklistObj.put("fullimage",studio.getFullimage());

			            ja.put(studio.getStudioId(),bklistObj);

			        } catch (JSONException e) {
			            e.printStackTrace();
			        }
//	     }
	     System.out.println(bklistObj);

		 PrintWriter writer = response.getWriter();
	      
	        writer.println(bklistObj);
	        writer.flush();
						
				//		for(StudioBooking c : theBookings) {
				//			//System.out.println(c.getStudioBookingDate());
				//		
				//		}
				//		request.setAttribute("list", theBookings);
				//		request.setAttribute("studio", studio);
				//		dispatcher = request.getRequestDispatcher("/views/studio-booking.jsp");
				//		
				//		dispatcher.forward(request, response);
		
	}
	//for java calendar methodology rather than angular version
	private void listLastMonthAvailability(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("In last month!");
		//studioId on form but 'id' parameter in links
				String id = request.getParameter("id");
				Studio studio = studioManager.getSingleStudio(Integer.parseInt(id));
				
				//List<StudioBooking> theBookings = studioManager.getStudioBookings(Integer.parseInt(id));
//				for(StudioBooking c : theBookings) {
//					//System.out.println(c.getStudioBookingDate());
//				
//				}
//				request.setAttribute("list", theBookings);
				request.setAttribute("studio", studio);
		dispatcher = request.getRequestDispatcher("/views/studio-booking.jsp");
		
		dispatcher.forward(request, response);
		
	}
	
	////for java calendar methodology rather than angular version
	private void listNextMonthAvailability(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//studioId on form but 'id' parameter in links
				String id = request.getParameter("id");
				Studio studio = studioManager.getSingleStudio(Integer.parseInt(id));
				
//				List<StudioBooking> theBookings = studioManager.getStudioBookings(Integer.parseInt(id));
//				for(StudioBooking c : theBookings) {
//					//System.out.println(c.getStudioBookingDate());
//				
//				}
//				request.setAttribute("list", theBookings);
				request.setAttribute("studio", studio);
		System.out.println("In Next Month!");
		dispatcher = request.getRequestDispatcher("/views/studio-booking.jsp");
		
		dispatcher.forward(request, response);
		
	}
	private void listStudio(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// fyi   JSONObject inp = (JSONObject) JSONValue.parse(request.getParameter("param1"));
		List<Studio> theList = studioManager.listStudios();
		
		//String element = gson.toJson( theList, new ArrayList<Studio>() {});
		//Create JSONArray from String:

		//JSONArray list = new JSONArray(element);
		
		
		JSONArray ja = new JSONArray();  
		for(Studio c : theList) {
			System.out.println(c.getStudioName());
			  JSONObject obj =new JSONObject();
			  
		        try {
		        	obj.put("studioId",c.getStudioId());
		        	 obj.put("studioName",c.getStudioName());
		        	 obj.put("studioDescription",c.getStudioDescription());
		        	 obj.put("price",c.getStudioCharge());
		        	 obj.put("studioSizeSq",c.getStudioSizeSq());
		        	 obj.put("studioAvailability",c.getStudioAvailability());
		            obj.put("studioAccessories",c.getStudioAccessories());
		            ja.put(c.getStudioId(),obj);
		           // studioArray..put("studio",ja);
		        } catch (JSONException e) {
		            e.printStackTrace();
		        }
			  }
		
		
		 PrintWriter writer = response.getWriter();
	      
	        writer.println(ja);
	        writer.flush();
     
        
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		String action =  request.getParameter("action");
		switch(action) {
		
			case "NEXT":
				listNextMonthAvailability(request, response);
				break;
			case "PREV":
				listLastMonthAvailability(request, response);
				break;
			default:
				createStudio(request, response);
				break;
		}		
		
		
	}
	
	
	//save
	private void createStudio(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		//studioId on form but 'id' parameter in links
		String id = request.getParameter("studioId");
		Studio studio = new Studio();
		studio.setStudioName(request.getParameter("studioName"));
		
		studio.setStudioSizeSq(request.getParameter("studioSizeSq"));
		studio.setStudioDescription(request.getParameter("studioDescription"));
		studio.setStudioAvailability(request.getParameter("studioAvailability"));
		String charge = request.getParameter("studioCharge");
		if(charge==null) {
			studio.setStudioCharge(new BigDecimal(0.00));
		}else {
			BigDecimal bigDecimalCurrency=new BigDecimal(charge); 
			studio.setStudioCharge(bigDecimalCurrency);
		}
		
		studio.setStudioAccessories(request.getParameter("studioAccessories"));
		
		
		
		if(id.trim().isEmpty() || id == null) {
			
			if(studioManager.createStudio(studio)==1) {
				request.setAttribute("NOTIFICATION", "Studio Saved Successfully!");
			}
		
		}else {
			
			studio.setStudioId(Integer.parseInt(id));
			if(studioManager.updateStudio(studio)==0) {
				request.setAttribute("NOTIFICATION", "Studio Updated Successfully!");
			}
			
		}
	}
	

	public void listFilesForFolder(final File folder) {
		System.out.println(folder);
	    for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	            listFilesForFolder(fileEntry);
	        } else {
	            System.out.println(fileEntry.getName());
	        }
	    }
	}

	
	/**
	 * Gets image dimensions for given file 
	 * @param imgFile image file
	 * @return dimensions of image
	 * @throws IOException if the file is not a known image
	 */
	public static Dimension getImageDimension(File imgFile) throws IOException {
	  int pos = imgFile.getName().lastIndexOf(".");
	  if (pos == -1)
	    throw new IOException("No extension for file: " + imgFile.getAbsolutePath());
	  String suffix = imgFile.getName().substring(pos + 1);
	  Iterator<ImageReader> iter = ImageIO.getImageReadersBySuffix(suffix);
	  while(iter.hasNext()) {
	    ImageReader reader = iter.next();
	    try {
	      ImageInputStream stream = new FileImageInputStream(imgFile);
	      reader.setInput(stream);
	      int width = reader.getWidth(reader.getMinIndex());
	      int height = reader.getHeight(reader.getMinIndex());
	      return new Dimension(width, height);
	    } catch (IOException e) {
	      System.out.println("Error reading: " + imgFile.getAbsolutePath() + "  e::" +  e);
	    } finally {
	      reader.dispose();
	    }
	  }

	  throw new IOException("Not a known image file: " + imgFile.getAbsolutePath());
	}
	
	
	// next month
	//function jsonParser(){
	
//	
//	JSONObject obj1 = new JSONObject(strAPI_TERMINAL);
//	 try {
//	  JSONArray result = obj1.getJSONArray("terminal");
//	for(int i=0;i<=result.length();i++)
//	 {
//	    String Id=result.getString("fmt_id");
//	      String terminalType=result.getString("terminal_type");
//	 }
//	 } catch (JSONException e) {
//	     e.printStackTrace();
//	}
	
	//}

}
