import greenfoot.World;

public class ConePlayer extends Player {
    public ConePlayer(World world) {
        super(world);
    }

    @Override
    protected ImageHolder[] defaultImageHolders() {
        return new ImageHolder[]{
                new ImageHolder("man_stand_body.png", 0, 0),
        };
    }

    /**
     * Act - do whatever the ConePlayer wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() {
        super.act();
    }
}
