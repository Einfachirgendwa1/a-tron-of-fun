import greenfoot.Actor;
import greenfoot.World;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseWorld extends World {
    public static boolean levelBuilder = false;
    private final List<Wall> walls = new ArrayList<>();

    public BaseWorld() {
        super(Misc.worldWidth, Misc.worldHeight, 1);

        if (!levelBuilder) {
            Misc.setWorld(this);
        }

        ArrayList<Line> walls = LevelLoader.getLevelData(getClass());
        System.out.println("Loaded " + walls.size() + " walls");
        setWalls(walls);
    }

    public List<Line> getLines() {
        return walls.stream().map(Wall::getLine).toList();
    }

    public List<Wall> getWalls() {
        return walls;
    }

    public void setWalls(ArrayList<Line> lines) {
        walls.forEach(this::removeObject);
        walls.clear();

        for (Line line : lines) {
            Wall wall = new Wall(line);
            walls.add(Misc.addObject(wall, wall.getPosition()));
        }
    }

    @Override
    public void addObject(Actor object, int x, int y) {
        super.addObject(object, x, y);
        BaseActor.run(object, BaseActor::initializePosition);
        BaseActor.run(object, BaseActor::updateChildren);
    }

    @Override
    public void removeObject(Actor object) {
        if (!levelBuilder) {
            BaseActor.run(object, BaseActor::destroyChildren);
        }

        super.removeObject(object);
    }

    public void removeObjectUnchecked(Actor object) {
        super.removeObject(object);
    }
}
