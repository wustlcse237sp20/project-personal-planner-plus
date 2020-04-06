package src;
import java.util.ArrayList;
import java.util.Collections;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Planner
{
    static List<Event> events;
    static Set<String> tagSet;
    static Integer nextId;

    
    public static void initializeVars() {
    	events = new ArrayList<Event>();
    	tagSet = new HashSet<String>();
    	nextId = 0;
    }
    
    public static void addEvent(String name, LocalDateTime startDate, LocalDateTime endDate, ArrayList<String> tags){
        Event event = new Event(name, startDate, endDate, tags, nextId);
        nextId++;
        events.add(event);
        Collections.sort(events); // TODO ask prof about unchecked sort invocation
        for(String tag:tags){
            tagSet.add(tag);
        }
    }
    
//   For unit testing purposes
    public static List<Event> getEvents() {
    	return events;
    }
}
