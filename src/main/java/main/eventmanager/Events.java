package main.eventmanager;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Events {
    private final StringProperty id;
    private final StringProperty eventName;
    private final StringProperty location;
    private final StringProperty eventDate;
    private final StringProperty imagePath;
    private final StringProperty description;

    public Events(String eventId, String eventName, String location, String eventDate, String imagePath, String description) {
        this.id = new SimpleStringProperty(this, "id", eventId);
        this.eventName = new SimpleStringProperty(this, "eventName", eventName);
        this.location = new SimpleStringProperty(this, "location", location);
        this.eventDate = new SimpleStringProperty(this, "eventDate", eventDate);
        this.imagePath = new SimpleStringProperty(this, "imagePath", imagePath);
        this.description = new SimpleStringProperty(this, "description", description);
    }

    // --- ID ---
    public StringProperty idProperty() { return id; }
    public String getId() { return id.get(); }
    public void setId(String newId) { id.set(newId); }

    // --- Event Name ---
    public StringProperty eventNameProperty() { return eventName; }
    public String getEventName() { return eventName.get(); }
    public void setEventName(String newEventName) { eventName.set(newEventName); }

    // --- Location ---
    public StringProperty locationProperty() { return location; }
    public String getLocation() { return location.get(); }
    public void setLocation(String newLocation) { location.set(newLocation); }

    // --- Event Date ---
    public StringProperty eventDateProperty() { return eventDate; }
    public String getEventDate() { return eventDate.get(); }
    public void setEventDate(String newEventDate) { eventDate.set(newEventDate); }

    // --- Image Path ---
    public StringProperty imagePathProperty() { return imagePath; }
    public String getImagePath() { return imagePath.get(); }
    public void setImagePath(String newImagePath) { imagePath.set(newImagePath); }

    // --- Description ---
    public StringProperty descriptionProperty() { return description; }
    public String getDescription() { return description.get(); }
    public void setDescription(String newDescription) { description.set(newDescription); }

    @Override
    public String toString() {
        return String.format(
                "Event[id=%s, eventName=%s, location=%s, eventDate=%s, imagePath=%s, description=%s]",
                getId(), getEventName(), getLocation(), getEventDate(), getImagePath(), getDescription()
        );
    }
}
