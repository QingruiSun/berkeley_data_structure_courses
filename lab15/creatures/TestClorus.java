package creatures;

import huglife.Direction;
import huglife.Action.ActionType;
import huglife.Empty;
import huglife.Occupant;
import huglife.Action;
import org.junit.Test;
import static org.junit.Assert.*;
import java.awt.Color;
import java.util.HashMap;



public class TestClorus {

    @Test
    public void testBasics() {
        Clorus c = new Clorus(1);
        assertEquals(c.energy(), 1, 0.01);
        assertEquals(c.color(), new Color(34, 0, 231));
        c.move();
        assertEquals(c.energy(), 0.97, 0.01);
        c.stay();
        assertEquals(c.energy(), 0.96, 0.01);
        Plip p = new Plip(0.04);
        c.attack(p);
        assertEquals(c.energy(), 1, 0.01);
    }

    @Test
    public void testChoose() {
        Clorus clorus = new Clorus(0.5);
        HashMap<Direction, Occupant> surrounded = new HashMap<Direction, Occupant>();
        Direction[] directions = {Direction.TOP, Direction.RIGHT, Direction.BOTTOM, Direction.LEFT};
        for (int i = 0; i < 4; ++i) {
            surrounded.put(directions[i], new Empty());
        }
        Action actual = clorus.chooseAction(surrounded);
        boolean choseToMove = false;
        for (int i = 0; i < 4; ++i) {
            Action onePossibleAction = new Action(ActionType.MOVE, directions[i]);
            if (actual.equals(onePossibleAction)) {
                choseToMove = true;
            }
        }
        assertTrue(choseToMove);
        Clorus energeticClorus = new Clorus(2);
        actual = energeticClorus.chooseAction(surrounded);
        boolean choseToReplicate = false;
        for (int i = 0; i < 4; ++i) {
            Action onePossibleAction = new Action(ActionType.REPLICATE, directions[i]);
            if (actual.equals(onePossibleAction)) {
                choseToReplicate = true;
            }
        }
        assertTrue(choseToReplicate);

    }
}
