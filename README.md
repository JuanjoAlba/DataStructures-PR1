DS - PR1

Has been included:

uoc.ds.pr.exceptions:
    - All needed exceptions.
uoc.ds.pr.model:
    - All necessary classes (Attendee, Event, Entity...)
    - Has been added classes for different entities.
uoc.ds.pr.util:
    - EntityMediator, a class to manage entities (could have been EntityFactory), and create different entities
        without the ADT worrying about the type.
    - EventRatingComparator, class to create a comparator for events depending on rating().
    - OrderedVector, a class representing an ordered array of non repeated elements.
    - ResourceUtil, this class has the necessary methods to control the flags for components of a event.
uoc.ds.pr:
    - UniversityEvents, interface with the ADT behaviour.
    - UniversityEventsImpl, implementation for the ADT.

Sadly, there are no more tests than the proposed ones.

I guess is something we will face on the next PR, but:
- There is no mechanism to delete past events.
- There is no mechanism for attendees to delete from an Event and take a substitute.

Also, regarding the rejectedPercentage, from the tests we can deduce the formula is
(totalRejected/events), as the expected result is 0.25, but this is the percentage 
corresponding to the events accepted, not with the total requests. 

