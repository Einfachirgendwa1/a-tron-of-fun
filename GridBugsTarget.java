import greenfoot.GreenfootImage;

public class GridBugsTarget extends BaseActor {
    private ImageHolder target;
    private ImageHolder winCone;

    @Override
    protected ImageHolder[] images() {
        GreenfootImage goal = new GreenfootImage(Misc.blank);
        goal.scale(40, 40);

        target = new ImageHolder(goal, 0, 0);
        winCone = new ImageHolder(Misc.blank, 0, -86, false);
        return new ImageHolder[]{
            new ImageHolder("grid_bugs_target.png", 0, 0, false), target, winCone
        };
    }

    public void win() {
        winCone.setImage(new GreenfootImage("images/grid_bugs_win_cone.png"));
    }

    public ImageHolder getTarget() {
        return target;
    }
}
