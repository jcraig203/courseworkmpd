package eu.pendual.mpdcoursework;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by James Craig S1428641
 */

public class Incidents {
    private String title;
    private String description;
    private String urlLink;
    private String location;
    private String author;
    private String comments;
    private Date dateTime;
    private String longitude;
    private String latitude;
    private String datetime;

    public Incidents() {
        this.title = "";
        this.description = "";
        this.urlLink = "";
        this.location = "";
        this.author = "";
        this.comments = "";
        this.dateTime = null;
        this.longitude = "";
        this.latitude = "";
        this.datetime = "";
    }

    public Incidents(String title, String description, String urlLink, String location, String author, String comments, Date dateTime, String longitude, String latitude, String datetime) {
        this.title = title;
        this.description = description;
        this.urlLink = urlLink;
        this.location = location;
        this.author = author;
        this.comments = comments;
        this.dateTime = dateTime;
        this.longitude = longitude;
        this.latitude = latitude;
        this.datetime = datetime;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrlLink() {
        return urlLink;
    }

    public void setUrlLink(String urlLink) {
        this.urlLink = urlLink;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public int getLength() throws ParseException {
        int i = 0;
        String start = description.substring(description.indexOf(":") +1, description.indexOf("<"));
        String first = description.substring(description.indexOf(":", description.indexOf(":", description.indexOf(":")+1)+1));
        String end = first.substring(first.indexOf(":")+1, first.indexOf("<"));
        DateFormat formatv2 = new SimpleDateFormat(" EE, dd MMM yyyy - HH:mm", Locale.ENGLISH);
        Date startTime = formatv2.parse(start);
        Date endTime = formatv2.parse(end);
        Long dateLength = endTime.getTime() - startTime.getTime();
        long diffDays = dateLength / (24 * 60 * 60 * 1000);
        i = (int)diffDays;

        return i;
    }
}
