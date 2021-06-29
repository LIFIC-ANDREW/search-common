package io.lific.search.common.utils.collection;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;

public class SortedLinkedList<E> extends LinkedList<E> {

    private final Comparator<E> comparator;
    private final Equalizer<E> equalizer;

    public SortedLinkedList(Comparator<E> comparator) {
        this(null, comparator, null);
    }

    public SortedLinkedList(Collection<? extends E> c, Comparator<E> comparator) {
        this(c, comparator, null);
    }

    public SortedLinkedList(Collection<? extends E> c, Comparator<E> comparator, Equalizer<E> equalizer) {
        this.comparator = comparator;
        this.equalizer = equalizer;
        if (c != null) addAll(c);
    }

    @Override
    public boolean add(E o) {
        int idx = 0;
        if (!isEmpty()) {
            idx = findInsertionPoint(o);
        }
        if (idx != -1) super.add(idx, o);
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        Iterator<? extends E> i = c.iterator();
        boolean changed = false;
        while (i.hasNext()) {
            boolean ret = add(i.next());
            if (!changed) {
                changed = ret;
            }
        }
        return changed;
    }

    public int findInsertionPoint(E o) {
        return findInsertionPoint(o, 0, size() - 1);
    }

    protected int findInsertionPoint(E o, int low, int high) {
        while (low <= high) {
            int mid = (low + high) >>> 1;
            int delta = compare(get(mid), o);

            if (delta > 0) {
                high = mid - 1;
            } else if (delta == 0) {
                if (equalizer.equals(get(mid), o)) {
                    low  = -1;
                    break;
                }
                low = mid + 1;
            } else {
                low = mid + 1;
            }
        }

        return low;
    }

    protected int compare(E k1, E k2) {
        return comparator.compare(k1, k2);
    }

}
