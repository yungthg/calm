import java.util.*;

public class MazeSolverDLDFS {
    static int rows;
    static int cols;
    static int[][] maze;
    static int[][] directions = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } }; // Up, Down, Left, Right

    public static boolean isValidCell(int x, int y) {
        // In bounds and also if the path is available (==0)
        return x >= 0 && x < rows && y >= 0 && y < cols && maze[x][y] == 0;
    }

    public static boolean solveMazeUsingDLDFS(int x, int y, boolean[][] visited, List<int[]> path, int depth) {
        if (depth == 0) {
            return false; // Reached the depth limit
        }

        if (x == rows - 1 && y == cols - 1) { // till the last cell of the maze
            path.add(new int[] { x, y }); // added the last cell
            return true; // Reached the goal
        }

        visited[x][y] = true;

        // For all directions (Up, Down, Left, Right)
        for (int[] dir : directions) {
            // new move
            int newX = x + dir[0];
            int newY = y + dir[1];

            if (isValidCell(newX, newY) && !visited[newX][newY]) {
                if (solveMazeUsingDLDFS(newX, newY, visited, path, depth - 1)) {
                    path.add(new int[] { x, y });
                    return true;
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

            boolean[][] visited = new boolean[rows][cols];
            List<int[]> path = new ArrayList<>();

            System.out.print("Enter the depth limit: ");
            int depth = scanner.nextInt();

            if (solveMazeUsingDLDFS(0, 0, visited, path, depth)) {
                System.out.println("DLDFS: Path found!");
                System.out.println("DLDFS Path:");
                Collections.reverse(path);
                printSolutionPath(path);
            } else {
                System.out.println("DLDFS: No path found within the specified depth.");
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
