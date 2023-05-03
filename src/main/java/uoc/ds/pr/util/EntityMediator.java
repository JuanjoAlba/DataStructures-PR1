package uoc.ds.pr.util;

import uoc.ds.pr.UniversityEvents;
import uoc.ds.pr.model.Entity;
import uoc.ds.pr.model.Organism;
import uoc.ds.pr.model.Professor;
import uoc.ds.pr.model.Student;

public class EntityMediator {

    public static Entity createEntity (String id, String name, String description, UniversityEvents.EntityType entityType) {
        Entity entity = null;
        switch (entityType) {
            case STUDENT -> {
                entity = new Student(id, name, description);
            }
            case PROFESSOR -> {
                entity = new Professor(id, name, description);
            }
            case OTHER -> {
                entity = new Organism(id, name, description);
            }
        }
        return entity;
    }
}
