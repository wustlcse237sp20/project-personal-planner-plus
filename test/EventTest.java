package test;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import src.Event;

public class EventTest 
{
    @Test
    public void testCompare(){
        Event event1 = new Event("event 1",  LocalDateTime.now(), LocalDateTime.now().plusMinutes(5), new ArrayList<String>(), "Test Details", 0);
        Event event2 = new Event("event 2", LocalDateTime.now().plusMinutes(5), LocalDateTime.now().plusMinutes(10), new ArrayList<String>(), "Test Details 2", 0);

        assertTrue("event1 not < event2 when event1 start time precedes event2 start time", event1.compareTo(event2) < 0);

        event2.setStartDate(event1.getStartDate());
        event2.setEndDate(event1.getEndDate().minusMinutes(5));
        assertTrue("event2 not < event1 when start times equal, event2 end time precedes event1 end time", event2.compareTo(event1) < 0);

        event2.setEndDate(event1.getEndDate());
        assertTrue("event1 not < event2 when start, end times equal", event1.compareTo(event2) < 0);

        event2.setName("event 1");
        assertTrue("event1 != event2 when startTime, endTime, names equal", event1.compareTo(event2) == 0);
    }
}