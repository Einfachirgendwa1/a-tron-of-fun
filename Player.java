import greenfoot.Actor;
import greenfoot.World;

public abstract class Player extends Actor {
    protected MultipleImages multipleImages;

    public Player(World world) {
        multipleImages = new MultipleImages(world, defaultImages());
    }

    protected abstract ImageHolder[] defaultImages();

    @Override
    public void act() {
        multipleImages.updateImagesActor(this);
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
