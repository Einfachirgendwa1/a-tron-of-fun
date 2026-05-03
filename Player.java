import greenfoot.Actor;

public abstract class Player extends Actor implements IDamageable {
    protected MultipleImages multipleImages = new MultipleImages(images());
    protected int health = 100;

    @Override
    public boolean intersects(Actor other) {
        return multipleImages.intersects(other);
    }

    protected ImageHolder[] images() {
        return new ImageHolder[]{};
    }

    @Override
    public void act() {
        multipleImages.updateImagesActor(this);

        if (health <= 0) {
            Misc.exitMinigame();
        }
    }

    protected Vector2 position() {
        return new Vector2(this);
    }

    @Override
    public void takeDamage(int amount) {
        health -= amount;
    }

    public Vector2 towards(Vector2 target) {
        return Vector2.towards(target, position());
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
