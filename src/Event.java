package src;

import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.lang.StringBuilder;

public class Event implements Comparable, Serializable {
    private static final long serialVersionUID = 4L;
    private String name;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private List<String> tags;
    private String details;
    private int id;

    public Event(String name, LocalDateTime startDate, LocalDateTime endDate, List<String> tags, String details,
            int id) {
        this.name = name;
        this.startDateTime = startDate;
        this.endDateTime = endDate;
        this.tags = tags;
        this.details = details;
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public LocalDateTime getStartDateTime() {
        return this.startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return this.endDateTime;
    }

    public List<String> getTags() {
        return this.tags;
    }

    public String getDetails() {
        return this.details;
    }

    public int getId() {
        return this.id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDateTime = startDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDateTime = endDate;
    }

    public void addTag(String tag) {
        this.tags.add(tag);
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String formatDate(LocalDateTime dateTime){
        int currentYear = LocalDateTime.now().getYear();
        DateTimeFormatter formatter;
        if(dateTime.getYear()/1000 != currentYear/1000){ // not in current millenium
            formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        }
        else if(dateTime.getYear() != currentYear) { // not in current yr, but in same millenium
            formatter = DateTimeFormatter.ofPattern("MM/dd/yy");
        }
        else { // in current yr
            formatter = DateTimeFormatter.ofPattern("MM/dd");
        }
        return dateTime.format(formatter);
    }

    public String formatTime(LocalDateTime dateTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return dateTime.format(formatter);
    }

    public String toString(){
        return formatDate(startDateTime) + " " + formatTime(startDateTime) + "\t" + name;
    }

    public String getDetailsString(){
        String startDateString = formatDate(startDateTime);
        String endDateString = formatDate(endDateTime);
        StringBuilder detailsString = new StringBuilder(name + "\n");
        if (startDateString.equals(endDateString)){
             detailsString.append(startDateString + "\t" + formatTime(startDateTime) + " - " + formatTime(endDateTime) + "\n" + "Tags:\t");
        }
        else{
            detailsString.append(startDateString + " " + formatTime(startDateTime) + " - " + endDateString + " " + formatTime(endDateTime) + "\n" + "Tags:\t");
        }
        for(int i = 0; i < tags.size() - 1; i++) {
        	detailsString.append(tags.get(i) + ", ");
        }
        detailsString.append(tags.get(tags.size() - 1));
        detailsString.append("\n" + details);
        return detailsString.toString();
    }

    @Override
    public int compareTo(Object o) {
        Event otherEvent = (Event) o;
        // earlier start date < later start date
        int startDateComp = this.startDateTime.compareTo(otherEvent.startDateTime);
        if (startDateComp == 0) {
            int endDateComp = this.endDateTime.compareTo(otherEvent.endDateTime);
            if (endDateComp == 0) {
                return this.name.compareTo(otherEvent.name);
            }
            return endDateComp;
        }
        return startDateComp;
    }

    public boolean equals(Object o) {
        Event otherEvent;
        if (o instanceof Event) {
            otherEvent = (Event) o;
        } else {
            return false;
        }

        if (!this.name.equals(otherEvent.name)) {
            System.out.println("this.name (" + this.name + ") != otherEvent.name (" + otherEvent.name + ")");
        }
        if (!this.startDateTime.equals(otherEvent.startDateTime)) {
            System.out.println("this.startDate (" + this.startDateTime + ") != otherEvent.startDate (" + otherEvent.startDateTime + ")");
        }
        if (!this.endDateTime.equals(otherEvent.endDateTime)) {
            System.out.println("this.endDate (" + this.endDateTime + ") != otherEvent.endDate (" + otherEvent.endDateTime + ")");
        }
        if (!this.tags.equals(otherEvent.tags)) {
            System.out.println("this.tags (" + this.tags + ") != otherEvent.tags (" + otherEvent.tags + ")");
        }
        if (!this.details.equals(otherEvent.details)) {
            System.out.println("this.details (" + this.details + ") != otherEvent.details (" + otherEvent.details + ")");
        }
        if (this.id != otherEvent.id) {
            System.out.println("this.id (" + this.id + ") != otherEvent.startDate (" + otherEvent.id + ")");
        }

        return this.name.equals(otherEvent.name) &&
            this.startDateTime.equals(otherEvent.startDateTime) &&
            this.endDateTime.equals(otherEvent.endDateTime) &&
            this.tags.equals(otherEvent.tags) &&
            this.details.equals(otherEvent.details) &&
            this.id == otherEvent.id;
    }

}
