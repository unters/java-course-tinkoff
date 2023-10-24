package edu.hw3;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/* Task statement asks to implement an iterator that takes a "collection" as a parameter. This wording is contradictory.
 * The first thing that came to my mind was that the BackwardIterator<> must take a Collection<>. The problem is that
 * the Collection<> interface does not specify any ordering at all (in other words you cannot iterate over an
 * arbitrary Collection<> neither forwards nor backwards).  */
public class Task8 {
    public static class BackwardIterator<T> implements Iterator<T> {
        private final List<T> list;

        BackwardIterator(List<T> list) {
            this.list = list;
            index = list.size() - 1;
        }

        @Override
        public boolean hasNext() {
            return index >= 0;
        }

        @Override
        public T next() {
            if (index < 0) {
                throw new NoSuchElementException();
            }

            return list.get(index--);
        }

        private int index;
    }
}
