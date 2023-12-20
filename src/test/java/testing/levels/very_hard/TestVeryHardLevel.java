package testing.levels.very_hard;

import iterator.BufferedIterator;
import iterator.RichIterator;
import org.testng.annotations.Test;
import testing.TestingUtils;
import testing.TestingUtils.CountablePredicate;

import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

import static org.testng.Assert.*;
import static testing.TestingUtils.basicTestIterator;
import static testing.TestingUtils.toCountablePredicate;

public class TestVeryHardLevel {
    @Test(timeOut = 1000, expectedExceptions = NoSuchElementException.class)
    public void bufferedTest() {
        RichIterator.empty().buffered().head();
    }

    @Test(timeOut = 1000)
    public void buffered_2_Test() {
        final BufferedIterator<Integer> buffered = RichIterator.apply(1, 2, 3, 4, 5).buffered();
        assertEquals(1, buffered.head().intValue());
        assertEquals(1, buffered.head().intValue());
        assertEquals(1, buffered.headOptional().get().intValue());
        assertEquals(1, buffered.headOptional().get().intValue());
        assertEquals(1, buffered.next().intValue());
        assertEquals(2, buffered.next().intValue());
        assertEquals(3, buffered.head().intValue());
        assertEquals(3, buffered.head().intValue());
        assertEquals(3, buffered.next().intValue());
        assertEquals(4, buffered.next().intValue());
        assertEquals(5, buffered.next().intValue());
        assertFalse(buffered.headOptional().isPresent());
        assertTrue(buffered.isEmpty());
    }

    @Test(timeOut = 1000)
    public void buffered_3_Test() {
        final BufferedIterator<Integer> buffered = RichIterator.apply(1).buffered();
        assertTrue(buffered.hasNext());
        assertEquals(1, buffered.next().intValue());
    }

    @Test(timeOut = 1000)
    public void buffered_4_Test() {
        final BufferedIterator<Integer> buffered = RichIterator.apply(1).buffered();
        assertTrue(buffered.hasNext());
        assertEquals(1, buffered.next().intValue());
    }

    @Test(timeOut = 1000)
    public void buffered_5_Test() {
        assertFalse(RichIterator.apply().buffered().hasNext());
        assertFalse(RichIterator.apply().buffered().headOptional().isPresent());
    }

    @Test(timeOut = 1000)
    public void buffered_6_Test() {
        final BufferedIterator<Integer> buffered = RichIterator.from(Stream.iterate(1, x -> x + 1).iterator()).buffered();
        assertEquals(1, buffered.head().intValue());
        assertEquals(1, buffered.head().intValue());
        assertEquals(1, buffered.headOptional().get().intValue());
        assertEquals(1, buffered.headOptional().get().intValue());
        assertEquals(1, buffered.next().intValue());
        assertEquals(2, buffered.next().intValue());
        assertEquals(3, buffered.head().intValue());
        assertEquals(3, buffered.head().intValue());
        assertEquals(3, buffered.headOptional().get().intValue());
        assertEquals(3, buffered.next().intValue());
        assertEquals(4, buffered.next().intValue());
        assertTrue(buffered.headOptional().isPresent());
        assertTrue(buffered.hasNext());
    }

    @Test(timeOut = 1000)
    public void buffered_7_Test() {
        final BufferedIterator<Integer> buffered = basicTestIterator().buffered();
        assertEquals(1, buffered.next().intValue());
        assertTrue(buffered.headOptional().isPresent());
        assertTrue(buffered.headOptional().isPresent());
        assertEquals(2, buffered.next().intValue());
        assertTrue(buffered.headOptional().isPresent());
        assertEquals(3, buffered.next().intValue());
        assertTrue(buffered.headOptional().isPresent());
        assertEquals(4, buffered.next().intValue());
        assertTrue(buffered.headOptional().isPresent());
        assertEquals(5, buffered.next().intValue());
        assertFalse(buffered.hasNext());
        assertFalse(buffered.headOptional().isPresent());
    }

    @Test(timeOut = 1000)
    public void buffered_8_Test() {
        RichIterator<Object> itr = RichIterator.from(Stream.of(1, 2, 3).map(x -> Collections.emptyIterator().next()).iterator());

        assertFalse( itr.buffered().headOptional().isPresent());
    }

    @Test(timeOut = 1000)
    public void filterTest() {
        final RichIterator<Integer> filter = basicTestIterator().filter(x -> x % 2 == 1);
        assertEquals(1, filter.next().intValue());
        assertEquals(3, filter.next().intValue());
        assertEquals(5, filter.next().intValue());
        assertFalse(filter.hasNext());
    }

    @Test(timeOut = 1000)
    public void filter_2_Test() {
        final RichIterator<Integer> filter = RichIterator.apply(1, 2, 3).filter(x -> true);
        for (int i = 0; i < 4; i++) {
            assertTrue(filter.hasNext());
        }
        assertEquals(1, filter.next().intValue());
        assertEquals(2, filter.next().intValue());
        assertEquals(3, filter.next().intValue());
    }

    @Test(timeOut = 1000)
    public void filter_3_Test() {
        final RichIterator<Integer> filter = RichIterator.from(Stream.iterate(0, x -> x + 1).iterator()).filter(x -> x % 2 == 0);
        assertEquals(0, filter.next().intValue());
        assertEquals(2, filter.next().intValue());
        assertEquals(4, filter.next().intValue());
        assertEquals(6, filter.next().intValue());
        assertEquals(8, filter.next().intValue());
        assertTrue(filter.hasNext());
        assertEquals(10, filter.next().intValue());
    }

    @Test(timeOut = 1000)
    public void filter_4_Test() {
        final RichIterator<Integer> filter = basicTestIterator().filter(x -> false);
        assertFalse(filter.hasNext());
    }

    @Test(timeOut = 1000)
    public void filter_5_Test() {
        final RichIterator<Integer> filter = basicTestIterator().filter(x -> true);
        assertEquals(1, filter.next().intValue());
        assertEquals(2, filter.next().intValue());
        assertEquals(3, filter.next().intValue());
        assertEquals(4, filter.next().intValue());
        assertEquals(5, filter.next().intValue());
        assertFalse(filter.hasNext());
    }

    @Test(timeOut = 1000)
    public void flatMapTest() {
        final RichIterator<Integer> itr = RichIterator.apply(1, 2, 3)
                .flatMap(x -> Stream.iterate(0, i -> i + 1).limit(x).iterator());

        assertEquals(0, itr.next().intValue());
        assertEquals(0, itr.next().intValue());
        assertEquals(1, itr.next().intValue());
        assertEquals(0, itr.next().intValue());
        assertEquals(1, itr.next().intValue());
        assertEquals(2, itr.next().intValue());
        assertFalse(itr.hasNext());
    }

    @Test(timeOut = 1000)
    public void flatMap_2_Test() {
        final RichIterator<Integer> itr = RichIterator.from(Stream.iterate(0, x -> x + 1).iterator());
        final RichIterator<Integer> flat = itr.flatMap(x -> Stream.iterate(0, i -> i + 1).limit(x + 1).iterator());
        assertEquals(0, flat.next().intValue());
        assertEquals(0, flat.next().intValue());
        assertEquals(1, flat.next().intValue());
        assertEquals(0, flat.next().intValue());
        assertEquals(1, flat.next().intValue());
        assertEquals(2, flat.next().intValue());
        assertEquals(0, flat.next().intValue());
        assertEquals(1, flat.next().intValue());
        assertEquals(2, flat.next().intValue());
        assertEquals(3, flat.next().intValue());
    }

    @Test(timeOut = 1000)
    public void flatMap_3_Test() {
        final RichIterator<Integer> itr = RichIterator.apply(1, 2, 3)
                .flatMap(x -> {
                    if (x == 1) {
                        return RichIterator.apply(1);
                    }
                    if (x == 2) {
                        return RichIterator.empty();
                    } else {
                        return RichIterator.apply(1,2,3);
                    }
                });
        assertEquals(1, itr.next().intValue());
        assertEquals(1, itr.next().intValue());
        assertEquals(2, itr.next().intValue());
        assertEquals(3, itr.next().intValue());
        assertFalse(itr.hasNext());
    }

    @Test(timeOut = 1000)
    public void flatMap_4_Test() {
        final RichIterator<Integer> itr = RichIterator.apply(1, 2, 3)
                .flatMap(x -> RichIterator.empty());

        assertFalse(itr.hasNext());
    }

    @Test(timeOut = 1000)
    public void flatMap_5_Test() {
        RichIterator<Object> itr = RichIterator.empty().flatMap(x -> null);
        assertFalse(itr.hasNext());
    }

    @Test(timeOut = 1000)
    public void distinctTest() {
        RichIterator<Integer> itr = RichIterator
                .from(Stream
                        .iterate(1, x -> x + 1)
                        .flatMap(x -> Stream.of(x, x, x))
                        .iterator()
                ).distinct();
        assertEquals(1, itr.next().intValue());
        assertEquals(2, itr.next().intValue());
        assertEquals(3, itr.next().intValue());
        assertEquals(4, itr.next().intValue());
        assertEquals(5, itr.next().intValue());
        assertEquals(6, itr.next().intValue());
        assertEquals(7, itr.next().intValue());
        assertEquals(8, itr.next().intValue());
        assertEquals(9, itr.next().intValue());
        assertEquals(10, itr.next().intValue());
        assertTrue(itr.hasNext());
    }

    @Test(timeOut = 1000)
    public void distinct_2_Test() {
        assertFalse(RichIterator.empty().distinct().hasNext());
    }

    @Test(timeOut = 1000)
    public void distinct_3_Test() {
        assertEquals(1, RichIterator.apply(1).distinct().next().intValue());
    }

    @Test(timeOut = 1000)
    public void distinct_4_Test() {
        RichIterator<TestingUtils.A> itr = RichIterator.from(Stream.of(1, 2, 3, 3, 2, 1, 4).map(TestingUtils.A::new).iterator()).distinct();
        assertEquals(1, itr.next().v);
        assertEquals(2, itr.next().v);
        assertEquals(3, itr.next().v);
        assertEquals(4, itr.next().v);
        assertFalse(itr.hasNext());
    }

    @Test(timeOut = 1000)
    public void distinct_5_Test() {
        RichIterator<Integer> itr = RichIterator.apply(1, 1, 2).distinct();
        assertEquals(1, itr.next().intValue());
        assertEquals(2, itr.next().intValue());
        assertFalse(itr.hasNext());
    }

    @Test(timeOut = 1000)
    public void distinct_6_Test() {
        RichIterator<Integer> itr = RichIterator.apply(1, 1, 2, 2, 3, 1, 2, 3, 1, 1, 2, 4, 2, 1).distinct();
        assertEquals(1, itr.next().intValue());
        assertEquals(2, itr.next().intValue());
        assertEquals(3, itr.next().intValue());
        assertEquals(4, itr.next().intValue());
        assertFalse(itr.hasNext());
    }

    @Test(timeOut = 1000)
    public void takeWhile_1() {
        RichIterator<Object> itr = RichIterator.empty().takeWhile(x -> x == "");
        assertFalse(itr.hasNext());
    }

    @Test(timeOut = 1000)
    public void takeWhile_2() {
        RichIterator<Integer> itr = RichIterator.apply(1, 2, 3, 4, 5, 1, 2, 3, 4, 5).takeWhile(x -> x != 4);
        assertEquals(1, itr.next().intValue());
        assertEquals(2, itr.next().intValue());
        assertEquals(3, itr.next().intValue());
        assertFalse(itr.hasNext());
    }

    @Test(timeOut = 1000)
    public void takeWhile_3() {
        RichIterator<Integer> itr = RichIterator.from(Stream.iterate(1, x -> x % 5 + 1).iterator()).takeWhile(x -> x != 4);
        assertEquals(1, itr.next().intValue());
        assertEquals(2, itr.next().intValue());
        assertEquals(3, itr.next().intValue());
        assertFalse(itr.hasNext());
    }

    @Test(timeOut = 1000)
    public void takeWhile_4() {
        RichIterator<Object> itr = RichIterator.empty().takeWhile(x -> x == "");
        assertFalse(itr.hasNext());
        assertFalse(itr.hasNext());
        assertFalse(itr.hasNext());
        assertFalse(itr.hasNext());
    }

    @Test(timeOut = 1000)
    public void takeWhile_5() {
        RichIterator<Integer> itr = RichIterator.apply(1, 2, 3, 4, 5, 1, 2, 3, 4, 5).takeWhile(x -> x != 4);
        assertTrue(itr.hasNext());
        assertTrue(itr.hasNext());
        assertTrue(itr.hasNext());
        assertEquals(1, itr.next().intValue());
        assertTrue(itr.hasNext());
        assertTrue(itr.hasNext());
        assertTrue(itr.hasNext());
        assertEquals(2, itr.next().intValue());
        assertTrue(itr.hasNext());
        assertTrue(itr.hasNext());
        assertTrue(itr.hasNext());
        assertEquals(3, itr.next().intValue());
        assertFalse(itr.hasNext());
        assertFalse(itr.hasNext());
        assertFalse(itr.hasNext());
    }

    @Test(timeOut = 1000)
    public void takeWhile_6() {
        RichIterator<Integer> itr = RichIterator.from(Stream.iterate(1, x -> x % 5 + 1).iterator()).takeWhile(x -> x != 4);
        assertTrue(itr.hasNext());
        assertTrue(itr.hasNext());
        assertTrue(itr.hasNext());
        assertEquals(1, itr.next().intValue());
        assertTrue(itr.hasNext());
        assertTrue(itr.hasNext());
        assertTrue(itr.hasNext());
        assertEquals(2, itr.next().intValue());
        assertTrue(itr.hasNext());
        assertTrue(itr.hasNext());
        assertTrue(itr.hasNext());
        assertEquals(3, itr.next().intValue());
        assertFalse(itr.hasNext());
        assertFalse(itr.hasNext());
        assertFalse(itr.hasNext());
        assertFalse(itr.hasNext());
    }

    @Test(timeOut = 1000)
    public void takeWhile_7() {
        RichIterator<Integer> itr = RichIterator.apply(1, 2, 3, 4, 5).takeWhile(x -> true);
        assertEquals(1, itr.next().intValue());
        assertEquals(2, itr.next().intValue());
        assertEquals(3, itr.next().intValue());
        assertTrue(itr.hasNext());
        assertTrue(itr.hasNext());
        assertEquals(4, itr.next().intValue());
        assertEquals(5, itr.next().intValue());
        assertFalse(itr.hasNext());
        assertFalse(itr.hasNext());
    }

    @Test(timeOut = 1000)
    public void takeUntil_1() {
        RichIterator<Object> itr = RichIterator.empty().takeUntil(x -> x == "");
        assertFalse(itr.hasNext());
    }

    @Test(timeOut = 1000)
    public void takeUntil_2() {
        RichIterator<Integer> itr = RichIterator.apply(1, 2, 3, 4, 5, 1, 2, 3, 4, 5).takeUntil(x -> x == 4);
        assertEquals(1, itr.next().intValue());
        assertEquals(2, itr.next().intValue());
        assertEquals(3, itr.next().intValue());
        assertFalse(itr.hasNext());
    }

    @Test(timeOut = 1000)
    public void takeUntil_3() {
        RichIterator<Integer> itr = RichIterator.from(Stream.iterate(1, x -> x % 5 + 1).iterator()).takeUntil(x -> x == 4);
        assertEquals(1, itr.next().intValue());
        assertEquals(2, itr.next().intValue());
        assertEquals(3, itr.next().intValue());
        assertFalse(itr.hasNext());
    }

    @Test(timeOut = 1000)
    public void takeUntil_4() {
        RichIterator<Integer> itr = RichIterator.apply(1, 2, 3, 4, 5).takeUntil(x -> x == 4);
        assertTrue(itr.hasNext());
        assertTrue(itr.hasNext());
        assertTrue(itr.hasNext());
        assertEquals(1, itr.next().intValue());
        assertEquals(2, itr.next().intValue());
        assertTrue(itr.hasNext());
        assertTrue(itr.hasNext());
        assertTrue(itr.hasNext());
        assertEquals(3, itr.next().intValue());
        assertFalse(itr.hasNext());
        assertFalse(itr.hasNext());
        assertFalse(itr.hasNext());
    }

    @Test(timeOut = 1000)
    public void takeUntil_5() {
        RichIterator<Integer> itr = RichIterator.from(Stream.iterate(1, x -> x % 5 + 1).iterator()).takeUntil(x -> x == 4);
        assertTrue(itr.hasNext());
        assertTrue(itr.hasNext());
        assertTrue(itr.hasNext());
        assertEquals(1, itr.next().intValue());
        assertTrue(itr.hasNext());
        assertTrue(itr.hasNext());
        assertTrue(itr.hasNext());
        assertEquals(2, itr.next().intValue());
        assertTrue(itr.hasNext());
        assertTrue(itr.hasNext());
        assertTrue(itr.hasNext());
        assertEquals(3, itr.next().intValue());
        assertFalse(itr.hasNext());
        assertFalse(itr.hasNext());
        assertFalse(itr.hasNext());
        assertFalse(itr.hasNext());
    }

    @Test(timeOut = 1000)
    public void takeUntil_6() {
        RichIterator<Object> itr = RichIterator.empty().takeUntil(x -> x == "");
        assertFalse(itr.hasNext());
        assertFalse(itr.hasNext());
        assertFalse(itr.hasNext());
        assertFalse(itr.hasNext());
    }

    @Test(timeOut = 1000)
    public void dropWhile_1() {
        RichIterator<Object> itr = RichIterator.empty().dropWhile(x -> x == "");
        assertFalse(itr.hasNext());
    }

    @Test(timeOut = 1000)
    public void dropWhile_2() {
        RichIterator<Integer> itr = RichIterator.apply(1, 2, 3, 4, 5, 1, 2, 3, 4, 5).dropWhile(x -> x != 4);
        assertEquals(4, itr.next().intValue());
        assertEquals(5, itr.next().intValue());
        assertEquals(1, itr.next().intValue());
        assertEquals(2, itr.next().intValue());
        assertEquals(3, itr.next().intValue());
        assertEquals(4, itr.next().intValue());
        assertEquals(5, itr.next().intValue());
        assertFalse(itr.hasNext());
    }

    @Test(timeOut = 1000)
    public void dropWhile_3() {
        RichIterator<Integer> itr = RichIterator.from(Stream.iterate(1, x -> x % 5 + 1).iterator()).dropWhile(x -> x != 4);
        assertEquals(4, itr.next().intValue());
        assertEquals(5, itr.next().intValue());
        assertEquals(1, itr.next().intValue());
        assertEquals(2, itr.next().intValue());
        assertEquals(3, itr.next().intValue());
        assertEquals(4, itr.next().intValue());
        assertTrue(itr.hasNext());
    }

    @Test(timeOut = 1000)
    public void dropWhile_4() {
        RichIterator<Object> itr = RichIterator.empty().dropWhile(x -> x == "");
        assertFalse(itr.hasNext());
        assertFalse(itr.hasNext());
        assertFalse(itr.hasNext());
        assertFalse(itr.hasNext());
    }

    @Test(timeOut = 1000)
    public void dropWhile_5() {
        RichIterator<Integer> itr = RichIterator.apply(1, 2, 3, 4, 5, 1, 2, 3, 4, 5).dropWhile(x -> x != 4);
        assertTrue(itr.hasNext());
        assertTrue(itr.hasNext());
        assertTrue(itr.hasNext());
        assertEquals(4, itr.next().intValue());
        assertTrue(itr.hasNext());
        assertTrue(itr.hasNext());
        assertTrue(itr.hasNext());
        assertEquals(5, itr.next().intValue());
        assertTrue(itr.hasNext());
        assertTrue(itr.hasNext());
        assertTrue(itr.hasNext());
        assertEquals(1, itr.next().intValue());
        assertTrue(itr.hasNext());
        assertTrue(itr.hasNext());
        assertTrue(itr.hasNext());
        assertEquals(2, itr.next().intValue());
        assertTrue(itr.hasNext());
        assertTrue(itr.hasNext());
        assertTrue(itr.hasNext());
        assertEquals(3, itr.next().intValue());
        assertTrue(itr.hasNext());
        assertTrue(itr.hasNext());
        assertTrue(itr.hasNext());
        assertEquals(4, itr.next().intValue());
        assertTrue(itr.hasNext());
        assertTrue(itr.hasNext());
        assertTrue(itr.hasNext());
        assertEquals(5, itr.next().intValue());
        assertFalse(itr.hasNext());
        assertFalse(itr.hasNext());
        assertFalse(itr.hasNext());
    }

    @Test(timeOut = 1000)
    public void dropWhile_6() {
        RichIterator<Integer> itr = RichIterator.from(Stream.iterate(1, x -> x % 5 + 1).iterator()).dropWhile(x -> x != 4);
        assertTrue(itr.hasNext());
        assertTrue(itr.hasNext());
        assertTrue(itr.hasNext());
        assertEquals(4, itr.next().intValue());
        assertTrue(itr.hasNext());
        assertTrue(itr.hasNext());
        assertTrue(itr.hasNext());
        assertEquals(5, itr.next().intValue()); //gives 4 instead of 5
        assertTrue(itr.hasNext());
        assertTrue(itr.hasNext());
        assertTrue(itr.hasNext());
        assertEquals(1, itr.next().intValue());
        assertTrue(itr.hasNext());
        assertTrue(itr.hasNext());
        assertTrue(itr.hasNext());
    }

    @Test(timeOut = 1000)
    public void dropWhile_7() {
        RichIterator<Integer> itr = RichIterator.apply(1, 2, 3, 4, 5).dropWhile(x -> true);
        assertFalse(itr.hasNext());
        assertFalse(itr.hasNext());
        assertFalse(itr.hasNext());
    }

    @Test(timeOut = 1000)
    public void dropUntil_1() {
        RichIterator<Object> itr = RichIterator.empty().dropUntil(x -> x == "");
        assertFalse(itr.hasNext());
        assertFalse(itr.hasNext());
        assertFalse(itr.hasNext());
    }

    @Test(timeOut = 1000)
    public void dropUntil_2() {
        RichIterator<Integer> itr = RichIterator.apply(1, 2, 3, 4, 5, 1, 2, 3, 4, 5).dropUntil(x -> x == 4);
        assertTrue(itr.hasNext());
        assertTrue(itr.hasNext());
        assertTrue(itr.hasNext());
        assertEquals(4, itr.next().intValue());
        assertTrue(itr.hasNext());
        assertTrue(itr.hasNext());
        assertTrue(itr.hasNext());
        assertEquals(5, itr.next().intValue());
        assertTrue(itr.hasNext());
        assertTrue(itr.hasNext());
        assertTrue(itr.hasNext());
        assertEquals(1, itr.next().intValue());
        assertTrue(itr.hasNext());
        assertTrue(itr.hasNext());
        assertTrue(itr.hasNext());
        assertEquals(2, itr.next().intValue());
        assertTrue(itr.hasNext());
        assertTrue(itr.hasNext());
        assertTrue(itr.hasNext());
        assertEquals(3, itr.next().intValue());
        assertTrue(itr.hasNext());
        assertTrue(itr.hasNext());
        assertTrue(itr.hasNext());
        assertEquals(4, itr.next().intValue());
        assertTrue(itr.hasNext());
        assertTrue(itr.hasNext());
        assertTrue(itr.hasNext());
        assertEquals(5, itr.next().intValue());
        assertFalse(itr.hasNext());
        assertFalse(itr.hasNext());
        assertFalse(itr.hasNext());
    }

    @Test(timeOut = 1000)
    public void dropUntil_3() {
        RichIterator<Integer> itr = RichIterator.from(Stream.iterate(1, x -> x % 5 + 1).iterator()).dropUntil(x -> x == 4);
        assertTrue(itr.hasNext());
        assertTrue(itr.hasNext());
        assertTrue(itr.hasNext());
        assertEquals(4, itr.next().intValue());
        assertTrue(itr.hasNext());
        assertTrue(itr.hasNext());
        assertTrue(itr.hasNext());
        assertEquals(5, itr.next().intValue());
        assertTrue(itr.hasNext());
        assertTrue(itr.hasNext());
        assertTrue(itr.hasNext());
        assertTrue(itr.hasNext());
        assertEquals(1, itr.next().intValue());
        assertTrue(itr.hasNext());
        assertTrue(itr.hasNext());
        assertTrue(itr.hasNext());
        assertTrue(itr.hasNext());
    }

    @Test(timeOut = 1000)
    public void dropUntil_7() {
        RichIterator<Integer> itr = RichIterator.apply(1, 2, 3, 4, 5).dropUntil(x -> false);
        assertFalse(itr.hasNext());
        assertFalse(itr.hasNext());
        assertFalse(itr.hasNext());
    }

    @Test(timeOut = 1000)
    public void takeWhile_8() {
        RichIterator<Integer> itr = RichIterator.apply(1, 2, 3, 4, 5).takeWhile(x -> true);
        assertTrue(itr.hasNext());
        assertTrue(itr.hasNext());
        assertTrue(itr.hasNext());
        assertEquals(1, itr.next().intValue());
        assertEquals(2, itr.next().intValue());
        assertEquals(3, itr.next().intValue());
        assertTrue(itr.hasNext());
        assertTrue(itr.hasNext());
        assertEquals(4, itr.next().intValue());
        assertEquals(5, itr.next().intValue());
        assertFalse(itr.hasNext());
        assertFalse(itr.hasNext());
    }

    @Test(timeOut = 1000)
    public void takeUntil_7() {
        RichIterator<Integer> itr = RichIterator.apply(1, 2, 3, 4, 5).takeUntil(x -> false);
        assertEquals(1, itr.next().intValue());
        assertEquals(2, itr.next().intValue());
        assertEquals(3, itr.next().intValue());
        assertEquals(4, itr.next().intValue());
        assertEquals(5, itr.next().intValue());
        assertFalse(itr.hasNext());
    }

    @Test(timeOut = 1000)
    public void takeUntil_8() {
        RichIterator<Integer> itr = RichIterator.apply(1, 2, 3, 4, 5).takeUntil(x -> true);
        assertFalse(itr.hasNext());
        assertFalse(itr.hasNext());
        assertFalse(itr.hasNext());
    }

    @Test(timeOut = 1000)
    public void takeUntil_9() {
        RichIterator<Integer> itr = RichIterator.apply(1, 2, 3, 4, 5);
        itr.takeUntil(x -> true);

        assertEquals(1, itr.next().intValue());
        assertEquals(2, itr.next().intValue());
        assertEquals(3, itr.next().intValue());
        assertEquals(4, itr.next().intValue());
        assertEquals(5, itr.next().intValue());
        assertFalse(itr.hasNext());
    }

    @Test(timeOut = 1000)
    public void takeWhile_9() {
        RichIterator<Integer> itr = RichIterator.apply(1, 2, 3, 4, 5);
        itr.takeWhile(x -> false);

        assertEquals(1, itr.next().intValue());
        assertEquals(2, itr.next().intValue());
        assertEquals(3, itr.next().intValue());
        assertEquals(4, itr.next().intValue());
        assertEquals(5, itr.next().intValue());
        assertFalse(itr.hasNext());
    }

    @Test(timeOut = 1000)
    public void dropWhile_9() {
        RichIterator<Integer> itr = RichIterator.apply(1, 2, 3, 4, 5);
        itr.dropWhile(x -> true);
        assertEquals(1, itr.next().intValue());
        assertEquals(2, itr.next().intValue());
        assertEquals(3, itr.next().intValue());
        assertEquals(4, itr.next().intValue());
        assertEquals(5, itr.next().intValue());
        assertFalse(itr.hasNext());
    }

    @Test(timeOut = 1000)
    public void takeWhile_10() {
        CountablePredicate<Integer> p = toCountablePredicate((x) -> x <= 3);
        RichIterator<Integer> itr = RichIterator.apply(1, 2, 3, 4, 5).takeWhile(p);
        assertEquals(0, p.count());
        itr.next();
        assertEquals(1, p.count());
        itr.next();
        assertEquals(2, p.count());
        itr.next();
        assertEquals(3, p.count());
        assertFalse(itr.hasNext());
        assertEquals(4, p.count());
        assertFalse(itr.hasNext());
        assertEquals(4, p.count());
        assertFalse(itr.hasNext());
        assertEquals(4, p.count());

    }

    @Test(timeOut = 1000)
    public void takeUntil_10() {
        CountablePredicate<Integer> p = toCountablePredicate((x) -> x > 3);
        RichIterator<Integer> itr = RichIterator.apply(1, 2, 3, 4, 5).takeUntil(p);
        assertEquals(0, p.count());
        itr.next();
        assertEquals(1, p.count());
        itr.next();
        assertEquals(2, p.count());
        itr.next();
        assertEquals(3, p.count());
        assertFalse(itr.hasNext());
        assertEquals(4, p.count());
        assertFalse(itr.hasNext());
        assertEquals(4, p.count());
        assertFalse(itr.hasNext());
        assertEquals(4, p.count());
        assertFalse(itr.hasNext());
        assertEquals(4, p.count());
    }

    @Test(timeOut = 1000)
    public void dropUntil_9() {
        RichIterator<Integer> itr = RichIterator.apply(1, 2, 3, 4, 5);
        itr.dropUntil(x -> false);
        assertEquals(1, itr.next().intValue());
        assertEquals(2, itr.next().intValue());
        assertEquals(3, itr.next().intValue());
        assertEquals(4, itr.next().intValue());
        assertEquals(5, itr.next().intValue());
        assertFalse(itr.hasNext());
    }

    @Test(timeOut = 1000)
    public void dropUntil_10() {
        CountablePredicate<Integer> p = toCountablePredicate((x) -> x > 3);
        RichIterator<Integer> itr = RichIterator.apply(1, 2, 3, 4, 5).dropUntil(p);
        assertEquals(0, p.count());
        itr.next();
        assertEquals(4, p.count());
        itr.next();
        assertFalse(itr.hasNext());
        assertEquals(4, p.count());
        assertFalse(itr.hasNext());
        assertEquals(4, p.count());
        assertFalse(itr.hasNext());
        assertEquals(4, p.count());
    }

    @Test(timeOut = 1000)
    public void dropWhile_10() {
        CountablePredicate<Integer> p = toCountablePredicate((x) -> x <= 3);
        RichIterator<Integer> itr = RichIterator.apply(1, 2, 3, 4, 5).dropWhile(p);
        assertEquals(0, p.count());
        itr.next();
        assertEquals(4, p.count());
        itr.next();
        assertFalse(itr.hasNext());
        assertEquals(4, p.count());
        assertFalse(itr.hasNext());
        assertEquals(4, p.count());
        assertFalse(itr.hasNext());
        assertEquals(4, p.count());
        assertFalse(itr.hasNext());
        assertEquals(4, p.count());
    }
}
