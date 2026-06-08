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

        ArrayList<Line2D> walls = LevelLoader.getLevelData(getClass());
        setWalls(walls);

        for (Line2D wall : walls) {
            getBackground().setColor(Color.RED);
            wall.draw(getBackground());
        }

        frame = Misc.addObject(new FrameSurface(), Vector2D.MIDDLE);
        showScore();
    }

    private static void drawText(TextRenderer renderer, Function<Vector2D, Position2D> pos) {
        Point2D position = new Point2D(pos.apply(renderer.dimensions()));
        renderer.render(position);
    }

    protected void showScore() {
        score = Misc.addObject(new Score(), Vector2D.ZERO);
    }

    public void drawOnce(String text, Function<Vector2D, Position2D> pos, float fontSize, Color color) {
        drawText(new TextRenderer(text, frame.getImage(), fontSize, color), pos);
    }

    public void drawForever(String text, Function<Vector2D, Position2D> pos, float fontSize, Color color) {
        drawText(new TextRenderer(text, getBackground(), fontSize, color), pos);
    }

    public GreenfootImage getFrame() {
        return frame.getImage();
    }

    public List<Wall> getWalls() {
        return walls;
    }

    public void setWalls(ArrayList<Line2D> lines) {
        walls.forEach(this::removeObject);
        walls.clear();

        for (Line2D line : lines) {
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
        BaseActor.run(object, BaseActor::destroyChildren);
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

    protected void won() {
        blank();
        ScoreTracker.addScore(500);
        drawForever("YOU WON!", Misc.centeredAround(Vector2D.MIDDLE), 50, Color.BLUE);
        Greenfoot.delay(40);
        Misc.exitMinigame();
    }

    protected void lost() {
        lost("YOU LOST!");
    }

    protected void lost(String deathMessage) {
        if (lost) return;
        lost = true;

        int currentScore = ScoreTracker.getScore();
        int highscore = ScoreTracker.readHighScore();

        ScoreTracker.saveHighScore();
        blank();

        GreenfootImage background = getBackground();
        background.setColor(Color.BLACK);
        background.fill();

        drawForever(deathMessage, Misc.centeredAround(new Vector2D(300, 150)), 30, Color.BLUE);
        Greenfoot.delay(20);

        drawForever("SCORE: " + currentScore, Misc.centeredAround(new Vector2D(300, 200)), 30, Color.BLUE);
        Greenfoot.delay(20);

        String nextLine = currentScore > highscore ? "NEW HIGH SCORE!" : "HIGH SCORE: " + highscore;
        drawForever(nextLine, Misc.centeredAround(new Vector2D(300, 250)), 30, Color.BLUE);
        Greenfoot.delay(80);

        ScoreTracker.setScore(0);
        Misc.reloadGameSelection();
    }

    protected void blank() {
        getObjects(Actor.class).forEach(this::removeObjectUnchecked);
    }
}
