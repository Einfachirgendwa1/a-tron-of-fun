import greenfoot.Actor;
import greenfoot.World;

public class BaseWorld extends World {
    public BaseWorld() {
        super(Misc.worldWidth, Misc.worldHeight, 1);
        Misc.setWorld(this);
    }

    @Override
    public void removeObject(Actor object) {
        if (object instanceof BaseActor) {
            ((BaseActor) object).destroyChildren();
        }

        super.removeObject(object);
    }
}
