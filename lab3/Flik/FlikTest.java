import org.junit.Test;
import static  org.junit.Assert.*;

public class FlikTest {

    @Test
    public void testIsSameNumber() {
        Integer A = 3;
        Integer B = 3;
        Integer C = 4;
        Integer D = 288;
        Integer E = 288;
        //test the function when the parameters is the same.
        assertTrue(Flik.isSameNumber(A, B));
        //test the function when the parameters is different.
        assertFalse(Flik.isSameNumber(A, C));
        //test the function when the parameters are big than 127.
        assertTrue(Flik.isSameNumber(D, E));
    }
}
