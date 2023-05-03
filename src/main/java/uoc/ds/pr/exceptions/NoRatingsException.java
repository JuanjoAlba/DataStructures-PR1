package uoc.ds.pr.exceptions;

public class NoRatingsException extends DSException {
    public NoRatingsException() {
        super("No ratings for that event");
    }
}
