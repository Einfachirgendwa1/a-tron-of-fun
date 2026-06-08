import java.util.Optional;

public class Bullet extends BaseActor {
    private final Vector2D velocity;
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
