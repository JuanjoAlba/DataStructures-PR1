package uoc.ds.pr.exceptions;

public class NotAllowedException extends DSException {
    public NotAllowedException() {
        super("This event does not allow registrations");
    }
}
