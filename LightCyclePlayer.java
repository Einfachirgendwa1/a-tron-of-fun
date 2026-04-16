import greenfoot.Greenfoot;
import greenfoot.World;

public class LightCyclePlayer extends Player {
    private int direction = 1;

    public LightCyclePlayer(World world) {
        super(world);
    }

    @Override
    protected ImageHolder[] defaultImageHolders() {
        return new ImageHolder[]{
                new ImageHolder("man_stand_body.png", 0, 0),
        };
    }

    public void act() {
        int result = direction % 4;

        if        (result == 3 || result == -3) {
            moveDown();
        } else if (result == 2 || result == -2) {
            moveRight();
        } else if (result == 1 || result == -1) {
            moveUp();
        } else if (result == 0) {
            moveLeft();
        }
        
        boolean isPressed = false;
        
        if (Greenfoot.isKeyDown ("a")) {
            isPressed = true;
            direction = direction - 1;
        } 

        if (Greenfoot.isKeyDown ("d")) {
            direction = direction + 1;
        }
    }
}
