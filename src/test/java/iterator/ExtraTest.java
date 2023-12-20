package iterator;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class ExtraTest {

    @Test
    public void testSumAges() {
        RichIterator<Extra.Person> itr = RichIterator.apply(
                new Extra.Person("a", 10, 100),
                new Extra.Person("b", 20, 100),
                new Extra.Person("c", 30, 100));

        assertEquals(Extra.sumAges(itr), 60);
    }

//    @Test
//    public void testAvgAge() {
//    }
//
//    @Test
//    public void testProduct() {
//    }

    @Test
    public void testMultiplicationBoard() {
        RichIterator<Integer> ACTUAL = Extra.multiplicationBoard();

        RichIterator<Integer> EXPECTED = RichIterator.apply
                (1, 2, 3, 4, 5, 6, 7, 8, 9, 10
                        , 2, 4, 6, 8, 10, 12, 14, 16, 18, 20,
                        3, 6, 9, 12, 15, 18, 21, 24, 27, 30,
                        4, 8, 12, 16, 20, 24, 28, 32, 36, 40,
                        5, 10, 15, 20, 25, 30, 35, 40, 45, 50,
                        6, 12, 18, 24, 30, 36, 42, 48, 54, 60,
                        7, 14, 21, 28, 35, 42, 49, 56, 63, 70,
                        8, 16, 24, 32, 40, 48, 56, 64, 72, 80,
                        9, 18, 27, 36, 45, 54, 63, 72, 81, 90,
                        10, 20, 30, 40, 50, 60, 70, 80, 90, 100);

        assertEquals(ACTUAL, EXPECTED);
    }

    @Test
    public void testFactorial() {
        RichIterator<Integer> factorial = Extra.factorial();
        assertEquals(1, factorial.next());
        assertEquals(1, factorial.next());
        assertEquals(2, factorial.next());
        assertEquals(6, factorial.next());
        assertEquals(24, factorial.next());
        assertEquals(120, factorial.next());
        assertEquals(720, factorial.next());
    }
}