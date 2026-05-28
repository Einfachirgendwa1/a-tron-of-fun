public class GridBugsBonus extends BaseActor {
    @Override
    public void act() {
        if (getOneIntersectingObject(GridBugsPlayer.class) != null) {
            ScoreTracker.addScore(1500);
            super.deathHandler();
        }
    }
}
