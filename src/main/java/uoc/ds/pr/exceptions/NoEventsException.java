package uoc.ds.pr.exceptions;

public class NoEventsException extends DSException {
    public NoEventsException() {
        super("No events found");
    }

    public NoEventsException(String msg) {
        super(msg);
    }
}
