package uoc.ds.pr.model;

import uoc.ds.pr.UniversityEvents;

public class Organism extends Entity {

    UniversityEvents.EntityType entityType = UniversityEvents.EntityType.OTHER;
    public Organism(String id, String name, String description) {
        super(id, name, description);
    }
}
