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
	List<TimeEntry> timeEntryList;

	public TimeTrackerRepository() {
		timeEntryList = new ArrayList<TimeEntry>();
	}

	public void writeTempTimeSheet(TimeEntry entry) {
		System.out.println("writeTempTimeSheet");
		int tempId = 1;

		// Create csv file
		try (FileWriter writer = new FileWriter("timesheet_week_temp.csv", false); // false makes this overwrite
				CSVPrinter printer = CSVFormat.DEFAULT.print(writer)) {

			entry.setId(tempId);

			entry.setSubmittedStatus(false);

			String[] record = { Integer.toString(entry.getId()), entry.getDateString(),
					Double.toString(entry.getMondayHours()), Double.toString(entry.getTuesdayHours()),
					Double.toString(entry.getWednesdayHours()), Double.toString(entry.getThursdayHours()),
					Double.toString(entry.getFridayHours()), Double.toString(entry.getTotalHours()) };
			printer.printRecord(record);

		} catch (IOException e) {
			System.out.println("Error on create method.");
		}

	}

	public TimeEntry getTempTimeSheet() {

		TimeEntry entry = new TimeEntry();
		int currentMaxId = 0;
		// store to-do-list in a reader
		try (Reader in = new FileReader("timesheet_week_temp.csv");
				CSVParser parser = new CSVParser(in, CSVFormat.DEFAULT)) {

			// get all the items from the csv file and store into CSVRecord list
			List<CSVRecord> csvList = parser.getRecords();

			for (CSVRecord csvRecord : csvList) {
				// parse each field into appropriate value and add to entry
				entry.setId(Integer.parseInt(csvRecord.get(0)));
				// DateFormat format = new SimpleDateFormat("MM/dd/yy", Locale.ENGLISH);
				// entry.setDate(format.parse(csvRecord.get(1)));
				entry.setDateString(csvRecord.get(1));
				entry.setMondayHours(Double.parseDouble(csvRecord.get(2)));
				entry.setTuesdayHours(Double.parseDouble(csvRecord.get(3)));
				entry.setWednesdayHours(Double.parseDouble(csvRecord.get(4)));
				entry.setThursdayHours(Double.parseDouble(csvRecord.get(5)));
				entry.setFridayHours(Double.parseDouble(csvRecord.get(6)));

				System.out.println(entry);
			}

		} catch (FileNotFoundException e) {
			System.err.println("Could not find file");

		} catch (IOException e) {
			System.err.println("Could not read file");
		}
		return entry;
		// return timeEntryList;
	}

	public void writeToTimeSheetHistory(TimeEntry entry) {
		System.out.println("writeTempTimeSheet");

		// Create csv file
		try (FileWriter writer = new FileWriter("timesheet_history.csv", true); // true makes this append
				CSVPrinter printer = CSVFormat.DEFAULT.print(writer)) {

			entry.setId(nextId);
			nextId += 1;
			entry.setSubmittedStatus(true);

			String[] record = { Integer.toString(entry.getId()), entry.getDateString(),
					Double.toString(entry.getMondayHours()), Double.toString(entry.getTuesdayHours()),
					Double.toString(entry.getWednesdayHours()), Double.toString(entry.getThursdayHours()),
					Double.toString(entry.getFridayHours()), Double.toString(entry.getTotalHours()) };
			printer.printRecord(record);
		} catch (IOException e) {
			System.out.println("Error on create method.");
		}

	}
	
	public List<TimeEntry> getTimeSheetHistory() {
		List<TimeEntry> timeEntryList = new ArrayList<TimeEntry>();
		int currentMaxId = 0;
		// store to-do-list in a reader
		try (Reader in = new FileReader("timesheet_history.csv"); 
		CSVParser parser = new CSVParser(in, CSVFormat.DEFAULT)) {

			// get all the items from the csv file and store into CSVRecord list
			List<CSVRecord> list = parser.getRecords();

			for (CSVRecord csvRecord : list) {

				// add each of these variable values to to-do-list
				TimeEntry entry = new TimeEntry();

				// parse each field into appropriate value and add to entry
				entry.setId(Integer.parseInt(csvRecord.get(0)));
				entry.setDateString(csvRecord.get(1));
				entry.setMondayHours(Double.parseDouble(csvRecord.get(2)));
				entry.setTuesdayHours(Double.parseDouble(csvRecord.get(3)));
				entry.setWednesdayHours(Double.parseDouble(csvRecord.get(4)));
				entry.setThursdayHours(Double.parseDouble(csvRecord.get(5)));
				entry.setFridayHours(Double.parseDouble(csvRecord.get(6)));
				// add this entry to Entrylist 
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
		}
		return timeEntryList;
	}

}
