package src;
import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Event implements Comparable, Serializable{
	private static final long serialVersionUID = 4L;
	private String name;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private List<String> tags;
    private String details;
    private int id;

    public Event(String name,  LocalDateTime startDate, LocalDateTime endDate, List<String> tags, String details, int id){
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.tags = tags;
        this.details = details;
        this.id = id;
    }

    public String getName(){
        return this.name;
    }

    public LocalDateTime getStartDate(){
        return this.startDate;
    }

    public LocalDateTime getEndDate(){
        return this.endDate;
    }

    public List<String> getTags(){
        return this.tags;
    }

    public String getDetails(){
        return this.details;
    }

    public int getId(){
        return this.id;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setStartDate(LocalDateTime startDate){
        this.startDate = startDate;
    }

    public void setEndDate(LocalDateTime endDate){
        this.endDate = endDate;
    }

    public void addTag(String tag){
        this.tags.add(tag);
    }

    public void setDetails(String details){
        this.details = details;
    }

    public String toString(){
        int currentYear = LocalDateTime.now().getYear();
        DateTimeFormatter formatter;
        if(this.startDate.getYear()/1000 != currentYear/1000) // not in current millenium
        {
            formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        }
        else if(this.startDate.getYear() != currentYear) // not in current yr, but in same millenium
        {
            formatter = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm");
        }
        else // in current yr
        {
            formatter = DateTimeFormatter.ofPattern("dd/MM HH:mm");
        }
        return startDate.format(formatter) + "\t" + name;
    }

//    for debugging purposes
    public String allFields() {
        return new String(
                this.name + "\n"
        		+ this.startDate + "\n"
        		+ this.endDate + "\n"
        		+ this.tags
        		+ "\n"
        		+ this.details + "\n" 
        		+ this.id);
    }
    
    @Override
    public int compareTo(Object o) {
        Event otherEvent = (Event)o;
        // earlier start date < later start date
        // if start dates equal, earlier end date < later end date
        // if end dates equal, compare names
        int startDateComp = this.startDate.compareTo(otherEvent.startDate);
        if(startDateComp == 0){
            int endDateComp = this.endDate.compareTo(otherEvent.endDate);
            if(endDateComp == 0){
                return this.name.compareTo(otherEvent.name);
            }
            return endDateComp;
        }
        return startDateComp;
    }
    
    public boolean equals(Object o) {
    	Event otherEvent;
    	if(o instanceof Event) {
    		otherEvent = (Event)o;
    	}
    	else {
    		return false;
    	}
    	
    	if(!this.name.equals(otherEvent.name)) {
    		System.out.println("this.name (" + this.name + ") != otherEvent.name (" + otherEvent.name + ")");
    	}
    	if(!this.startDate.equals(otherEvent.startDate)) {
    		System.out.println("this.startDate (" + this.startDate + ") != otherEvent.startDate (" + otherEvent.startDate + ")");
    	}
    	if(!this.endDate.equals(otherEvent.endDate)) {
    		System.out.println("this.endDate (" + this.endDate + ") != otherEvent.endDate (" + otherEvent.endDate + ")");
    	}
    	if(!this.tags.equals(otherEvent.tags)) {
    		System.out.println("this.tags (" + this.tags + ") != otherEvent.tags (" + otherEvent.tags + ")");
    	}
    	if(!this.details.equals(otherEvent.details)) {
    		System.out.println("this.details (" + this.details + ") != otherEvent.details (" + otherEvent.details + ")");
    	}
    	if(this.id != otherEvent.id) {
    		System.out.println("this.id (" + this.id + ") != otherEvent.startDate (" + otherEvent.id + ")");
    	}
    	
        return this.name.equals(otherEvent.name) && 
        	   this.startDate.equals(otherEvent.startDate) &&
        	   this.endDate.equals(otherEvent.endDate) &&
        	   this.tags.equals(otherEvent.tags) &&
        	   this.details.equals(otherEvent.details) &&
        	   this.id == otherEvent.id;
    }
    
}
