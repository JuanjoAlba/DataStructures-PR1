package uoc.ds.pr.model;

import edu.uoc.ds.adt.sequential.LinkedList;

import java.time.LocalDate;

public class Attendee {

    private String id;
    private String name;
    private String surname;
    private LocalDate dateOfBrith;
    private LinkedList<Event> eventsAttended;

    public Attendee(String id, String name, String surname, LocalDate dateOfBrith) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.dateOfBrith = dateOfBrith;
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public LocalDate getDateOfBrith() {
        return dateOfBrith;
    }

    public void setDateOfBrith(LocalDate dateOfBrith) {
        this.dateOfBrith = dateOfBrith;
    }

    public LinkedList<Event> getEventsAttended() {
        return eventsAttended;
    }

    public void addEventsAttended(Event eventAttended) { this.eventsAttended.insertEnd(eventAttended); }

    public int numEvents() { return this.eventsAttended.size(); }
}
