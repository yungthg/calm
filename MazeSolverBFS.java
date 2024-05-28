import java.util.*;

public class MazeSolverBFS {
    static int rows;
    static int cols;
    static int[][] maze;
    static int[][] directions = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } }; // Up, Down, Left, Right

    public static boolean isValidCell(int x, int y) {
        // In bounds and also if the path is available (==0)
        return x >= 0 && x < rows && y >= 0 && y < cols && maze[x][y] == 0;
    }

    public static void reconstructPath(Map<Integer, Integer> parents, List<int[]> path, int x, int y) {
        int current = x * cols + y; // 2D -> 1D
        while (parents.containsKey(current)) { // while current(child) present in parents Hashmap
            int parentCell = parents.get(current); // get the parent of the child node
            int parentX = parentCell / cols; // Extracting x value from the parent cell
            int parentY = parentCell % cols; // Extracting y value from the parent cell
            path.add(new int[] { parentX, parentY }); // adding to the path variable
            current = parentX * cols + parentY; // converting parent into child
        }
    }

    public static boolean solveMazeUsingBFS(int startX, int startY, List<int[]> path) {
        Queue<int[]> queue = new LinkedList<>();
        boolean[][] visited = new boolean[rows][cols];
        Map<Integer, Integer> parents = new HashMap<>();

        queue.offer(new int[] { startX, startY }); // enqueue
        visited[startX][startY] = true;

        while (!queue.isEmpty()) {
            int[] current = queue.poll(); // dequeue
            int x = current[0];
            int y = current[1];

            if (x == rows - 1 && y == cols - 1) {
                path.add(new int[] { x, y });
                reconstructPath(parents, path, x, y);
                return true; // Reached the goal
            }

            for (int[] dir : directions) { // { Up : { -1, 0 }, Down : { 1, 0 }, Left : { 0, -1 }, Right: { 0, 1 } };
                int newX = x + dir[0];
                int newY = y + dir[1];
                int newCell = newX * cols + newY; // convert 2D into 1D
                // If the cell is valid and not visited
                if (isValidCell(newX, newY) && !visited[newX][newY]) {
                    // Enqueue for continuing loop
                    queue.offer(new int[] { newX, newY });
                    // Set visited true to avoid a loop
                    visited[newX][newY] = true;
                    // Add in the Parent Hashmap
                    parents.put(newCell, x * cols + y); // ChildCell, Direct Parent Cell
                }
            }
        }

        return false;
    }

    public static void printSolutionPath(List<int[]> path) {
        for (int[] point : path) {
            System.out.println("(" + point[0] + ", " + point[1] + ")");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Enter the number of rows in the maze: ");
            rows = scanner.nextInt();

            System.out.print("Enter the number of columns in the maze: ");
            cols = scanner.nextInt();

            if (rows <= 0 || cols <= 0) {
                throw new InputMismatchException("Rows and columns must be positive integers.");
            }

            maze = new int[rows][cols];

            System.out.println("Enter the maze values (0 for path, 1 for wall):");
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    maze[i][j] = scanner.nextInt();
                }
            }

            List<int[]> path = new ArrayList<>();

            if (solveMazeUsingBFS(0, 0, path)) {
                System.out.println("BFS: Path found!");
                System.out.println("BFS Path:");
                Collections.reverse(path);
                printSolutionPath(path);
            } else {
                System.out.println("BFS: No path found.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter valid integers.");
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}
