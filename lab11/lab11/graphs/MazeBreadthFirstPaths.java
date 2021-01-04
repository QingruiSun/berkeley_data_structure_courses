package lab11.graphs;


import java.util.LinkedList;
import java.util.Queue;

/**
 *  @author Josh Hug
 */
public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;
    private Queue<Integer> queue = new LinkedList<>();

    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        // Add more variables here!
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
    }

    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs() {
        //Your code here.
        // Don't forget to update distTo, edgeTo, and marked, as well as call announce()
        queue.add(s);
        distTo[s] = 0;
        edgeTo[s] = s;
        marked[s] = true;
        announce(); // Announce to the observer to change the graph.
        while (!queue.isEmpty()) {
            int w = queue.poll();
            for (Integer v : maze.adj(w)) {
                if (!marked[v]) {
                    edgeTo[v] = w;
                    distTo[v] = distTo[w] + 1;
                    queue.add(v);
                    marked[v] = true;
                    announce();
                    if (v == t) {
                        return;
                    }
                }
            }
        }
    }


    @Override
    public void solve() {
        bfs();
    }
}

