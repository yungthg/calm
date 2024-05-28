import java.util.*;

class Point {
    int x, y;

    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

public class IterativeMazeDFS {
    final public static int maze[][] = {
            { 1, 1, 1, 1, 1, 1, 1, 1 },
            { 1, 10, 0, 0, 1, 0, 0, 1 },
            { 1, 1, 0, 1, 1, 0, 1, 1 },
            { 1, 0, 0, 0, 0, 0, 0, 1 },
            { 1, 0, 1, 1, 1, 1, 0, 1 },
            { 1, 0, 1, 0, 0, 0, 0, 1 },
            { 1, 0, 1, 0, 1, 1, -10, 1 },
            { 1, 1, 1, 1, 1, 1, 1, 1 }
    };
    final static Point start = new Point(1, 1);
    final static Point end = new Point(6, 6);

    public static void main(String args[]) {
        boolean[][] visited = new boolean[maze.length][maze.length];
        Point parent[][] = new Point[maze.length][maze.length];
        for (boolean i[] : visited) {
            Arrays.fill(i, false);
        }
        for (Point i[] : parent) {
            Arrays.fill(i, null);
        }
        Stack<Point> s = new Stack<Point>();
        s.push(start);
        Point p;
        while (!s.isEmpty()) {
            p = s.pop();
            if (visited[p.x][p.y] == true) {
                break;
            }
            visited[p.x][p.y] = true;
            if (p.x - 1 > -1) {
                if (maze[p.x - 1][p.y] != 1 && visited[p.x - 1][p.y] != true) {
                    s.push(new Point(p.x - 1, p.y));
                    parent[p.x - 1][p.y] = p;
                }
            }
            if (p.x + 1 < maze.length) {
                if (maze[p.x + 1][p.y] != 1 && visited[p.x + 1][p.y] != true) {
                    s.push(new Point(p.x + 1, p.y));
                    parent[p.x + 1][p.y] = p;
                }
            }
            if (p.y - 1 > -1) {
                if (maze[p.x][p.y - 1] != 1 && visited[p.x][p.y - 1] != true) {
                    s.push(new Point(p.x, p.y - 1));
                    parent[p.x][p.y - 1] = p;
                }
            }
            if (p.y + 1 < maze.length) {
                if (maze[p.x][p.y + 1] != 1 && visited[p.x][p.y + 1] != true) {
                    s.push(new Point(p.x, p.y + 1));
                    parent[p.x][p.y + 1] = p;
                }
            }
        }
        s.clear();
        p = end;
        while (true) {
            s.push(p);
            if (p.x == start.x && p.y == start.y) {
                break;
            }
            p = parent[p.x][p.y];
        }
        while (!s.isEmpty()) {
            p = s.pop();
            System.out.print("(" + p.x + "," + p.y + ")->");
        }
    }
}