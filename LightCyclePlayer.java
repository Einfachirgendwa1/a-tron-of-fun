import greenfoot.Greenfoot;

public class LightCyclePlayer extends Player {
    private int direction = 1; 
    public void act() {
        int result = direction % 4;

        if        (result == 3 || result == -3) {
            moveUp();
        } else if (result == 2 || result == -2) {
            moveRight();
        } else if (result == 1 || result == -1) {
            moveDown();
        } else if (result == 0) {
            moveLeft();
        }

        if (Greenfoot.isKeyDown ("a")) {
            direction = direction - 1;
        } 

        if (Greenfoot.isKeyDown ("d")) {
            direction = direction + 1;
        }
    }
}
