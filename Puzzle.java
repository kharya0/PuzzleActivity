import java.util.Arrays;

class State {

    private int[] node;
    private State parent;

    public State(int[] node, State parent) {
        this.node = node;
        this.parent = parent;
    }

    public State expand() {
        
    }

    /*
    public boolean isGoal() {
        int[] goalArray = new int[]{0,1,2,3,4,5,6,7,8};
        return node == goalArray;
    } */

    @Override
    public String toString() {
        return String.format("[%3d, %3d, %3d]\n[%3d, %3d, %3d]\n[%3d, %3d, %3d]", node[0], node[1], node[2], node[3], node[4], node[5], node[6], node[7], node[8]);
    }

}

public class Puzzle {
    public static void main(String[] args) {
        int[] sample = new int[]{1,2,3,4,0,5,6,7,8};

        State sampleState = new State(sample, null);
        System.out.println(sampleState.toString());
    }
}
