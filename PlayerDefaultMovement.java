import greenfoot.Greenfoot;
import greenfoot.World;

public abstract class PlayerDefaultMovement extends Player {
    public PlayerDefaultMovement(World world) {
        super(world);
    }

    @Override
    public void act() {
        super.act();

        if (Greenfoot.isKeyDown("w")) {
            moveUp();
        }

        if (Greenfoot.isKeyDown("s")) {
            moveDown();
        }

        if (Greenfoot.isKeyDown("a")) {
            moveLeft();
        }

        if (Greenfoot.isKeyDown("d")) {
            moveRight();
        }
    }
}
