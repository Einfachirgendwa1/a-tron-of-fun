import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Beschreibt einen pathfinding Algorithmus.
 *
 * @author Faris
 * @see TankLabyrinthEnemy
 * @see BreadthFirstSearch
 * @see DepthFirstSearch
 */
public interface PathFinder {
    /**
     * Sucht nach einem Weg in einem Labyrinth.
     *
     * @param start Die Startposition.
     * @param end   Die Zielposition.
     * @param set   Beschreibt, welche Positionen direkt von einem Punkt erreichbar sind (die Geometrie des Labyrinths).
     * @return Eine Liste der Punkte von {@code end} zu {@code start}, beginnend mit {@code end}.
     */
    List<Point2D> pathFind(Position2D start, Position2D end, Map<Point2D, ArrayList<Point2D>> set) throws NoPathException;

    class NoPathException extends RuntimeException {
        Point2D start;
        Point2D end;

        public NoPathException(Position2D start, Position2D end) {
            this.start = start.point();
            this.end = end.point();
        }

        @Override
        public String getMessage() {
            return "No path found from " + start + " to " + end;
        }
    }
}
