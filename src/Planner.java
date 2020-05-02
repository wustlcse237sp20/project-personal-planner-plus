package src;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Planner
{
    static List<Event> events;
    static Set<String> tagSet;
    static Integer nextId;
    static final String DATA_FILE_NAME = "./bin/data.txt";

    public static void initializeVars() {
    	events = new ArrayList<Event>();
    	tagSet = new HashSet<String>();
    	nextId = 0;
    }

    // Note: writeDatat() and loadData() adapted from:
    // https://mkyong.com/java/how-to-read-and-write-java-object-to-a-file/

    public static void writeData() {
        File eventFile = new File(DATA_FILE_NAME);
        if (eventFile.exists()) {
            eventFile.delete();
        }
        try{
            FileOutputStream fileStream = new FileOutputStream(eventFile);
            ObjectOutputStream objStream = new ObjectOutputStream(fileStream);
            
            objStream.writeObject(events);
            objStream.writeObject(tagSet);
            objStream.writeObject(nextId);

            objStream.close();
            fileStream.close();
        }
        catch(FileNotFoundException e) {
            e.printStackTrace();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadData() {
        File eventFile = new File(DATA_FILE_NAME);
        if(eventFile.exists()) {
            try
            {
                FileInputStream fileInput = new FileInputStream(eventFile);
                ObjectInputStream objectInput = new ObjectInputStream(fileInput);

                events = (ArrayList<Event>)objectInput.readObject(); // TODO: ask Prof about unchecked cast warning 
                tagSet = (Set<String>)objectInput.readObject();
                nextId = (Integer)objectInput.readObject();

                objectInput.close();
                fileInput.close();
            }
            catch(FileNotFoundException e) {
                e.printStackTrace();
            }
            catch(IOException e) {
                e.printStackTrace();
            }
            catch(ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        else{
            events = new ArrayList<Event>();
            tagSet = new HashSet<String>();
            nextId = 0;
        }
    }
    
    public static void addEvent(String name, LocalDateTime startDate, LocalDateTime endDate, List<String> tags, String details) {
        Event event = new Event(name, startDate, endDate, tags, details, nextId);
        nextId++;
        events.add(event);
        Collections.sort(events); // TODO ask prof about unchecked sort invocation
        for(String tag:tags) {
            tagSet.add(tag);
        }
    }
    
    public static List<Event> getEvents() {
    	return events;
    }

    public static List<Event> getFilteredEvents(String filter) {
        if (filter.equals("all")) {
            return events;
        }
        List<Event> filtered = new ArrayList<Event>();
        for (Event e : events) {
            for (String tag : e.getTags()) {
                if (tag.equals(filter)) {
                    filtered.add(e);
                    continue;
                }
            }
        }
        return filtered;
    }

    public static void editEvent(int index, Event replacementEvent) {
    	events.set(index, replacementEvent);
    }
    
    public static void setEvents(List<Event> newEvents) {
        events = newEvents;
    }

    public static Set<String> getTagSet() {
        return tagSet;
    }
    
    public static void removeTag(String tag) {
    	tagSet.remove(tag);
    }
}
