package uoc.ds.pr.model;

import uoc.ds.pr.UniversityEvents;

public class Professor extends Entity {

    UniversityEvents.EntityType entityType = UniversityEvents.EntityType.PROFESSOR;
    public Professor(String id, String name, String description) {
        super(id, name, description);
    }
}
