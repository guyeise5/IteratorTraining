package iterator;

import org.testng.annotations.Test;

import javax.swing.text.rtf.RTFEditorKit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.*;

public class extraHardTest {

    @Test
    public void testTakeWhile_partOfTheElemtsMeeting() {
        RichIterator<Integer> ACTUAL = RichIterator.apply(1, 2, 3, 4, 5, 1, 2, 3, 4, 5).takeWhile(x -> x <= 3);

        assertFalse(ACTUAL.isEmpty());
        assertTrue(ACTUAL.hasNext());
        assertEquals(ACTUAL.next(), 1);
        assertEquals(ACTUAL.next(), 2);
        assertEquals(ACTUAL.next(), 3);
        assertFalse(ACTUAL.hasNext());
        assertTrue(ACTUAL.isEmpty());
    }

    @Test
    public void testTakeWhile_noElementsMeeting() {
        final RichIterator<Integer> EXPECTED = RichIterator.empty();

        RichIterator<Integer> ACTUAL = RichIterator.apply(1, 2, 3, 4, 5, 1, 2, 3, 4, 5).takeWhile(x -> x < 0);

        assertFalse(ACTUAL.hasNext());
        assertTrue(ACTUAL.isEmpty());
        assertEquals(EXPECTED, ACTUAL);

    }

    @Test
    public void testTakeWhile_everyElementMeets() {
        final RichIterator<Integer> EXPECTED = RichIterator.apply(1, 2, 3, 4, 5, 1, 2, 3, 4, 5);

        RichIterator<Integer> ACTUAL = RichIterator.apply(1, 2, 3, 4, 5, 1, 2, 3, 4, 5).takeWhile(x -> x > 0);

        assertEquals(EXPECTED, ACTUAL);
    }

    @Test
    public void testTakeWhile_firstElementNotMeeting() {
        final RichIterator<Integer> EXPECTED = RichIterator.empty();

        RichIterator<Integer> ACTUAL = RichIterator.apply(7, 2, 3, 4, 5, 1, 2, 3, 4, 5).takeWhile(x -> x <= 3);

        assertEquals(EXPECTED, ACTUAL);
        assertFalse(ACTUAL.hasNext());
        assertTrue(ACTUAL.isEmpty());

    }


    @Test
    public void testTakeUntil_partOfTheElementsMeeting() {
        RichIterator<Integer> ACTUAL = RichIterator.apply(1, 2, 3, 4, 5, 1, 2, 3, 4, 5).takeUntil(x -> x > 3);

        assertFalse(ACTUAL.isEmpty());
        assertTrue(ACTUAL.hasNext());
        assertEquals(ACTUAL.next(), 1);
        assertEquals(ACTUAL.next(), 2);
        assertEquals(ACTUAL.next(), 3);
        assertFalse(ACTUAL.hasNext());
        assertTrue(ACTUAL.isEmpty());
    }

    @Test
    public void testTakeUntil_noElementsMeeting() {
        final RichIterator<Integer> EXPECTED = RichIterator.empty();

        RichIterator<Integer> ACTUAL = RichIterator.apply(1, 2, 3, 4, 5, 1, 2, 3, 4, 5).takeUntil(x -> x > 0);

        assertFalse(ACTUAL.hasNext());
        assertTrue(ACTUAL.isEmpty());
        assertEquals(EXPECTED, ACTUAL);

    }

    @Test
    public void testTakeUntil_everyElementMeets() {
        final RichIterator<Integer> EXPECTED = RichIterator.apply(1, 2, 3, 4, 5, 1, 2, 3, 4, 5);

        RichIterator<Integer> ACTUAL = RichIterator.apply(1, 2, 3, 4, 5, 1, 2, 3, 4, 5).takeUntil(x -> x < 0);

        assertEquals(EXPECTED, ACTUAL);
    }

    @Test
    public void testTakeUntil_firstElementNotMeeting() {
        final RichIterator<Integer> EXPECTED = RichIterator.empty();

        RichIterator<Integer> ACTUAL = RichIterator.apply(7, 2, 3, 4, 5, 1, 2, 3, 4, 5).takeUntil(x -> x > 3);

        assertEquals(EXPECTED, ACTUAL);
        assertFalse(ACTUAL.hasNext());
        assertTrue(ACTUAL.isEmpty());
    }


    @Test
    public void testDropWhile_partOfTheElementsMeeting() {
        RichIterator<Integer> EXPECTED = RichIterator.apply(4, 5, 1, 2, 3, 4, 5);
        RichIterator<Integer> ACTUAL = RichIterator.apply(1, 2, 3, 4, 5, 1, 2, 3, 4, 5).dropWhile(x -> x <= 3);

//        assertEquals(ACTUAL, EXPECTED);
        assertFalse(ACTUAL.isEmpty());
        assertTrue(ACTUAL.hasNext());
        assertEquals(ACTUAL.next(), 4);
        assertEquals(ACTUAL.next(), 5);
        assertEquals(ACTUAL.next(), 1);
        assertEquals(ACTUAL.next(), 2);
        assertEquals(ACTUAL.next(), 3);
        assertEquals(ACTUAL.next(), 4);
        assertEquals(ACTUAL.next(), 5);
        assertFalse(ACTUAL.hasNext());
        assertTrue(ACTUAL.isEmpty());
    }

    @Test
    public void testDropWhile_noElementsMeeting() {
        final RichIterator<Integer> EXPECTED = RichIterator.apply(1, 2, 3, 4, 5, 1, 2, 3, 4, 5);

        RichIterator<Integer> ACTUAL = RichIterator.apply(1, 2, 3, 4, 5, 1, 2, 3, 4, 5).dropWhile(x -> x < 0);

        assertFalse(ACTUAL.isEmpty());
        assertEquals(ACTUAL.next(), 1);
        assertEquals(ACTUAL.next(), 2);
        assertEquals(ACTUAL.next(), 3);
        assertEquals(ACTUAL.next(), 4);
        assertEquals(ACTUAL.next(), 5);
        assertEquals(ACTUAL.next(), 1);
        assertEquals(ACTUAL.next(), 2);
        assertEquals(ACTUAL.next(), 3);
        assertEquals(ACTUAL.next(), 4);
        assertEquals(ACTUAL.next(), 5);
        assertFalse(ACTUAL.hasNext());
        assertTrue(ACTUAL.isEmpty());


    }

    @Test
    public void testDropWhile_everyElementMeets() {

        final RichIterator<Integer> EXPECTED = RichIterator.empty();

        RichIterator<Integer> ACTUAL = RichIterator.apply(1, 2, 3, 4, 5, 1, 2, 3, 4, 5).dropWhile(x -> x > 0);

        assertEquals(EXPECTED, ACTUAL);
        assertFalse(ACTUAL.hasNext());
        assertTrue(ACTUAL.isEmpty());
    }

    @Test
    public void testDropWhile_firstElementNotMeeting() {
        final RichIterator<Integer> EXPECTED = RichIterator.apply(2, 3, 4, 5, 1, 2, 3, 4, 5);

        RichIterator<Integer> ACTUAL = RichIterator.apply(7, 2, 3, 4, 5, 1, 2, 3, 4, 5).dropWhile(x -> x > 6);

//        assertEquals(ACTUAL, EXPECTED);
//        assertFalse(ACTUAL.hasNext());
        assertEquals(ACTUAL.next(), 2);
        assertEquals(ACTUAL.next(), 3);
        assertEquals(ACTUAL.next(), 4);
        assertEquals(ACTUAL.next(), 5);
        assertEquals(ACTUAL.next(), 1);
        assertEquals(ACTUAL.next(), 2);
        assertEquals(ACTUAL.next(), 3);
        assertEquals(ACTUAL.next(), 4);
        assertEquals(ACTUAL.next(), 5);
        assertFalse(ACTUAL.hasNext());
        assertTrue(ACTUAL.isEmpty());
    }

    @Test
    public void testDropUntil_partOfTheElementsMeeting() {
        RichIterator<Integer> EXPECTED = RichIterator.apply(4, 5, 1, 2, 3, 4, 5);
        RichIterator<Integer> ACTUAL = RichIterator.apply(1, 2, 3, 4, 5, 1, 2, 3, 4, 5).dropUntil(x -> x > 3);

//        assertEquals(ACTUAL, EXPECTED);
        assertFalse(ACTUAL.isEmpty());
        assertTrue(ACTUAL.hasNext());
        assertEquals(ACTUAL.next(), 4);
        assertEquals(ACTUAL.next(), 5);
        assertEquals(ACTUAL.next(), 1);
        assertEquals(ACTUAL.next(), 2);
        assertEquals(ACTUAL.next(), 3);
        assertEquals(ACTUAL.next(), 4);
        assertEquals(ACTUAL.next(), 5);
        assertFalse(ACTUAL.hasNext());
        assertTrue(ACTUAL.isEmpty());
    }

    @Test
    public void testDropUntil_everyElementMeets() {
        RichIterator<Integer> ACTUAL = RichIterator.apply(1, 2, 3, 4, 5, 1, 2, 3, 4, 5).dropUntil(x -> x > 0);

        assertFalse(ACTUAL.isEmpty());
        assertEquals(ACTUAL.next(), 1);
        assertEquals(ACTUAL.next(), 2);
        assertEquals(ACTUAL.next(), 3);
        assertEquals(ACTUAL.next(), 4);
        assertEquals(ACTUAL.next(), 5);
        assertEquals(ACTUAL.next(), 1);
        assertEquals(ACTUAL.next(), 2);
        assertEquals(ACTUAL.next(), 3);
        assertEquals(ACTUAL.next(), 4);
        assertEquals(ACTUAL.next(), 5);
        assertFalse(ACTUAL.hasNext());
        assertTrue(ACTUAL.isEmpty());


    }

    @Test
    public void testDropUntil_noElementsMeeting() {

        final RichIterator<Integer> EXPECTED = RichIterator.empty();

        RichIterator<Integer> ACTUAL = RichIterator.apply(1, 2, 3, 4, 5, 1, 2, 3, 4, 5).dropUntil(x -> x < 0);

        assertEquals(EXPECTED, ACTUAL);
        assertFalse(ACTUAL.hasNext());
        assertTrue(ACTUAL.isEmpty());
    }

    @Test
    public void testDropUntil_firstElementNotMeeting() {
        RichIterator<Integer> ACTUAL = RichIterator.apply(7, 2, 3, 4, 5, 1, 2, 3, 4, 5).dropUntil(x -> x < 6);

        assertEquals(ACTUAL.next(), 2);
        assertEquals(ACTUAL.next(), 3);
        assertEquals(ACTUAL.next(), 4);
        assertEquals(ACTUAL.next(), 5);
        assertEquals(ACTUAL.next(), 1);
        assertEquals(ACTUAL.next(), 2);
        assertEquals(ACTUAL.next(), 3);
        assertEquals(ACTUAL.next(), 4);
        assertEquals(ACTUAL.next(), 5);
        assertFalse(ACTUAL.hasNext());
        assertTrue(ACTUAL.isEmpty());
    }


    @Test
    public void testBuffered() {
        BufferedIterator itr = RichIterator.apply(1, 2, 3, 4).buffered();

        assertEquals(itr.head(), 1);
        assertEquals(itr.head(), 1);
        assertEquals(itr.next(), 1);
        assertEquals(itr.head(), 2);
        assertEquals(itr.head(), 2);
        assertEquals(itr.next(), 2);

    }

    @Test
    public void testDistinct() {
        RichIterator<Integer> EXPECTED = RichIterator.apply(1, 2, 3, 4);
        RichIterator<Integer> ACTUAL = RichIterator.apply(1, 2, 1, 2, 1, 3, 3, 4, 4, 4, 2, 3, 4, 1, 3, 4, 2).distinct();

        assertEquals(ACTUAL, EXPECTED);
    }

    @Test
    public void testDistinct_2() {
        RichIterator<Integer> ACTUAL = RichIterator.apply(1, 2, 3, 3, 2, 1, 4).distinct();

        List<Integer> distinctElements = new ArrayList<>();
        while (ACTUAL.hasNext()) {
            distinctElements.add(ACTUAL.next());
        }

        List<Integer> EXPECTED = Arrays.asList(1, 2, 3, 4);

        assertEquals(distinctElements, EXPECTED);
    }

    @Test
    public void testDistinct_3() {
        RichIterator<Integer> EXPECTED = RichIterator.apply(1, 2, 3, 4);
        RichIterator<Integer> ACTUAL = RichIterator.apply(1, 2, 1, 2, 1, 3, 3, 4, 4, 4, 2, 3, 4, 1, 3, 4, 2).distinct();

        assertEquals(ACTUAL.next(), 1);
        assertEquals(ACTUAL.next(), 2);
        assertEquals(ACTUAL.next(), 3);
        assertEquals(ACTUAL.next(), 4);

    }

    @Test
    public void testFlatMap_1() {
        RichIterator<Integer> EXPECTED = RichIterator.apply(1, 1, 1, 2, 2, 2, 3, 3, 3);
        RichIterator<Integer> ACTUAL = RichIterator.apply(1, 2, 3).flatMap(x -> RichIterator.apply(x, x, x));

//        assertEquals(ACTUAL, EXPECTED);
        assertEquals(ACTUAL.next(), 1);
        assertEquals(ACTUAL.next(), 1);
        assertEquals(ACTUAL.next(), 1);
        assertEquals(ACTUAL.next(), 2);
        assertEquals(ACTUAL.next(), 2);
        assertEquals(ACTUAL.next(), 2);
        assertEquals(ACTUAL.next(), 3);
        assertEquals(ACTUAL.next(), 3);
        assertEquals(ACTUAL.next(), 3);
        assertFalse(ACTUAL.hasNext());
    }

    @Test
    public void testFlatMap_2() {
        RichIterator<Integer> EXPECTED = RichIterator.empty();
        RichIterator<Integer> ACTUAL = RichIterator.apply(1, 2, 3).flatMap(x -> RichIterator.empty());

        assertEquals(ACTUAL, EXPECTED);
        assertTrue(ACTUAL.isEmpty());
    }

    @Test
    public void testFlatMap_3() {
        RichIterator<Integer> ACTUAL = RichIterator.apply(1, 2, 3).flatMap(x -> RichIterator.apply(2 * x));

        assertEquals(ACTUAL.next(), 2);
        assertEquals(ACTUAL.next(), 4);
        assertEquals(ACTUAL.next(), 6);
        assertFalse(ACTUAL.hasNext());
    }

    @Test
    public void testFilter_1() {
        RichIterator<Integer> ACTUAL = RichIterator.apply(1,2,3,4,5,6,7,8).filter(x -> x % 2 == 0);

        assertEquals(ACTUAL.next(), 2);
        assertEquals(ACTUAL.next(), 4);
        assertEquals(ACTUAL.next(), 6);
        assertEquals(ACTUAL.next(), 8);
        assertFalse(ACTUAL.hasNext());
    }
    @Test
    public void testFilter_2() {
        RichIterator<Integer> ACTUAL = RichIterator.apply(1,2,3,4,5,6,7,8).filter(x -> x > 10);

        assertTrue(ACTUAL.isEmpty());
        assertFalse(ACTUAL.hasNext());
    }
}