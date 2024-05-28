import java.util.*;

class PuzzleNodes implements Comparable<PuzzleNodes> {
    int[][] state;
    PuzzleNodes parent;
    String move;
    int costFromStart;
    int heuristic;
    int totalCost;

    public PuzzleNodes(int[][] state, PuzzleNodes parent, String move) {
        this.state = state;
        this.parent = parent;
        this.move = move;
        this.costFromStart = parent == null ? 0 : parent.costFromStart + 1;
        this.heuristic = calculateHeuristic();
        this.totalCost = costFromStart + heuristic;
    }

    private int calculateHeuristic() {
        int totalDistance = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int value = state[i][j];
                if (value != 0) {
                    int targetRow = (value - 1) / 3;
                    int targetCol = (value - 1) % 3;
                    totalDistance += Math.abs(i - targetRow) + Math.abs(j -
                            targetCol);
                }
            }
        }
        return totalDistance;
    }

    @Override
    public int compareTo(PuzzleNodes other) {
        return Integer.compare(this.totalCost, other.totalCost);
    }
}

public class EightPuzzA {

    public static void main(String[] args) {

        int[][] initialState = {
                {1, 2, 3},
                {4, 0, 5},
                {6, 7, 8}
        };

        int[][] goalState = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 0}
        };

        PuzzleNodes solutionNode = solvePuzzle(initialState, goalState);

        if (solutionNode != null) {

            System.out.println("Path:");
            PuzzleNodes current = solutionNode;
            while (current != null) {
                System.out.println(current.totalCost + " - " +
                        current.costFromStart + " - " +
                        current.move + " - " +
                        Arrays.deepToString(current.state));
                current = current.parent;
            }

        } else {
            System.out.println("No solution found");
        }
    }
    public static PuzzleNodes solvePuzzle(int[][] initial, int[][] goal) {

        PriorityQueue<PuzzleNodes> openSet = new PriorityQueue<>();
        Set<String> closedSet = new HashSet<>();

        PuzzleNodes initialNode = new PuzzleNodes(initial, null, "");
        openSet.add(initialNode);

        while (!openSet.isEmpty()) {
            PuzzleNodes current = openSet.poll();
            closedSet.add(Arrays.deepToString(current.state));

            if (Arrays.deepEquals(current.state, goal)) {
                return current;
            }

            int zeroRow = 0;
            int zeroCol = 0;
            outerLoop:
            for (zeroRow = 0; zeroRow < 3; zeroRow++) {
                for (zeroCol = 0; zeroCol < 3; zeroCol++) {
                    if (current.state[zeroRow][zeroCol] == 0) {
                        break outerLoop;
                    }
                }
            }

            int[][] possibleMoves = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
            String[] moveNames = {"Up", "Down", "Left", "Right"};

            for (int i = 0; i < possibleMoves.length; i++) {

                int newRow = zeroRow + possibleMoves[i][0];
                int newCol = zeroCol + possibleMoves[i][1];

                if (newRow >= 0 && newRow < 3 &&
                        newCol >= 0 && newCol < 3) {

                    int[][] newState = cloneState(current.state);
                    newState[zeroRow][zeroCol] = newState[newRow][newCol];
                    newState[newRow][newCol] = 0;

                    PuzzleNodes child = new PuzzleNodes(newState, current,
                            moveNames[i]);

                    if (!closedSet.contains(Arrays.deepToString(newState)))
                    {
                        openSet.add(child);
                    }
                }
            }
        }

        return null;
    }
    public static int[][] cloneState(int[][] state) {
        int[][] clone = new int[3][3];
        for (int i = 0; i < 3; i++) {
            System.arraycopy(state[i], 0, clone[i], 0, 3);
        }
        return clone;
    }
}