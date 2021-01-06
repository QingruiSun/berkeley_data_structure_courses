import org.junit.Test;
import static org.junit.Assert.*;

public class TestArrayDeque {

    ArrayDeque<Integer> deque = new ArrayDeque<>();

    @Test
    public void testAddAndRemove() {
        for (int i = 0; i < 100; ++i) {
            deque.addLast(i);
        }
        for (int i = 0; i < 95; ++i) {
            deque.removeLast();
        }
        assertTrue(deque.get(3) == 3);
        for(int i = 0; i < 5; ++i) {
            deque.removeLast();
        }
        assertTrue(deque.isEmpty());
    }
}
