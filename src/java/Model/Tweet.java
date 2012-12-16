/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Utils.Months;
import com.google.code.morphia.Datastore;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.NotSaved;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import org.bson.types.ObjectId;

public class Tweet implements Serializable {

    @Id
    private ObjectId id;
    private String source;
    private User source_user;
    private String text;
    private String target;
    private String monthYear;
    private Date date;
    @NotSaved
    private Datastore ds;

    public Tweet() {
    }


    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
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
        this.target = target.replaceAll("\\@", "").trim();
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
        return Months.getMonths(month) + " " + year;
    }

    public User getSource_user() {
        return this.source_user;
    }

    public void setSource_user(User source_user) {
        this.source_user = source_user;
    }
}
