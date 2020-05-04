package test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

import org.junit.Before;
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
		String details = "Test Details";
		tags.add("test-tag");
		tags.add("test-tag1");
		
		Planner.addEvent(eventName, startDate, endDate, tags, details);
		assertTrue("Calling Planner.addEvent() once did not add one event to Planner's event list", Planner.getEvents().size()==1);
		
		Event event = Planner.getEvents().get(0);
		
		assertTrue("Event name was added wrong", event.getName().equals(eventName));
		assertTrue("Event startDate was added wrong", event.getStartDateTime().equals(startDate));
		assertTrue("Event endDate was added wrong", event.getEndDateTime().equals(endDate));
		assertTrue("Event tags were added wrong", event.getTags().equals(tags));
		assertTrue("Event details were added wrong", event.getDetails().equals(details));		
	}

	@Test
	public void testLoadData(){
		Planner.initializeVars();
		String eventName = "testEvent";
		LocalDateTime startDate= LocalDateTime.now();
		LocalDateTime endDate = LocalDateTime.now().plusMinutes(5);
		ArrayList<String> tags = new ArrayList<String>();
		String details = "Test Details";
		tags.add("test-tag");
		tags.add("test-tag1");
		
		Planner.addEvent(eventName, startDate, endDate, tags, details);

		List<Event> eventsList = Planner.getEvents();
		Planner.writeData();
		Planner.setEvents(new ArrayList<Event>());
		Planner.loadData();
		
		assertTrue("Data was not loaded properly", eventsList.equals(Planner.getEvents()));					
	}
	
	@Test
	public void testGetFilteredEvents() {
		Planner.initializeVars();
		
		String eventName = "testEvent";
		LocalDateTime startDate= LocalDateTime.now();
		LocalDateTime endDate = LocalDateTime.now().plusMinutes(5);
		ArrayList<String> tags = new ArrayList<String>();
		String details = "Test Details";
		tags.add("test-tag");
		tags.add("test-tag1");
		
		Planner.addEvent(eventName, startDate, endDate, tags, details);
		
		String eventName1 = "testEvent";
		LocalDateTime startDate1= LocalDateTime.now();
		LocalDateTime endDate1 = LocalDateTime.now().plusMinutes(5);
		ArrayList<String> tags1 = new ArrayList<String>();
		String details1 = "Test Details";
		tags.add("test-tag");
		
		Planner.addEvent(eventName1, startDate1, endDate1, tags1, details1);
		
		String eventName2 = "testEvent";
		LocalDateTime startDate2= LocalDateTime.now();
		LocalDateTime endDate2 = LocalDateTime.now().plusMinutes(5);
		ArrayList<String> tags2 = new ArrayList<String>();
		String details2 = "Test Details";
		tags.add("test-tag1");
		
		Planner.addEvent(eventName2, startDate2, endDate2, tags2, details2);
		
		List<Event> allEvents = Planner.getFilteredEvents("all");
		
		assertTrue("wrong number of events returned", allEvents.size() == 3);		
		
		List<Event> testTagEvents = Planner.getFilteredEvents("test-tag");
		
		assertTrue("wrong number of events returned", testTagEvents.size() == 2);
		for (Event e : testTagEvents) {
			assertTrue("event does not contain the given tag", e.getTags().contains("test-tag"));
		}
		
		List<Event> testTag1Events = Planner.getFilteredEvents("test-tag1");
		
		assertTrue("wrong number of events returned", testTag1Events.size() == 2);
		for (Event e : testTag1Events) {
			assertTrue("event does not contain the given tag", e.getTags().contains("test-tag1"));
		}
		
		List<Event> noEvents = Planner.getFilteredEvents("tag");
		assertTrue("wrong number of events returned", noEvents.size() == 0);
	}
	
	@Test
	public void testEditEvent() {
		Planner.initializeVars();
		
		String eventName = "testEvent";
		LocalDateTime startDate= LocalDateTime.now();
		LocalDateTime endDate = LocalDateTime.now().plusMinutes(5);
		ArrayList<String> tags = new ArrayList<String>();
		String details = "Test Details";
		tags.add("test-tag");
		tags.add("test-tag1");
		
		Event eventToAdd = new Event(eventName, startDate, endDate, tags, details, 0);
		
		Planner.addEvent(eventName, startDate, endDate, tags, details);
		
		Event originalEvent = Planner.getEvents().get(0);
		
		assertTrue("Original Event not added correctly", originalEvent.equals(eventToAdd));
		
		String eventName1 = "testEventEdited";
		LocalDateTime startDate1= LocalDateTime.now().plusMinutes(2);
		LocalDateTime endDate1 = LocalDateTime.now().plusMinutes(10);
		ArrayList<String> tags1 = new ArrayList<String>();
		String details1 = "Test Details Edited";
		tags.add("test-tag");
		
		Event newEvent = new Event(eventName1, startDate1, endDate1, tags1, details1, 0);
		
		Planner.editEvent(0, newEvent);
		
		Event editedEvent = Planner.getEvents().get(0);
		
		assertTrue("Event not updated correctly", newEvent.equals(editedEvent));
	}
	
	@Test
	public void testDeleteEvent() {
		Planner.initializeVars();
		
		String eventName = "testEvent";
		LocalDateTime startDate= LocalDateTime.now();
		LocalDateTime endDate = LocalDateTime.now().plusMinutes(5);
		ArrayList<String> tags = new ArrayList<String>();
		String details = "Test Details";
		tags.add("test-tag");
		tags.add("test-tag1");
		
		Planner.addEvent(eventName, startDate, endDate, tags, details);
		
		Planner.addEvent("to delete", startDate.plusMinutes(1), endDate, tags, details);
		
		assertTrue("Events not added correctly", Planner.getEvents().size() == 2);
		
		Planner.deleteEvent(1);
		
		assertTrue("No event was deleted", Planner.getEvents().size() == 1);
		
		assertTrue("Incorrect event was deleted", Planner.getEvents().get(0).getName().equals(eventName));
	}
	
	@Test
	public void testRemoveUnusedTags() {
		Planner.initializeVars();
		
		String eventName = "testEvent";
		LocalDateTime startDate= LocalDateTime.now();
		LocalDateTime endDate = LocalDateTime.now().plusMinutes(5);
		ArrayList<String> tags = new ArrayList<String>();
		String details = "Test Details";
		tags.add("test-tag");
		tags.add("test-tag1");

		ArrayList<String> tags1 = new ArrayList<String>();
		tags1.add("test-tag");
		
		Planner.addEvent(eventName, startDate, endDate, tags, details);
		
		Planner.addEvent(eventName, startDate.plusMinutes(1), endDate, tags1, details);
		assertTrue("Tags were not added correctly", Planner.getTagSet().size() == 2);
		assertTrue("Tags were not added correctly", Planner.getTagSet().containsAll(tags));
		
		Planner.deleteEvent(0);
		assertTrue("Unused tags were not removed from tagset", Planner.getTagSet().size() == 1);
		assertTrue("Incorrect tag was removed from tagset", Planner.getTagSet().contains("test-tag"));
		
		ArrayList<String> tags2 = new ArrayList<String>();
		tags2.add("editedTag");
		Event editedEvent = new Event(eventName, startDate, endDate, tags2, details, 0);
		Planner.editEvent(0, editedEvent);
		assertTrue("Old tags were not removed on edit", Planner.getTagSet().size()==1);
		assertTrue("Tags were not correctly updated on edit", Planner.getTagSet().containsAll(tags2));
	}
}