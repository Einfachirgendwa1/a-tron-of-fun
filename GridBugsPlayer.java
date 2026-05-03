import greenfoot.Actor;

public class GridBugsPlayer extends HumanoidPlayer {
    public boolean touches(Actor target) {
        return intersects(target);
    }

    @Override
    public void act() {
        super.act();
    }
}
