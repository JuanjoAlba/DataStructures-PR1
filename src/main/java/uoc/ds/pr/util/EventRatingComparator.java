package uoc.ds.pr.util;

import uoc.ds.pr.model.Event;

import java.util.Comparator;

public class EventRatingComparator implements Comparator<Event> {

    @Override
    public int compare(Event o1, Event o2) {
        return o1.rating().compareTo(o2.rating());
    }
}
