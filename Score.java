import greenfoot.Color;

public class Score extends BaseActor {
    public Score() {
        act();
    }

    private static IGetVector2 topLeft(Vector2 dimensions) {
        return new Vector2(5, 5).plus(new Vector2(0, dimensions.y()));
    }

    public void act() {
        Misc.getCurrentWorld().drawOnce("SCORE: " + ScoreTracker.getScore(), Score::topLeft, 19, Color.BLUE);
    }
}
