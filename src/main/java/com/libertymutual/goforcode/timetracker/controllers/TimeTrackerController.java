package com.libertymutual.goforcode.timetracker.controllers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
	private TimeEntry currentTimeEntry;

	public TimeTrackerController(TimeTrackerRepository repository) {
		this.repository = repository;
	}

	// @GetMapping("")
	// public String redirectToApplication() {
	// return "time/default";
	// }
	@GetMapping("")
	public String redirectToApplication() {
		return "redirect:/update";
	}

	// can't test this until getAll is finished
	@GetMapping("update")
	public String showDefaultPage(Model model) {
		List<TimeEntry> timeEntryList = repository.getMasterList();
		currentTimeEntry = repository.getCurrentTimeEntry();
		model.addAttribute("timeEntryList", timeEntryList);
		model.addAttribute("hasTimeEntries", !timeEntryList.isEmpty());
		
		//when hitting submit, will need to clear out currentEntry in repository so this can kick off again
		if (currentTimeEntry == null) {
			System.out.println("currentTimeEntry is null");
			model.addAttribute("hasNoItem", true);
			model.addAttribute("hasItem", false);
		} else {
			DateFormat format = new SimpleDateFormat("MM/dd/yy", Locale.ENGLISH);
			String prettyDate = format.format(currentTimeEntry.getDate());
			System.out.println("currentTimeEntry is not null");
			model.addAttribute("hasNoItem", false);
			model.addAttribute("hasItem", true);
			model.addAttribute("date", prettyDate);
			model.addAttribute("mondayHours", currentTimeEntry.getMondayHours());
			model.addAttribute("tuesdayHours", currentTimeEntry.getTuesdayHours());
			model.addAttribute("wednesdayHours", currentTimeEntry.getWednesdayHours());
			model.addAttribute("thursdayHours", currentTimeEntry.getThursdayHours());
			model.addAttribute("fridayHours", currentTimeEntry.getFridayHours());
			model.addAttribute("totalHours", currentTimeEntry.getTotalHours());
			model.addAttribute("id", currentTimeEntry.getId()); //this id will be = 1
			System.out.println(currentTimeEntry.getId());
		}

		return "time/default";
	}

	// this should move item in UI down below
	// create a new TimeEntry item to display in the UI

	@PostMapping("update")
	public String create(TimeEntry timeEntry, String formButtons) {
		if (formButtons.equals("update"))
		{
			if (timeEntry.getId() == 0) {
				repository.create(timeEntry);				
			} else {
//				repository.update(timeEntry);
			}


		} else {
			
		}

		return "redirect:/update";
	}

}
