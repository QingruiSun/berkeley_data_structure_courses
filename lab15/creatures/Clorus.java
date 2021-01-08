package creatures;

import huglife.Creature;
import huglife.Action;
import huglife.Direction;
import huglife.Occupant;
import java.awt.Color;
import java.util.Map;
import java.util.List;
import java.util.Random;

public class Clorus extends Creature {

    private final int r = 34;
    private final int g = 0;
    private final int b = 231;

    public Clorus(double e) {
        super("clorus");
        energy = e;
    }

    public Clorus() {
        this(1);
    }

    public Color color() {
        return new Color(r, g, b);
    }

    public void move() {
        energy = energy - 0.03;
    }

    public void stay() {
        energy = energy - 0.01;
    }

    public Clorus replicate() {
        Clorus newClorus = new Clorus(energy / 2);
        energy = energy / 2;
        return newClorus;
    }

    public void attack(Creature c) {
        energy = energy + c.energy();
    }

    @Override
    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        List<Direction> emptyDirections = getNeighborsOfType(neighbors, "empty");
        List<Direction> plipDirections = getNeighborsOfType(neighbors, "plip");
        if (emptyDirections.size() == 0) {
            return new Action(Action.ActionType.STAY);
        } else if (plipDirections.size() != 0) {
            Random random = new Random();
            int plipDirectionIndex =  random.nextInt(plipDirections.size());
            Direction acctackDirection = plipDirections.get(plipDirectionIndex);
            return new Action(Action.ActionType.ATTACK, acctackDirection);
        } else if (energy < 1.0) {
            Random random = new Random();
            int emptyDirectionIndex = random.nextInt(emptyDirections.size());
            Direction moveDirection = emptyDirections.get(emptyDirectionIndex);
            return new Action(Action.ActionType.MOVE, moveDirection);
        } else {
            Random random = new Random();
            int emptyDirectionIndex = random.nextInt(emptyDirections.size());
            Direction replicateDirection = emptyDirections.get(emptyDirectionIndex);
            return new Action(Action.ActionType.REPLICATE, replicateDirection);
        }
    }
}
