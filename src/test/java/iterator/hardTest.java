package iterator;

import org.testng.annotations.Test;

import static org.testng.Assert.*;
import static testing.TestingUtils.basicTestIterator;

public class hardTest {

    @Test
    public void testPure() {
        assertEquals(RichIterator.apply(5), RichIterator.pure(5));
    }

    //    @Test
//    public void testMax() {
//        final RichIterator<Integer> itr = basicTestIterator();
//        assertEquals(5, itr.max());
//    }
//
//    @Test
//    public void testMin() {
//    }
//
    @Test
    public void testMap() {
        assertEquals
                (RichIterator.apply(2, 4, 6, 8, 10),
                RichIterator.apply(1, 2, 3, 4, 5).map(x -> 2 * x));
    }

//    @Test
//    public void testTapEach() {
//    }
//
//    @Test
//    public void testReduce() {
//    }
//
//    @Test
//    public void testReduceOptional() {
//    }
//
//    @Test
//    public void testMkString() {
//    }
//
//    @Test
//    public void testTestMkString() {
//    }
//
//    @Test
//    public void testAppend() {
//    }
//
//    @Test
//    public void testAppendAll() {
//    }
//
//    @Test
//    public void testPrepend() {
//    }
//
//    @Test
//    public void testPrependAll() {
//    }
//
//    @Test
//    public void testDrop() {
//    }
//
//    @Test
//    public void testTake() {
//    }
//
//    @Test
//    public void testFoldLeft() {
//    }
//
//    @Test
//    public void testScanLeft() {
//    }
//
//    @Test
//    public void testIterate() {
//    }
//
//    @Test
//    public void testZip() {
//    }
//
//    @Test
//    public void testZipWithIndex() {
//    }
//
//    @Test
//    public void testToMap() {
//    }
}