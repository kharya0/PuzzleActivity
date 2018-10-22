import java.util.Arrays;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.HashSet;

class State implements Comparable<State>{

    private int[] node;
    private State parent;
    private int cost, depth, heuristic;

    //add heuristic variable
    public State(int[] node, State parent, int cost, int depth, int heuristic) {
        this.node = node;
        this.parent = parent;
        this.cost = cost;
        this.depth = depth;
        this.heuristic = heuristic;
    }

    public int[] getNode() {
        return node;
    }

    public State getParent() {
        return parent;
    }

    public int getCost() {
        return cost;
    }

    public int getHeuristic(){
        return heuristic;
    }

    public void setH(int heuristic) {
        this.heuristic = heuristic;
    }

    public ArrayList<State> expand(int[] goal) {
        ArrayList<State> successor = new ArrayList<>();
        int[] child = node.clone();

        if (node[0] == 0) {
            successor.add(new State(swap(0, 1, child), this, cost + 1, depth + 1, 0));
            child = node.clone();
            successor.add(new State(swap(0, 3, child), this, cost + 1, depth + 1, 0));
            child = node.clone();
        }

        if (node[1] == 0) {
            successor.add(new State(swap(1, 0, child), this, cost + 1, depth + 1, 0));
            child = node.clone();
            successor.add(new State(swap(1, 2, child), this, cost + 1, depth + 1, 0));
            child = node.clone();
            successor.add(new State(swap(1, 4, child), this, cost + 1, depth + 1, 0));
            child = node.clone();
        }

        if (node[2] == 0) {
            successor.add(new State(swap(2, 1, child), this, cost + 1, depth + 1, 0));
            child = node.clone();
            successor.add(new State(swap(2, 5, child), this, cost + 1, depth + 1, 0));
            child = node.clone();
        }

        if (node[3] == 0) {
            successor.add(new State(swap(3, 0, child), this, cost + 1, depth + 1, 0));
            child = node.clone();
            successor.add(new State(swap(3, 4, child), this, cost + 1, depth + 1, 0));
            child = node.clone();
            successor.add(new State(swap(3, 6, child), this, cost + 1, depth + 1, 0));
            child = node.clone();
        }

        if (node[4] == 0) {
             successor.add(new State(swap(4, 1, child), this, cost + 1, depth + 1, 0));
             child = node.clone();
             successor.add(new State(swap(4, 3, child), this, cost + 1, depth + 1, 0));
             child = node.clone();
             successor.add(new State(swap(4, 5, child), this, cost + 1, depth + 1, 0));
             child = node.clone();
             successor.add(new State(swap(4, 7, child), this, cost + 1, depth + 1, 0));
             child = node.clone();
        }

        if (node[5] == 0) {
            successor.add(new State(swap(5, 2, child), this, cost + 1, depth + 1, 0));
            child = node.clone();
            successor.add(new State(swap(5, 4, child), this, cost + 1, depth + 1, 0));
            child = node.clone();
            successor.add(new State(swap(5, 8, child), this, cost + 1, depth + 1, 0));
            child = node.clone();
        }

        if (node[6] == 0) {
            successor.add(new State(swap(6, 3, child), this, cost + 1, depth + 1, 0));
            child = node.clone();
            successor.add(new State(swap(6, 7, child), this, cost + 1, depth + 1, 0));
            child = node.clone();
        }

        if (node[7] == 0) {
            successor.add(new State(swap(7, 4, child), this, cost + 1, depth + 1, 0));
            child = node.clone();
            successor.add(new State(swap(7, 6, child), this, cost + 1, depth + 1, 0));
            child = node.clone();
            successor.add(new State(swap(7, 8, child), this, cost + 1, depth + 1, 0));
            child = node.clone();
        }

        if (node[8] == 0) {
            successor.add(new State(swap(8, 5, child), this, cost + 1, depth + 1, 0));
            child = node.clone();
            successor.add(new State(swap(8, 7, child), this, cost + 1, depth + 1, 0));
            child = node.clone();
        }

        return successor;
    }

    // swaps the elements of the specified index
    public int[] swap(int x, int y, int[] node) {
        int temp = node[x];
        node[x] = node[y];
        node[y] = temp;
        return node;
    }

    public boolean isGoal(int[] goal) {
        return Arrays.equals(goal, node);
    }

    @Override
    public String toString() {
        return String.format("[%d, %3d, %3d]\n[%d, %3d, %3d]\n[%d, %3d, %3d]\n", node[0], node[1], node[2], node[3], node[4], node[5], node[6], node[7], node[8]);
    }

    public int compareTo(State other) {
        return this.getHeuristic() - other.getHeuristic();
    }

} //end of class State

interface H {
    public int compute(State s, int[] g);
}

class ManhattanDistance implements H{

    @Override
    public int compute(State s, int[] goal){
        int h = 0;
        int[] node = s.getNode();
        int row = 0, col = 0;

        for (int i = 0; i < node.length; i++) {
            if (node[i] != goal[i]) {
                row = Math.abs((node[i] - goal[i]) / 3);
                col = Math.abs((node[i] - goal[i]) % 3);
                h += row + col;
            }
        }
        return h;
    }
}

public class Puzzle {

    final static int[] GOAL = new int[] {1,7,8,5,0,3,4,6,2};
    final static HashSet <String> seen = new HashSet <String>();

    public static void main(String[] args) {
        int[] init = new int[] {1,0,7,3,4,8,5,6,2};

        State initialState = new State(init, null, 0, 0, computeH(init, GOAL));
        search(initialState, new ManhattanDistance());
    }

    public static void search(State init, H h){

        PriorityQueue<State> frontier = new PriorityQueue<>();
        frontier.add(init);

        int totalNodesVisited = 0;
        int maxFrontierSize = 1;

        while (frontier.size() > 0) {
            State currentState = frontier.remove();

            totalNodesVisited++;

            if (currentState.isGoal(GOAL)) {
                showSolution(currentState, totalNodesVisited, maxFrontierSize);
                return;
            } else {
                ArrayList<State> successorStates = currentState.expand(GOAL);

                for(State s : successorStates){
                    s.setH(h.compute(s, GOAL));

                    if (!seen.contains(s.toString())) {
                        frontier.add(s);
                        seen.add(s.toString());
                    }
                }
                maxFrontierSize = Math.max(maxFrontierSize, frontier.size());
            }
        }
        System.out.println("No Solution.");
    }

    public static void showSolution(State state, int totalNodesVisited, int maxFrontierSize) {
        ArrayList<State> path = new ArrayList<>();

        while  (state != null) {
            path.add(0, state);
            state = state.getParent();
        }

        System.out.println("Solution: ");
        for (State st : path) {
            System.out.println(st);
        }

        int cost = path.get(path.size()-1).getCost();
        System.out.println("Number of moves: " + cost);
        System.out.println("Total nodes visited: " + totalNodesVisited);
        System.out.println("Maximum size of frontier: " + maxFrontierSize);
    }

    public static int computeH(int[] node, int[] goal) {
        int h = 0;
        int row = 0, col = 0;

        for (int i = 0; i < node.length; i++) {
            if (node[i] != goal[i]) {
                row = Math.abs((node[i] - goal[i]) / 3);
                col = Math.abs((node[i] - goal[i]) % 3);
                h += row + col;
            }
        }
        return h;
    }
}
