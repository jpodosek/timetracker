package com.libertymutual.goforcode.timetracker.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.libertymutual.goforcode.timetracker.models.TimeEntry;
import com.libertymutual.goforcode.timetracker.services.TimeTrackerRepository;

@Controller
@RequestMapping("/")
public class TimeTrackerController {
	//private int sum = 0;
	private TimeTrackerRepository repository;

	public TimeTrackerController(TimeTrackerRepository repository) {
		this.repository = repository;
	}

	@GetMapping("")
	public String redirectToApplication() {
		return "time/default";
	}

//	//can't test this until getAll is finished
//	@GetMapping("time")
//	public ModelAndView list() {
//		ModelAndView mv = new ModelAndView("time/default");
//        List<TimeEntry> items = repository.getAll();
//        mv.addObject("toDoItems", items);
//        mv.addObject("hasToDoItems", !items.isEmpty());
//		return mv;
//	}
	
	

	// this should move item in UI down below
	// create a new TimeEntry item to display in the UI
	
	@PostMapping("update")
	public String create(TimeEntry timeEntry) {
		repository.create(timeEntry);
		System.out.println("The update post mapping is working");
		return "redirect:/time";
	}
	
	//temporary for now just to get page to display and test post
	@GetMapping("time")
	public ModelAndView displayTheDamnPage() {
		ModelAndView mv = new ModelAndView("time/default");

		return mv;
	}
	
	
	

	// update should call the create method in respository
	// redirect to GetMapping("time") to display current state
//	@PostMapping("update")
//	public String updateTimeEntry(TimeEntry entry) {
//		
//		
//		 ModelAndView mv = new ModelAndView("time/default");
//		 
//		 return "redirect:/time";
//		// think i need to setHours here using values from form
//		// double totalHours;
//
//		 ModelAndView mv = new ModelAndView("time/default");
		// return "redirect:/todos";
		// entry.setDate(date);
		// entry.setHours(mondayHours, tuesdayHours, wednesdayHours, thursdayHours,
		// fridayHours);
		// totalHours = entry.addHours();
		//
		// // entry.addHours()
		// mv.addObject("date", date);
		// mv.addObject("mondayHours", mondayHours);
		// mv.addObject("tuesdayHours", tuesdayHours);
		// mv.addObject("wednesdayHours", wednesdayHours);
		// mv.addObject("thursdayHours", thursdayHours);
		// mv.addObject("fridayHours", fridayHours);
		// mv.addObject("totalHours", totalHours);

		// return mv;
	}

	// @PostMapping("update")
	// public ModelAndView addHours(Date date, int mondayHours, int tuesdayHours,
	// int wednesdayHours, int thursdayHours, int fridayHours) {
	// double totalHours;
	//
	// ModelAndView mv = new ModelAndView("time/default");
	// TimeEntry entry = new TimeEntry();
	// entry.setDate(date);
	// entry.setHours(mondayHours, tuesdayHours, wednesdayHours, thursdayHours,
	// fridayHours);
	// totalHours = entry.addHours();
	//
	// // entry.addHours()
	// mv.addObject("date", date);
	// mv.addObject("mondayHours", mondayHours);
	// mv.addObject("tuesdayHours", tuesdayHours);
	// mv.addObject("wednesdayHours", wednesdayHours);
	// mv.addObject("thursdayHours", thursdayHours);
	// mv.addObject("fridayHours", fridayHours);
	// mv.addObject("totalHours", totalHours);
	//
	// return mv;


