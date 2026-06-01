import greenfoot.Actor;
import greenfoot.GreenfootImage;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class BaseActor extends Actor implements IDamageable, Position2D {
    protected int health = 100;
    protected float speed = 1;
    private ArrayList<ImageHolder> images;
    private boolean wallCollision = false;
    private Vector2D subpixelPosition;

    public static void run(Actor actor, Consumer<BaseActor> function) {
        if (actor instanceof BaseActor) {
            function.accept((BaseActor) actor);
        }
    }

    public boolean anyCollider(Predicate<ImageHolder> predicate) {
        return images.stream().filter(ImageHolder::isCollider).anyMatch(predicate);
    }

    protected boolean wallCollisionOccurred() {
        return wallCollision;
    }

    protected ImageHolder[] images() {
        return new ImageHolder[]{};
    }

    protected Vector2D towards(Position2D other) {
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

    @Override
    public void setImage(GreenfootImage image) {
        if (images != null) {
            images.getFirst().setImage(image);
            return;
        }

        images = new ArrayList<>();
        ImageHolder[] imageHolders = images();

        if (imageHolders.length == 0) {
            imageHolders = new ImageHolder[]{new ImageHolder(image, 0, 0)};
        }

        for (ImageHolder imageHolder : imageHolders) {
            Misc.getCurrentWorld().addObject(imageHolder, imageHolder.getOffsetX(), imageHolder.getOffsetY());
            images.add(imageHolder);
        }

        super.setImage(Misc.blank);
    }

    @Override
    public boolean intersects(Actor other) {
        List<Actor> otherColliders = new ArrayList<>();

        run(other, otherActor -> otherColliders.addAll(otherActor.images));
        if (!(other instanceof BaseActor)) otherColliders.add(other);

        return anyCollider(image -> otherColliders.stream().anyMatch(image::intersects));
    }

    private void syncPositions() {
        Point2D position = new Point2D(this);
        super.setLocation(position.x(), position.y());
        updateChildren();
    }

    public void mirrorHorizontally() {
        images.forEach(ImageHolder::mirrorHorizontally);
    }

    @Override
    public Vector2D position() {
        return subpixelPosition;
    }

    public void initializePosition() {
        subpixelPosition = new Vector2D(getX(), getY());
    }

    public void updateChildren() {
        images.forEach(image -> image.updatePosition(this));
    }

    public void destroyChildren() {
        images.forEach(getWorld()::removeObject);
    }

    protected void deathHandler() {
        getWorld().removeObject(this);
    }

    protected void teleport(Position2D vector) {
        subpixelPosition = vector.position();
    }

    protected void moveWithSpeed(Vector2D vector) {
        move(vector.normalize().multiply(speed));
    }

    protected boolean insideWall() {
        return Misc.getCurrentWorld().getWalls().stream().anyMatch(this::intersects);
    }

    protected void move(Vector2D vector, boolean checkCollision) {
        Vector2D startPosition = position();
        teleport(startPosition.plus(vector));
        syncPositions();

        if (checkCollision && insideWall()) {
            wallCollision = true;
            teleport(startPosition);
            syncPositions();
        }
    }

    protected void move(Vector2D vector) {
        move(vector, true);
    }

    public void freeze() {
        Misc.getCurrentWorld().removeObjectUnchecked(this);
    }

    protected void moveUp() {
        moveWithSpeed(Vector2D.UP);
    }

    protected void moveDown() {
        moveWithSpeed(Vector2D.DOWN);
    }

    protected void moveLeft() {
        moveWithSpeed(Vector2D.LEFT);
    }

    protected void moveRight() {
        moveWithSpeed(Vector2D.RIGHT);
    }
}
