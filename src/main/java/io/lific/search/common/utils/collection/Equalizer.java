package io.lific.search.common.utils.collection;

@FunctionalInterface
public interface Equalizer<T> {

    boolean equals(T o1, T o2);

}
