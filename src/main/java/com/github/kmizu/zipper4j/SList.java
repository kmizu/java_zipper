package com.github.kmizu.zipper4j;

import java.util.function.Function;
import java.util.function.Predicate;

public sealed interface SList<T> permits SCons, SNil {
    <U> SList<U> map(Function<T, U> f);

    SList<T> filter(Predicate<T> p);

    boolean isEmpty();

    T head();

    SList<T> tail();

    static <T> SList<T> of(T... elements) {
        SList<T> result = new SNil<T>();
        for(int i = elements.length - 1; i >= 0; i--) {
            result = new SCons<>(elements[i], result);
        }
        return result;
    }
}