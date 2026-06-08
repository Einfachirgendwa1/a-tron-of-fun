import greenfoot.Actor;
import greenfoot.GreenfootImage;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class BaseActor extends Actor implements Position2D {
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

    /**
     * Überprüft, ob dieser Actor im letzten Frame mit einer Wand kollidiert ist.
     */
    protected boolean wallCollisionOccurred() {
        return wallCollision;
    }

    protected ImageHolder[] images() {
        return new ImageHolder[]{};
    }

    protected Vector2D towards(Position2D other) {
        return other.minus(this).normalize();
    }

    public void takeDamage(int amount) {
        health -= amount;
    }

    @Override
    public void act() {
        wallCollision = false;
        applyPosition();

        if (health <= 0) {
            deathHandler();
        }
    }

    @Override
    public void setLocation(int x, int y) {
        setLocation(new Point2D(x, y));
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

    /**
     * Überprüft, ob dieser Actor mit einem anderen kollidiert.
     * Ein spezieller override ist notwendig, da die Standardimplementierung {@link Actor#intersects(Actor)} nur das
     * aktuelle Bild überprüft, aber ein {@link BaseActor} ja aus mehreren {@link ImageHolder}n besteht.
     * Deshalb muss {@code intersects()} auf allen {@link ImageHolder}n von {@code this} und {@code other} aufgerufen
     * werden.
     */
    @Override
    public boolean intersects(Actor other) {
        List<Actor> otherColliders = new ArrayList<>();

        if (other instanceof BaseActor) otherColliders.addAll(((BaseActor) other).images);
        else otherColliders.add(other);

        return images.stream()
            .filter(ImageHolder::isCollider)
            .anyMatch(image -> otherColliders.stream().anyMatch(image::intersects));
    }

    /**
     * Bewegt diesen Actor und alle {@link ImageHolder} zu der Position die in {@link #subpixelPosition}
     * gespeichert ist.
     */
    private void applyPosition() {
        super.setLocation(point().x(), point().y());
        updateChildren();
    }

    public void mirrorHorizontally() {
        images.forEach(ImageHolder::mirrorHorizontally);
    }

    @Override
    public Vector2D vec() {
        return subpixelPosition;
    }

    public void initializePosition() {
        subpixelPosition = new Vector2D(getX(), getY());
    }

    public void updateChildren() {
        images.forEach(image -> image.updatePosition(this));
    }

    /**
     * Entfernt alle {@link ImageHolder}.
     */
    public void destroyChildren() {
        images.forEach(getWorld()::removeObject);
    }

    /**
     * Wird beim Tod gerufen.
     * Die Standardimplementation entfernt das Objekt aus der Welt.
     */
    protected void deathHandler() {
        getWorld().removeObject(this);
    }

    protected void setLocation(Position2D vector) {
        subpixelPosition = vector.vec();
        applyPosition();
    }

    /**
     * Bewegt sich mit der in dem {@link #speed} Attribut vorgegebenen Geschwindigkeit mit Kollisionsüberprüfung.
     *
     * @param position Die Richtung in welche die Bewegung passiert. Wird vor Verwendung normalisiert.
     */
    protected void moveWithSpeed(Position2D position) {
        moveWithSpeed(position, true);
    }

    /**
     * Bewegt sich mit der in dem {@link #speed} Attribut vorgegebenen Geschwindigkeit.
     *
     * @param vector         Die Richtung in welche die Bewegung passiert. Wird vor Verwendung normalisiert.
     * @param checkCollision Ob Kollisionüberprüfung durchgeführt werden soll oder nicht.
     */
    protected void moveWithSpeed(Position2D vector, boolean checkCollision) {
        move(vector.normalize().multiply(speed), checkCollision);
    }

    /**
     * Bestimmt, ob sich der Actor in einer Wand befindet oder nicht.
     */
    protected boolean insideWall() {
        return Misc.getCurrentWorld().getWalls().stream().anyMatch(this::intersects);
    }

    /**
     * Bewegt den Actor entlang des Vectors.
     *
     * @param checkCollision Ob Kollisionsüberprüfung durchgeführt werden soll oder nicht.
     */
    protected void move(Vector2D vector, boolean checkCollision) {
        Vector2D startPosition = vec();
        setLocation(plus(vector));
        applyPosition();

        if (checkCollision && insideWall()) {
            wallCollision = true;
            setLocation(startPosition);
            applyPosition();
        }
    }

    /**
     * Bewegt den Actor entlang des Vectors, mit Kollisionsüberprüfung.
     */
    protected void move(Vector2D vector) {
        move(vector, true);
    }

    /**
     * Entfernt das Objekt, behält aber alle zugehörigen {@link ImageHolder}.
     */
    public void freeze() {
        Misc.getCurrentWorld().removeObjectUnchecked(this);
    }

    /**
     * Wird gerufen, wenn der Actor sich nach oben bewegen soll.
     */
    protected void moveUp() {
        moveWithSpeed(Vector2D.UP);
    }

    /**
     * Wird gerufen, wenn der Actor sich nach unten bewegen soll.
     */
    protected void moveDown() {
        moveWithSpeed(Vector2D.DOWN);
    }

    /**
     * Wird gerufen, wenn der Actor sich nach links bewegen soll.
     */
    protected void moveLeft() {
        moveWithSpeed(Vector2D.LEFT);
    }

    /**
     * Wird gerufen, wenn der Actor sich nach rechts bewegen soll.
     */
    protected void moveRight() {
        moveWithSpeed(Vector2D.RIGHT);
    }
}
