package testing.levels.hard;
import iterator.Pair;
import iterator.RichIterator;
import org.junit.Assert;
import org.testng.annotations.Test;
import testing.TestingDataProvider;
import testing.TestingUtils;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.junit.Assert.*;
import static testing.TestingUtils.*;
import static testing.TestingUtils.basicTestIterator;
import static testing.TestingUtils.randomOrderAIterator;

public class TestHardLevel {
    @Test(timeOut = 1000)
    public void drop_1_Test() {
        Assert.assertEquals(1, basicTestIterator().drop(0).next().intValue());
        Assert.assertEquals(2, basicTestIterator().drop(1).next().intValue());
        Assert.assertEquals(3, basicTestIterator().drop(2).next().intValue());
        Assert.assertEquals(4, basicTestIterator().drop(3).next().intValue());
        Assert.assertEquals(5, basicTestIterator().drop(4).next().intValue());
        assertFalse(basicTestIterator().drop(5).hasNext());
    }

    @Test(timeOut = 1000)
    public void drop_2_Test() {
        RichIterator<Integer> itr = RichIterator.apply(1, 2, 3, 4, 5, 6);
        itr.drop(3);
        assertEquals(1, itr.next().intValue());
    }

    @Test(timeOut = 1000)
    public void drop_3_Test() {
        RichIterator<Integer> itr = RichIterator.apply(1, 2, 3, 4, 5, 6);
        RichIterator<Integer> drop = itr.drop(10);
        assertFalse(drop.hasNext());
    }

    @Test(timeOut = 1000)
    public void drop_4_Test() {
        Supplier<RichIterator<Integer>> itr = () -> RichIterator.from(Stream.iterate(1, x -> x + 1).iterator());
        assertEquals(1, itr.get().drop(0).next().intValue());
        assertEquals(2, itr.get().drop(1).next().intValue());
        assertEquals(3, itr.get().drop(2).next().intValue());
        assertEquals(4, itr.get().drop(3).next().intValue());
        assertEquals(5, itr.get().drop(4).next().intValue());
    }

    @Test(timeOut = 1000)
    public void mapTest() {
        final RichIterator<Integer> itr = basicTestIterator().map(x -> x * 10);
        assertEquals(10, itr.next().intValue());
        assertEquals(20, itr.next().intValue());
        assertEquals(30, itr.next().intValue());
        assertEquals(40, itr.next().intValue());
        assertEquals(50, itr.next().intValue());
        assertFalse(itr.hasNext());

        final RichIterator<Integer> infItr = RichIterator.from(Stream.iterate(1, x -> x + 1).iterator()).map(x -> x * 10);
        assertEquals(10, infItr.next().intValue());
        assertEquals(20, infItr.next().intValue());
        assertEquals(30, infItr.next().intValue());
        assertEquals(40, infItr.next().intValue());
        assertEquals(50, infItr.next().intValue());
        assertTrue(infItr.hasNext());
    }

    @Test(timeOut = 1000, expectedExceptions = NoSuchElementException.class)
    public void take_test() {
        RichIterator<Integer> itr = RichIterator.apply(1, 2, 3, 4, 5).take(3);
        assertEquals(1, itr.next().intValue());
        assertEquals(2, itr.next().intValue());
        assertEquals(3, itr.next().intValue());
        itr.next();
    }

    @Test(timeOut = 1000)
    public void take_1_Test() {
        final RichIterator<Integer> itr = RichIterator.from(Stream.iterate(0, x -> x + 1).iterator()).take(10);
        for (int i = 0; i < 10; i++) {
            assertEquals(i, itr.next().intValue());
        }
        assertFalse(itr.hasNext());

        final RichIterator<Integer> itr2 = basicTestIterator().take(20);
        for (int i = 1; i <= 5; i++) {
            assertEquals(i, itr2.next().intValue());
        }
        assertFalse(itr.hasNext());
    }

    @Test(timeOut = 1000)
    public void take_2_Test() {
        RichIterator<Integer> itr = RichIterator.apply(1, 2, 3, 4, 5, 6);
        itr.take(2);
        assertEquals(1, itr.next().intValue());
    }

    @Test(timeOut = 1000)
    public void foldLeftTest() {
        Assert.assertEquals("12345", basicTestIterator().foldLeft("", (total, i) -> total + i));
    }

    @Test(timeOut = 1000)
    public void foldLeft_2_Test() {
        assertEquals("", RichIterator.<String>empty().foldLeft("", (t, i) -> t + i));
    }

    @Test(timeOut = 1000)
    public void zipTest() {
        final RichIterator<Integer> infInt = RichIterator.from(Stream.iterate(1, x -> x + 1).iterator());
        final Iterator<Character> infChar = Stream.iterate('a', x -> (char) ((int) x + 1)).iterator();
        final RichIterator<Pair<Integer, Character>> zip4 = infInt.zip(infChar);
        assertEquals(Pair.apply(1, 'a'), zip4.next());
        assertEquals(Pair.apply(2, 'b'), zip4.next());
        assertEquals(Pair.apply(3, 'c'), zip4.next());
        assertEquals(Pair.apply(4, 'd'), zip4.next());
        assertEquals(Pair.apply(5, 'e'), zip4.next());
        assertTrue(zip4.hasNext());
    }

    @Test(timeOut = 1000)
    public void zip_2_Test() {
        final Iterator<Character> chars2 = Stream.iterate('a', c -> (char) ((int) c + 1)).limit(10).iterator();
        final RichIterator<Pair<Integer, Character>> zip2 = basicTestIterator().zip(chars2);
        assertEquals(Pair.apply(1, 'a'), zip2.next());
        assertEquals(Pair.apply(2, 'b'), zip2.next());
        assertEquals(Pair.apply(3, 'c'), zip2.next());
        assertEquals(Pair.apply(4, 'd'), zip2.next());
        assertEquals(Pair.apply(5, 'e'), zip2.next());
        assertFalse(zip2.hasNext());
    }

    @Test(timeOut = 1000)
    public void zip_3_Test() {
        final Iterator<Character> chars3 = Stream.iterate('a', c -> (char) ((int) c + 1)).limit(3).iterator();
        final RichIterator<Pair<Integer, Character>> zip3 = basicTestIterator().zip(chars3);
        assertEquals(Pair.apply(1, 'a'), zip3.next());
        assertEquals(Pair.apply(2, 'b'), zip3.next());
        assertEquals(Pair.apply(3, 'c'), zip3.next());
        assertFalse(zip3.hasNext());
    }

    @Test(timeOut = 1000)
    public void zip_4_Test() {
        final Iterator<Character> chars = Stream.iterate('a', c -> (char) ((int) c + 1)).limit(5).iterator();
        final RichIterator<Pair<Integer, Character>> zip = basicTestIterator().zip(chars);
        assertEquals(Pair.apply(1, 'a'), zip.next());
        assertEquals(Pair.apply(2, 'b'), zip.next());
        assertEquals(Pair.apply(3, 'c'), zip.next());
        assertEquals(Pair.apply(4, 'd'), zip.next());
        assertEquals(Pair.apply(5, 'e'), zip.next());
        assertFalse(zip.hasNext());
    }

    @Test(timeOut = 1000)
    public void zipWithIndexTest() {
        final RichIterator<Character> itr = RichIterator.from(Stream.iterate('a', c -> (char) ((int) c + 1)).iterator());
        final RichIterator<Pair<Character, Integer>> zip = itr.zipWithIndex();
        assertEquals(Pair.apply('a', 0), zip.next());
        assertEquals(Pair.apply('b', 1), zip.next());
        assertEquals(Pair.apply('c', 2), zip.next());
        assertEquals(Pair.apply('d', 3), zip.next());
        assertEquals(Pair.apply('e', 4), zip.next());
        assertTrue(zip.hasNext());
    }

    @Test(timeOut = 1000)
    public void zipWithIndex_2_Test() {
        final RichIterator<Character> itr = RichIterator.from(Stream.iterate('a', c -> (char) ((int) c + 1)).limit(5).iterator());
        final RichIterator<Pair<Character, Integer>> zip = itr.zipWithIndex();
        assertEquals(Pair.apply('a', 0), zip.next());
        assertEquals(Pair.apply('b', 1), zip.next());
        assertEquals(Pair.apply('c', 2), zip.next());
        assertEquals(Pair.apply('d', 3), zip.next());
        assertEquals(Pair.apply('e', 4), zip.next());
        assertFalse(zip.hasNext());
    }

    @Test(timeOut = 1000)
    public void toMapTest() {
        final RichIterator<Pair<Character, Integer>> itr = RichIterator.apply(
                Pair.apply('a', 1),
                Pair.apply('b', 2),
                Pair.apply('c', 3),
                Pair.apply('d', 4),
                Pair.apply('e', 5)
        );

        final Map<Character, Integer> map = itr.toMap(Function.identity());

        assertEquals(5, map.size());
        assertEquals(1, map.get('a').intValue());
        assertEquals(2, map.get('b').intValue());
        assertEquals(3, map.get('c').intValue());
        assertEquals(4, map.get('d').intValue());
        assertEquals(5, map.get('e').intValue());
    }

    @Test(timeOut = 1000)
    public void toMap_2_Test() {
        final RichIterator<Pair<Character, Integer>> itr = RichIterator.empty();
        final Map<Character, Integer> map = itr.toMap(Function.identity());
        assertTrue(map.isEmpty());
    }
    @Test(timeOut = 1000)
    public void maxTest() {
        Assert.assertEquals(1, basicTestIterator().max((x, y) -> y - x).intValue());
    }

    @Test(timeOut = 1000)
    public void maxTest2() {
        Assert.assertEquals(new A(1), randomOrderAIterator().max((x, y) -> y.v - x.v));
    }

    @Test(expectedExceptions = NoSuchElementException.class)
    public void maxFailedTest() {
        final RichIterator<Integer> itr = RichIterator.apply(1);
        itr.next();
        itr.max(Comparator.naturalOrder());
    }

    @Test(timeOut = 1000)
    public void minTest() {
        Assert.assertEquals(5, basicTestIterator().min((x, y) -> y - x).intValue());
    }

    @Test(timeOut = 1000)
    public void minTest2() {
        Assert.assertEquals(new A(5), randomOrderAIterator().min((x, y) -> y.v - x.v));
    }

    @Test(expectedExceptions = NoSuchElementException.class)
    public void minFailedTest() {
        final RichIterator<Integer> itr = RichIterator.apply(1);
        itr.next();
        itr.min(Comparator.naturalOrder());
    }

    @Test(timeOut = 1000)
    public void iterateTest() {
        final RichIterator<Integer> itr = RichIterator.iterate(0, x -> x + 1);
        assertEquals(0, itr.next().intValue());
        assertEquals(1, itr.next().intValue());
        assertEquals(2, itr.next().intValue());
        assertEquals(3, itr.next().intValue());
        assertEquals(4, itr.next().intValue());
        assertEquals(5, itr.next().intValue());
        assertTrue(itr.hasNext());
    }
    @Test(timeOut = 1000)
    public void iterateTest_2() {
        TestingUtils.CountableFunction<Integer, Integer> f = toCountableFunction(x -> x + 1);
        RichIterator<Integer> itr = RichIterator.iterate(0, f);
        int i = 0;
        assertEquals(0, f.count());
        itr.next();
        assertEquals(i++, f.count());
        itr.next();
        assertEquals(i++, f.count());
        itr.next();
        assertEquals(i++, f.count());
        itr.next();
        assertEquals(i++, f.count());
        itr.next();
        assertEquals(i++, f.count());
        itr.next();
        assertEquals(i++, f.count());
        itr.next();
        assertEquals(i, f.count());
    }

    @Test(timeOut = 1000)
    public void pureTest() {
        final RichIterator<String> itr = RichIterator.pure("test");
        assertEquals("test", itr.next());
        assertFalse(itr.hasNext());
    }


    @Test(timeOut = 1000)
    public void tapEachTest() {
        AtomicInteger sum = new AtomicInteger(0);
        Consumer<Integer> add = sum::addAndGet;
        RichIterator<Integer> itr = RichIterator.from(Stream.iterate(1, x -> x + 1).iterator()).tapEach(add);
        itr.next();
        itr.next();
        itr.next();
        assertEquals(6, sum.get());
    }

    @Test(timeOut = 1000)
    public void tapEach_2_Test() {
        AtomicInteger sum = new AtomicInteger(0);
        Consumer<Integer> add = sum::addAndGet;
        RichIterator.<Integer>empty().tapEach(add);
        assertEquals(0, sum.get());
    }

    @Test(timeOut = 1000)
    public void tapEach_3_Test() {
        AtomicInteger sum = new AtomicInteger(0);
        Consumer<Integer> add = sum::addAndGet;
        RichIterator.apply(1, 2, 3, 4).tapEach(add);
        assertEquals(0, sum.get());
    }

    @Test(timeOut = 1000)
    public void tapEach_4_Test() {
        AtomicInteger sum = new AtomicInteger(0);
        Consumer<Integer> add = sum::addAndGet;
        RichIterator<Integer> itr = RichIterator.apply(1, 2, 3, 4, 5).tapEach(add);
        itr.next();
        itr.next();
        itr.next();
        assertEquals(6, sum.get());
    }

    @Test(timeOut = 1000)
    public void tapEach_5_Test() {
        AtomicInteger sum = new AtomicInteger(0);
        Consumer<Integer> add = sum::addAndGet;
        RichIterator<Integer> itr = RichIterator.from(Stream.iterate(1, x -> x + 1).iterator()).tapEach(add);
        itr.next();
        itr.next();
        itr.next();
        assertEquals(6, sum.get());
    }

    @Test(timeOut = 1000, expectedExceptions = NoSuchElementException.class)
    public void reduceTest() {
        RichIterator.<Integer>empty().reduce((total, x) -> 0);
    }

    @Test(timeOut = 1000)
    public void reduce_2_Test() {
        assertEquals(15, RichIterator.apply(1, 2, 3, 4, 5).reduce(Integer::sum).intValue());
    }

    @Test(timeOut = 1000)
    public void reduce_3_Test() {
        assertEquals(1, RichIterator.apply(1).reduce(Integer::sum).intValue());
    }

    @Test(timeOut = 1000)
    public void reduceOptional_Test() {
        assertFalse(RichIterator.<Integer>empty().reduceOptional(Integer::sum).isPresent());
    }

    @Test(timeOut = 1000)
    public void reduceOptional_2_Test() {
        assertEquals(15, RichIterator.apply(1, 2, 3, 4, 5).reduceOptional(Integer::sum).get().intValue());
    }

    @Test(timeOut = 1000)
    public void reduceOptional_3_Test() {
        assertEquals(1, RichIterator.apply(1).reduceOptional(Integer::sum).get().intValue());
    }

    @Test(timeOut = 1000, expectedExceptions = NoSuchElementException.class)
    public void reduceOptional_4_Test() {
        RichIterator<Object> itr = RichIterator.from(Stream.of(1, 2, 3).map(x -> Collections.emptyIterator().next()).iterator());

        itr.reduceOptional((x,y) -> null);
    }


    @Test(timeOut = 1000)
    public void appendTest() {
        RichIterator<Integer> itr = RichIterator.<Integer>empty().append(1);
        assertEquals(1, itr.next().intValue());
        assertFalse(itr.hasNext());
    }

    @Test(timeOut = 1000)
    public void append_2_Test() {
        RichIterator<Integer> itr = RichIterator.apply(1, 2, 3).append(4);
        assertEquals(1, itr.next().intValue());
        assertEquals(2, itr.next().intValue());
        assertEquals(3, itr.next().intValue());
        assertEquals(4, itr.next().intValue());
        assertFalse(itr.hasNext());
    }

    @Test(timeOut = 1000)
    public void append_3_Test() {
        RichIterator<Integer> itr = RichIterator.from(Stream.iterate(1, x -> x + 1).iterator()).append(-1);
        assertEquals(1, itr.next().intValue());
        assertEquals(2, itr.next().intValue());
        assertEquals(3, itr.next().intValue());
        assertEquals(4, itr.next().intValue());
        assertEquals(5, itr.next().intValue());
        assertEquals(6, itr.next().intValue());
        assertTrue(itr.hasNext());
    }

    @Test(timeOut = 1000)
    public void append_4_Test() {
        RichIterator<Integer> itr = RichIterator.apply(1, 2, 3, 4);
        itr.append(5);
        assertEquals(1, itr.next().intValue());
        assertEquals(2, itr.next().intValue());
        assertEquals(3, itr.next().intValue());
        assertEquals(4, itr.next().intValue());
        assertFalse(itr.hasNext());
    }

    @Test(timeOut = 1000)
    public void appendAllTest() {
        assertFalse(RichIterator.empty().appendAll(RichIterator.empty()).hasNext());
    }

    @Test(timeOut = 1000)
    public void appendAll_2_Test() {
        RichIterator<Integer> itr = RichIterator.<Integer>empty().appendAll(RichIterator.apply(1, 2, 3, 4));
        assertEquals(1, itr.next().intValue());
        assertEquals(2, itr.next().intValue());
        assertEquals(3, itr.next().intValue());
        assertEquals(4, itr.next().intValue());
        assertFalse(itr.hasNext());
    }

    @Test(timeOut = 1000, dataProvider = "tf", dataProviderClass = TestingDataProvider.class)
    public void appendAll_3_Test(boolean chk) {
        RichIterator<Integer> itr = RichIterator.<Integer>empty().appendAll(RichIterator.from(Stream.iterate(1, x -> x + 1).iterator()));
        if(chk) {
            assertTrue(itr.hasNext());
        }
        assertEquals(1, itr.next().intValue());
        assertEquals(2, itr.next().intValue());
        assertEquals(3, itr.next().intValue());
        assertEquals(4, itr.next().intValue());
        assertTrue(itr.hasNext());
    }

    @Test(timeOut = 1000)
    public void appendAll_4_Test() {
        RichIterator<Integer> itr = RichIterator.apply(1, 2, 3, 4).appendAll(RichIterator.empty());
        assertEquals(1, itr.next().intValue());
        assertEquals(2, itr.next().intValue());
        assertEquals(3, itr.next().intValue());
        assertEquals(4, itr.next().intValue());
        assertFalse(itr.hasNext());
    }

    @Test(timeOut = 1000)
    public void appendAll_5_Test() {
        RichIterator<Integer> itr = RichIterator.apply(1, 2, 3, 4).appendAll(RichIterator.apply(5, 6, 7, 8));
        assertEquals(1, itr.next().intValue());
        assertEquals(2, itr.next().intValue());
        assertEquals(3, itr.next().intValue());
        assertEquals(4, itr.next().intValue());
        assertEquals(5, itr.next().intValue());
        assertEquals(6, itr.next().intValue());
        assertEquals(7, itr.next().intValue());
        assertEquals(8, itr.next().intValue());
        assertFalse(itr.hasNext());
    }

    @Test(timeOut = 1000)
    public void appendAll_6_Test() {
        RichIterator<Integer> itr = RichIterator.apply(1, 2, 3, 4).appendAll(Stream.iterate(5, x -> x + 1).iterator());
        assertEquals(1, itr.next().intValue());
        assertEquals(2, itr.next().intValue());
        assertEquals(3, itr.next().intValue());
        assertEquals(4, itr.next().intValue());
        assertEquals(5, itr.next().intValue());
        assertEquals(6, itr.next().intValue());
        assertEquals(7, itr.next().intValue());
        assertEquals(8, itr.next().intValue());
        assertTrue(itr.hasNext());
    }

    @Test(timeOut = 1000)
    public void appendAll_7_Test() {
        RichIterator<Integer> itr = RichIterator.from(Stream.iterate(1, x -> x + 1).iterator()).appendAll(RichIterator.empty());
        assertEquals(1, itr.next().intValue());
        assertEquals(2, itr.next().intValue());
        assertEquals(3, itr.next().intValue());
        assertEquals(4, itr.next().intValue());
        assertTrue(itr.hasNext());
    }

    @Test(timeOut = 1000)
    public void appendAll_8_Test() {
        RichIterator<Integer> itr = RichIterator.from(Stream.iterate(1, x -> x + 1).iterator()).appendAll(RichIterator.apply(-1, -2, -3, -4));
        assertEquals(1, itr.next().intValue());
        assertEquals(2, itr.next().intValue());
        assertEquals(3, itr.next().intValue());
        assertEquals(4, itr.next().intValue());
        assertTrue(itr.hasNext());
    }

    @Test(timeOut = 1000)
    public void appendAll_9_Test() {
        RichIterator<Integer> itr = RichIterator.from(Stream.iterate(1, x -> x + 1).iterator()).appendAll(Stream.iterate(-1, x -> x - 1).iterator());
        assertEquals(1, itr.next().intValue());
        assertEquals(2, itr.next().intValue());
        assertEquals(3, itr.next().intValue());
        assertEquals(4, itr.next().intValue());
        assertTrue(itr.hasNext());
    }

    @Test(timeOut = 1000)
    public void appendAll_10_Test() {
        RichIterator<Integer> itr = RichIterator.apply(1, 2, 3, 4);
        itr.appendAll(RichIterator.apply(5,6,7));
        assertEquals(1,itr.next().intValue());
        assertEquals(2,itr.next().intValue());
        assertEquals(3,itr.next().intValue());
        assertEquals(4,itr.next().intValue());
        assertFalse(itr.hasNext());
    }

    @Test(timeOut = 1000)
    public void prependTest() {
        RichIterator<Integer> itr = RichIterator.<Integer>empty().prepend(1);
        assertEquals(1, itr.next().intValue());
        assertFalse(itr.hasNext());
    }

    @Test(timeOut = 1000)
    public void prepend_2_Test() {
        RichIterator<Integer> itr = RichIterator.apply(1, 2, 3, 4).prepend(0);
        assertEquals(0, itr.next().intValue());
        assertEquals(1, itr.next().intValue());
        assertEquals(2, itr.next().intValue());
        assertEquals(3, itr.next().intValue());
        assertEquals(4, itr.next().intValue());
        assertFalse(itr.hasNext());
    }

    @Test(timeOut = 1000)
    public void prepend_3_Test() {
        RichIterator<Integer> itr = RichIterator.from(Stream.iterate(1, x -> x + 1).iterator()).prepend(0);
        assertEquals(0, itr.next().intValue());
        assertEquals(1, itr.next().intValue());
        assertEquals(2, itr.next().intValue());
        assertEquals(3, itr.next().intValue());
        assertEquals(4, itr.next().intValue());
        assertTrue(itr.hasNext());
    }

    @Test(timeOut = 1000)
    public void prepend_4_Test() {
        RichIterator<Integer> itr = RichIterator.apply(1, 2, 3, 4);
        itr.prepend(0);
        assertEquals(1, itr.next().intValue());
        assertEquals(2, itr.next().intValue());
        assertEquals(3, itr.next().intValue());
        assertEquals(4, itr.next().intValue());
        assertFalse(itr.hasNext());
    }

    @Test(timeOut = 1000)
    public void prependAllTest() {
        assertFalse(RichIterator.empty().prependAll(RichIterator.empty()).hasNext());
    }

    @Test(timeOut = 1000)
    public void prependAll_2_Test() {
        RichIterator<Integer> itr = RichIterator.<Integer>empty().prependAll(RichIterator.apply(1, 2, 3, 4));
        assertEquals(1, itr.next().intValue());
        assertEquals(2, itr.next().intValue());
        assertEquals(3, itr.next().intValue());
        assertEquals(4, itr.next().intValue());
        assertFalse(itr.hasNext());
    }

    @Test(timeOut = 1000)
    public void prependAll_3_Test() {
        RichIterator<Integer> itr = RichIterator.<Integer>empty().prependAll(Stream.iterate(1, x -> x + 1).iterator());
        assertEquals(1, itr.next().intValue());
        assertEquals(2, itr.next().intValue());
        assertEquals(3, itr.next().intValue());
        assertEquals(4, itr.next().intValue());
        assertTrue(itr.hasNext());
    }

    @Test(timeOut = 1000)
    public void prependAll_4_Test() {
        RichIterator<Integer> itr = RichIterator.apply(1, 2, 3, 4).prependAll(RichIterator.empty());
        assertEquals(1, itr.next().intValue());
        assertEquals(2, itr.next().intValue());
        assertEquals(3, itr.next().intValue());
        assertEquals(4, itr.next().intValue());
        assertFalse(itr.hasNext());
    }

    @Test(timeOut = 1000)
    public void prependAll_5_Test() {
        RichIterator<Integer> itr = RichIterator.apply(5, 6, 7, 8).prependAll(RichIterator.apply(1, 2, 3, 4));
        assertEquals(1, itr.next().intValue());
        assertEquals(2, itr.next().intValue());
        assertEquals(3, itr.next().intValue());
        assertEquals(4, itr.next().intValue());
        assertEquals(5, itr.next().intValue());
        assertEquals(6, itr.next().intValue());
        assertEquals(7, itr.next().intValue());
        assertEquals(8, itr.next().intValue());
        assertFalse(itr.hasNext());
    }

    @Test(timeOut = 1000)
    public void prependAll_6_Test() {
        RichIterator<Integer> itr = RichIterator.apply(-1, -2, -3, -4).prependAll(Stream.iterate(1, x -> x + 1).iterator());
        assertEquals(1, itr.next().intValue());
        assertEquals(2, itr.next().intValue());
        assertEquals(3, itr.next().intValue());
        assertEquals(4, itr.next().intValue());
        assertEquals(5, itr.next().intValue());
        assertEquals(6, itr.next().intValue());
        assertEquals(7, itr.next().intValue());
        assertEquals(8, itr.next().intValue());
        assertTrue(itr.hasNext());
    }

    @Test(timeOut = 1000, dataProvider = "tf", dataProviderClass = TestingDataProvider.class)
    public void prependAll_7_Test(boolean chk) {
        RichIterator<Integer> itr = RichIterator.from(Stream.iterate(1, x -> x + 1).iterator()).prependAll(RichIterator.empty());
        if(chk){
            assertTrue(itr.hasNext());
        }
        assertEquals(1, itr.next().intValue());
        assertEquals(2, itr.next().intValue());
        assertEquals(3, itr.next().intValue());
        assertEquals(4, itr.next().intValue());
        assertTrue(itr.hasNext());
    }


    @Test(timeOut = 1000)
    public void prependAll_8_Test() {
        RichIterator<Integer> itr = RichIterator.from(Stream.iterate(4, x -> x + 1).iterator()).prependAll(RichIterator.apply(1, 2, 3));
        assertEquals(1, itr.next().intValue());
        assertEquals(2, itr.next().intValue());
        assertEquals(3, itr.next().intValue());
        assertEquals(4, itr.next().intValue());
        assertEquals(5, itr.next().intValue());
        assertEquals(6, itr.next().intValue());
        assertTrue(itr.hasNext());
    }

    @Test(timeOut = 1000)
    public void prependAll_9_Test() {
        RichIterator<Integer> itr = RichIterator.from(Stream.iterate(-1, x -> x - 1).iterator())
                .prependAll(Stream.iterate(1, x -> x + 1).iterator());
        assertEquals(1, itr.next().intValue());
        assertEquals(2, itr.next().intValue());
        assertEquals(3, itr.next().intValue());
        assertEquals(4, itr.next().intValue());
        assertEquals(5, itr.next().intValue());
        assertEquals(6, itr.next().intValue());
        assertTrue(itr.hasNext());
    }

    @Test(timeOut = 1000)
    public void prependAll_10_Test() {
        RichIterator<Integer> itr = RichIterator.apply(1, 2, 3, 4);
        itr.prependAll(RichIterator.apply(-2, -1, 0));
        assertEquals(1,itr.next().intValue());
        assertEquals(2,itr.next().intValue());
        assertEquals(3,itr.next().intValue());
        assertEquals(4,itr.next().intValue());
        assertFalse(itr.hasNext());
    }

    @Test(timeOut = 1000)
    public void scanLeft_1_Test() {
        RichIterator<Integer> itr = RichIterator.<Integer>empty().scanLeft(0, Integer::sum);
        assertEquals(0, itr.next().intValue());
        assertFalse(itr.hasNext());
    }
    @Test(timeOut = 1000)
    public void scanLeft_2_Test() {
        RichIterator<Integer> itr = RichIterator.apply(1,2,3,4,5).scanLeft(0, Integer::sum);
        assertEquals(0, itr.next().intValue());
        assertEquals(1, itr.next().intValue());
        assertEquals(3, itr.next().intValue());
        assertEquals(6, itr.next().intValue());
        assertEquals(10, itr.next().intValue());
        assertEquals(15, itr.next().intValue());
        assertFalse(itr.hasNext());
    }

    @Test(timeOut = 1000)
    public void scanLeft_3_Test() {
        RichIterator<Integer> itr = RichIterator.from(Stream.iterate(1, x -> x + 1).iterator()).scanLeft(0, Integer::sum);
        assertEquals(0, itr.next().intValue());
        assertEquals(1, itr.next().intValue());
        assertEquals(3, itr.next().intValue());
        assertEquals(6, itr.next().intValue());
        assertEquals(10, itr.next().intValue());
        assertEquals(15, itr.next().intValue());
        assertTrue(itr.hasNext());
    }

    @Test(timeOut = 1000)
    public void mkString_1_Test() {
        String s = RichIterator.apply(1, 2, 3, 4, 5).mkString("MyPrefix[", "|", "]");
        assertEquals("MyPrefix[1|2|3|4|5]", s);
    }

    @Test(timeOut = 1000)
    public void mkString_2_Test() {
        String s = RichIterator.empty().mkString("MyPrefix[", "|", "]");
        assertEquals("MyPrefix[]", s);
    }

    @Test(timeOut = 1000)
    public void mkString_3_Test() {
        String s = RichIterator.apply(1).mkString("MyPrefix[", "|", "]");
        assertEquals("MyPrefix[1]", s);
    }

    @Test(timeOut = 1000)
    public void mkString_4_Test() {
        String s = RichIterator.apply(1, 2, 3, 4, 5).mkString();
        assertEquals("RichIterator(1,2,3,4,5)", s);
    }

    @Test(timeOut = 1000)
    public void mkString_5_Test() {
        String s = RichIterator.apply(1).mkString();
        assertEquals("RichIterator(1)", s);
    }
    @Test(timeOut = 1000)
    public void mkString_6_Test() {
        String s = RichIterator.empty().mkString();
        assertEquals("RichIterator()", s);
    }

}
