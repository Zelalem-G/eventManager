package model;

import java.time.LocalDate;

public class Event {
    private int eventId;
    private String eventName;
    private String description;
    private String location;
    private LocalDate eventDate;
    private String imagePath;
    private int createdBy;

    // Constructor
    public Event(int eventId, String eventName) {
        this.eventId = eventId;
        this.eventName = eventName;
    }

    public Event(int eventId, String eventName, String description, String location,
                 LocalDate eventDate, String imagePath, int createdBy) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.description = description;
        this.location = location;
        this.eventDate = eventDate;
        this.imagePath = imagePath;
        this.createdBy = createdBy;
    }

    // Getters and Setters
    @Override
    public String toString() {
        return eventId + ", " + eventName;
    }


    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }
}

