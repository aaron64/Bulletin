package net.aaronchambers.bulletin;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BulletinPost {

    private String name, date;

    public BulletinPost(String name, String date) {
        this.name = name;
        setDate(date);
    }

    public void setName(String n) {
        this.name = n;
    }

    public void setDate(String d) {
        this.date = d;

        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");

        DateFormat dateFormat = new SimpleDateFormat("MM-dd-yy");
        DateFormat timeFormat = new SimpleDateFormat("hh:mm a");

        Date dateInput = null;
        try {
            dateInput = inputFormat.parse(this.date);
        } catch (ParseException e) {

        }

        Date recent = new Date(System.currentTimeMillis() - (24 * 60 * 60 * 1000));
        
        if(dateInput.after(recent)) {
            this.date = timeFormat.format(dateInput);
        } else {
            this.date = dateFormat.format(dateInput);
        }
    }

    public String getName() {
        return this.name;
    }

    public String getDate() {
        return this.date;
    }
}
