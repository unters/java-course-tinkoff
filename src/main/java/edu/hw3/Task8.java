package edu.hw3;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Task8 {
    /* Task statement asks to implement an iterator that takes a "collection" as a parameter. This wording is
     * contradictory. The first thing that came to my mind was that the BackwardIterator<> must be applicable to an
     * arbitrary Collection<>. The problem is that the Collection<> interface does not specify any ordering at all (in
     * other words you cannot iterate over an arbitrary Collection<> neither forwards nor backwards).
     *
     * I've decided to implement a fail-fast backwards iterator for List<> as a wrapper over a fail-fast iterator
     * ListIterator<> provided by List interface.  */
    public static class BackwardIterator<T> implements Iterator<T> {
        private final ListIterator<T> listIterator;

        BackwardIterator(List<T> list) {
            listIterator = list.listIterator(list.size());
        }

        @Override
        public boolean hasNext() {
            return listIterator.hasPrevious();
        }

        @Override
        public T next() {
            return listIterator.previous();
        }
    }
}
