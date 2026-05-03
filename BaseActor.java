import greenfoot.Actor;
import greenfoot.GreenfootImage;

import javax.annotation.CheckReturnValue;
import java.util.Optional;

public class BaseActor extends Actor implements IGetVector2, IDamageable {
    protected MultipleImages multipleImages = new MultipleImages(images());
    protected int health = 100;
    protected float speed = 1;

    {
        if (multipleImages.hasImages()) {
            setImage(Misc.blank);
        }
    }

    @Override
    public boolean intersects(Actor other) {
        //noinspection unused
        return getGreenfootImage().map(i -> super.intersects(other)).orElse(false) || multipleImages.intersects(other);
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

    @CheckReturnValue
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

    @CheckReturnValue
    protected Optional<GreenfootImage> getGreenfootImage() {
        return getImage() != Misc.blank ? Optional.ofNullable(getImage()) : Optional.empty();
    }
}
