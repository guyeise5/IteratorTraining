package iterator;


import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public final class Extra {
    private Extra() {
    }

    /**
     * @param itr iterator of person
     * @return the sum of their ages
     * @implNote try implement it with only one line of code !
     */
    public static int sumAges(RichIterator<Person> itr) {
        return itr.foldLeft(0, (x, y) -> x + y.age);
    }

    /**
     * @param itr iterator of person
     * @return the average age or empty if itr is empty
     * @implNote try implement it with only one line of code !
     */
    public static Optional<Integer> avgAge(RichIterator<Person> itr) {
        List<Person> iteratorKeeper = itr.toList();
        int length = RichIterator.from(iteratorKeeper.iterator()).length();
        return length == 0 ?
                Optional.empty() :
                Optional.of(sumAges(RichIterator.from(iteratorKeeper.iterator())) / length);
    }


//    public static Optional<Integer> avgAge(RichIterator<Person> itr) {
// doesnt work -> the index increasing in the methods, so we need separate iterators/lists to call on each method.
//        return itr.toList().isEmpty() ?
//                Optional.empty() :
//                Optional.of(sumAges(RichIterator.from(itr.toList().iterator())) / itr.toList().size());
//    }


    /**
     * Cartesian product between the two iterables
     * must use RichIterator
     * <p>
     * for example:
     * product( List(1,2,3), List("a","b","c")) ---->  RichIterator( (1, "a"), (1, "b"), (1, "c"), (2, "a"), (2, "b"), (2, "c"), (3, "a"), (3, "b"), (3, "c"))
     *
     * @param a   first iterable
     * @param b   second iterable
     * @param <A> type A
     * @param <B> type B
     * @return Cartesian product
     * @implNote try implement it with only one line of code !
     */
    public static <A, B> RichIterator<Pair<A, B>> product(Iterable<A> a, Iterable<B> b) {
        return RichIterator.from(a.iterator()).flatMap(elemA -> RichIterator.from(() ->
                Collections.nCopies(RichIterator.from(b.iterator()).length(),
                        elemA).iterator()).zip(RichIterator.from(b.iterator())));
    }


    /**
     * @return an iterator on multiplication board
     * e.g. 1,2,3,4,5,6,7,8,9,10,2,4,6,8....
     * @implNote try implement it with only 2 lines of code !
     */
    public static RichIterator<Integer> multiplicationBoard() {
        return RichIterator.apply(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
                .flatMap(i -> RichIterator.iterate(1, j -> j + 1).take(10)
                        .map(j -> i * j));
    }


    /**
     * creates a factorial iterator
     * <p>
     * 1 1 2 6 24 120 720  ....
     *
     * @return factorial iterator
     * @implNote try implement it with only one line of code !
     */

    public static RichIterator<Integer> factorial() {
        return RichIterator.iterate(new int[]{0, 1}, pair -> new int[]{pair[0] + 1, pair[1] * (pair[0] + 1)})
                .map(pair -> pair[1]);
    }


    public static class Person {
        public final String name;
        public final int age;
        public final int height;

        public Person(String name, int age, int height) {
            this.name = name;
            this.age = age;
            this.height = height;
        }
    }
}
