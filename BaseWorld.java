import greenfoot.World;

public class BaseWorld extends World {
    public BaseWorld() {
        super(Misc.worldWidth, Misc.worldHeight, 1);
        Misc.setWorld(this);
    }
}
