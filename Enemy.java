public abstract class Enemy extends BaseActor {
    @Override
    protected void deathHandler() {
        getWorld().removeObject(this);
    }
}