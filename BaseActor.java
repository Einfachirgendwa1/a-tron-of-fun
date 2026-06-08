import greenfoot.Actor;
import greenfoot.GreenfootImage;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Die Basisklasse von (fast) allen Actors.
 * Erweitert die {@link Actor} Klasse um verschiedene praktische Funktionalitäten, wie zum Beispiel, dass ein
 * {@link BaseActor} aus mehreren Bildern bestehen kann, diverse Funktionen zur Bewegung bereitstellt und automatisch
 * Kollisionen überprüfen kann.
 */
public class BaseActor extends Actor implements Position2D {
    /**
     * Wie viel Leben der Actor hat.
     */
    protected int health = 100;

    /**
     * Wie schnell der Actor ist.
     *
     * @see #moveWithSpeed(Position2D)
     */

    protected float speed = 1;
    /**
     * Die zu diesem Actor zugehörigen Bilder. Häufig ist das nur ein einziges.
     */
    private ArrayList<ImageHolder> images;

    /**
     * Ob im letzten frame eine Kollision mit einer Wand aufgetreten ist.
     */
    private boolean wallCollision = false;

    /**
     * Die "wahre" Position des Actors. Wird für Greenfoot immer von einem {@link Vector2D} in einen {@link Point2D}
     * übersetzt.
     */
    private Vector2D subpixelPosition;

    /**
     * Ruft die {@code function} wenn {@code actor} ein {@link BaseActor} ist.
     */
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

    /**
     * Die Bilder des Actors.
     *
     * @return Ein Array mit all den {@link ImageHolder}n.
     */
    protected ImageHolder[] images() {
        return new ImageHolder[]{};
    }

    /**
     * Berechnet eine Richtung.
     *
     * @param other Das Ziel.
     * @return Ein normalisierter Vektor der in Richtung des Ziels zeigt.
     */
    protected Vector2D towards(Position2D other) {
        return other.minus(this).normalize();
    }

    /**
     * Nimmt Schaden.
     *
     * @param amount Wie viel Schaden genommen werden soll.
     */
    public void takeDamage(int amount) {
        health -= amount;
    }

    /**
     * Das Verhalten des Actors.
     */
    @Override
    public void act() {
        wallCollision = false;
        applyPosition();

        if (health <= 0) {
            deathHandler();
        }
    }

    /**
     * Teleportiert den Actor.
     *
     * @param x Die neue X-Koordinate.
     * @param y Die neue Y-Koordinate.
     */
    @Override
    public void setLocation(int x, int y) {
        setLocation(new Point2D(x, y));
    }

    /**
     * Ändert das Bild des Actors. Macht nur Sinn bei Objekten mit nur einem Bild.
     * Wird einmal von Greenfoot beim Programmstart gerufen. Dieser Fall wird abgefangen und auf die multiple-images
     * Funktionalität des Actors übersetzt.
     *
     * @param image Das neue Bild.
     */
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

    /**
     * Spiegelt den Actor horizontal.
     */
    public void mirrorHorizontally() {
        images.forEach(ImageHolder::mirrorHorizontally);
    }

    @Override
    public Vector2D vec() {
        return subpixelPosition;
    }

    /**
     * Speichert die aktuelle Position auf dem Canvas als {@code subpixelPosition}.
     */
    public void initializePosition() {
        subpixelPosition = new Vector2D(getX(), getY());
    }

    /**
     * Synchronisiert die Positionen der {@link ImageHolder}.
     */
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

    /**
     * Teleportiert den Actor.
     *
     * @param vector Die neue Position.
     */
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
