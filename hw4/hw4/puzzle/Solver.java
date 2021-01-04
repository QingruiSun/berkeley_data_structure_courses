package hw4.puzzle;

import java.util.HashSet;
import edu.princeton.cs.algs4.MinPQ;
import java.util.Set;

public class Solver {

    private Set<WorldState> worldStates;
    private WorldState worldState;
    private MinPQ<SearchNode> queue;
    private SearchNode endSearchNode;

    public Solver(WorldState initial) {
        worldState = initial;
        worldStates = new HashSet<>();
        queue = new MinPQ<SearchNode>();
        SearchNode initialSearchNode = new SearchNode(initial, 0, null);
        queue.insert(initialSearchNode);
        while (!queue.isEmpty()) {
            SearchNode nowSearchNode = queue.delMin();
            Iterable<SearchNode> nextSearchNodeSet = nowSearchNode.getNeighbooSearchNodes();
            for (SearchNode nextSearchNode : nextSearchNodeSet) {
                if (nextSearchNode.isGoal()) {
                    endSearchNode = nextSearchNode;
                    return;
                } else {
                    if (!nextSearchNode.equals(nowSearchNode.getPrevSearchNode())) {
                        queue.insert(nextSearchNode);
                    }
                }
            }
        }
    }

    public int moves() {
        return endSearchNode.getMoveNumbers();
    }

    public Iterable<WorldState> solution() {
        Set<WorldState> sets = new HashSet<>();
        SearchNode ptrNode = endSearchNode;
        while (ptrNode != null) {
            sets.add(ptrNode.getWorldState());
            ptrNode = ptrNode.getPrevSearchNode();
        }
        return sets;
    }
}
