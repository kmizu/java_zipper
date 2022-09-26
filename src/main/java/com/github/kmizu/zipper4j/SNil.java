package com.github.kmizu.zipper4j;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

public record SNil<T>() implements SList<T> {
    @Override
    public <U> SList<U> map(Function<T, U> f) {
        return new SNil<>();
    }

    @Override
    public SList<T> filter(Predicate<T> p) {
        return this;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public T head() {
        throw new UnsupportedOperationException("SNil#head()");
    }

    @Override
    public SList<T> tail() {
        throw new UnsupportedOperationException("SNil#tail()");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this);
    }

    @Override
    public String toString() {
        return "SNil";
    }
}