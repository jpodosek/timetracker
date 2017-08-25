package com.libertymutual.goforcode.timetracker.models;

import java.util.Date;

public class TimeEntry {
	private int id;
	private boolean isSubmitted;
	private Date date;
	private double mondayHours;
	private double tuesdayHours;
	private double wednesdayHours;
	private double thursdayHours;
	private double fridayHours;
	//private String mondayHours;
   // private String[] daysOfTheWeek = {"Monday" , "Tuesday", "Wednesday", "Thursday", "Friday"};
	//private double totalHours; //total weekly hours for an item
    
	public TimeEntry() {
		// TODO Auto-generated constructor stub
	}
	
	public double getTotalHours() {
		return mondayHours + tuesdayHours + wednesdayHours + thursdayHours + fridayHours;
	}
	
	
	public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
   
   
    public Date getDate() {
    	return date;
    }
    
    public void setDate(Date date) {
    	this.date = date;
    }


	public boolean isSubmitted() {
		return isSubmitted;
	}

	public void setSubmitted(boolean isSubmitted) {
		this.isSubmitted = isSubmitted;
	}
	

	public double getMondayHours() {
		return mondayHours;
	}

	
	public double getTuesdayHours() {
		return tuesdayHours;
	}


	public double getWednesdayHours() {
		return wednesdayHours;
	}

	

	public double getThursdayHours() {
		return thursdayHours;
	}

	public double getFridayHours() {
		return fridayHours;
	}

	public void setMondayHours(double mondayHours) {
		this.mondayHours = mondayHours;
	}
	
	public void setTuesdayHours(double tuesdayHours) {
		this.tuesdayHours = tuesdayHours;
	}
	
	public void setWednesdayHours(double wednesdayHours) {
		this.wednesdayHours = wednesdayHours;
	}
	
	public void setThursdayHours(double thursdayHours) {
		this.thursdayHours = thursdayHours;
	}
	
	public void setFridayHours(double fridayHours) {
		this.fridayHours = fridayHours;
	}
		
	
}
