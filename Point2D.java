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
    public static final Point2D CANVAS = new Point2D(Misc.worldWidth, Misc.worldHeight);
    public static final Point2D MIDDLE = CANVAS.vec().divide(2).point();

    public static final Point2D UP = new Point2D(0, -1);
    public static final Point2D DOWN = new Point2D(0, 1);
    public static final Point2D LEFT = new Point2D(-1, 0);
    public static final Point2D RIGHT = new Point2D(1, 0);

    public Point2D(Position2D pos) {
        this(Math.round(pos.vec().x()), Math.round(pos.vec().y()));
    }

    @Override
    public Vector2D vec() {
        return new Vector2D(x, y);
    }
}
