package uoc.ds.pr;

import edu.uoc.ds.adt.sequential.LinkedList;
import edu.uoc.ds.adt.sequential.QueueArrayImpl;
import edu.uoc.ds.traversal.Iterator;
import edu.uoc.ds.traversal.IteratorArrayImpl;
import uoc.ds.pr.exceptions.*;
import uoc.ds.pr.model.Attendee;
import uoc.ds.pr.model.Entity;
import uoc.ds.pr.model.Event;
import uoc.ds.pr.model.EventRequest;
import uoc.ds.pr.util.EntityMediator;
import uoc.ds.pr.util.EventRatingComparator;
import uoc.ds.pr.util.OrderedVector;

import java.time.LocalDate;

/**
 * This class implements the functionalities required for the ADT on PR-1
 *
 * @author UOC
 * @author Juan José Alba
 */
public class UniversityEventsImpl implements UniversityEvents {

    private Attendee[] attendees = new Attendee[MAX_NUM_ATTENDEES];
    private int totalAttendees = 0;
    private Entity[] entities = new Entity[MAX_NUM_ENTITIES];
    private int totalEntities = 0;
    private Event[] events = new Event[MAX_NUM_EVENTS];
    private int totalEvents = 0;
    private QueueArrayImpl<EventRequest> requests = new QueueArrayImpl<>(MAX_NUM_REQUESTS);
    private LinkedList<EventRequest> requestsRejected = new LinkedList<>();
    private Attendee mostActiveAttendee;
    private OrderedVector<Event> highestRatedEvents = new OrderedVector<>(new EventRatingComparator());

    @Override
    public void addEntity(String id, String name, String description, EntityType entityType) {
        for (int i = 0; i < this.entities.length; i++) {
            if (this.entities[i] != null) {
                if (this.entities[i].getId().equals(id)) {
                    this.entities[i] = EntityMediator.createEntity(id, name, description, entityType);
                    break;
                }
            } else {
                // We assume when we find the first null there will be no more entities to check,
                // so we can add the new entity.
                this.entities[i] = EntityMediator.createEntity(id, name, description, entityType);
                ++this.totalEntities;
                break;
            }
        }
    }

    @Override
    public void addAttendee(String id, String name, String surname, LocalDate dateOfBirth) {
        for (int i = 0; i < this.attendees.length; i++) {
            if (this.attendees[i] != null) {
                if (this.attendees[i].getId().equals(id)) {
                    this.attendees[i].setName(name);
                    this.attendees[i].setSurname(surname);
                    this.attendees[i].setDateOfBrith(dateOfBirth);
                    break;
                }
            } else {
                // Same as entities, assume after firs null there will be no more attendees
                this.attendees[i] = new Attendee(id, name, surname, dateOfBirth);
                ++this.totalAttendees;
                break;
            }
        }
    }

    @Override
    public void addEventRequest(String id, String eventId, String entityId, String description, InstallationType installationType, byte resources, int max, LocalDate startDate, LocalDate endDate, boolean allowRegister) throws EntityNotFoundException {
        if (getEntity(entityId) == null) throw new EntityNotFoundException();
        this.requests.add(new EventRequest(id, eventId, entityId, description, installationType, resources, max, startDate, endDate, allowRegister));
    }

    @Override
    public EventRequest updateEventRequest(Status status, LocalDate date, String message) throws NoEventRequestException {
        if (this.requests.isEmpty()) throw new NoEventRequestException();
        EventRequest request = requests.poll();
        request.setStatus(status);
        request.setApprovedOrRejectedDate(date);
        request.setRejectedReason(message);
        if (status.equals(Status.ENABLED)) {
            Event newEvent = new Event(request.getEventId(), request);
            addEvent(newEvent);
            getEntity(request.getEntityId()).addEventsOrganized(newEvent);
        } else {
            requestsRejected.insertEnd(request);
        }
        return request;
    }

    private void addEvent(Event event) {
        for (int i = 0; i < this.events.length; i++) {
            if (this.events[i] == null) {
                this.events[i] = event;
                ++this.totalEvents;
                break;
            }
        }
    }

    @Override
    public void signUpEvent(String attendeeId, String eventId) throws AttendeeNotFoundException, EventNotFoundException, NotAllowedException, AttendeeAlreadyInEventException {
        Event event = getEvent(eventId);
        if (event == null) throw new EventNotFoundException();

        Attendee attendee = getAttendee(attendeeId);
        if (attendee == null) throw new AttendeeNotFoundException();

        if (!event.getRequest().isAllowRegister()) throw new NotAllowedException();
        if (event.isAttendeeRegistered(attendeeId)) throw new AttendeeAlreadyInEventException();

        if (event.getRegisteredAttendees().size() < event.getRequest().getMaxAttendee()) {
            event.addAttendee(attendee);
            attendee.getEventsAttended().insertEnd(event);
        } else {
            event.addSubstitute(attendee);
        }
    }

    @Override
    public double getPercentageRejectedRequests() {
        if (this.requestsRejected.size() == 0) return 0;
        Double rejected = (double) this.requestsRejected.size();
        Double events = (double) this.totalEvents;
        return Math.round((rejected / events) * 100) / 100.00;
    }

    @Override
    public Iterator<EventRequest> getRejectedRequests() throws NoEventRequestException {
        if (this.requestsRejected.isEmpty()) throw new NoEventRequestException();
        return this.requestsRejected.values();
    }

    @Override
    public Iterator<Event> getEventsByEntity(String entityId) throws NoEventsException {
        Entity entity = getEntity(entityId);
        if (entity.getEventsOrganized().isEmpty()) throw new NoEventsException("No events found for that entity");
        return entity.getEventsOrganized().values();
    }

    @Override
    public Iterator<Event> getAllEvents() throws NoEventsException {
        if (this.totalEvents == 0) throw new NoEventsException();
        return new IteratorArrayImpl<Event>(this.events, this.totalEvents, 0);
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
        this.highestRatedEvents.update(event);
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
        if (this.totalAttendees == 0) throw new AttendeeNotFoundException();
        this.mostActiveAttendee = this.attendees[0];
        for (int i = 1; i < this.totalAttendees; i++) {
            if (this.mostActiveAttendee.getEventsAttended().size() < this.attendees[i].getEventsAttended().size()) {
                this.mostActiveAttendee = this.attendees[i];
            }
        }
        return this.mostActiveAttendee;
    }

    @Override
    public Event bestEvent() throws EventNotFoundException {
        if (this.highestRatedEvents.isEmpty()) throw new EventNotFoundException();
        return this.highestRatedEvents.values().next();
    }

    @Override
    public int numEntities() {
        return this.totalEntities;
    }

    @Override
    public int numAttendees() {
        return this.totalAttendees;
    }

    @Override
    public int numRequests() {
        return this.requests.size();
    }

    @Override
    public int numEvents() {
        return this.totalEvents;
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
        Event event = getEvent(eventId);
        return event.getRegisteredSubstitutes().size();
    }

    @Override
    public int numEventsByEntity(String entityId) {
        Entity entity = getEntity(entityId);
        return entity.getEventsOrganized().size();
    }

    @Override
    public int numRejectedRequests() {
        return this.requestsRejected.size();
    }

    @Override
    public int numRatingsByEvent(String eventId) {
        Event event = getEvent(eventId);
        return event.getRatings().size();
    }

    @Override
    public Entity getEntity(String entityId) {
        for (int i = 0; i < this.totalEntities; i++) {
            if (this.entities[i].getId().equals(entityId)) return this.entities[i];
        }
        return null;
    }

    @Override
    public Attendee getAttendee(String attendeeId) {
        for (int i = 0; i < this.totalAttendees; i++) {
            if (this.attendees[i].getId().equals(attendeeId)) return this.attendees[i];
        }
        return null;
    }

    @Override
    public Event getEvent(String eventId) {
        for (int i = 0; i < this.totalEvents; i++) {
            if (this.events[i].getEventId().equals(eventId)) return this.events[i];
        }
        return null;
    }
}
