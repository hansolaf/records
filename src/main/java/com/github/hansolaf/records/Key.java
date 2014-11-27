package com.github.hansolaf.records;

import java.util.function.Function;

public class Key<T> implements Function<Rec<?>, T> {

    private final String name;

    public Key(String name) {
        this.name = name;
    }

    public static <X> Key<X> create(String name) {
        return new Key<>(name);
    }

    public String name() {
        return name;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Key && ((Key) obj).name().equals(name);
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public T apply(Rec<?> rec) {
        return rec.get(this);
    }

}
