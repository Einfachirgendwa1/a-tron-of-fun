import greenfoot.Color;

/**
 * Rendert den blauen Text in der oberen linken Ecke, der den aktuellen Score anzeigt.
 *
 * @author Faris
 * @see ScoreTracker
 */
public class Score extends BaseActor {
    public Score() {
        // Macht, dass der Score schon angezeigt wird, wenn noch nicht Run gedrückt wurde.
        act();
    }

    private static Position2D topLeft(Vector2D dimensions) {
        return new Vector2D(5, 5).plus(new Vector2D(0, dimensions.y()));
    }

    public void act() {
        Misc.getCurrentWorld().drawOnce("SCORE: " + ScoreTracker.getScore(), Score::topLeft, 19, Color.BLUE);
    }
}
