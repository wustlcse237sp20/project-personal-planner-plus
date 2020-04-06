package src;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Event implements Comparable{
    private String name;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private ArrayList<String> tags;
    private int id;

    public Event(String name,  LocalDateTime startDate, LocalDateTime endDate, ArrayList<String> tags, int id){
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.tags = tags;
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

    public ArrayList<String> getTags(){
        return this.tags;
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
}
