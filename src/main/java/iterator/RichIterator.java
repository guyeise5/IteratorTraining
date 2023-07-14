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
        throw new NotImplementedException();
    }

    /**
     * @return the last element of the iterator
     * @throws NoSuchElementException if the iterator is empty
     */
    default A last() {
        throw new NotImplementedException();
    }

    /**
     * @return the last element if not empty.
     */
    default Optional<A> lastOptional() {
        throw new NotImplementedException();
    }

    /**
     * @param elem the element
     * @return the index of the element if exists, -1 otherwise
     */
    default int indexOf(A elem) {
        throw new NotImplementedException();
    }

    /**
     * returns the first element fit to the given predicate
     *
     * @param f the predicate
     * @return Optional.of(A) if exists, empty() otherwise.
     */
    default Optional<A> find(Predicate<? super A> f) {
        throw new NotImplementedException();
    }

    /**
     * @return Optional.of(next) if exists or empty() otherwise
     */
    default Optional<A> nextOptional() {
        throw new NotImplementedException();
    }

    /**
     * executes the given Consumer on all the elements
     *
     * @param f the Consumer
     */
    default void foreach(Consumer<? super A> f) {
        throw new NotImplementedException();
    }

    /**
     * @param elem checks if the iterator has the element
     * @return true if exists, false otherwise
     */
    default boolean contains(A elem) {
        throw new NotImplementedException();
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
        throw new NotImplementedException();
    }

    /**
     * @return a list built from the iterator's elements
     */
    default List<A> toList() {
        throw new NotImplementedException();
    }

    /**
     * @return a set built from the iterator's elements
     */
    default Set<A> toSet() {
        throw new NotImplementedException();
    }

    /**
     * @param that other iterator
     * @return true if this and that have the same elements in the same order
     */
    default boolean sameElements(Iterator<A> that) {
        // TODO: implement this method
        throw new NotImplementedException();
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
        // TODO: implement this method
        throw new NotImplementedException();
    }

    /**
     * finds the maximum element based on the given comparator
     *
     * @param comparator the comparator
     * @return the maximum element
     * @throws NoSuchElementException if the iterator is empty
     */
    default A max(Comparator<? super A> comparator) {
        throw new NotImplementedException();
    }

    /**
     * finds the minimum element based on the given comparator
     *
     * @param comparator the comparator
     * @return the minimum element
     * @throws NoSuchElementException if the iterator is empty
     */
    default A min(Comparator<? super A> comparator) {
        throw new NotImplementedException();
    }

    /**
     * converts the iterator to new one after the mapping function
     *
     * @param f   the function
     * @param <B> the new type
     * @return a new Iterator.
     */
    default <B> RichIterator<B> map(Function<? super A, ? extends B> f) {
        throw new NotImplementedException();
    }

    /**
     * @param f the function to apply on each element.
     * @return an iterator with the method applied on all the elements
     * e.g.
     * Iterator itr = Iterator(1,2,3).tapEach(System.out::print).foreach(System.out::print)
     * output (printed): 112233
     */
    default RichIterator<A> tapEach(Consumer<? super A> f) {
        throw new NotImplementedException();
    }

    /**
     * reducing the elements to one according to the given function
     *
     * @param acc accumulate function (first element is the total, second element is the iterated element)
     * @return the reduced element.
     * @throws NoSuchElementException when the iterator is empty
     * e.g.
     * RichIterator.apply(1,2,3).reduce((total, a) -> total + a) // 6
     * RichIterator.empty().reduce((total, a) -> null) // throws NoSuchElementException
     * RichIterator.apply(1).reduce((total, a) -> total + a) // 1
     */
    default A reduce(BiFunction<? super A, ? super A, ? extends A> acc) {
        throw new NotImplementedException();
    }

    /**
     * same as reduce but return Optional of result if exists or empty() if not.
     *
     * @param acc // see reduce.
     * @return // see reduce
     */
    default Optional<A> reduceOptional(BiFunction<? super A, ? super A, ? extends A> acc) {
        throw new NotImplementedException();
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
        throw new NotImplementedException();
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
        throw new NotImplementedException();
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
        throw new NotImplementedException();
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
        throw new NotImplementedException();
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
        throw new NotImplementedException();
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
        throw new NotImplementedException();
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
        throw new NotImplementedException();
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
        throw new NotImplementedException();
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
        throw new NotImplementedException();
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
        throw new NotImplementedException();
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
        throw new NotImplementedException();
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
        throw new NotImplementedException();
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
        throw new NotImplementedException();
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
        throw new NotImplementedException();
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
        throw new NotImplementedException();
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
        throw new NotImplementedException();
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
        throw new NotImplementedException();
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
        throw new NotImplementedException();
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
        throw new NotImplementedException();
    }

    /**
     * distinct the elements in the iterator using equals() method.
     *
     * @return an iterator with distinct values
     * e.g. Iterator(1,2,3,3,2,1,4).distinct() // Iterator(1,2,3,4)
     */
    default RichIterator<A> distinct() {
        throw new NotImplementedException();
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
        throw new NotImplementedException();
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
        throw new NotImplementedException();
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
