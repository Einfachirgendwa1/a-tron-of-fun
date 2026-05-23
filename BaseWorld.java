import greenfoot.Actor;
import greenfoot.World;

import java.util.ArrayList;

public abstract class BaseWorld extends World {
    public static boolean levelBuilder = false;
    private ArrayList<Line> walls = new ArrayList<>();

    public BaseWorld() {
        super(Misc.worldWidth, Misc.worldHeight, 1);

        if (!levelBuilder) {
            Misc.setWorld(this);
        }

        ArrayList<Line> walls = LevelLoader.getLevelData(getClass());
        System.out.println("Loaded " + walls.size() + " walls");
        setWalls(walls);
    }

    public ArrayList<Line> getWalls() {
        return walls;
    }

    public void setWalls(ArrayList<Line> walls) {
        this.walls = walls;
    }

    @Override
    public void addObject(Actor object, int x, int y) {
        super.addObject(object, x, y);
        BaseActor.run(object, BaseActor::updateChildren);
    }

    @Override
    public void removeObject(Actor object) {
        if (!levelBuilder) {
            BaseActor.run(object, BaseActor::destroyChildren);
        }

        super.removeObject(object);
    }
}
