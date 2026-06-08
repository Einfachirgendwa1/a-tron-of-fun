import greenfoot.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Die Basisklasse von allen Welten.
 */
public abstract class BaseWorld extends World {
    /**
     * Ob der {@link LevelBuilder} gerade läuft oder nicht. Irrelevant im Release-Mode.
     */
    public static boolean levelBuilder = false;

    /**
     * Die Wände (manche Level haben keine Wände, dann ist die Liste einfach leer.)
     */
    private final List<Wall> walls = new ArrayList<>();

    /**
     * Eine temporäre FrameSurface zum Rendern.
     */
    private final FrameSurface frame;

    /**
     * Der Score-Tracker.
     */
    protected Score score;

    /**
     * Ob das Spiel verloren wurde oder nicht.
     */
    private boolean lost = false;

    public BaseWorld() {
        super(Misc.worldWidth, Misc.worldHeight, 1);

        if (!levelBuilder) {
            Misc.setWorld(this);
        }

        setWalls(LevelLoader.getLevelData(getClass()));

        frame = Misc.addObject(new FrameSurface(), Vector2D.MIDDLE);
        showScore();
    }

    /**
     * Rendert text.
     *
     * @param renderer Die Informationen über den text.
     * @param pos      Wohin er gerendert werden soll.
     */
    private static void drawText(TextRenderer renderer, Function<Vector2D, Position2D> pos) {
        Point2D position = new Point2D(pos.apply(renderer.dimensions()));
        renderer.render(position);
    }

    /**
     * Rendert den score.
     */
    protected void showScore() {
        score = Misc.addObject(new Score(), Vector2D.ZERO);
    }

    /**
     * Zeichnet Text für einen einzigen Frame, danach verschwindet er wieder.
     *
     * @param text     Der Text.
     * @param pos      Wohin er gerendert werden soll.
     * @param fontSize Die Schriftgröße.
     * @param color    Die Farbe.
     */
    public void drawOnce(String text, Function<Vector2D, Position2D> pos, float fontSize, Color color) {
        drawText(new TextRenderer(text, frame.getImage(), fontSize, color), pos);
    }

    /**
     * Rendert Text permanent auf den Hintergrund.
     *
     * @param text     Der Text.
     * @param pos      Wohin er gerendert werden soll.
     * @param fontSize Die Schriftgröße.
     * @param color    Die Farbe.
     */
    public void drawForever(String text, Function<Vector2D, Position2D> pos, float fontSize, Color color) {
        drawText(new TextRenderer(text, getBackground(), fontSize, color), pos);
    }

    /**
     * Ein Weg um Dinge für einen einzigen Frame zu zeichnen.
     *
     * @return Ein GreenfootImage das zu Beginn von jedem Frame resettet wird.
     */
    public GreenfootImage getFrame() {
        return frame.getImage();
    }

    /**
     * Die Wände.
     *
     * @return Eine Liste an Wänden.
     */
    public List<Wall> getWalls() {
        return walls;
    }

    /**
     * Lädt die Wände in die Welt.
     *
     * @param lines Die Wände.
     */
    public void setWalls(ArrayList<Line2D> lines) {
        walls.forEach(this::removeObject);
        walls.clear();

        for (Line2D line : lines) {
            Wall wall = new Wall(line);
            walls.add(Misc.addObject(wall, wall.getPosition()));
        }
    }

    /**
     * Fügt ein Objekt zur Welt hinzu.
     *
     * @param object Das Objekt was hinzugefügt werden soll.
     * @param x      Die X-Koordinate.
     * @param y      Die Y-Koordinate.
     */
    @Override
    public void addObject(Actor object, int x, int y) {
        super.addObject(object, x, y);
        BaseActor.run(object, BaseActor::initializePosition);
        BaseActor.run(object, BaseActor::updateChildren);
    }

    /**
     * Entfernt ein Objekt aus der Welt.
     *
     * @param object Das Objekt was entfernt werden soll.
     */
    @Override
    public void removeObject(Actor object) {
        BaseActor.run(object, BaseActor::destroyChildren);
        super.removeObject(object);
    }

    /**
     * Das Verhalten der Welt.
     */
    @Override
    public void act() {
        super.act();
        frame.blank();
    }

    /**
     * Entfernt ein Objekt, ohne die Kinder eines BaseActors auch zu entfernen.
     *
     * @param object Das Objekt was entfernt werden soll.
     */
    public void removeObjectUnchecked(Actor object) {
        super.removeObject(object);
    }

    /**
     * Zeigt den "YOU WON!" Text an und beendet das Minigame.
     */
    protected void won() {
        blank();
        ScoreTracker.addScore(500);
        drawForever("YOU WON!", Misc.centeredAround(Vector2D.MIDDLE), 50, Color.BLUE);
        Greenfoot.delay(40);
        Misc.exitMinigame();
    }

    /**
     * Zeigt den "YOU LOST!" Text an und resettet das Spiel.
     */
    protected void lost() {
        lost("YOU LOST!");
    }

    /**
     * Zeigt die Todesnachricht an und resettet das Spiel.
     *
     * @param deathMessage Die Todesnachricht an.
     */
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

    /**
     * Entfernt alle Objekte aus der Welt.
     */
    protected void blank() {
        getObjects(Actor.class).forEach(this::removeObjectUnchecked);
    }
}
