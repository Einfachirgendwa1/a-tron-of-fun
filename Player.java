import greenfoot.Actor;

public abstract class Player extends Actor {
    protected MultipleImages multipleImages = new MultipleImages(defaultImages());

    @Override
    public boolean intersects(Actor other) {
        return multipleImages.intersects(other);
    }

    protected ImageHolder[] defaultImages() {
        return new ImageHolder[]{};
    }

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
