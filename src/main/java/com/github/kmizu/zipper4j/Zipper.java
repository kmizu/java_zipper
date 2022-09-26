package com.github.kmizu.zipper4j;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public record Zipper<T>(SList<T> visit, SList<T> rest) {
    public static <T> Zipper<T> of(SList<T> list) {
        return new Zipper<>(new SNil<T>(), list);
    }

    public boolean isBegin() {
        return visit instanceof SNil<T>;
    }

    public boolean isEnd() {
        return rest instanceof SNil<T>;
    }

    public Zipper<T> next() {
        return switch (rest) {
            case SCons<T> cons -> new Zipper<>(SCons.of(cons.head(), visit), cons.tail());
            default -> throw new RuntimeException("SNil");
        };
    }

    public Zipper<T> previous() {
        return switch (visit) {
            case SCons<T> cons -> new Zipper<>(cons.tail(), SCons.of(cons.head(), rest));
            default -> throw new RuntimeException("SNil");
        };
    }

    public SList<T> toList() {
        var current = this;
        if (current.isEnd()) {
            return new SNil<>();
        } else {
            return SCons.of(current.get(), current.next().toList());
        }
    }

    public T get() {
        return switch (rest) {
            case SCons<T> cons -> cons.head();
            default -> throw new RuntimeException("empty");
        };
    }

    public Zipper<T> set(T element) {
        return switch (rest) {
            case SCons<T> cons -> new Zipper<>(visit, SCons.of(element, cons.tail()));
            default -> throw new RuntimeException("empty");
        };
    }

    public Zipper<T> insert(T element) {
        return new Zipper<>(visit, SCons.of(element, rest));
    }

    public Zipper<T> remove() {
        return switch (rest) {
            case SCons<T> cons -> new Zipper<>(visit, cons.tail());
            default -> throw new RuntimeException("empty");
        };
    }

    public Zipper<T> reset() {
        var result = this;
        while (!result.isBegin()) {
            result = result.previous();
        }
        return result;
    }

    public <U> Zipper<U> map(Function<T, U> function) {
        return new Zipper<>(visit.map(function), rest.map(function));
    }

    public Zipper<T> filter(Predicate<T> predicate) {
        return new Zipper<>(visit, rest.filter(predicate));
    }

    public <U> U foldLeft(U zero, BiFunction<U, T, U> function) {
        var zipper = this;
        var result = zero;
        while(!zipper.isEnd()) {
            result = function.apply(result, zipper.get());
            zipper = zipper.next();
        }
        return result;
    }

    public boolean allMatch(Predicate<? super T> predicate) {
        var result = true;
        return foldLeft(
                true,
                (accumlator, element) -> accumlator && predicate.test(element)
        );
    }

    public boolean anyMatch(Predicate<? super T> predicate) {
        var result = true;
        return foldLeft(
                false,
                (accumlator, element) -> accumlator || predicate.test(element)
        );
    }

    public Optional<Zipper<T>> findFirst(Predicate<T> predicate) {
        var current = this;
        while (!current.isEnd()) {
            if (predicate.test(current.get())) return Optional.of(current);
            current = current.next();
        }
        return Optional.empty();
    }
}
