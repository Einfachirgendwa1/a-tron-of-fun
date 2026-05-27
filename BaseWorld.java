import greenfoot.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public abstract class BaseWorld extends World {
    public static boolean levelBuilder = false;
    protected final Score score;
    private final List<Wall> walls = new ArrayList<>();
    private FrameSurface frame;
    private boolean lost = false;

    public BaseWorld() {
        super(Misc.worldWidth, Misc.worldHeight, 1);

        if (!levelBuilder) {
            Misc.setWorld(this);
        }

        ArrayList<Line> walls = LevelLoader.getLevelData(getClass());
        setWalls(walls);

        score = Misc.addObject(new Score(), Vector2.ZERO);
    }

    public void drawText(String text, Function<Vector2, IGetVector2> pos, float fontSize, Color color) {
        TextRenderer renderer = new TextRenderer(frame.getSurface());
        renderer.setFontSize(fontSize);
        renderer.setText(text);

        frame.getSurface().setColor(color);

        Point position = new Point(pos.apply(renderer.dimensions()).position());
        renderer.render(position);
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

    protected void lost() {
        if (lost) return;
        lost = true;

        int currentScore = ScoreTracker.getScore();
        int highscore = ScoreTracker.readHighScore();

        ScoreTracker.saveHighScore();
        blank();

        GreenfootImage background = getBackground();
        background.setColor(Color.BLACK);
        background.fill();

        drawText("YOU LOST!", Misc.centeredAround(new Vector2(300, 150)), 30, Color.BLUE);
        Greenfoot.delay(20);

        drawText("SCORE: " + currentScore, Misc.centeredAround(new Vector2(300, 200)), 30, Color.BLUE);
        Greenfoot.delay(20);

        String nextLine = currentScore > highscore ? "NEW HIGH SCORE!" : "HIGH SCORE: " + highscore;
        drawText(nextLine, Misc.centeredAround(new Vector2(300, 250)), 30, Color.BLUE);
        Greenfoot.delay(80);

        ScoreTracker.setScore(0);
        Misc.reloadGameSelection();
    }

    protected void blank() {
        getObjects(Actor.class).forEach(this::removeObjectUnchecked);
    }
}
