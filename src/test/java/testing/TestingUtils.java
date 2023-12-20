package testing;

import iterator.RichIterator;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class TestingUtils {
    public static RichIterator<A> aTestIterator() {
        return RichIterator.apply(new A(1), new A(2), new A(3), new A(4), new A(5));
    }

    public static RichIterator<A> randomOrderAIterator() {
        return RichIterator.apply(new A(3), new A(4), new A(5), new A(1), new A(2));
    }

    public static RichIterator<Integer> basicTestIterator() {
        return RichIterator.apply(1, 2, 3, 4, 5);
    }

    public static RichIterator<Integer> naturalNumbers() {
        return RichIterator.from(Stream.iterate(0, x -> x + 1).iterator());
    }
    public static <A,R>  CountableFunction<A,R> toCountableFunction(Function<A,R> f) {
        return new CountableFunction<>(f);
    }
    public static <T> CountablePredicate<T> toCountablePredicate(Predicate<T> p) {
        return new CountablePredicate<>(p);
    }
    public static class A {
        public int v;

        public A(int v) {
            this.v = v;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            A a = (A) o;
            return v == a.v;
        }

        @Override
        public int hashCode() {
            return Objects.hash(v);
        }
    }

    public static class CountablePredicate<T> implements Predicate<T> {
        private AtomicInteger i;
        private final Predicate<T> p;

        protected CountablePredicate(Predicate<T> p) {
            this.p = p;
            this.i = new AtomicInteger(0);
        }

        @Override
        public boolean test(T t) {
            i.incrementAndGet();
            return p.test(t);
        }

        public int count() {
            return i.get();
        }
    }

    public static class CountableFunction<A, R> implements Function<A,R> {
        private final Function<A, R> f;
        private final AtomicInteger counter = new AtomicInteger(0);

        protected CountableFunction(Function<A,R> f) {
            this.f = f;
        }


        @Override
        public R apply(A a) {
            counter.incrementAndGet();
            return f.apply(a);
        }

        public int count() {
            return counter.get();
        }
    }
}
