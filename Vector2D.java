/**
 * Ein zweidimensionaler Vektor.
 * Speichert einen X und einen Y Wert als {@link Float}.
 * Unterstützt folgende Operationen: {@link #normalize()}, {@link #plus(Position2D)}, {@link #minus(Position2D)},
 * {@link #multiply(float)}, {@link #divide(float)}, {@link #angle(Position2D)}.
 *
 * @param x Die X-Koordinate.
 * @param y Die Y-Koordinate.
 * @author Faris
 * @see Point2D
 * @see Position2D
 */
public record Vector2D(float x, float y) implements Position2D {
    public static final Vector2D ZERO = new Vector2D(0, 0);
    public static final Vector2D CANVAS = new Vector2D(Misc.worldWidth, Misc.worldHeight);
    public static final Vector2D MIDDLE = CANVAS.divide(2);

    public static final Vector2D UP = new Vector2D(0, -1);
    public static final Vector2D DOWN = new Vector2D(0, 1);
    public static final Vector2D LEFT = new Vector2D(-1, 0);
    public static final Vector2D RIGHT = new Vector2D(1, 0);

    @Override
    public Vector2D vec() {
        return this;
    }
}
