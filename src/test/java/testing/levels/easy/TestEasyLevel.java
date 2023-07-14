package testing.levels.easy;

import iterator.RichIterator;
import org.testng.annotations.Test;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.testng.Assert.*;
import static testing.TestingUtils.*;

public class TestEasyLevel {
    @Test(timeOut = 1000)
    public void isEmptyTest() {
        final RichIterator<Integer> itr = basicTestIterator();
        for (int i = 0; i < 10; i++) {
            assertFalse(itr.isEmpty());
        }
        itr.next();
        assertFalse(itr.isEmpty());
        itr.next();
        assertFalse(itr.isEmpty());
        itr.next();
        assertFalse(itr.isEmpty());
        assertFalse(itr.isEmpty());
        assertFalse(itr.isEmpty());
        itr.next();
        itr.next();
        assertTrue(itr.isEmpty());
    }

    @Test(timeOut = 1000)
    public void lastTest_1() {
        assertEquals(5, RichIterator.apply(1,2,3,4,5).last().intValue());
        assertEquals(1, RichIterator.apply(1).last().intValue());
    }

    @Test(timeOut = 1000, expectedExceptions = NoSuchElementException.class)
    public void lastTest_2() {
        RichIterator.empty().last();
    }

    @Test(timeOut = 1000)
    public void lastOptionalTest() {
        assertEquals(5, RichIterator.apply(1,2,3,4,5).lastOptional().get().intValue());
        assertEquals(1, RichIterator.apply(1).lastOptional().get().intValue());
        assertFalse(RichIterator.empty().lastOptional().isPresent());
    }
    @Test(timeOut = 1000, expectedExceptions = NoSuchElementException.class)
    public void lastOptionalTest_2() {
        RichIterator.from(Stream.iterate(null, x -> RichIterator.empty().next()).iterator()).lastOptional();
    }

    @Test(timeOut = 1000)
    public void lengthTest() {
        assertEquals(5, basicTestIterator().length());
    }

    @Test(timeOut = 1000)
    public void indexOfTest() {
        assertEquals(2, aTestIterator().indexOf(new A(3)));
        assertEquals(1, aTestIterator().indexOf(new A(2)));
        assertEquals(0, aTestIterator().indexOf(new A(1)));
        assertEquals(-1, aTestIterator().indexOf(new A(8)));
        assertEquals(5, naturalNumbers().indexOf(5));
        RichIterator<Integer> itr = RichIterator.apply(1, 2, 3, 4, 5);
        itr.indexOf(3);
        assertEquals(4, itr.next().intValue());
        assertEquals(3, RichIterator.apply(1, 2, 3, 4, 5, 1, 2, 3, 4, 5).indexOf(4));
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Test(timeOut = 1000)
    public void findTest() {
        assertEquals(3, basicTestIterator().find((x) -> x > 2).get().intValue());
        assertEquals(2, basicTestIterator().find((x) -> x == 2).get().intValue());
        assertEquals(3, basicTestIterator().find((x) -> x == 3).get().intValue());
        assertEquals(4, basicTestIterator().find((x) -> x == 4).get().intValue());
        assertEquals(5, basicTestIterator().find((x) -> x == 5).get().intValue());
        assertFalse(basicTestIterator().find((x) -> x == 8).isPresent());
    }

    @Test(timeOut = 1000)
    public void nextOptionalTest() {
        final RichIterator<Integer> itr = basicTestIterator();
        assertEquals(1, itr.nextOptional().get().intValue());
        assertEquals(2, itr.nextOptional().get().intValue());
        assertEquals(3, itr.nextOptional().get().intValue());
        assertEquals(4, itr.nextOptional().get().intValue());
        assertEquals(5, itr.nextOptional().get().intValue());
        assertFalse(itr.nextOptional().isPresent());
    }

    @Test(timeOut = 1000, expectedExceptions = NoSuchElementException.class)
    public void nextOptional_2_Test() {
        RichIterator<Object> itr = RichIterator.from(Stream.of(1, 2, 3).map(x -> Collections.emptyIterator().next()).iterator());

        itr.nextOptional();
    }

    @Test(timeOut = 1000)
    public void foreachTest() {
        List<Integer> lst = new ArrayList<>(5);
        basicTestIterator().foreach(lst::add);
        assertEquals(Arrays.asList(1, 2, 3, 4, 5), lst);
    }

    @Test(timeOut = 1000)
    public void contains_1_Test() {
        Supplier<RichIterator<A>> itr = () -> RichIterator.apply(new A(1), new A(2), new A(3), new A(4), new A(5));
        assertFalse(itr.get().contains(new A(0)));
        assertTrue(itr.get().contains(new A(1)));
        assertTrue(itr.get().contains(new A(2)));
        assertTrue(itr.get().contains(new A(3)));
        assertTrue(itr.get().contains(new A(4)));
        assertTrue(itr.get().contains(new A(4)));
    }

    @Test(timeOut = 1000)
    public void contains_2_Test() {
        RichIterator<Integer> itr = RichIterator.from(Stream.iterate(0, x -> x + 1).iterator());
        assertTrue(itr.contains(1000));
    }


    @Test(timeOut = 1000)
    public void toCollectionTest() {
        LinkedList<Integer> lst = RichIterator.apply(1, 2, 3, 4, 5).toCollection(LinkedList::new);
        assertEquals(5, lst.size());
        assertEquals(1, lst.get(0).intValue());
        assertEquals(2, lst.get(1).intValue());
        assertEquals(3, lst.get(2).intValue());
        assertEquals(4, lst.get(3).intValue());
        assertEquals(5, lst.get(4).intValue());
        Set<Object> empty = RichIterator.empty().toCollection(HashSet::new);
        assertEquals(0, empty.size());

    }

    @Test(timeOut = 1000)
    public void toListTest() {
        final List<Integer> lst = basicTestIterator().toList();
        assertEquals(5, lst.size());
        assertEquals(1, lst.get(0).intValue());
        assertEquals(2, lst.get(1).intValue());
        assertEquals(3, lst.get(2).intValue());
        assertEquals(4, lst.get(3).intValue());
        assertEquals(5, lst.get(4).intValue());
        assertEquals(0, RichIterator.empty().toList().size());
    }

    @Test(timeOut = 1000)
    public void toSetTest() {
        final Set<Integer> set = basicTestIterator().toSet();
        assertEquals(5, set.size());
        assertTrue(set.contains(1));
        assertTrue(set.contains(2));
        assertTrue(set.contains(3));
        assertTrue(set.contains(4));
        assertTrue(set.contains(5));
    }

    @Test(timeOut = 1000)
    public void sameElements() {
        assertTrue(aTestIterator().sameElements(Stream.iterate(1, x -> x + 1).map(A::new).limit(5).iterator()));
        assertFalse(aTestIterator().sameElements(Stream.iterate(1, x -> x + 1).map(A::new).limit(6).iterator()));
        assertFalse(aTestIterator().sameElements(Stream.iterate(1, x -> x + 1).map(A::new).limit(4).iterator()));

        // Check on infinite stream
        final RichIterator<A> testItr = RichIterator.from(Stream.iterate(1, x -> {
            if (x < 10000) {
                return x + 1;
            } else {
                return 0;
            }
        }).map(A::new).iterator());
        assertFalse(testItr.sameElements(Stream.iterate(0, x -> x + 1).map(A::new).iterator()));
    }
}
