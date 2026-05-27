public abstract class Enemy extends BaseActor {
    protected int pointsOnDeath = 0;

    @Override
    protected void deathHandler() {
        super.deathHandler();
        ScoreTracker.addScore(pointsOnDeath);
    }
}