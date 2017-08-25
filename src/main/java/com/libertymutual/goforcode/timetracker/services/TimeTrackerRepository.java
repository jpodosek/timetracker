package com.libertymutual.goforcode.timetracker.services;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Service;

import com.libertymutual.goforcode.timetracker.models.TimeEntry;

@Service
public class TimeTrackerRepository {
	//private int nextId = 1;
	private int currentId = 1;
	List<TimeEntry> timeEntryList;
	TimeEntry currentEntry;
	
	public TimeTrackerRepository() {
		timeEntryList = new ArrayList<TimeEntry>();
	}
	
	public List<TimeEntry> getAll() {
		//timeEntryList = new ArrayList<TimeEntry>();
		
		return timeEntryList;
	}
	
	public void create(TimeEntry item) {
		System.out.println("the create method is running");
		
		//if the current time entry id equals current, dont use the passed in item instance
		//create a new instance, that will be reused and reset
		if (item.getId() == currentId) {
			currentEntry = new TimeEntry();
			item = currentEntry;
		}
		
		//Create csv file
		  try (FileWriter writer = new FileWriter("timetracker.csv", true); 
			   CSVPrinter printer = CSVFormat.DEFAULT.print(writer)) {
  
			  item.setId(currentId); 
			  
			  //maybe only update currentId once submitted
			  //make sure item does not already exist, before incrementing id;
			  //if item does already exist, item.getId() should match nextId value;
//			  if (item.getId() != currentId) {
//				  nextId += 1; 
//			  }
//			  
			  
			  String[] record = {Integer.toString(item.getId()), item.getDate().toString(), Double.toString(item.getMondayHours()), Double.toString(item.getTuesdayHours()), 
					  			Double.toString(item.getWednesdayHours()), Double.toString(item.getThursdayHours()), Double.toString(item.getFridayHours())};
			  printer.printRecord(record);
			  
			  
		  } catch (IOException e) {
			  System.out.println("Error on create method.");
		}
		  
//		//Create the csv file 
//	      try (FileWriter writer = new
//		  FileWriter("to-do-list.csv", true); CSVPrinter printer =
//		  CSVFormat.DEFAULT.print(writer)) {
//		 
//		  //Assign new id to ToDoItem 
//	      item.setId(nextId); 
//	      nextId += 1;
//	       
//		  item.setComplete(false); 
//		  String[] record = {Integer.toString(item.getId()), item.getText(), Boolean.toString(item.isComplete())};
//		  printer.printRecord(record);
//		  
//		  } catch (IOException e) { System.out.println("Error on create method."); }
//		  
//		  }
	}
	
//	public int addHours(TimeEntry item) {
//		
//		
//	}
	
//	 public void create(ToDoItem item) {
//			
//			//Create the csv file 
//		      try (FileWriter writer = new
//			  FileWriter("to-do-list.csv", true); CSVPrinter printer =
//			  CSVFormat.DEFAULT.print(writer)) {
//			 
//			  //Assign new id to ToDoItem 
//		      item.setId(nextId); 
//		      nextId += 1;
//		       
//			  item.setComplete(false); 
//			  String[] record = {Integer.toString(item.getId()), item.getText(), Boolean.toString(item.isComplete())};
//			  printer.printRecord(record);
//			  
//			  } catch (IOException e) { System.out.println("Error on create method."); }
//			  
//			  }

}
