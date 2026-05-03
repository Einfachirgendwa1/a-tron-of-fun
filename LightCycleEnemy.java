public class LightCycleEnemy extends Enemy {
    private final int direction = 4;

    {
        getImage().rotate(-90);
    }

    public void act() {
        int result = direction % 4;

        if (result == 3 || result == -1) {
            moveDown();
        } else if (result == 2 || result == -2) {
            moveRight();
        } else if (result == 1 || result == -3) {
            moveUp();
        } else if (result == 0) {
            moveLeft();
        }
    }
}
