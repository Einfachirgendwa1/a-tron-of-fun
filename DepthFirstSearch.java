import java.util.*;

/**
 * Eine einfach DFS Implementierung.
 * Basierend auf meinen Erinnerungen daran wie der Algorithmus funktioniert, ähnelt
 * <a href="https://en.wikipedia.org/wiki/Depth-first_search#Pseudocode">Pseudocode von Wikipedia</a> (rekursiv).
 *
 * @author Faris
 * @see PathFinder
 * @see BreadthFirstSearch
 */
public class DepthFirstSearch implements PathFinder {
    Map<Point2D, ArrayList<Point2D>> set = new HashMap<>();

    private Optional<List<Point2D>> pathFind(Position2D start, Position2D end, List<Point2D> visited) {
        if (start.point().equals(end.point())) {
            ArrayList<Point2D> path = new ArrayList<>();
            path.add(start.point());
            return Optional.of(path);
        }

        List<Point2D> potentialTargets = set.get(start.point())
            .stream()
            .filter(point -> !visited.contains(point))
            .toList();

        if (potentialTargets.isEmpty()) {
            return Optional.empty();
        }

        List<Point2D> newVisited = new ArrayList<>(visited);
        newVisited.add(start.point());
        for (Point2D potentialTarget : potentialTargets) {
            Optional<List<Point2D>> path = pathFind(potentialTarget, end, newVisited);

            if (path.isPresent()) {
                path.get().add(start.point());
                return path;
            }
        }

        return Optional.empty();
    }

    @Override
    public List<Point2D> pathFind(Position2D start, Position2D end, Map<Point2D, ArrayList<Point2D>> set) {
        this.set = set;

        Optional<List<Point2D>> path = pathFind(start, end, new ArrayList<>());

        if (path.isEmpty()) {
            throw new NoPathException(start, end);
        }

        return path.get();
    }
}
