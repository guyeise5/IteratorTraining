package testing.levels.extra;

import iterator.Pair;
import iterator.RichIterator;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.stream.Stream;

import static iterator.Extra.*;
import static org.testng.Assert.*;

@SuppressWarnings("OptionalGetWithoutIsPresent")
@Test
public class TestExtra {
    @Test(timeOut = 1000)
    public void sumAgesTest() {
        assertEquals(0, sumAges(RichIterator.empty()));
    }

    @Test(timeOut = 1000)
    public void sumAges_2_Test() {
        assertEquals(1, sumAges(RichIterator.apply(new Person("aaa", 1, 100))));
    }

    @Test(timeOut = 1000)
    public void sumAges_3_Test() {
        assertEquals(15, sumAges(
                RichIterator.from(
                        Stream.of(1, 2, 3, 4, 5)
                                .map(age -> new Person("aaa", age, 100)).iterator()
                )
        ));
    }

    @Test(timeOut = 1000)
    public void avgAgeTest() {
        assertFalse(avgAge(RichIterator.empty()).isPresent());
    }

    @Test(timeOut = 1000)
    public void avgAge_2_Test() {
        assertEquals(1, avgAge(RichIterator.apply(new Person("a", 1,1))).get().intValue());
    }

    @Test(timeOut = 1000)
    public void avgAge_3_Test() {
        assertEquals(2, avgAge(RichIterator.apply(
                new Person("a", 1,1),
                new Person("b", 2,1),
                new Person("c", 3,1)
        )).get().intValue());
    }

    @Test(timeOut = 1000)
    public void factorialTest() {
        Iterator<Integer> itr = factorial();
        assertEquals(1, itr.next().intValue()); // 0!
        assertEquals(1, itr.next().intValue()); // 1!
        assertEquals(2, itr.next().intValue()); // 2!
        assertEquals(6, itr.next().intValue()); // 3!
        assertEquals(24, itr.next().intValue()); // 4!
        assertEquals(120, itr.next().intValue()); // 5!
        assertEquals(720, itr.next().intValue()); // 6!
        assertEquals(5040, itr.next().intValue()); // 7!
        assertTrue(itr.hasNext());

    }

    @Test(timeOut = 1000)
    public void productTest() {
        Iterable<Integer> itr1 = Arrays.asList(1,2,3);
        Iterable<String> itr2 = Arrays.asList("a","b");
        RichIterator<Pair<Integer, String>> product = product(itr1, itr2);
        assertEquals(Pair.apply(1,"a"), product.next());
        assertEquals(Pair.apply(1,"b"), product.next());
        assertEquals(Pair.apply(2,"a"), product.next());
        assertEquals(Pair.apply(2,"b"), product.next());
        assertEquals(Pair.apply(3,"a"), product.next());
        assertEquals(Pair.apply(3,"b"), product.next());
        assertFalse(product.hasNext());
    }

    @Test(timeOut = 1000)
    public void product_2_Test() {
        Iterable<Integer> itr1 = Collections.emptyList();
        Iterable<Integer> itr2 = Collections.emptyList();
        assertFalse(product(itr1,itr2).hasNext());
    }

    @Test(timeOut = 1000)
    public void product_3_Test() {
        Iterable<Integer> itr1 = Arrays.asList(1,2,3);
        Iterable<Integer> itr2 = Collections.emptyList();
        assertFalse(product(itr1,itr2).hasNext());
    }

    @Test(timeOut = 1000)
    public void product_4_Test() {
        Iterable<Integer> itr1 = Collections.emptyList();
        Iterable<String> itr2 = Arrays.asList("a","b","c");
        assertFalse(product(itr1,itr2).hasNext());
    }

    @Test(timeOut = 1000)
    public void product_5_Test() {
        Iterable<Integer> itr1 = () -> RichIterator.from(Stream.iterate(1, x -> x + 1).iterator());
        Iterable<String> itr2 = Arrays.asList("a","b");
        RichIterator<Pair<Integer, String>> product = product(itr1, itr2);

        assertEquals(Pair.apply(1,"a"), product.next());
        assertEquals(Pair.apply(1,"b"), product.next());
        assertEquals(Pair.apply(2,"a"), product.next());
        assertEquals(Pair.apply(2,"b"), product.next());
        assertEquals(Pair.apply(3,"a"), product.next());
        assertEquals(Pair.apply(3,"b"), product.next());
        assertTrue(product.hasNext());
    }

    @Test(timeOut = 1000)
    public void product_6_Test() {
        Iterable<Integer> itr1 = Collections.emptyList();
        Iterable<Integer> itr2 = () -> RichIterator.from(Stream.iterate(1, x -> x + 1).iterator());
        assertFalse(product(itr1,itr2).hasNext());
    }

    @Test(timeOut = 1000)
    public void multiplicationBoardTest() {
        RichIterator<Integer> itr = multiplicationBoard();
        for (int i = 1; i <= 10; i++) {
            for (int j = 1; j <= 10; j++) {
                assertEquals(i*j, itr.next().intValue());
            }
        }

        assertFalse(itr.hasNext());
    }
}
