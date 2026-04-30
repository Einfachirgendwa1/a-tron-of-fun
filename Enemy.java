import greenfoot.Actor;

public abstract class Enemy extends Actor {
    private final MultipleImages multipleImages;

    public Enemy() {
        multipleImages = new MultipleImages(defaultImages());
    }

    protected ImageHolder[] defaultImages() {
        return new ImageHolder[]{};
    }

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