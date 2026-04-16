import greenfoot.Greenfoot;
import greenfoot.World;

public abstract class Player extends MultipleImages {
    public Player(World world) {
        super(world);
    }

    @Override
    public void act() {
        updateImages();

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

    protected void moveUp() {
        setLocation(getX(), getY() - 1);
    }

    protected void moveDown() {
        setLocation(getX(), getY() + 1);
    }

    protected void moveLeft() {
        setLocation(getX() - 1, getY());
    }

    protected void moveRight() {
        setLocation(getX() + 1, getY());
    }
}
