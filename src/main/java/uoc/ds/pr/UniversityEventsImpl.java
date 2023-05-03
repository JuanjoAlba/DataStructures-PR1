package uoc.ds.pr;

import edu.uoc.ds.adt.sequential.LinkedList;
import edu.uoc.ds.adt.sequential.QueueArrayImpl;
import edu.uoc.ds.traversal.Iterator;
import edu.uoc.ds.traversal.IteratorArrayImpl;
import uoc.ds.pr.exceptions.*;
import uoc.ds.pr.model.*;
import uoc.ds.pr.util.OrderedVector;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * This class implements the functionalities required for the ADT on PR-1
 *
 * @author UOC
 * @author Juan José Alba
 */
public class UniversityEventsImpl implements UniversityEvents {

    private Attendee[] attendees = new Attendee[MAX_NUM_ATTENDEES];
    private Entity[] entities = new Entity[MAX_NUM_ENTITIES];
    private Event[] events = new Event[MAX_NUM_EVENTS];
    private QueueArrayImpl<EventRequest> requests = new QueueArrayImpl<>(MAX_NUM_REQUESTS);
    private LinkedList<EventRequest> requestsRejected;
    private Integer totalRequests = 0;
    private Attendee mostActiveAttendee;
    private OrderedVector<Event> highestRatedEvents;

    @Override
    public void addEntity(String id, String name, String description, EntityType entityType) {
        for (int i = 0; i < entities.length; i++) {
            if (entities[i] != null) {
                if (entities[i].getId().equals(id)) {
                    entities[i].setName(name);
                    entities[i].setDescription(description);
                    entities[i].setEntityType(entityType);
                    break;
                }
            } else {
                // We assume when we find the first null there will be no more entities to check,
                // so we can add the new entity.
                entities[i] = new Entity(id, name, description, entityType);
                break;
            }
        }
    }

    @Override
    public void addAttendee(String id, String name, String surname, LocalDate dateOfBirth) {
        for (int i = 0; i < attendees.length; i++) {
            if (attendees[i] != null) {
                if (attendees[i].getId().equals(id)) {
                    attendees[i].setName(name);
                    attendees[i].setSurname(surname);
                    attendees[i].setDateOfBrith(dateOfBirth);
                    break;
                }
            } else {
                // Same as entities, assume after firs null there will be no more attendees
                attendees[i] = new Attendee(id, name, surname, dateOfBirth);
            }
        }
    }

    @Override
    public void addEventRequest(String id, String eventId, String entityId, String description, InstallationType installationType, byte resources, int max, LocalDate startDate, LocalDate endDate, boolean allowRegister) throws EntityNotFoundException {
        if (getEntity(entityId) == null) throw new EntityNotFoundException();
        requests.add(new EventRequest(id, eventId, entityId, description, installationType, resources, max, startDate, endDate, allowRegister));
        totalRequests += 1;
    }

    @Override
    public EventRequest updateEventRequest(Status status, LocalDate date, String message) throws NoEventRequestException {
        if (requests.isEmpty()) throw new NoEventRequestException();
        EventRequest request = requests.poll();
        request.setStatus(status);
        request.setApprovedOrRejectedDate(date);
        request.setRejectedReason(message);
        if (status.equals(Status.ENABLED)) {
            Event newEvent = new Event(request.getEventId(), request);
            addEvent(newEvent);
        } else {
            requestsRejected.insertEnd(request);
        }
        return request;
    }

    private void addEvent(Event event) {
        for (int i = 0; i < events.length; i++) {
            if (events[i] == null) {
                events[i] = event;
            }
        }
    }

    @Override
    public void signUpEvent(String attendeeId, String eventId) throws AttendeeNotFoundException, EventNotFoundException, NotAllowedException, AttendeeAlreadyInEventException {
        Attendee attendee = getAttendee(attendeeId);
        if (attendee == null) throw new AttendeeNotFoundException();

        Event event = getEvent(eventId);
        if (event == null) throw new EventNotFoundException();

        if (!event.getRequest().isAllowRegister()) throw new NotAllowedException();
        if (event.isAttendeeRegistered(attendeeId)) throw new AttendeeAlreadyInEventException();

        if (event.getRequest().getMaxAttendee() < event.getRegisteredAttendees().size()) {
            event.addAttendee(attendee);
            attendee.getEventsAttended().insertEnd(event);
        } else {
            event.addSubstitute(attendee);
        }
    }

    @Override
    public double getPercentageRejectedRequests() {
        BigDecimal percentage = BigDecimal.valueOf((100*requestsRejected.size())/totalRequests).setScale(2);
        return percentage.doubleValue();
    }

    @Override
    public Iterator<EventRequest> getRejectedRequests() throws NoEventRequestException {
        if (requests.isEmpty()) throw new NoEventRequestException();
        return requests.values();
    }

    @Override
    public Iterator<Event> getEventsByEntity(String entityId) throws NoEventsException {
        Entity entity = getEntity(entityId);
        if (entity.getEventsOrganized().isEmpty()) throw new NoEventsException("No events found for that entity");
        return entity.getEventsOrganized().values();
    }

    @Override
    public Iterator<Event> getAllEvents() throws NoEventsException {
        if (events.length == 0) throw new NoEventsException();
        return new IteratorArrayImpl<Event>(events, 0, events.length);
    }

    @Override
    public Iterator<Event> getEventsByAttendee(String attendeeId) throws NoEventsException {
        Attendee attendee = getAttendee(attendeeId);
        if (attendee.getEventsAttended().isEmpty()) throw new NoEventsException("No events attended by this person");
        return attendee.getEventsAttended().values();
    }

    @Override
    public void addRating(String attendeeId, String eventId, Rating rating, String message) throws AttendeeNotFoundException, EventNotFoundException, AttendeeNotInEventException {
        Attendee attendee = getAttendee(attendeeId);
        if (attendee == null) throw new AttendeeNotFoundException();

        Event event = getEvent(eventId);
        if (event == null) throw new EventNotFoundException();
        if (!event.isAttendeeRegistered(attendeeId)) throw new AttendeeNotInEventException();

        event.addRating(new uoc.ds.pr.model.Rating(attendee, event, rating, message));
    }

    @Override
    public Iterator<uoc.ds.pr.model.Rating> getRatingsByEvent(String eventId) throws EventNotFoundException, NoRatingsException {
        Event event = getEvent(eventId);
        if (event == null) throw new EventNotFoundException();
        if (event.getRatings().isEmpty()) throw new NoRatingsException();
        return event.getRatings().values();
    }

    @Override
    public Attendee mostActiveAttendee() throws AttendeeNotFoundException {
        if (attendees.length == 0) throw new AttendeeNotFoundException();

        return null;
    }

    @Override
    public Event bestEvent() throws EventNotFoundException {
        return null;
    }

    @Override
    public int numEntities() {
        return entities.length;
    }

    @Override
    public int numAttendees() {
        return entities.length;
    }

    @Override
    public int numRequests() {
        return requests.size();
    }

    @Override
    public int numEvents() {
        return events.length;
    }

    @Override
    public int numEventsByAttendee(String attendeeId) {
        Attendee attendee = getAttendee(attendeeId);
        return attendee.getEventsAttended().size();
    }

    @Override
    public int numAttendeesByEvent(String eventId) {
        Event event = getEvent(eventId);
        return event.getRegisteredAttendees().size();
    }

    @Override
    public int numSubstitutesByEvent(String eventId) {
        return 0;
    }

    @Override
    public int numEventsByEntity(String entityId) {
        Entity entity = getEntity(entityId);
        return entity.getEventsOrganized().size();
    }

    @Override
    public int numRejectedRequests() {
        return requestsRejected.size();
    }

    @Override
    public int numRatingsByEvent(String eventId) {
        Event event = getEvent(eventId);
        return event.getRatings().size();
    }

    @Override
    public Entity getEntity(String entityId) {
        for (Entity entity : entities) {
            if (entity.getId().equals(entityId)) return entity;
        }
        return null;
    }

    @Override
    public Attendee getAttendee(String attendeeId) {
        for (Attendee attendee : attendees) {
            if (attendee.getId().equals(attendeeId)) return attendee;
        }
        return null;
    }

    @Override
    public Event getEvent(String eventId) {
        for (Event event : events) {
            if (event.getEventId().equals(eventId)) return event;
        }
        return null;
    }
}
