import greenfoot.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public abstract class BaseWorld extends World {
    public static boolean levelBuilder = false;
    private final List<Wall> walls = new ArrayList<>();
    private final FrameSurface frame;
    protected Score score;
    private boolean lost = false;

    public BaseWorld() {
        super(Misc.worldWidth, Misc.worldHeight, 1);

        if (!levelBuilder) {
            Misc.setWorld(this);
        }

        ArrayList<Line> walls = LevelLoader.getLevelData(getClass());
        setWalls(walls);

        frame = Misc.addObject(new FrameSurface(), Vector2.MIDDLE);
        showScore();
    }

    private static void drawText(TextRenderer renderer, Function<Vector2, IGetVector2> pos) {
        Point position = new Point(pos.apply(renderer.dimensions()).position());
        renderer.render(position);
    }

    protected void showScore() {
        score = Misc.addObject(new Score(), Vector2.ZERO);
    }

    public void drawOnce(String text, Function<Vector2, IGetVector2> pos, float fontSize, Color color) {
        drawText(new TextRenderer(text, frame.getImage(), fontSize, color), pos);
    }

    public void drawForever(String text, Function<Vector2, IGetVector2> pos, float fontSize, Color color) {
        drawText(new TextRenderer(text, getBackground(), fontSize, color), pos);
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

    @Override
    public void act() {
        super.act();
        frame.blank();
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

        drawForever("YOU LOST!", Misc.centeredAround(new Vector2(300, 150)), 30, Color.BLUE);
        Greenfoot.delay(20);

        drawForever("SCORE: " + currentScore, Misc.centeredAround(new Vector2(300, 200)), 30, Color.BLUE);
        Greenfoot.delay(20);

        String nextLine = currentScore > highscore ? "NEW HIGH SCORE!" : "HIGH SCORE: " + highscore;
        drawForever(nextLine, Misc.centeredAround(new Vector2(300, 250)), 30, Color.BLUE);
        Greenfoot.delay(80);

        ScoreTracker.setScore(0);
        Misc.reloadGameSelection();
    }

    protected void blank() {
        getObjects(Actor.class).forEach(this::removeObjectUnchecked);
    }
}
