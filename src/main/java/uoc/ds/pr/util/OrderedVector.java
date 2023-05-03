package uoc.ds.pr.util;

import edu.uoc.ds.adt.sequential.FiniteContainer;
import edu.uoc.ds.exceptions.EmptyContainerException;
import edu.uoc.ds.exceptions.NonComparableException;
import edu.uoc.ds.traversal.Iterator;
import edu.uoc.ds.traversal.IteratorArrayImpl;

import java.util.Arrays;
import java.util.Comparator;

public class OrderedVector<E> implements FiniteContainer<E> {

    private static final int DEFAULT_LEN = 10;
    protected E[] elems;
    protected int n = 0;
    protected java.util.Comparator<E> comparator;

    public OrderedVector(Comparator<E> comparator) {
        this(DEFAULT_LEN, comparator);
    }

    public OrderedVector(int max, Comparator<E> comparator) {
        this.elems = (E[]) new Object[max];
        this.n = 0;
        this.comparator = comparator.reversed();
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
        if (isEmpty()) {
            this.elems[n] = elem;
            ++this.n;
        } else {
            int exist = this.contains(elem);
            if (exist != -1) {
                this.elems[exist] = elem;
                Arrays.sort(this.elems, 0, this.n, comparator);
            } else {
                E[] aux = (E[]) new Object[this.n + 1];
                for (int i = 0; i < this.n; i++) {
                    aux[i] = this.elems[i];
                }
                aux[this.n] = elem;
                Arrays.sort(aux, 0, aux.length, comparator);
                if (this.n < this.elems.length) ++this.n;
                for (int i = 0; i < this.n; i++) {
                    this.elems[i] = aux[i];
                }
            }
        }
    }

    public void delete(E elem) {
        if (isEmpty()) throw new EmptyContainerException();
        int index = -1;
        for (int i = 0; i < n; i++) {
            if (this.elems[i].equals(elem)) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            for (int i = index; i < this.n - 1; i++) {
                this.elems[i] = this.elems[i + 1];
            }
            this.elems[n - 1] = null;
            --this.n;
        }
    }


    public int contains(E elem) {
        int found = -1;
        for (int i = 0; i < this.n; i++) {
            if (this.elems[i] == elem) {
                found = i;
                break;
            }
        }
        return found;
    }
}
