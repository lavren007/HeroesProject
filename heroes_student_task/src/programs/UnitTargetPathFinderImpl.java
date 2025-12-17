package programs;

import com.battle.heroes.army.Unit;
import com.battle.heroes.army.programs.Edge;
import com.battle.heroes.army.programs.UnitTargetPathFinder;

import java.util.List;

public class UnitTargetPathFinderImpl implements UnitTargetPathFinder {
    @Override
    public List<Edge> getTargetPath(Unit attackUnit, Unit targetUnit, List<Unit> existingUnitList) {
        int width = 27;
        int height = 21;
        boolean[][] blocked = new boolean[width][height];
        for (Unit u : existingUnitList) {
            if (u.isAlive() && u != attackUnit && u != targetUnit) {
                blocked[u.getxCoordinate()][u.getyCoordinate()] = true;
            }
        }
        int startX = attackUnit.getxCoordinate();
        int startY = attackUnit.getyCoordinate();
        int targetX = targetUnit.getxCoordinate();
        int targetY = targetUnit.getyCoordinate();

        // Используем java.util.PriorityQueue и другие классы с полными именами
        java.util.PriorityQueue<Node> open = new java.util.PriorityQueue<>(java.util.Comparator.comparingInt(n -> n.f));
        java.util.Map<String, Node> allNodes = new java.util.HashMap<>();
        Node start = new Node(startX, startY, null, 0, heuristic(startX, startY, targetX, targetY));
        open.add(start);
        allNodes.put(startX + "," + startY, start);
        int[][] directions = {{-1,0},{1,0},{0,-1},{0,1},{-1,-1},{-1,1},{1,-1},{1,1}};
        while (!open.isEmpty()) {
            Node current = open.poll();
            if (current.x == targetX && current.y == targetY) {
                java.util.List<Edge> path = new java.util.ArrayList<>();
                while (current != null) {
                    path.add(new Edge(current.x, current.y));
                    current = current.parent;
                }
                java.util.Collections.reverse(path);
                return path;
            }
            for (int[] dir : directions) {
                int nx = current.x + dir[0];
                int ny = current.y + dir[1];
                if (nx < 0 || nx >= width || ny < 0 || ny >= height) continue;
                if (blocked[nx][ny]) continue;
                String key = nx + "," + ny;
                int newG = current.g + 1;
                Node neighbor = allNodes.get(key);
                if (neighbor == null || newG < neighbor.g) {
                    if (neighbor == null) {
                        neighbor = new Node(nx, ny, current, newG, heuristic(nx, ny, targetX, targetY));
                        allNodes.put(key, neighbor);
                        open.add(neighbor);
                    } else {
                        neighbor.parent = current;
                        neighbor.g = newG;
                        neighbor.f = newG + neighbor.h;
                        open.remove(neighbor);
                        open.add(neighbor);
                    }
                }
            }
        }
        return new java.util.ArrayList<>();
    }

    private int heuristic(int x1, int y1, int x2, int y2) {
        return Math.max(Math.abs(x1 - x2), Math.abs(y1 - y2));
    }

    private static class Node {
        int x, y, g, h, f;
        Node parent;
        Node(int x, int y, Node parent, int g, int h) {
            this.x = x;
            this.y = y;
            this.parent = parent;
            this.g = g;
            this.h = h;
            this.f = g + h;
        }
    }
}
