import greenfoot.Actor;
import greenfoot.World;

public abstract class Enemy extends Actor {
    private final MultipleImages multipleImages;

    public Enemy(World world) {
        multipleImages = new MultipleImages(world, defaultImages());
    }

    protected abstract ImageHolder[] defaultImages();

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