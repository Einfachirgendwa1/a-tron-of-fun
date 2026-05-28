public abstract class Player extends BaseActor {
    @Override
    protected void deathHandler() {
        Misc.getCurrentWorld().lost();
    }
}
