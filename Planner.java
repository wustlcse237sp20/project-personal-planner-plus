import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Planner
{
    static List<Event> events;
    static Set<String> tagSet;
    static Integer nextId;

    public static void addEvent(String name, Date startDate, Date endDate, ArrayList<String> tags){
        Event event = new Event(name, startDate, endDate, tags, nextId);
        nextId++;
        events.add(event);
        Collections.sort(events); // TODO ask prof about unchecked sort invocation
        for(String tag:tags){
            tagSet.add(tag);
        }
    }   
}
