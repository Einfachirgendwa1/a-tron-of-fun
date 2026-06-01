public class GridBugsWinAnimation extends HumanoidPlayer {
    @Override
    protected boolean isMoving() {
        return true;
    }

    public void act() {
        move(Vector2D.UP.multiply(2f), false);
    }
}
