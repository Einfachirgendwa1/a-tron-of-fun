public class Bullet extends BaseActor {
    private final Vector2 velocity;
    private final boolean canHitPlayer;

    public Bullet(Vector2 velocity, boolean canHitPlayer) {
        this.velocity = velocity;
        this.canHitPlayer = canHitPlayer;
    }

    public void act() {
        move(velocity);

        if (isAtEdge()) {
            getWorld().removeObject(this);
            return;
        }

        IDamageable target = null;

        if (canHitPlayer) {
            target = (Player) getOneIntersectingObject(Player.class);
        }

        if (target == null) {
            target = (Enemy) getOneIntersectingObject(Enemy.class);
        }

        if (target != null) {
            target.takeDamage(20);
            getWorld().removeObject(this);
        }
    }
}
