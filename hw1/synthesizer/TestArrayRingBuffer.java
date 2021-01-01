package synthesizer;
import org.junit.Test;
import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<>(10);
        for (int i = 0; i < 10; ++i) {
            arb.enqueue(i);
        }
        assertTrue(arb.isFull());
        double eps = 1e-7;
        for (int i = 0; i < 10; ++i) {
            int expected = i;
            int actual = arb.dequeue();
            assertTrue(expected == actual);
        }
        assertTrue(arb.isEmpty());
    }


    @Test
    public void testIteration() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<>(10);
        for (int i = 0; i < 10; ++i) {
            arb.enqueue(i);
        }
        for (Integer arbItem : arb) {
            System.out.println(arbItem);
        }
    }

    /** Calls tests for ArrayRingBuffer. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
    }
} 
