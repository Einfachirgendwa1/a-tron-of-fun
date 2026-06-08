import java.util.*;

/**
 * Eine einfache BFS Implementierung.
 * Basierend auf <a href="https://en.wikipedia.org/wiki/Breadth-first_search#Pseudocode">Pseudocode von Wikipedia</a>.
 *
 * @author Faris
 * @see DepthFirstSearch
 * @see PathFinder
 */
public class BreadthFirstSearch implements PathFinder {
    @Override
    public List<Point2D> pathFind(Position2D start, Position2D end, Map<Point2D, ArrayList<Point2D>> set) {
        List<Point2D> queue = new ArrayList<>();
        Set<Point2D> visited = new HashSet<>();
        Map<Point2D, Point2D> previous = new HashMap<>();

        visited.add(start.point());
        previous.put(start.point(), null);

        queue.add(start.point());
        while (!queue.isEmpty()) {
            Point2D current = queue.removeFirst();

            if (current.equals(end.point())) {
                List<Point2D> path = new ArrayList<>();
                Point2D backtrack = current;
                while (backtrack != null) {
                    path.add(backtrack);
                    backtrack = previous.get(backtrack);
                }

                return path;
            }

            for (Point2D children : set.get(current)) {
                if (!visited.contains(children)) {
                    queue.add(children);
                    visited.add(children);
                    previous.put(children, current);
                }
            }
        }

        throw new NoPathException(start, end);
    }
}
