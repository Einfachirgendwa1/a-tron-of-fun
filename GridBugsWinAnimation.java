public class GridBugsWinAnimation extends HumanoidPlayer {
    @Override
    protected boolean isMoving() {
        return true;
    }

    public void act() {
        move(Vector2.UP.scale(2f), false);
    }
}
