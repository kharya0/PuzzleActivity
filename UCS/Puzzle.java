import java.util.Arrays;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Comparator;

class State implements Comparable<State>{

    private int[] node;
    private State parent;
    private int cost, depth;

    public State(int[] node, State parent, int cost, int depth) {
        this.node = node;
        this.parent = parent;
        this.cost = cost;
        this.depth = depth;
    }

    public State getParent() {
		return parent;
	}

    public int getCost() {
        return cost;
    }

    public ArrayList<State> expand(int[] goal) {
        ArrayList<State> successor = new ArrayList<>();
        int[] child = node.clone();

        if (node[0] == 0) {
            successor.add(new State(swap(0, 1, child), this, cost + 1, depth + 1));
            child = node.clone();
            successor.add(new State(swap(0, 3, child), this, cost + 1, depth + 1));
            child = node.clone();
        }

        if (node[1] == 0) {
            successor.add(new State(swap(1, 0, child), this, cost + 1, depth + 1));
            child = node.clone();
            successor.add(new State(swap(1, 2, child), this, cost + 1, depth + 1));
            child = node.clone();
            successor.add(new State(swap(1, 4, child), this, cost + 1, depth + 1));
            child = node.clone();
        }

        if (node[2] == 0) {
            successor.add(new State(swap(2, 1, child), this, cost + 1, depth + 1));
            child = node.clone();
            successor.add(new State(swap(2, 5, child), this, cost + 1, depth + 1));
            child = node.clone();
        }

        if (node[3] == 0) {
            successor.add(new State(swap(3, 0, child), this, cost + 1, depth + 1));
            child = node.clone();
            successor.add(new State(swap(3, 4, child), this, cost + 1, depth + 1));
            child = node.clone();
            successor.add(new State(swap(3, 6, child), this, cost + 1, depth + 1));
            child = node.clone();
        }

        if (node[4] == 0) {
             successor.add(new State(swap(4, 1, child), this, cost + 1, depth + 1));
             child = node.clone();
             successor.add(new State(swap(4, 3, child), this, cost + 1, depth + 1));
             child = node.clone();
             successor.add(new State(swap(4, 5, child), this, cost + 1, depth + 1));
             child = node.clone();
             successor.add(new State(swap(4, 7, child), this, cost + 1, depth + 1));
             child = node.clone();
        }

        if (node[5] == 0) {
            successor.add(new State(swap(5, 2, child), this, cost + 1, depth + 1));
            child = node.clone();
            successor.add(new State(swap(5, 4, child), this, cost + 1, depth + 1));
            child = node.clone();
            successor.add(new State(swap(5, 8, child), this, cost + 1, depth + 1));
            child = node.clone();
        }

        if (node[6] == 0) {
            successor.add(new State(swap(6, 3, child), this, cost + 1, depth + 1));
            child = node.clone();
            successor.add(new State(swap(6, 7, child), this, cost + 1, depth + 1));
            child = node.clone();
        }

        if (node[7] == 0) {
            successor.add(new State(swap(7, 4, child), this, cost + 1, depth + 1));
            child = node.clone();
            successor.add(new State(swap(7, 6, child), this, cost + 1, depth + 1));
            child = node.clone();
            successor.add(new State(swap(7, 8, child), this, cost + 1, depth + 1));
            child = node.clone();
        }

        if (node[8] == 0) {
            successor.add(new State(swap(8, 5, child), this, cost + 1, depth + 1));
            child = node.clone();
            successor.add(new State(swap(8, 7, child), this, cost + 1, depth + 1));
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
        return this.getCost() - other.getCost();
    }

} //end of class State

public class Puzzle {

    final static int[] GOAL = new int[]{1,2,3,4,5,6,7,8,0};

    public static void main(String[] args) {
        int[] sample = new int[]{8,1,3,4,0,2,7,6,5};

        State initialState = new State(sample, null, 0, 0);

        PriorityQueue<State> frontier = new PriorityQueue<>();
        frontier.add(initialState);

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
                frontier.addAll(successorStates);

                maxFrontierSize = Math.max(maxFrontierSize, frontier.size());
            }
        }
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
}
