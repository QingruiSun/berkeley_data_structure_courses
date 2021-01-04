package lab11.graphs;

/**
 *  @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private boolean cyclehasFound = false;


    public MazeCycles(Maze m) {
        super(m);
    }

    @Override
    public void solve() {
        dfs(0);
    }

    private void dfs(int s) {
        marked[s] = true;
        for (Integer t : maze.adj(s)) {
            if (!marked[t]) {
                marked[t] = true;
                edgeTo[t] = s;
                announce();
                distTo[t] = distTo[s] + 1;
                dfs(t);
            } else {
                if (edgeTo[s] != t) {
                    cyclehasFound = true;
                    System.out.println("Find cycle!");
                    edgeTo[s] = t;
                    announce();
                    return;
                }
            }
        }
    }

    // Helper methods go here
}

