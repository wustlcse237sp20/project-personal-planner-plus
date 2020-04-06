package test;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import src.Planner;
import src.Event;

public class PlannerTest 
{
	@Test
	public void testAddEvent(){
		Planner.initializeVars();
		assertTrue("Planner list does not start with 0 items", Planner.getEvents().size() == 0);
		
		String eventName = "testEvent";
		LocalDateTime startDate= LocalDateTime.now();
		LocalDateTime endDate = LocalDateTime.now().plusMinutes(5);
		ArrayList<String> tags = new ArrayList<String>();
		tags.add("test-tag");
		tags.add("test-tag1");
		
		Planner.addEvent(eventName, startDate, endDate, tags);
		assertTrue("Calling Planner.addEvent() once did not add one event to Planner's event list", Planner.getEvents().size()==1);
		
		Event event = Planner.getEvents().get(0);
		
		assertTrue("Event name was added wrong", event.getName().equals(eventName));
		assertTrue("Event startDate was added wrong", event.getStartDate().equals(startDate));
		assertTrue("Event endDate was added wrong", event.getEndDate().equals(endDate));
		assertTrue("Event tags were added wrong", event.getTags().equals(tags));		
	}
}