package uoc.ds.pr.model;

import uoc.ds.pr.UniversityEvents;

public class Rating {

    Attendee attendee;
    Event event;
    UniversityEvents.Rating rating;
    String message;

    public Rating(Attendee attendee, Event event, UniversityEvents.Rating rating, String message) {
        this.attendee = attendee;
        this.event = event;
        this.rating = rating;
        this.message = message;
    }

    public Attendee getAttendee() {
        return attendee;
    }

    public void setAttendee(Attendee attendee) {
        this.attendee = attendee;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public UniversityEvents.Rating getRating() {
        return rating;
    }

    public void setRating(UniversityEvents.Rating rating) {
        this.rating = rating;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
