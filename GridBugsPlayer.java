import greenfoot.World;

/**
 * Write a description of class GridBugsPlayer here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class GridBugsPlayer extends Player {
    public GridBugsPlayer(World world) {
        super(world);
    }

    @Override
    protected ImageHolder[] defaultImageHolders() {
        return new ImageHolder[]{
                new ImageHolder("man_stand_body.png", 0, 0),
        };
    }
}
