package iterator;

import java.util.Optional;

import static iterator.RichIterator.*;

public final class Extra {
    private Extra() {
    }

    /**
     * @param itr iterator of person
     * @return the sum of their ages
     * @implNote try implement it with only one line of code !
     */
    public static int sumAges(RichIterator<Person> itr) {
        return itr.isEmpty() ? 0 : itr.map(person -> person.age).reduce(Integer::sum);
    }

    /**
     * @param itr iterator of person
     * @return the average age or empty if itr is empty
     * @implNote try implement it with only one line of code !
     */
    public static Optional<Integer> avgAge(RichIterator<Person> itr) {
        return itr.isEmpty()
                ? Optional.empty()
                : Optional.of(
                pure(itr.map(person -> person.age)
                        .zipWithIndex()
                        .reduce((total, pair) -> Pair.apply(total._1 + pair._1, pair._2)))
                        .map(pair -> pair._1 / (pair._2 + 1)).next()
        );

    }

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
        return from(a).flatMap(element1 -> from(b).map(element2 -> Pair.apply(element1, element2)));
    }

    /**
     * @return an iterator on multiplication board
     * e.g. 1,2,3,4,5,6,7,8,9,10,2,4,6,8....
     * @implNote try implement it with only 2 lines of code !
     */
    public static RichIterator<Integer> multiplicationBoard() {
        Iterable<Integer> line = iterate(1, x -> x + 1).take(10).toList();

        return product(line, line).map(pair -> pair._1 * pair._2);
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
        return iterate(Pair.apply(1, 1), pair -> Pair.apply(pair._1 * pair._2, pair._2 + 1)).map(pair -> pair._1);
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
