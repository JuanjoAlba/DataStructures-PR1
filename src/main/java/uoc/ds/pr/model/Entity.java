package uoc.ds.pr.model;

import edu.uoc.ds.adt.sequential.LinkedList;
import uoc.ds.pr.UniversityEvents;

public class Entity {
    private String id;
    private String name;
    private String description;
    private LinkedList<Event> eventsOrganized = new LinkedList<>();

    public Entity(String id, String name, String description) {
        setId(id);
        setName(name);
        setDescription(description);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void addEventsOrganized(Event eventsOrganized) {
        this.eventsOrganized.insertEnd(eventsOrganized);
    }

    public LinkedList<Event> getEventsOrganized() {
        return eventsOrganized;
    }
}
