import greenfoot.Actor;

public class BaseActor extends Actor implements IGetVector2, IDamageable {
    protected MultipleImages multipleImages = new MultipleImages(images());
    protected int health = 100;
    protected float speed = 1;

    @Override
    public boolean intersects(Actor other) {
        return multipleImages.intersects(other);
    }

    protected ImageHolder[] images() {
        return new ImageHolder[]{};
    }

    @Override
    public Vector2 position() {
        return new Vector2(getX(), getY());
    }

    protected void move(Vector2 vector) {
        Vector2 newPos = position().plus(vector);
        setLocation(Math.round(newPos.x()), Math.round(newPos.y()));
    }

    protected Vector2 towards(IGetVector2 other) {
        return other.position().minus(this).normalize();
    }

    @Override
    public void takeDamage(int amount) {
        health -= amount;
    }

    @Override
    public void act() {
        multipleImages.updateImages(this);

        if (health <= 0) {
            deathHandler();
        }
    }

    protected void moveWithSpeed(Vector2 vector) {
        move(vector.normalize().scale(speed));
    }

    protected void moveUp() {
        moveWithSpeed(Vector2.UP);
    }

    protected void moveDown() {
        moveWithSpeed(Vector2.DOWN);
    }

    protected void moveLeft() {
        moveWithSpeed(Vector2.LEFT);
    }

    protected void moveRight() {
        moveWithSpeed(Vector2.RIGHT);
    }

    protected void deathHandler() {}
}
