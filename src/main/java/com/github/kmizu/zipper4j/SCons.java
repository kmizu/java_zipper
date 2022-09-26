package com.github.kmizu.zipper4j;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

public record SCons<T>(T head, SList<T> tail) implements SList<T> {
    public static <T> SCons<T> of(T head, SList<T> tail) {
        return new SCons<>(head, tail);
    }

    @Override
    public <U> SList<U> map(Function<T, U> f) {
        return new SCons<>(f.apply(head), tail.map(f));
    }

    @Override
    public SList<T> filter(Predicate<T> p) {
        var result = tail.filter(p);
        var condition = p.test(head);
        if (condition) {
            return new SCons<>(head, result);
        } else {
            return result;
        }
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SCons<?> sCons = (SCons<?>) o;
        return Objects.equals(head, sCons.head) && Objects.equals(tail, sCons.tail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(head, tail);
    }

    @Override
    public String toString() {
        return "SCons(" + head + ", " + tail + ")";
    }
}
