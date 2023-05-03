package uoc.ds.pr.model;

import uoc.ds.pr.UniversityEvents;

public class Student extends Entity {

    UniversityEvents.EntityType entityType = UniversityEvents.EntityType.STUDENT;
    public Student(String id, String name, String description) {
        super(id, name, description);
    }
}
