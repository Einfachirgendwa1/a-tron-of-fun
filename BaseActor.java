import greenfoot.Actor;
import greenfoot.GreenfootImage;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class BaseActor extends Collider implements IDamageable {
    private final MultipleImages multipleImages;
    protected int health = 100;
    protected float speed = 1;
    private boolean wallCollision = false;
    private Vector2 subpixelPosition;

    {
        ImageHolder[] imageHolders = images();

        if (imageHolders.length == 0) {
            imageHolders = new ImageHolder[]{new ImageHolder(getImage(), 0, 0)};
        }

        multipleImages = new MultipleImages(imageHolders);
        setImage(Misc.blank);
    }

    public static void run(Actor actor, Consumer<BaseActor> function) {
        if (actor instanceof BaseActor) {
            function.accept((BaseActor) actor);
        }
    }

    protected boolean wallCollisionOccured() {
        return wallCollision;
    }

    protected ImageHolder[] images() {
        return new ImageHolder[]{};
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
        syncPositions();

        if (health <= 0) {
            deathHandler();
        }
    }

    @Override
    public void setLocation(int x, int y) {
        super.setLocation(x, y);
        subpixelPosition = position();
    }

    private void syncPositions() {
        Point position = new Point(position());
        super.setLocation(position.x(), position.y());
        updateChildren();
    }

    @Override
    public void setImage(GreenfootImage image) {
        if (image == Misc.blank || multipleImages == null) {
            super.setImage(image);
        } else if (multipleImages.getImages().size() == 1) {
            multipleImages.getImages().getFirst().setImage(image);
        }
    }

    @Override
    public boolean intersects(Actor other) {
        List<Actor> otherColliders = new ArrayList<>();

        if (other instanceof BaseActor) {
            otherColliders.addAll(((BaseActor) other).multipleImages.getImages());
        } else {
            otherColliders.add(other);
        }

        return multipleImages.anyWithCollision(image -> otherColliders.stream().anyMatch(image::intersects));
    }

    @Override
    public void mirrorHorizontally() {
        multipleImages.forEach(ImageHolder::mirrorHorizontally);
    }

    @Override
    public void mirrorVertically() {
        multipleImages.forEach(ImageHolder::mirrorVertically);
    }

    @Override
    public Vector2 position() {
        return subpixelPosition;
    }

    public void initializePosition() {
        subpixelPosition = new Vector2(getX(), getY());
    }

    public void updateChildren() {
        multipleImages.forEach(image -> image.updatePosition(this));
    }

    public void destroyChildren() {
        multipleImages.forEach(getWorld()::removeObject);
    }

    protected void deathHandler() {
        getWorld().removeObject(this);
    }

    protected void teleport(IGetVector2 vector) {
        subpixelPosition = vector.position();
    }

    protected void moveWithSpeed(Vector2 vector) {
        move(vector.normalize().scale(speed));
    }

    protected boolean insideWall() {
        return Misc.getCurrentWorld().getWalls().stream().anyMatch(this::intersects);
    }

    protected void move(Vector2 vector, boolean checkCollision) {
        Vector2 startPosition = position();
        teleport(startPosition.plus(vector));
        syncPositions();

        if (checkCollision && insideWall()) {
            wallCollision = true;
            teleport(startPosition);
            syncPositions();
        }
    }

    protected void move(Vector2 vector) {
        move(vector, true);
    }

    public void freeze() {
        Misc.getCurrentWorld().removeObjectUnchecked(this);
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
}
