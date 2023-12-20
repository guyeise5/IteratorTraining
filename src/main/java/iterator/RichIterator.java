package iterator;

//import com.sun.org.apache.bcel.internal.generic.ATHROW;
//import com.sun.org.apache.xpath.internal.objects.XNodeSet;
//import com.sun.tools.javac.util.Iterators;
//import sun.jvm.hotspot.ui.treetable.JTreeTable;

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
        int count = 0;
        for (; this.hasNext(); ++count) this.next();

        return count;
    }

    /**
     * @return the last element of the iterator
     * @throws NoSuchElementException if the iterator is empty
     */
    default A last() {
        try {
            A lastElement = this.next();
            while (this.hasNext()) {
                lastElement = this.next();
            }

            return lastElement;
        } catch (Exception exception) {
            throw new NoSuchElementException();
        }
    }

    /**
     * @return the last element if not empty.
     */
    default Optional<A> lastOptional() {
        if (!this.hasNext()) {
            return Optional.empty();
        }

        return Optional.of(this.last());
    }

    /**
     * @param elem the element
     * @return the index of the element if exists, -1 otherwise
     */
    default int indexOf(A elem) {
        for (int index = 0; this.hasNext(); index++) {
            if (this.next().equals(elem)) return index;
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
        while (this.hasNext()) {
            A element = this.next();
            if (f.test(element)) return Optional.of(element);
        }
        return Optional.empty();
    }

    /**
     * @return Optional.of(next) if exists or empty() otherwise
     */
    default Optional<A> nextOptional() {
        return this.hasNext() ? Optional.of(this.next()) : Optional.empty();
    }

    /**
     * executes the given Consumer on all the elements
     *
     * @param f the Consumer
     */
    default void foreach(Consumer<? super A> f) {
        while (this.hasNext()) {
            f.accept(this.next());
        }
    }

    /**
     * @param elem checks if the iterator has the element
     * @return true if exists, false otherwise
     */
    default boolean contains(A elem) {
        while (this.hasNext()) {

            if (this.next().equals(elem)) return true;
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
        while (this.hasNext()) {
            collection.add(this.next());
        }

        return collection;
    }

    /**
     * @return a list built from the iterator's elements
     */
    default List<A> toList() {

        return this.toCollection(ArrayList::new);
    }

    /**
     * @return a set built from the iterator's elements
     */
    default Set<A> toSet() {

        return this.toCollection(HashSet::new);
    }

    /**
     * @param that other iterator
     * @return true if this and that have the same elements in the same order
     */
    default boolean sameElements(Iterator<A> that) {
        while (this.hasNext() && that.hasNext()) {
            if (!Objects.deepEquals(this.next(), that.next())) {
                return false;
            }
        }

        return !this.hasNext() && !that.hasNext();
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
        return new RichIterator<A>() {
            private boolean didUsed = false;

            @Override
            public boolean hasNext() {
                return !didUsed;
            }

            @Override
            public A next() {
                if (!didUsed) {
                    didUsed = true;
                    return elem;
                } else {
                    throw new NoSuchElementException();
                }
            }
        };
    }

    /**
     * finds the maximum element based on the given comparator
     *
     * @param comparator the comparator
     * @return the maximum element
     * @throws NoSuchElementException if the iterator is empty
     */
    default A max(Comparator<? super A> comparator) {
        try {

            return Collections.max(this.toList(), comparator);
        } catch (Exception exception) {
            throw new NoSuchElementException();
        }
    }

    /**
     * finds the minimum element based on the given comparator
     *
     * @param comparator the comparator
     * @return the minimum element
     * @throws NoSuchElementException if the iterator is empty
     */
    default A min(Comparator<? super A> comparator) {
        try {

            return Collections.min(this.toList(), comparator);
        } catch (Exception exception) {
            throw new NoSuchElementException();
        }
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
        if (!this.hasNext()) {
            throw new NoSuchElementException();
        }

        A accumulator = this.next();
        while (this.hasNext()) {
            accumulator = acc.apply(accumulator, this.next());
        }
        return accumulator;
    }

    /**
     * same as reduce but return Optional of result if exists or empty() if not.
     *
     * @param acc // see reduce.
     * @return // see reduce
     */
    default Optional<A> reduceOptional(BiFunction<? super A, ? super A, ? extends A> acc) {
        if (!this.hasNext()) {

            return Optional.empty();
        }
        A reduced = this.reduce(acc);

        return Optional.of(reduced);
    }

    /**
     * Creates a String from the iterator.
     *
     * @param prefix    Start of the string
     * @param delimiter delimiter between elements
     * @param suffix    end of the string
     * @return a string containing all the elments in the iterator.
     * @implNote use previous functions to solve.
     * e.g.
     * RichIterator(1,2,3,4,5).mkString("[", ";","]") // [1;2;3;4;5]
     * RichIterator(1).mkString("[", ";","]") // [1]
     * RichIterator().mkString("[", ";","]") // []
     */
    default String mkString(String prefix, String delimiter, String suffix) {
        StringBuilder result = new StringBuilder(prefix);
        while (this.hasNext()) {
            result.append(this.next());
            if (this.hasNext()) {
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
        return this.mkString("RichIterator(", ",", ")");
    }

    /**
     * adding the element at the end of the iterator
     *
     * @param elem the element
     * @return an iterator with the element appended
     * e.g.
     * RichIterator.apply(1,2,3,4).append(5) // 1,2,3,4,5
     */
    default RichIterator<A> append(A elem) {
        return new RichIterator<A>() {
            private boolean isAppended = false;

            @Override
            public boolean hasNext() {

                return RichIterator.this.hasNext() || !isAppended;
            }

            @Override
            public A next() {
                if (RichIterator.this.hasNext()) {

                    return RichIterator.this.next();
                }
                if (!isAppended) {
                    isAppended = true;

                    return elem;
                } else {
                    throw new NoSuchElementException();
                }
            }
        };
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
        return new RichIterator<A>() {
            private boolean isAppended = false;

            @Override
            public boolean hasNext() {
                return RichIterator.this.hasNext() || !isAppended;
            }

            @Override
            public A next() {
                if (!isAppended) {
                    isAppended = true;

                    return elem;
                }
                if (RichIterator.this.hasNext()) {

                    return RichIterator.this.next();
                } else {
                    throw new NoSuchElementException();
                }
            }
        };
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
        return new RichIterator<A>() {
            @Override
            public boolean hasNext() {
                return RichIterator.this.hasNext() || elems.hasNext();
            }

            @Override
            public A next() {
                if (elems.hasNext()) {

                    return elems.next();
                }
                if (RichIterator.this.hasNext()) {

                    return RichIterator.this.next();
                } else {
                    throw new NoSuchElementException();
                }
            }
        };
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
            private int count = n;

            @Override
            public boolean hasNext() {
                while (count > 0 && RichIterator.this.hasNext()) {
                    RichIterator.this.next();
                    count--;
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
            private int count = 0;

            @Override
            public boolean hasNext() {
                return count < n && RichIterator.this.hasNext();
            }

            @Override
            public A next() {
                if (hasNext()) {
                    count++;
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
        B accumulator = zero;

        while (this.hasNext()) {
            accumulator = acc.apply(accumulator, this.next());
        }

        return accumulator;
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
            private B accumulator = zero;
            private boolean isFirst = true;

            @Override
            public boolean hasNext() {
                return isFirst || RichIterator.this.hasNext();
            }

            @Override
            public B next() {
                if (isFirst) {
                    isFirst = false;
                    return accumulator;
                }

                if (RichIterator.this.hasNext()) {
                    accumulator = acc.apply(accumulator, RichIterator.this.next());
                    return accumulator;
                }

                throw new NoSuchElementException();
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
            private boolean didFirstUsed = false;
            private A last = first;

            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public A next() {
                if (!didFirstUsed) {
                    didFirstUsed = true;

                    return first;
                } else {
                    last = progress.apply(last);

                    return last;
                }
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
                if (hasNext()) {
                    return Pair.apply(RichIterator.this.next(), that.next());
                } else {
                    throw new NoSuchElementException();
                }
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
        return new RichIterator<Pair<A, Integer>>() {
            private int index = -1;


            @Override
            public boolean hasNext() {

                return RichIterator.this.hasNext();
            }

            @Override
            public Pair<A, Integer> next() {
                if (hasNext()) {
                    index++;

                    return Pair.apply(RichIterator.this.next(), index);
                } else {
                    throw new NoSuchElementException();
                }
            }
        };
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
        HashMap<K, V> resultMap = new HashMap<>();

        while (this.hasNext()) {
            Pair<K, V> currPair = asPair.apply(this.next());
            resultMap.put(currPair._1, currPair._2);
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
            private boolean hasNextChecked = false;
            private boolean hasNextResult = true;
            private A nextElement = null;

            private void findNext() {
                hasNextResult = RichIterator.this.hasNext();
                if (hasNextResult) {
                    nextElement = RichIterator.this.next();
                    hasNextResult = predicate.test(nextElement);
                }
                hasNextChecked = true;
            }

            @Override
            public boolean hasNext() {
                if (!hasNextChecked) {
                    findNext();
                }
                return hasNextResult;
            }

            @Override
            public A next() {
                if (hasNext()) {
                    hasNextChecked = false;
                    A result = nextElement;
                    nextElement = null;
                    return result;
                }
                throw new NoSuchElementException();
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
        return new RichIterator<A>() {
            private boolean hasNextChecked = false;
            private boolean hasNextResult = true;
            private A nextElement = null;

            private void findNext() {
                hasNextResult = RichIterator.this.hasNext();
                if (hasNextResult) {
                    nextElement = RichIterator.this.next();
                    hasNextResult = !predicate.test(nextElement);
                }
                hasNextChecked = true;
            }

            @Override
            public boolean hasNext() {
                if (!hasNextChecked) {
                    findNext();
                }
                return hasNextResult;
            }

            @Override
            public A next() {
                if (hasNext()) {
                    hasNextChecked = false;
                    A result = nextElement;
                    nextElement = null;
                    return result;
                }
                throw new NoSuchElementException();
            }
        };
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
            private boolean isStoppedDropping = false;
            private boolean dropping = false;
            private A nextElement = null;

            @Override
            public boolean hasNext() {
                while ((!isStoppedDropping && RichIterator.this.hasNext())
                        || (isStoppedDropping && !RichIterator.this.hasNext())) {
                    nextElement = RichIterator.this.hasNext()? RichIterator.this.next(): nextElement;
                    if (dropping || !predicate.test(nextElement)) {
                        dropping = true;
                        isStoppedDropping = true;
                        return true;
                    }
                }
                return RichIterator.this.hasNext();
            }

            @Override
            public A next() {
                if (hasNext()) {
                    isStoppedDropping = false;
                    return nextElement;
                }
                throw new NoSuchElementException();
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
     * -> tell Guy that the example is not correct - its (x -> x > 3)
     */
    default RichIterator<A> dropUntil(Predicate<? super A> predicate) {
        return new RichIterator<A>() {
            private boolean isStoppedDropping = false;
            private boolean dropping = false;
            private A nextElement = null;

            @Override
            public boolean hasNext() {
                while ((!isStoppedDropping && RichIterator.this.hasNext()) || (isStoppedDropping && !RichIterator.this.hasNext())) {
                    nextElement = RichIterator.this.hasNext()? RichIterator.this.next(): nextElement;
                    if (dropping || predicate.test(nextElement)) {
                        dropping = true;
                        isStoppedDropping = true;
                        return true;
                    }
                }
                return RichIterator.this.hasNext();
            }

            @Override
            public A next() {
                if (hasNext()) {
                    isStoppedDropping = false;
                    return nextElement;
                }
                throw new NoSuchElementException();
            }
        };
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
            private boolean hasBuffered = false;
            private A buffer;

            @Override
            public A head() {
                if (!hasBuffered) {
                    buffer = hasNext() ? next() : null;
                    if (buffer == null) {
                        throw new NoSuchElementException();
                    }
                    hasBuffered = true;
                }
                return buffer;
            }

            @Override
            public Optional<A> headOptional() {
                try {
                    return Optional.ofNullable(head());
                } catch (NoSuchElementException exception) {
                    return Optional.empty();
                }
            }

            @Override
            public boolean hasNext() {
                return hasBuffered || RichIterator.this.hasNext();
            }

            @Override
            public A next() {
                if (hasNext()) {
                    if (hasBuffered) {
                        hasBuffered = false;
                        A toReturn = buffer;
                        buffer = null;
                        return toReturn;
                    } else {
                        return RichIterator.this.next();
                    }
                } else {
                    throw new NoSuchElementException();
                }
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
            private final HashSet<A> uniqueElements = new HashSet<>();
            private A nextElement = null;

            private void findNextUnique() {
                while (RichIterator.this.hasNext()) {
                    A element = RichIterator.this.next();
                    if (uniqueElements.add(element)) {
                        nextElement = element;
                        return;
                    }
                }
                nextElement = null;
            }

            @Override
            public boolean hasNext() {
                if (nextElement == null) {
                    findNextUnique();
                }
                return nextElement != null;
            }

            @Override
            public A next() {
                if (hasNext()) {
                    A toReturn = nextElement;
                    nextElement = null;
                    return toReturn;
                }
                throw new NoSuchElementException();
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
            private Iterator<B> currentIterator = null;

            @Override
            public boolean hasNext() {
                while ((currentIterator == null || !currentIterator.hasNext()) && RichIterator.this.hasNext()) {
                    currentIterator = f.apply(RichIterator.this.next());
                }
                return currentIterator != null && currentIterator.hasNext();
            }

            @Override
            public B next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return currentIterator.next();
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
            private A nextElement = null;
            private boolean isNextAvailable = false;
            private boolean isNextChecked = false;

            private void findNext() {
                while (RichIterator.this.hasNext()) {
                    A element = RichIterator.this.next();
                    if (f.test(element)) {
                        nextElement = element;
                        isNextAvailable = true;
                        break;
                    }
                }
                isNextChecked = true;
            }

            @Override
            public boolean hasNext() {
                if (!isNextChecked) {
                    findNext();
                }
                return isNextAvailable;
            }

            @Override
            public A next() {
                if (hasNext()) {
                    isNextChecked = false;
                    isNextAvailable = false;
                    return nextElement;
                }
                throw new NoSuchElementException();
            }
        };
    }


    // end

    @SuppressWarnings("rawtypes")
    RichIterator EMPTY = EmptyIterator.instance;

    /**
     * creates RichIterator based on the given elements
     *
     * @param elements the elements
     * @param <A>      the type
     * @return the iterator
     */
    @SafeVarargs
    static <A> RichIterator<A> apply(A... elements) {
        return RichIterator.from(Arrays.stream(elements).iterator());
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
