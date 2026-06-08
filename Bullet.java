import java.util.Optional;

/**
 * Eine Kugel.
 */
public class Bullet extends BaseActor {
    /**
     * Die Bewegung der Kugel.
     */
    private final Vector2D velocity;

    /**
     * Die Art von Objekten welche die Kugel treffen kann.
     */
    private final Class<? extends BaseActor> target;

    public Bullet(Vector2D velocity, Class<? extends BaseActor> target) {
        this.target = target;
        this.velocity = velocity;
    }

    @Override
    protected ImageHolder[] images() {
        return new ImageHolder[]{
            new ImageHolder("ammunition_large.png", 0, 0, true)
        };
    }

    public void act() {
        super.act();

        move(velocity);

        if (isAtEdge() || wallCollisionOccurred()) {
            getWorld().removeObject(this);
            return;
        }

        Optional.ofNullable((BaseActor) getOneIntersectingObject(target)).ifPresent(actor -> {
            actor.takeDamage(20);
            getWorld().removeObject(this);
        });
    }
}
