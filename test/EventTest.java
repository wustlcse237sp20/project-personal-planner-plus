package test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import src.Event;

public class EventTest 
{
    @Test
    public void testCompare() {
        Event event1 = new Event("event 1",  LocalDateTime.now(), LocalDateTime.now().plusMinutes(5), new ArrayList<String>(), "Test Details", 0);
        Event event2 = new Event("event 2", LocalDateTime.now().plusMinutes(5), LocalDateTime.now().plusMinutes(10), new ArrayList<String>(), "Test Details 2", 0);

        assertTrue("event1 not < event2 when event1 start time precedes event2 start time", event1.compareTo(event2) < 0);

        event2.setStartDate(event1.getStartDateTime());
        event2.setEndDate(event1.getEndDateTime().minusMinutes(5));
        assertTrue("event2 not < event1 when start times equal, event2 end time precedes event1 end time", event2.compareTo(event1) < 0);

        event2.setEndDate(event1.getEndDateTime());
        assertTrue("event1 not < event2 when start, end times equal", event1.compareTo(event2) < 0);

        event2.setName("event 1");
        assertTrue("event1 != event2 when startTime, endTime, names equal", event1.compareTo(event2) == 0);
    }
    
    @Test
    public void testDetailsString() {
    	String name = "event";
    	LocalDateTime start = LocalDate.now().atTime(6, 0);
    	LocalDateTime end = LocalDate.now().atTime(6, 5);
    	List<String> tags = Arrays.asList(new String[]{"tag1", "tag2", "tag3"});
    	String details = "event details";
    	Event event = new Event(name, start, end, tags, details, 0);
    	
    	String detailsString = event.getDetailsString();
    	String[] detailsSplit = detailsString.split("\n");
    	assertTrue("first part of details string should be name", detailsSplit[0].equals(name));

    	try {
    		String[] secondLineSplit = detailsSplit[1].split("\t");
    		DateTimeFormatter dateFormatter1 = DateTimeFormatter.ofPattern("MM/dd");
	    	DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
	    	
	    	assertTrue("second part of details string should start with startDate in MM/dd format", secondLineSplit[0].equals(start.toLocalDate().format(dateFormatter1)));
	    	String times = start.toLocalTime().format(timeFormatter) + " - " + end.toLocalTime().format(timeFormatter);
	    	assertTrue("second part of details string should include `startTime - endTime` with times in HH:mm format", secondLineSplit[1].equals(times));
    	}
    	catch(IndexOutOfBoundsException e) {
    		fail("detailString does not have a second line");
    	}
    	
    	try {
    		String[] thirdLineSplit = detailsSplit[2].split("\t");
    		assertTrue("third line should start with `Tags:`", thirdLineSplit[0].equals("Tags:"));
    		String tagString = String.join(", ", tags);
    		assertTrue("third line does not list tags correctly", thirdLineSplit[1].contentEquals(tagString));
    	}
    	catch(IndexOutOfBoundsException e) {
    		fail("detailString does not have a third line");
    	}
    	
    	try {
    		assertTrue("fourth line does not equal event details", detailsSplit[3].equals(details));    		
    	}
    	catch(IndexOutOfBoundsException e) {
    		fail("detailString does not have a fourth line");
    	}
    }
    
    
}