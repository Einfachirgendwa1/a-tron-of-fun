/**
 * Ein Punkt im zweidimensionalen Raum.
 * Ähnelt {@link Vector2D}, mit dem entscheidenden Unterschied, dass die X und Y Koordinaten als {@link Integer} und
 * nicht als {@link Float} gespeichert werden. Positionen sind in Greenfoot {@link Integer} und nicht {@link Float},
 * weshalb ein {@link Vector2D} zuerst in einen {@link Point2D} umgewandelt werden muss bevor er z.B. als neue Position
 * eines {@link greenfoot.Actor} verwendet werden kann.
 *
 * @param x Die X-Koordinate.
 * @param y Die Y-Koordinate.
 * @author Faris
 * @see Vector2D
 * @see Position2D
 */
public record Point2D(int x, int y) implements Position2D {
    public Point2D(Position2D pos) {
        Vector2D vector = pos.position();
        this(Math.round(vector.x()), Math.round(vector.y()));
    }

    @Override
    public Vector2D position() {
        return new Vector2D(x, y);
    }
}
