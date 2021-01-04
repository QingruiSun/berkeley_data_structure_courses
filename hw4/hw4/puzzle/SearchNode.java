package hw4.puzzle;

import java.util.HashSet;
import java.util.Set;

public class SearchNode implements Comparable<SearchNode> {

    private WorldState worldState;
    private int moveNumbers;
    private SearchNode prevSearchNode;

    public SearchNode(WorldState initialWorldState, int moveNumbers, SearchNode prevSearchNode) {
        worldState = initialWorldState;
        this.moveNumbers = moveNumbers;
        this.prevSearchNode = prevSearchNode;
    }

    public int getEstimateDistance() {
        int remainEstimateDistance = worldState.estimatedDistanceToGoal();
        return remainEstimateDistance + moveNumbers;
    }

    public Iterable<SearchNode> getNeighbooSearchNodes() {
        Set<SearchNode> searchNodeSet = new HashSet<>();
        Iterable<WorldState> neighborStates = worldState.neighbors();
        for (WorldState nextWorldState : neighborStates) {
            SearchNode nextSearchNode = new SearchNode(nextWorldState, moveNumbers + 1, this);
            searchNodeSet.add(nextSearchNode);
        }
        return searchNodeSet;
    }

    public SearchNode getPrevSearchNode() {
        return prevSearchNode;
    }

    public boolean isGoal() {
        return this.worldState.isGoal();
    }

    public int getMoveNumbers() {
        return moveNumbers;
    }

    public WorldState getWorldState() {
        return worldState;
    }

    @Override
    public int compareTo(SearchNode searchNode2) {
        int estimateDistance1 = getEstimateDistance();
        int estimateDistance2 = searchNode2.getEstimateDistance();
        if (estimateDistance1 < estimateDistance2) {
            return -1;
        } else if (estimateDistance1 == estimateDistance2) {
            return 0;
        } else {
            return 1;
        }
    }

    /* According to the use, we implement the equals() according to the worldState.
    * Concretely, according to the word and the target. */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (this.getClass() != o.getClass()) {
            return false;
        }
        SearchNode oNode = (SearchNode) o;
        if (this.worldState.equals(oNode.worldState)) {
            return true;
        }
        return false;
    }

}
