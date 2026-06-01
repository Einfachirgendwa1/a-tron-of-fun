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
    public static final Vector2D MIDDLE = new Vector2D(Misc.worldWidth / 2f, Misc.worldHeight / 2f);

    public static final Vector2D UP = new Vector2D(0, -1);
    public static final Vector2D DOWN = new Vector2D(0, 1);
    public static final Vector2D LEFT = new Vector2D(-1, 0);
    public static final Vector2D RIGHT = new Vector2D(1, 0);

    @Override
    public Vector2D position() {
        return this;
    }

    /**
     * Berechnet den Betrag eines Vektors.
     *
     * @return Die Wurzel aus x^2 + y^2.
     */
    public float magnitude() {
        return (float) Math.sqrt(x * x + y * y);
    }

    /**
     * Bildet den Betragsvektor.
     *
     * @return Einen neuen Vektor mit {@link #magnitude()} = 1.
     */
    public Vector2D normalize() {
        return divide(magnitude());
    }

    /**
     * Addiert zwei Positionen.
     *
     * @param other Der zweite Summand.
     * @return Die Summe (ein neuer Vektor).
     */
    public Vector2D plus(Position2D other) {
        return new Vector2D(this.x + other.position().x, this.y + other.position().y);
    }

    /**
     * Subtrahiert eine Position von einer anderen.
     *
     * @param other Der Subtrahend.
     * @return Die Differenz (ein neuer Vektor).
     */
    public Vector2D minus(Position2D other) {
        return new Vector2D(this.x - other.position().x, this.y - other.position().y);
    }

    /**
     * Multipliziert einen Vektor mit einem Skalar.
     *
     * @param d Der skalare Faktor.
     * @return Das Produkt (ein neuer Vektor).
     */
    public Vector2D multiply(float d) {
        return new Vector2D(this.x * d, this.y * d);
    }

    /**
     * Teilt einen Vektor durch einen Skalar.
     *
     * @param d Der skalare Divisor.
     * @return Einen neuen Vektor oder {@link #ZERO} wenn d null ist.
     */
    public Vector2D divide(float d) {
        if (d == 0) return ZERO;
        return new Vector2D(this.x / d, this.y / d);
    }

    /**
     * Der Winkel zwischen zwei Positionen.
     *
     * @param end Die zweite Position.
     * @return Der Winkel als eine Zahl zwischen 0 (0°) und 1 (360°).
     */
    public double angle(Position2D end) {
        Vector2D direction = end.position().minus(this).normalize();
        double angle = Math.atan2(direction.y(), direction.x());

        return 1 - (angle + Math.PI) / (2 * Math.PI);
    }
}
