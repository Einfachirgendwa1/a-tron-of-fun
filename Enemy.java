public abstract class Enemy extends BaseActor {
    protected int pointsOnDeath = 0;

    protected void onPlayerContact() {}

    @Override
    public void act() {
        if (getOneIntersectingObject(Player.class) != null) {
            onPlayerContact();
        }

        super.act();
    }

    @Override
    protected void deathHandler() {
        super.deathHandler();
        ScoreTracker.addScore(pointsOnDeath);
    }
}