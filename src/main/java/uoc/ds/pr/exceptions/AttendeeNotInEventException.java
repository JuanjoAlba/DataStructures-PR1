package uoc.ds.pr.exceptions;

public class AttendeeNotInEventException extends DSException {
    public AttendeeNotInEventException() {
        super("Attendee not registered in that event");
    }
}
