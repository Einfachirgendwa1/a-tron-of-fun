/**
 * Ein Gegner.
 */
public abstract class Enemy extends BaseActor {
    /**
     * Wie viele Punkte der Spieler beim Töten des Gegners erhält.
     */
    protected int pointsOnDeath = 0;

    /**
     * Wird ausgeführt, wenn der Spieler den Gegner berührt.
     */
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