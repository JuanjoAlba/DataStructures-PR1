package uoc.ds.pr.model;

import edu.uoc.ds.adt.sequential.LinkedList;
import edu.uoc.ds.adt.sequential.QueueArrayImpl;
import edu.uoc.ds.traversal.Iterator;

public class Event {

    private String eventId;
    private EventRequest request;
    private LinkedList<Rating> ratings = new LinkedList<>();
    private QueueArrayImpl<Attendee> registeredAttendees = new QueueArrayImpl<>();
    private QueueArrayImpl<Attendee> registeredSubstitutes = new QueueArrayImpl<>();

    public Event(String id, EventRequest request) {
        setEventId(id);
        setRequest(request);
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public void addRating (Rating rating) {
        this.ratings.insertEnd(rating);
    }

    public LinkedList<Rating> getRatings() {
        return ratings;
    }

    public EventRequest getRequest() {
        return request;
    }

    public void setRequest(EventRequest request) {
        this.request = request;
    }

    public void addAttendee (Attendee attendee) {
        this.registeredAttendees.add(attendee);
    }

    public QueueArrayImpl<Attendee> getRegisteredAttendees() {
        return registeredAttendees;
    }

    public boolean isAttendeeRegistered (String attendeeId) {
        boolean registered = false;
        Iterator<Attendee> iter = this.registeredAttendees.values();
        while (iter.hasNext()) {
            Attendee attendee = iter.next();
            if (attendee.getId().equals(attendeeId)) {
                registered = true;
                break;
            }
        }
        return registered;
    }

    public void addSubstitute (Attendee attendee) {
        this.registeredSubstitutes.add(attendee);
    }

    public double rating () {
        if (this.ratings.isEmpty()) return 0;
        int totalRating = 0;
        Iterator<Rating> iter = this.ratings.values();
        while (iter.hasNext()) {
            totalRating += iter.next().rating.getValue();
        }
        return totalRating / this.ratings.size();
    }
}
