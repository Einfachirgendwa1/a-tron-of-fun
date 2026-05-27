import greenfoot.Color;

public class Score extends BaseActor {
    public Score() {
        act();
    }

    private static IGetVector2 topLeft(Vector2 dimensions) {
        return Vector2.ZERO.plus(new Vector2(0, dimensions.y()));
    }

    public void act() {
        Misc.getCurrentWorld().drawText("SCORE: " + ScoreTracker.getScore(), Score::topLeft, 23, Color.BLUE);
    }
}
