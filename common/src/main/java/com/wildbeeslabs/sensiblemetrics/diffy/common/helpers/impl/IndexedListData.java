package com.wildbeeslabs.sensiblemetrics.diffy.common.helpers.impl;

import java.util.*;

/**
 * This object contains a list of values.  The list is not always sorted, but will
 * always be returned sorted.
 * <p>
 * All of the methods on here must be called with lock held.
 *
 * @author jwells
 */
public class IndexedListData<T> {
    private final ArrayList<T> unsortedList = new ArrayList<>();
    private final Comparator<? super T> comparator;
    private volatile boolean sorted = true;

    public IndexedListData(final Comparator<? super T> comparator) {
        Objects.requireNonNull(comparator, "Comparator should not be null");
        this.comparator = comparator;
    }

    public Collection<T> getSortedList() {
        if (this.sorted) return this.unsortedList;
        synchronized (this) {
            if (this.sorted) return this.unsortedList;
            if (this.unsortedList.size() <= 1) {
                sorted = true;
                return this.unsortedList;
            }
            Collections.sort(this.unsortedList, this.comparator);
            this.sorted = true;
            return this.unsortedList;
        }
    }

    public synchronized void addDescriptor(final T descriptor) {
        this.unsortedList.add(descriptor);
        if (this.unsortedList.size() > 1) {
            sorted = false;
        } else {
            sorted = true;
        }
    }

    public synchronized void removeDescriptor(final T descriptor) {
        final ListIterator<T> iterator = this.unsortedList.listIterator();
        while (iterator.hasNext()) {
            T candidate = iterator.next();
            if (this.comparator.compare(descriptor, candidate) == 0) {
                iterator.remove();
                break;
            }
        }

        if (this.unsortedList.size() > 1) {
            this.sorted = false;
        } else {
            this.sorted = true;
        }
    }

    public synchronized boolean isEmpty() {
        return this.unsortedList.isEmpty();
    }

    /**
     * Called by a SystemDescriptor when its ranking has changed
     */
    public synchronized void unSort() {
        if (this.unsortedList.size() > 1) {
            this.sorted = false;
        }
    }

    public synchronized void clear() {
        this.unsortedList.clear();
    }

    public synchronized int size() {
        return this.unsortedList.size();
    }
}
