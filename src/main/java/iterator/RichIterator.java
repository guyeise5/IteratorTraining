package iterator;

import java.util.*;
import java.util.function.*;

public interface RichIterator<A> extends Iterator<A> {
    // easy

    /**
     * @return true if there are no more elements, false otherwise
     */
    default boolean isEmpty() {
        return !hasNext();
    }

    /**
     * @return number of elements
     */
    default int length() {
        int counter = 0;
        while (hasNext()) {
            counter++;
            next();
        }

        return counter;
    }

    /**
     * @return the last element of the iterator
     * @throws NoSuchElementException if the iterator is empty
     */
    default A last() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        A lastElement = next();
        while (hasNext()) {
            lastElement = next();
        }

        return lastElement;
    }

    /**
     * @return the last element if not empty.
     */
    default Optional<A> lastOptional() {
        if (isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(last());
    }


    /**
     * @param elem the element
     * @return the index of the element if exists, -1 otherwise
     */
    default int indexOf(A elem) {
        int index = 0;
        while (hasNext()) {
            if (next().equals(elem)) {
                return index;
            }
            index++;
        }

        return -1;
    }

    /**
     * returns the first element fit to the given predicate
     *
     * @param f the predicate
     * @return Optional.of(A) if exists, empty() otherwise.
     */
    default Optional<A> find(Predicate<? super A> f) {
        A element;
        while (hasNext()) {
            element = next();
            if (f.test(element)) {
                return Optional.of(element);
            }
        }

        return Optional.empty();
    }

    /**
     * @return Optional.of(next) if exists or empty() otherwise
     */
    default Optional<A> nextOptional() {
        if (hasNext()) {
            return Optional.of(next());
        }

        return Optional.empty();
    }

    /**
     * executes the given Consumer on all the elements
     *
     * @param f the Consumer
     */
    default void foreach(Consumer<? super A> f) {
        while (hasNext()) {
            f.accept(next());
        }
    }

    /**
     * @param elem checks if the iterator has the element
     * @return true if exists, false otherwise
     */
    default boolean contains(A elem) {
        while (hasNext()) {
            if (next().equals(elem)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Converts the iterator to a collection using the collection factory
     *
     * @param collectionFactory a supplier that creates a new instance of the collection
     * @param <C>               the type of the collection to return
     *                          e.g.
     *                          RichIterator.apply(1,2,3,2,4,5).toCollection(HashSet::new) // HashSet(1,2,3,4,5)
     *                          RichIterator.apply(1,2,3,2,4,5).toCollection(LinkedList::new) // LinkedList(1,2,3,2,4,5)
     * @return the collection
     */
    default <C extends Collection<A>> C toCollection(Supplier<C> collectionFactory) {
        C collection = collectionFactory.get();
        while (hasNext()) {
            collection.add(next());
        }

        return collection;
    }

    /**
     * @return a list built from the iterator's elements
     */
    default List<A> toList() {
        return toCollection(ArrayList::new);
    }

    /**
     * @return a set built from the iterator's elements
     */
    default Set<A> toSet() {
        return toCollection(HashSet::new);
    }

    /**
     * @param that other iterator
     * @return true if this and that have the same elements in the same order
     */
    default boolean sameElements(Iterator<A> that) {
        while (hasNext() && that.hasNext()) {
            if (!next().equals(that.next())) {
                return false;
            }
        }

        return !hasNext() && !that.hasNext();
    }

    // hard

    /**
     * builds an iterator with only the given element
     *
     * @param elem the element
     * @param <A>  the type
     * @return an iterator that contains only this element
     */
    static <A> RichIterator<A> pure(A elem) {
        return RichIterator.apply(elem);
    }

    /**
     * finds the maximum element based on the given comparator
     *
     * @param comparator the comparator
     * @return the maximum element
     * @throws NoSuchElementException if the iterator is empty
     */
    default A max(Comparator<? super A> comparator) {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        A element = next();
        A maximum = element;

        while (hasNext()) {
            element = next();

            if (comparator.compare(element, maximum) > 0) {
                maximum = element;
            }
        }

        return maximum;
    }

    /**
     * finds the minimum element based on the given comparator
     *
     * @param comparator the comparator
     * @return the minimum element
     * @throws NoSuchElementException if the iterator is empty
     */
    default A min(Comparator<? super A> comparator) {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        A element = next();
        A minimum = element;

        while (hasNext()) {
            element = next();

            if (comparator.compare(element, minimum) < 0) {
                minimum = element;
            }
        }

        return minimum;
    }

    /**
     * converts the iterator to new one after the mapping function
     *
     * @param f   the function
     * @param <B> the new type
     * @return a new Iterator.
     */
    default <B> RichIterator<B> map(Function<? super A, ? extends B> f) {
        return new RichIterator<B>() {
            @Override
            public boolean hasNext() {
                return RichIterator.this.hasNext();
            }

            @Override
            public B next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }

                return f.apply(RichIterator.this.next());
            }
        };
    }

    /**
     * @param f the function to apply on each element.
     * @return an iterator with the method applied on all the elements
     * e.g.
     * Iterator itr = Iterator(1,2,3).tapEach(System.out::print).foreach(System.out::print)
     * output (printed): 112233
     */
    default RichIterator<A> tapEach(Consumer<? super A> f) {
        return new RichIterator<A>() {
            @Override
            public boolean hasNext() {
                return RichIterator.this.hasNext();
            }

            @Override
            public A next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }

                A element = RichIterator.this.next();
                f.accept(element);

                return element;
            }
        };
    }

    /**
     * reducing the elements to one according to the given function
     *
     * @param acc accumulate function (first element is the total, second element is the iterated element)
     * @return the reduced element.
     * @throws NoSuchElementException when the iterator is empty
     *                                e.g.
     *                                RichIterator.apply(1,2,3).reduce((total, a) -> total + a) // 6
     *                                RichIterator.empty().reduce((total, a) -> null) // throws NoSuchElementException
     *                                RichIterator.apply(1).reduce((total, a) -> total + a) // 1
     */
    default A reduce(BiFunction<? super A, ? super A, ? extends A> acc) {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        A element = next();
        A total = element;

        while (hasNext()) {
            element = next();
            total = acc.apply(total, element);
        }

        return total;
    }

    /**
     * same as reduce but return Optional of result if exists or empty() if not.
     *
     * @param acc // see reduce.
     * @return // see reduce
     */
    default Optional<A> reduceOptional(BiFunction<? super A, ? super A, ? extends A> acc) {
        if (isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(reduce(acc));
    }

    /**
     * Creates a String from the iterator.
     *
     * @param prefix    Start of the string
     * @param delimiter delimiter between elements
     * @param suffix    end of the string
     * @return a string containing all the elements in the iterator.
     * @implNote use previous functions to solve.
     * e.g.
     * RichIterator(1,2,3,4,5).mkString("[", ";","]") // [1;2;3;4;5]
     * RichIterator(1).mkString("[", ";","]") // [1]
     * RichIterator().mkString("[", ";","]") // []
     */
    default String mkString(String prefix, String delimiter, String suffix) {
        StringBuilder result = new StringBuilder();
        result.append(prefix);

        while (hasNext()) {
            result.append(next().toString());
            if (hasNext()) {
                result.append(delimiter);
            }
        }

        result.append(suffix);

        return result.toString();
    }

    /**
     * Creates a String from the iterator that starts with "RichIterator("
     * and the elements are separated by ',' and ends with ')'
     * e.g.
     * RichIterator.apply(1,2,3,4,5).mkString() // RichIterator(1,2,3,4,5)
     * RichIterator.empty().mkString() // RichIterator()
     *
     * @return a String
     */
    default String mkString() {
        return mkString("RichIterator(", ",", ")");
    }

    /**
     * adding the element at the end of the iterator
     *
     * @param elem the element
     * @return an iterator with the element appended
     * e.g.
     * RichIterator.apply(1,2,3,4).append(5) // 1,2,3,4,5
     * throw new NotImplementedException();
     */
    default RichIterator<A> append(A elem) {
        return appendAll(pure(elem));
    }

    /**
     * same as append but with iterator
     *
     * @param elems the element
     * @return an iterator with the elements appended
     * e.g.
     * RichIterator.apply(1,2,3,4).appendAll(Arrays.asList(5,6,7,8)) // 1,2,3,4,5,6,7,8
     */
    default RichIterator<A> appendAll(Iterator<A> elems) {
        return new RichIterator<A>() {
            @Override
            public boolean hasNext() {
                return RichIterator.this.hasNext() || elems.hasNext();
            }

            @Override
            public A next() {
                if (RichIterator.this.hasNext()) {
                    return RichIterator.this.next();
                } else if (elems.hasNext()) {
                    return elems.next();
                } else {
                    throw new NoSuchElementException();
                }
            }
        };
    }

    /**
     * adding the element at the start of the iterator
     *
     * @param elem the element
     * @return an iterator with the element prepended
     * e.g.
     * RichIterator.apply(1,2,3,4).prepend(0) // 0,1,2,3,4
     */
    default RichIterator<A> prepend(A elem) {
        return pure(elem).appendAll(this);
    }

    /**
     * same as prepend but with iterator
     *
     * @param elems the element
     * @return an iterator with the elements prepended
     * e.g.
     * RichIterator.apply(4,5,6,7).prependAll(Arrays.asList(1,2,3)) // 1,2,3,4,5,6,7
     */
    default RichIterator<A> prependAll(Iterator<A> elems) {
        return from(elems).appendAll(this);
    }

    /**
     * removes the first n elements of the iterator
     *
     * @param n the number to drop
     * @return the iterator without the first n elements or empty iterator if (n >= length)
     * e.g.
     * RichIterator.apply(1,2,3,4,5,1,2,3,4,5).drop(2) // 3,4,5,1,2,3,4,5
     * RichIterator.apply(1,2,3,4,5,1,2,3,4,5).drop(20) // empty()
     */
    default RichIterator<A> drop(int n) {
        return new RichIterator<A>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                while (index < n && RichIterator.this.hasNext()) {
                    RichIterator.this.next();
                    index++;
                }

                return RichIterator.this.hasNext();
            }

            @Override
            public A next() {
                if (hasNext()) {
                    return RichIterator.this.next();
                } else {
                    throw new NoSuchElementException();
                }
            }
        };
    }

    /**
     * takes only the first n elements
     *
     * @param n number of elements
     * @return an Iterator that has the first n elements (or less)
     * e.g.
     * RichIterator.apply(1,2,3,4,5,1,2,3,4,5).take(2) // 1,2
     * RichIterator.apply(1,2,3,4,5).take(20) // 1,2,3,4,5
     */
    default RichIterator<A> take(int n) {
        return new RichIterator<A>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < n && RichIterator.this.hasNext();
            }

            @Override
            public A next() {
                if (hasNext()) {
                    index++;

                    return RichIterator.this.next();
                } else {
                    throw new NoSuchElementException();
                }
            }
        };
    }

    /**
     * "sums" the elements on the iterator from left to right
     *
     * @param zero the first element usually neutral
     * @param acc  accumulate function
     * @param <B>  new type
     * @return the accumulated result
     * e.g.
     * RichIterator.apply(1,2,3,4,5).foldLeft(0, (x,y) -> x + y) // 15
     * RichIterator.apply("a","b","c","d").foldLeft("", (x,y) -> x + y) // "abcd"
     * empty().foldLeft(0, (x,y) -> x + y) // 0
     */
    default <B> B foldLeft(B zero, BiFunction<? super B, ? super A, ? extends B> acc) {
        B total = zero;

        while (hasNext()) {
            total = acc.apply(total, next());
        }

        return total;
    }

    /**
     * "sums" the elements on the iterator from left to right
     * but the result is an iterator where each element
     * presents the "sum" of all elements till it.
     * <p>
     * e.g.
     * Iterator(1,2,3,4,5).scanLeft(0, Integer::sum) // Iterator(0,1,3,6,10,15)
     */
    default <B> RichIterator<B> scanLeft(B zero, BiFunction<? super B, ? super A, ? extends B> acc) {
        return new RichIterator<B>() {
            private B result;

            @Override
            public boolean hasNext() {
                if (result == null) {
                    return true;
                } else {
                    return RichIterator.this.hasNext();
                }
            }

            @Override
            public B next() {
                if (result == null) {
                    result = zero;
                } else if (hasNext()) {
                    result = acc.apply(result, RichIterator.this.next());
                } else {
                    throw new NoSuchElementException();
                }

                return result;
            }
        };
    }

    /**
     * generates a serial of elements that every element leads to the next one.
     * <p>
     * for example
     * Iterator itr = RichIterator.iterate(0, x -> x + 1)
     * itr.next() // 0
     * itr.next() // 1
     * itr.next() // 2
     * itr.next() // 3
     *
     * @param first    the first element
     * @param progress the progress function
     * @param <A>      the type
     * @return an iterator
     */
    static <A> RichIterator<A> iterate(A first, Function<? super A, ? extends A> progress) {
        return new RichIterator<A>() {
            private A element;

            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public A next() {
                if (isEmpty()) {
                    throw new NoSuchElementException();
                }

                if (element == null) {
                    element = first;
                } else {
                    element = progress.apply(element);
                }

                return element;
            }
        };
    }

    /**
     * creates a pair based iterator over, the left element comes from this and the right comes from that
     * <p>
     * for example:
     * RichIterator(1,2,3).zip(RichIterator.apply("a","b","c","d")) // RichIterator((1,"a"), (2,"b"), (3,"c"))
     *
     * @param that the other
     * @param <B>  the other type
     * @return zipped iterator
     */
    default <B> RichIterator<Pair<A, B>> zip(Iterator<B> that) {
        return new RichIterator<Pair<A, B>>() {
            @Override
            public boolean hasNext() {
                return RichIterator.this.hasNext() && that.hasNext();
            }

            @Override
            public Pair<A, B> next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }

                return Pair.apply(RichIterator.this.next(), that.next());
            }
        };
    }

    /**
     * zips the current iterator with indices
     * <p>
     * for example:
     * RichIterator(1,2,3).zipWithIndex() // RichIterator((1,0), (2,1), (3,2))
     *
     * @return the zipped iterator
     */
    default RichIterator<Pair<A, Integer>> zipWithIndex() {
        RichIterator<Integer> indexes = iterate(0, x -> x + 1);

        return zip(indexes);
    }

    /**
     * Converts the given iterator to map
     *
     * @param asPair a function that converts the elements to pair
     * @param <K>    key type
     * @param <V>    value type
     * @return a map
     */
    default <K, V> Map<K, V> toMap(Function<? super A, ? extends Pair<K, V>> asPair) {
        Pair<K, V> pair;
        Map<K, V> resultMap = new HashMap<>();
        while (hasNext()) {
            pair = asPair.apply(next());
            resultMap.put(pair._1, pair._2);
        }

        return resultMap;
    }

    // very hard

    /**
     * takes elements while the predicate returns true
     *
     * @param predicate the predicate
     * @return the first elements that fulfill the predicate
     * e.g.
     * RichIterator.apply(1,2,3,4,5,1,2,3,4,5).takeWhile(x -> x <= 3) // 1,2,3
     */
    default RichIterator<A> takeWhile(Predicate<? super A> predicate) {
        return new RichIterator<A>() {
            private A element;
            private boolean hasNext = true;
            private boolean checked = false;

            @Override
            public boolean hasNext() {
                if (!hasNext || checked) {
                    return hasNext;
                }

                hasNext = RichIterator.this.hasNext();

                if (hasNext) {
                    element = RichIterator.this.next();
                    hasNext = predicate.test(element);
                }

                checked = true;

                return hasNext;
            }

            @Override
            public A next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }

                checked = false;

                return element;
            }
        };
    }

    /**
     * takes elements until the predicate returns true
     *
     * @param predicate the predicate
     * @return the first elements that not fulfill the predicate
     * e.g.
     * RichIterator.apply(1,2,3,4,5,1,2,3,4,5).takeUntil(x -> x > 3) // 1,2,3
     */
    default RichIterator<A> takeUntil(Predicate<? super A> predicate) {
        return takeWhile(element -> !predicate.test(element));
    }

    /**
     * drops elements while the predicate returns true
     *
     * @param predicate the predicate
     * @return removes the first elements that fulfill the predicate
     * e.g.
     * RichIterator.apply(1,2,3,4,5,1,2,3,4,5).dropWhile(x -> x <= 3) // 4,5,1,2,3,4,5
     */
    default RichIterator<A> dropWhile(Predicate<? super A> predicate) {
        return new RichIterator<A>() {
            private A element;
            private boolean skip = true;

            @Override
            public boolean hasNext() {
                while (skip && RichIterator.this.hasNext()) {
                    element = RichIterator.this.next();
                    skip = predicate.test(element);
                }

                if (!skip && element != null) {
                    return true;
                }

                return RichIterator.this.hasNext();
            }

            @Override
            public A next() {
                if (hasNext()) {
                    if (element != null) {
                        A value = element;
                        element = null;

                        return value;
                    }

                    return RichIterator.this.next();
                } else {
                    throw new NoSuchElementException();
                }
            }
        };
    }

    /**
     * drops elements until the predicate returns true
     *
     * @param predicate the predicate
     * @return removes the first elements that not fulfill the predicate
     * e.g.
     * RichIterator.apply(1,2,3,4,5,1,2,3,4,5).dropUntil(x -> x >= 3) // 4,5,1,2,3,4,5
     */
    default RichIterator<A> dropUntil(Predicate<? super A> predicate) {
        return dropWhile(element -> !predicate.test(element));
    }

    /**
     * extends the iterator to supply a method that yields the first element without moving to the next element.
     * <p>
     * for example:
     * BuffedIterator itr = RichIterator.apply(1,2,3,4).buffed()
     * <p>
     * itr.head() // 1
     * itr.head() // 1
     * itr.head() // 1
     * itr.next() // 1
     * itr.head() // 2
     * itr.next() // 2
     *
     * @return the iterator
     */
    default BufferedIterator<A> buffered() {
        return new BufferedIterator<A>() {
            private A head;

            @Override
            public boolean hasNext() {
                return headOptional().isPresent();
            }

            @Override
            public A next() {
                Optional<A> element = headOptional();
                Optional<A> newHead = RichIterator.this.nextOptional();

                head = newHead.orElse(null);

                if (element.isPresent()) {
                    return element.get();
                } else {
                    throw new NoSuchElementException();
                }
            }

            @Override
            public A head() {
                if (head == null && RichIterator.this.hasNext()) {
                    head = RichIterator.this.next();
                }

                if (head == null) {
                    throw new NoSuchElementException();
                }

                return head;
            }

            @Override
            public Optional<A> headOptional() {
                A element;
                try {
                    element = head();
                } catch (NoSuchElementException exception) {
                    return Optional.empty();
                }

                return Optional.of(element);
            }
        };
    }

    /**
     * distinct the elements in the iterator using equals() method.
     *
     * @return an iterator with distinct values
     * e.g. Iterator(1,2,3,3,2,1,4).distinct() // Iterator(1,2,3,4)
     */
    default RichIterator<A> distinct() {
        return new RichIterator<A>() {
            private A element;
            private boolean hasNext = true;
            private boolean checked = false;
            private final Map<A, Boolean> values = new HashMap<>();

            @Override
            public boolean hasNext() {
                if (!hasNext || checked) {
                    return hasNext;
                }

                while (RichIterator.this.hasNext()) {
                    element = RichIterator.this.next();
                    if (!values.containsKey(element)) {
                        checked = true;
                        hasNext = true;
                        values.put(element, true);

                        return true;
                    }
                }

                hasNext = false;

                return false;

            }

            @Override
            public A next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }

                checked = false;

                return element;
            }
        };
    }

    /**
     * converts every element to Iterator and then flat them to single iterator
     * <p>
     * for example:
     * RichIterator.apply(1,2,3).flatMap(i -> RichIterator.apply(i,i,i)) // RichIterator(1,1,1,2,2,2,3,3,3)
     * RichIterator.apply(1,2,3).flatMap(i -> RichIterator.empty()) // RichIterator.empty()
     *
     * @param f   the function
     * @param <B> the type
     * @return the iterator
     */
    default <B> RichIterator<B> flatMap(Function<? super A, ? extends Iterator<B>> f) {
        return new RichIterator<B>() {
            private Iterator<B> iterator;

            @Override
            public boolean hasNext() {
                if (iterator != null && iterator.hasNext()) {
                    return true;
                } else if (RichIterator.this.hasNext()) {
                    iterator = f.apply(RichIterator.this.next());

                    return iterator.hasNext() || hasNext();
                } else {
                    iterator = null;

                    return false;
                }
            }

            @Override
            public B next() {
                if (hasNext()) {
                    return iterator.next();
                } else {
                    throw new NoSuchElementException();
                }
            }
        };
    }

    /**
     * return an iterator with only the elements fit the iterator
     * <p>
     * for example
     * RichIterator itr = RichIterator.from(1,2,3,4,5,6,7,8)
     * itr.filter(x -> x % 2 == 0) // RichIterator(2,4,6,8)
     * itr.filter(x -> x > 10) // RichIterator.empty()
     *
     * @param f the predicate
     * @return an iterator
     */
    default RichIterator<A> filter(Predicate<? super A> f) {
        return new RichIterator<A>() {
            private A element;
            private boolean hasNext = true;
            private boolean checked = false;

            @Override
            public boolean hasNext() {
                if (!hasNext || checked) {
                    return hasNext;
                }

                while (RichIterator.this.hasNext()) {
                    element = RichIterator.this.next();
                    hasNext = f.test(element);

                    if (hasNext) {
                        checked = true;

                        return true;
                    }
                }

                return false;
            }

            @Override
            public A next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }

                checked = false;

                return element;
            }
        };
    }

    // end

    @SuppressWarnings("rawtypes")
    RichIterator EMPTY = EmptyIterator.instance;

    /**
     * creates RichIterator based on the given elements
     *
     * @param values the elements
     * @param <A>    the type
     * @return the iterator
     */
    @SafeVarargs
    static <A> RichIterator<A> apply(A... values) {
        return RichIterator.from(Arrays.stream(values).iterator());
    }

    /**
     * Creates RichIterator based on the given Iterable
     *
     * @param that the iterable
     * @param <A>  the type
     * @return the iterator
     */
    static <A> RichIterator<A> from(Iterable<A> that) {
        return from(that.iterator());
    }

    /**
     * Extends the given iterator
     *
     * @param that the iterator
     * @param <A>  the type
     * @return the given iterator but better.
     */
    static <A> RichIterator<A> from(Iterator<A> that) {
        return new Wrapper<>(that);
    }

    @SuppressWarnings("unchecked")
    static <A> RichIterator<A> empty() {
        return EMPTY;
    }
}
