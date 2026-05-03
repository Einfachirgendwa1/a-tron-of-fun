public abstract class Player extends BaseActor {
    @Override
    public void act() {
        super.act();

        if (health <= 0) {
            Misc.exitMinigame();
        }
    }
}
