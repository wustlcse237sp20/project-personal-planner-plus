import java.util.Date;
import java.util.ArrayList;

public class Event implements Comparable
{
    private String name;
    private Date startDate;
    private Date endDate;
    private ArrayList<String> tags;
    private int id;

    public Event(String name,  Date startDate, Date endDate, ArrayList<String> tags, int id)
    {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.tags = tags;
        this.id = id;
    }

    public String getName(){
        return this.name;
    }

    public Date getStartDate(){
        return this.startDate;
    }

    public Date getEndDate(){
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

    public void setStartDate(Date startDate){
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate){
        this.endDate = endDate;
    }

    public void addTag(String tag){
        this.tags.add(tag);
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
