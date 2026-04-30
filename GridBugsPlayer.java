import greenfoot.Actor;

public class GridBugsPlayer extends PlayerDefaultMovement {
    @Override
    protected ImageHolder[] defaultImages() {
        return new ImageHolder[]{
                new ImageHolder("man_stand_body.png", 0, 0),
        };
    }

    public boolean touches(Actor target) {
        return intersects(target);
    }

    @Override
    public void act() {
        super.act();
    }
}
