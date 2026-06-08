/**
 * Implementierungen beschreiben oder besitzen eine zweidimensionale Position.
 * Das sind einerseits die Klassen {@link Vector2D} und {@link Point2D} die Positionen im Raum beschreiben, aber auch
 * z.B. alle {@link BaseActor} und {@link Collider} die im zweidimensionalen Raum existieren.
 * Anstelle eines {@link Vector2D} als Parameter sollten Methoden meistens eine {@link Position2D} nehmen, da das
 * die callsites der Methode verkürzt.
 *
 * @author Faris
 */
public interface Position2D {
    static Point2D of(int x, int y) {
        return new Point2D(x, y);
    }

    static Vector2D of(float x, float y) {
        return new Vector2D(x, y);
    }

    Vector2D vec();

    default float xPos() {
        return vec().x();
    }

    default float yPos() {
        return vec().y();
    }

    default Point2D point() {
        return new Point2D(Math.round(xPos()), Math.round(yPos()));
    }

    /**
     * Berechnet den Betrag eines Vektors.
     *
     * @return Die Wurzel aus x^2 + y^2.
     */
    default float magnitude() {
        return (float) Math.sqrt(xPos() * xPos() + yPos() * yPos());
    }

    /**
     * Bildet den Betragsvektor.
     *
     * @return Einen neuen Vektor mit {@link #magnitude()} = 1, außer wenn der aktuelle Vector
     * {@link Vector2D#ZERO} ist, dann wird der gleiche Vektor zurückgegeben.
     */
    default Vector2D normalize() {
        return magnitude() == 0 ? Vector2D.ZERO : divide(magnitude());
    }

    /**
     * Addiert zwei Positionen.
     *
     * @param other Der zweite Summand.
     * @return Die Summe (ein neuer Vektor).
     */
    default Vector2D plus(Position2D other) {
        return new Vector2D(xPos() + other.xPos(), yPos() + other.yPos());
    }

    /**
     * Subtrahiert eine Position von einer anderen.
     *
     * @param other Der Subtrahend.
     * @return Die Differenz (ein neuer Vektor).
     */
    default Vector2D minus(Position2D other) {
        return new Vector2D(xPos() - other.xPos(), yPos() - other.yPos());
    }

    /**
     * Multipliziert einen Vektor mit einem Skalar.
     *
     * @param d Der skalare Faktor.
     * @return Das Produkt (ein neuer Vektor).
     */
    default Vector2D multiply(float d) {
        return new Vector2D(xPos() * d, yPos() * d);
    }

    /**
     * Teilt einen Vektor durch einen Skalar.
     *
     * @param d Der skalare Divisor.
     * @return Einen neuen Vektor oder {@link Vector2D#ZERO} wenn d null ist.
     */
    default Vector2D divide(float d) {
        if (d == 0) return Vector2D.ZERO;
        return new Vector2D(xPos() / d, yPos() / d);
    }

    /**
     * Der Winkel zwischen zwei Positionen.
     *
     * @param end Die zweite Position.
     * @return Der Winkel als eine Zahl zwischen 0 (0°) und 1 (360°).
     */
    default double angle(Position2D end) {
        Vector2D direction = end.vec().minus(this).normalize();
        double angle = Math.atan2(direction.y(), direction.x());

        return 1 - (angle + Math.PI) / (2 * Math.PI);
    }

    default boolean zero() {
        return vec().equals(Vector2D.ZERO);
    }

    default boolean nonZero() {
        return !zero();
    }
}
