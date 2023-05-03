package uoc.ds.pr.util;

import edu.uoc.ds.adt.sequential.FiniteContainer;
import edu.uoc.ds.exceptions.EmptyContainerException;
import edu.uoc.ds.exceptions.FullContainerException;
import edu.uoc.ds.traversal.Iterator;
import edu.uoc.ds.traversal.IteratorArrayImpl;

import java.util.Arrays;
import java.util.Comparator;

public class OrderedVector<E> implements FiniteContainer<E> {

    protected E[] elems;
    protected int n = 0;
    protected java.util.Comparator<E> comparator;

    public OrderedVector(int max, Comparator<E> comparator) {
        this.elems = (E[]) new Object[max];
        this.n = 0;
        this.comparator = comparator;
    }

    public boolean isEmpty() {
        return this.n == 0;
    }

    public int size() {
        return this.n;
    }

    public boolean isFull() {
        return this.n == this.elems.length;
    }

    public Iterator<E> values() {
        return new IteratorArrayImpl(this.elems, this.size(), 0);
    }

    public void update(E elem) {
        if (isFull()) throw new FullContainerException();
        this.elems[n] = elem;
        ++this.n;
        Arrays.sort(this.elems, this.comparator);
    }

    public void delete(E elem) {
        if (isEmpty()) throw new EmptyContainerException();
        for (int i = 0; i < n; i++) {
            if (this.elems[i].equals(elem)) {
                this.elems[i] = null;
                Arrays.sort(this.elems, this.comparator);
                break;
            }
        }
    }
}
