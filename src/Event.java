package src;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.lang.StringBuilder;

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

    public String formatDate(LocalDateTime dateTime){
        int currentYear = LocalDateTime.now().getYear();
        DateTimeFormatter formatter;
        if(dateTime.getYear()/1000 != currentYear/1000) // not in current millenium
        {
            formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        }
        else if(dateTime.getYear() != currentYear) // not in current yr, but in same millenium
        {
            formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
        }
        else // in current yr
        {
            formatter = DateTimeFormatter.ofPattern("dd/MM");
        }
        return dateTime.format(formatter);
    }

    public String formatTime(LocalDateTime dateTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return dateTime.format(formatter);
    }

    public String toString(){
        return formatDate(startDate) + " " + formatTime(startDate) + "\t" + name;
    }

    public String getDetailsString(){
        String startDateString = formatDate(startDate);
        String endDateString = formatDate(endDate);
        StringBuilder detailsString = new StringBuilder(name + "\n");
        if (startDateString.equals(endDateString)){
             detailsString.append(startDateString + "\t" + formatTime(startDate) + " - " + formatTime(endDate) + "\n" + "Tags:\t");
        }
        else{
            detailsString.append(startDateString +" "+ formatTime(startDate) + " - "+endDateString +" "+ formatTime(endDate) + "\n" + "Tags:\t");
        }
        for (String tag : tags){
            detailsString.append(tag + ", ");
        }
        detailsString.append("\n"+details);
        return detailsString.toString();
    }

//    for debugging purposes
    public String allFields() {
        return new String(this.name + "\n"
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
