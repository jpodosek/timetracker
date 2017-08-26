package com.libertymutual.goforcode.timetracker.controllers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.libertymutual.goforcode.timetracker.models.TimeEntry;
import com.libertymutual.goforcode.timetracker.services.TimeTrackerRepository;

@Controller
@RequestMapping("/")
public class TimeTrackerController {
	// private int sum = 0;
	private TimeTrackerRepository repository;

	public TimeTrackerController(TimeTrackerRepository repository) {
		this.repository = repository;
	}

	// @GetMapping("")
	// public String redirectToApplication() {
	// return "time/default";
	// }
	@GetMapping("")
	public String redirectToApplication() {
		return "time/default";
		// return "redirect:/update";
	}

	// can't test this until getAll is finished
	@GetMapping("update")
	public String showUpdatePage(Model model) {

		List<TimeEntry> timeEntryList = new ArrayList<TimeEntry>();
		timeEntryList = repository.getTimeSheetHistory();
		
		TimeEntry timeEntry = new TimeEntry();
		timeEntry = repository.getTempTimeSheet();

		model.addAttribute("timeEntryList", timeEntryList);
		model.addAttribute("entry", timeEntry);
		model.addAttribute("hasTimeEntries", !timeEntryList.isEmpty());

		// submitted status is set to false in writeTempTimeSheet
		// if (timeEntry.getSubmittedStatus()) {
		// System.out.println("currentTimeEntry is null");
		// model.addAttribute("hasNoItem", true);
		// model.addAttribute("hasItem", false);
		// } else {
		// DateFormat format = new SimpleDateFormat("MM/dd/yy", Locale.ENGLISH);
		// String prettyDate = format.format(timeEntry.getDate());

		// model.addAttribute("hasNoItem", false);
		// model.addAttribute("hasItem", true);
		// model.addAttribute("date", prettyDate);
		model.addAttribute("dateString", timeEntry.getDateString());
		model.addAttribute("mondayHours", timeEntry.getMondayHours());
		model.addAttribute("tuesdayHours", timeEntry.getTuesdayHours());
		model.addAttribute("wednesdayHours", timeEntry.getWednesdayHours());
		model.addAttribute("thursdayHours", timeEntry.getThursdayHours());
		model.addAttribute("fridayHours", timeEntry.getFridayHours());
		model.addAttribute("totalHours", timeEntry.getTotalHours());
		model.addAttribute("id", timeEntry.getId()); // this id will be = 1
		System.out.println(timeEntry.getId());
		// }

		return "time/update";
	}
	
	@GetMapping("default")
	public String showDefaultPage(Model model) {
		List<TimeEntry> timeEntryList = new ArrayList<TimeEntry>();
		timeEntryList = repository.getTimeSheetHistory();
		
		TimeEntry timeEntry = new TimeEntry();
		timeEntry = repository.getTempTimeSheet();
		
		//TimeEntry timeEntry = new TimeEntry();
		// this will need to be handled once submit is performed
		// right now its just a null TimeEntryList to allow the mustache on page to work
	

		model.addAttribute("timeEntryItems", timeEntryList);
		model.addAttribute("hasTimeEntries", !timeEntryList.isEmpty());

		model.addAttribute("dateString", timeEntry.getDateString());
		model.addAttribute("mondayHours", timeEntry.getMondayHours());
		model.addAttribute("tuesdayHours", timeEntry.getTuesdayHours());
		model.addAttribute("wednesdayHours", timeEntry.getWednesdayHours());
		model.addAttribute("thursdayHours", timeEntry.getThursdayHours());
		model.addAttribute("fridayHours", timeEntry.getFridayHours());
		model.addAttribute("totalHours", timeEntry.getTotalHours());
	//	model.addAttribute("id", timeEntry.getId()); // this id will be = 1
		System.out.println(timeEntry.getId());
		// }

		return "time/default";
	}

	// this should move item in UI down below
	// create a new TimeEntry item to display in the UI

	@PostMapping("update")
	public String create(TimeEntry timeEntry, String formButtons) {
		if (formButtons.equals("update")) {
			repository.writeTempTimeSheet(timeEntry);
			return "redirect:/update";

		} else if (formButtons.equals("submit")) {
			repository.writeToTimeSheetHistory(timeEntry);
			return "redirect:/default";
			
		} else {
			System.err.println("This should be unreachable code. Update or Submit were not trigger");
			return null;
		}

	}

}
