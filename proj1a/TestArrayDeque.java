import org.junit.Test;
import static org.junit.Assert.*;

public class TestArrayDeque {

    ArrayDeque<Integer> deque = new ArrayDeque<>();

    @Test
    public void testAddAndRemove() {
        for (int i = 0; i < 100; ++i) {
            deque.addFirst(100);
        }
        deque.printDeque();
        for (int i = 0; i < 100; ++i) {
            deque.removeFirst();
        }
        assertTrue(deque.isEmpty());
    }
}
