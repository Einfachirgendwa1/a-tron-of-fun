/**
 * Eine Animation die abgespielt wird, wenn man in GridBugs gewinnt.
 * Bewegt sich kontinuierlich nach oben und ignoriert dabei jegliche Kollisionen.
 *
 * @author Faris
 * @see GridBugsWorld#winAnimation(Animator)
 */
public class GridBugsWinAnimation extends HumanoidPlayer {
    @Override
    protected boolean isMoving() {
        return true;
    }

    public void act() {
        move(Vector2D.UP.multiply(2f), false);
    }
}
