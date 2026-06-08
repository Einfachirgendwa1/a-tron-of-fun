import greenfoot.GreenfootImage;

/**
 * Das Ziel im Grid-Bugs Spiel.
 */
public class GridBugsTarget extends BaseActor {

    /**
     * Der Cone der bei der Siegesanimation erstellt wird.
     */
    private ImageHolder winCone;

    @Override
    protected ImageHolder[] images() {
        GreenfootImage goal = new GreenfootImage(Misc.blank);
        goal.scale(40, 40);

        winCone = new ImageHolder(Misc.blank, 0, -86, false);
        return new ImageHolder[]{
            new ImageHolder("grid_bugs_target.png", 0, 0, false), new ImageHolder(goal, 0, 0), winCone
        };
    }

    /**
     * Lädt die Siegesanimation.
     */
    public void win() {
        winCone.setImage(new GreenfootImage("images/grid_bugs_win_cone.png"));
    }
}
