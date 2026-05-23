import greenfoot.Actor;
import greenfoot.GreenfootImage;

import java.util.ArrayList;
import java.util.List;

public class BaseActor extends Collider implements IDamageable {
    private final MultipleImages multipleImages;
    protected int health = 100;
    protected float speed = 1;

    {
        ImageHolder[] imageHolders = images();

        if (imageHolders.length == 0) {
            imageHolders = new ImageHolder[]{new ImageHolder(getImage(), 0, 0)};
        }

        multipleImages = new MultipleImages(imageHolders);
        setImage(Misc.blank);
    }

    @Override
    public boolean intersects(Actor other) {
        List<Actor> otherColliders = new ArrayList<>();

        if (other instanceof BaseActor) {
            otherColliders.addAll(((BaseActor) other).multipleImages.getImages());
        } else {
            otherColliders.add(other);
        }

        return multipleImages.any(image -> otherColliders.stream().anyMatch(image::intersects));
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

    protected void deathHandler() {
        getWorld().removeObject(this);
    }

    @Override
    public void mirrorHorizontally() {
        multipleImages.forEach(ImageHolder::mirrorHorizontally);
    }

    @Override
    public void mirrorVertically() {
        multipleImages.forEach(ImageHolder::mirrorVertically);
    }

    public void destroyChildren() {
        multipleImages.forEach(getWorld()::removeObject);
    }

    @Override
    public void setImage(GreenfootImage image) {
        if (image == Misc.blank || multipleImages == null) {
            super.setImage(image);
        } else if (multipleImages.getImages().size() == 1) {
            multipleImages.getImages().getFirst().setImage(image);
        }
    }
}
