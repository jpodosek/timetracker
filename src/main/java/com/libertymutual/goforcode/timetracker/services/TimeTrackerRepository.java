package com.libertymutual.goforcode.timetracker.services;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import com.libertymutual.goforcode.timetracker.models.TimeEntry;

@Service
public class TimeTrackerRepository {
	private int nextId = 1;
	private int currentId = 1;
	// private int currentMaxId = 1;
	List<TimeEntry> timeEntryList;
	TimeEntry currentEntry;

	public TimeTrackerRepository() {
		timeEntryList = new ArrayList<TimeEntry>();
	}

	public TimeEntry getCurrentTimeEntry() {
		List<TimeEntry> list = getMasterList();
		for (TimeEntry entry : list) {
			if (!entry.getIsSubmitted()) {
				return entry;
			}
		}
		return null;
	}

	public void create(TimeEntry item) {
		System.out.println("the create method is running");

		// If this is the first time we've seen this specific TimeEntry,
		// we need to assign the next id to this,
		// then we want to save it to the file and
		// set it to currentEntry
		if (item.getId() == 0) {
			currentEntry = item;
			item.setId(nextId);
			currentId = nextId;
			nextId += 1;
			timeEntryList.add(item);
		}

		// If item has the same id as currentEntry, we just want
		// to update values and save them to the file
		if (item.getId() == currentId) {
			for (TimeEntry entry : timeEntryList) {
				if (entry.getId() == item.getId()) {
					entry.setDate(item.getDate());
					entry.setFridayHours(item.getFridayHours());
					entry.setMondayHours(item.getMondayHours());
					entry.setSubmitted(item.getIsSubmitted());
					entry.setThursdayHours(item.getThursdayHours());
					entry.setTotalHours(item.getTotalHours());
					entry.setTuesdayHours(item.getTuesdayHours());
					entry.setWednesdayHours(item.getWednesdayHours());
				}
				currentEntry = entry;
			}
		}

		// Create csv file
		try (FileWriter writer = new FileWriter("timetracker.csv", false);
				CSVPrinter printer = CSVFormat.DEFAULT.print(writer)) {

			// maybe only update currentId once submitted
			// make sure item does not already exist, before incrementing id;
			// if item does already exist, item.getId() should match nextId value;
			// if (item.getId() != currentId) {
			// nextId += 1;
			// }

			for (TimeEntry entry : timeEntryList) {
				DateFormat format = new SimpleDateFormat("MM/dd/yy", Locale.ENGLISH);
				String prettyDate = format.format(entry.getDate());
				String[] record = { Integer.toString(entry.getId()), prettyDate,
						Double.toString(entry.getMondayHours()), Double.toString(entry.getTuesdayHours()),
						Double.toString(entry.getWednesdayHours()), Double.toString(entry.getThursdayHours()),
						Double.toString(entry.getFridayHours()), Double.toString(entry.getTotalHours()) };
				printer.printRecord(record);
			}

		} catch (IOException e) {
			System.out.println("Error on create method.");
		}

	}

	public List<TimeEntry> getMasterList() {
		List<TimeEntry> timeEntryList = new ArrayList<TimeEntry>();
		int currentMaxId = 0;
		// store to-do-list in a reader
		try (Reader in = new FileReader("timetracker.csv"); CSVParser parser = new CSVParser(in, CSVFormat.DEFAULT)) {

			// get all the items from the csv file and store into CSVRecord list
			List<CSVRecord> csvList = parser.getRecords();

			// iterate through list, and for each record, extract fields into variables
			for (CSVRecord csvRecord : csvList) {
				String idColumn = csvRecord.get(0);
				String dateColumn = csvRecord.get(1);
				String mondayColumn = csvRecord.get(2);
				String tuesdayColumn = csvRecord.get(3);
				String wednesdayColumn = csvRecord.get(4);
				String thursdayColumn = csvRecord.get(5);
				String fridayColumn = csvRecord.get(6);

				// add each of these variable values to to-do-list
				TimeEntry entry = new TimeEntry();

				// parse each field into appropriate value and add to entry
				entry.setId(Integer.parseInt(idColumn));
				DateFormat format = new SimpleDateFormat("MM/dd/yy", Locale.ENGLISH);
				entry.setDate(format.parse(dateColumn));
				entry.setMondayHours(Double.parseDouble(mondayColumn));
				entry.setTuesdayHours(Double.parseDouble(tuesdayColumn));
				entry.setWednesdayHours(Double.parseDouble(wednesdayColumn));
				entry.setThursdayHours(Double.parseDouble(thursdayColumn));
				entry.setFridayHours(Double.parseDouble(fridayColumn));

				System.out.println(entry);
				// Add item to timeEntryList
				timeEntryList.add(entry);

				// this allows ids to continue incrementing after restarting app
				if (entry.getId() > currentMaxId) {
					currentMaxId = entry.getId();
				}
				currentId = currentMaxId + 1;

			}

		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} catch (IOException e) {
			System.out.println("IO Exception");
		} catch (ParseException e) {
			System.out.println("The date did not parse from String to a date format");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return timeEntryList;
	}

}
