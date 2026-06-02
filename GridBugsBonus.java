/**
 * Ein Bonusitem, welches man in GridBugs einsammeln kann, woraufhin man einen großen Score Bonus erhält.
 * Überprüft, ob es mit dem Spieler kollidiert und wenn ja, fügt den Bonus hinzu und entfernt sich von der Welt.
 *
 * @see GridBugsWorld#winAnimation(Animator)
 */
public class GridBugsBonus extends BaseActor {
    /**
     * Die Punktzahl die man erhält.
     */
    private static final int scoreBonus = 1500;

    @Override
    public void act() {
        if (getOneIntersectingObject(GridBugsPlayer.class) != null) {
            ScoreTracker.addScore(scoreBonus);
            super.deathHandler();
        }
    }
}
