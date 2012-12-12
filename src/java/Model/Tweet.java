/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import com.google.code.morphia.annotations.Id;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import org.bson.types.ObjectId;

public class Tweet implements Serializable {

    @Id
    private ObjectId id;
    private Author author;
    private String text;
    private String target;
    private String monthYear;
    private Date date;
    private String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

    public Tweet() {
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target.replaceAll("\\@", "");
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setMonthYear(String monthYear) {
        this.monthYear = monthYear;
    }

    
    public String getMonthYear() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);
        return months[month] + " " + year;
    }
}
