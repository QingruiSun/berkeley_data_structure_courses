package lab11.graphs;

/**
 *  @author Josh Hug
 */
public class MazeAStarPath extends MazeExplorer {

    private int t;
    private boolean targetFound = false;
    private Maze maze;


    public MazeAStarPath(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
    }

    /** Estimate of the distance from v to the target. */
    private int h(int v) {
        int nowX = maze.toX(v);
        int nowY = maze.toY(v);
        int targetX = maze.toX(t);
        int targetY = maze.toY(t);
        int estimateDistance = Math.abs(nowX - targetX) + Math.abs(nowY - targetY);
        return estimateDistance;
    }

    /** Finds vertex estimated to be closest to target. */
    private int findMinimumUnmarked() {
        return -1;
        /* You do not have to use this method. */
    }

    /** Performs an A star search from vertex s. */
    private void astar(int s) {
        // TODO
    }

    @Override
    public void solve() {

    }



}

