package uoc.ds.pr.exceptions;

public class AttendeeAlreadyInEventException extends DSException {
    public AttendeeAlreadyInEventException() {
        super("The attendee is already registered for this event");
    }
}
