package uoc.ds.pr.exceptions;

public class EntityNotFoundException extends DSException {

    public EntityNotFoundException() {
        super("Entity not found in the system");
    }
}
