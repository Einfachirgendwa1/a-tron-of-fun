import greenfoot.Actor;

public abstract class Enemy extends Actor implements IDamageable {
    private final MultipleImages multipleImages;
    private int health = maxHealth();

    public Enemy() {
        multipleImages = new MultipleImages(defaultImages());

        if (health <= 0) {
            getWorld().removeObject(this);
        }
    }

    protected int maxHealth() {
        return 100;
    }

    @Override
    public void takeDamage(int amount) {
        health -= amount;
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