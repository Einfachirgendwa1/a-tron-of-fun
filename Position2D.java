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
    Vector2D vec();

    default Point2D point() {
        return vec().point();
    }
}
