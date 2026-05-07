import greenfoot.Actor;
import greenfoot.GreenfootImage;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class BaseActor extends Collider implements IDamageable {
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
        List<Actor> otherColliders = new ArrayList<>();

        if (other instanceof BaseActor) {
            otherColliders.addAll(((BaseActor) other).colliders());
        } else {
            otherColliders.add(other);
        }

        for (Collider myCollider : colliders()) {
            for (Actor otherCollider : otherColliders) {
                Function<Actor, Boolean> collides = myCollider == this ? super::intersects : myCollider::intersects;

                if (collides.apply(otherCollider)) {
                    return true;
                }
            }
        }

        return false;
    }

    protected ImageHolder[] images() {
        return new ImageHolder[]{};
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

    protected ArrayList<Collider> colliders() {
        ArrayList<Collider> colliders = new ArrayList<>();

        GreenfootImage baseImage = getImage();
        if (baseImage != null && baseImage != Misc.blank) {
            colliders.add(this);
        }

        colliders.addAll(multipleImages.getImages());
        return colliders;
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

    @Override
    public void mirrorHorizontally() {
        for (Collider collider : colliders()) {
            if (collider == this) getImage().mirrorHorizontally();
            else collider.mirrorHorizontally();
        }
    }

    @Override
    public void mirrorVertically() {
        for (Collider collider : colliders()) {
            if (collider == this) getImage().mirrorVertically();
            else collider.mirrorVertically();
        }
    }
}
