import org.junit.Test;
import static org.junit.Assert.*;

public class TestArrayDequeGold {
    @Test
    public void testStudentArrayDeque() {
        StudentArrayDeque<Integer> studentDeque = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> solutionDeque = new ArrayDequeSolution<>();
        String errorMessage = "";
        int[] numberRecord = new int[3];
        for (int i = 0; i < 3; ++i) {
            int addNumber = StdRandom.uniform(9);
            studentDeque.addFirst(addNumber);
            solutionDeque.addFirst(addNumber);
            numberRecord[i] = addNumber;
        }
        for (int i = 0; i < 3; ++i) {
            errorMessage = errorMessage + "addFirst(" + numberRecord[i] +")\n" ;
        }
        for (int i = 0; i < 3; ++i) {
            int addNumber = StdRandom.uniform(9);
            studentDeque.addLast(addNumber);
            solutionDeque.addLast(addNumber);
            numberRecord[i] = addNumber;
        }
        for (int i = 0; i < 3; ++i) {
            errorMessage = errorMessage + "addLast(" + numberRecord[i] +")\n" ;
        }
        for (int i = 0; i < 3; ++i) {
            Integer expected = solutionDeque.removeFirst();
            Integer actual = studentDeque.removeFirst();
            assertEquals(expected, actual);
            expected = solutionDeque.removeLast();
            actual = studentDeque.removeLast();
            assertEquals(expected, actual);
        }
    }
}
