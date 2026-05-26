import greenfoot.Color;
import greenfoot.GreenfootImage;

public class Score extends BaseActor {
    private int score;
    private Point lastBoundingBox = new Point(Vector2.ZERO);

    public Score(int score) {
        this.score = score;
        act();
    }

    private static IGetVector2 topLeft(Vector2 dimensions) {
        return Vector2.ZERO.plus(new Vector2(0, dimensions.y()));
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void addScore(int amount) {
        score += amount;
    }

    public void removeScore(int amount) {
        score -= amount;
    }

    public void act() {
        //noinspection unused

        GreenfootImage surface = Misc.getCurrentWorld().getBackground();
        surface.setColor(Color.BLACK);
        surface.fillRect(0, 0, lastBoundingBox.x() + 5, lastBoundingBox.y() + 5);

        Misc.drawText(
            "SCORE: " + score, dimensions -> {
                lastBoundingBox = new Point(dimensions);
                return topLeft(dimensions);
            }, 23, Color.BLUE
        );
    }
}
