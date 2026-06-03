import greenfoot.Actor;
import greenfoot.Greenfoot;

/**
 * Der Spieler im Auswahlmenü für die verschiedenen Minispiele am Anfang.
 * Die Bewegungsanimationen etc. werden von {@link HumanoidPlayer} geerbt.
 * Ein entscheidender Unterschied zu den anderen Klassen die von {@link HumanoidPlayer} erben
 * ({@link GridBugsPlayer} und {@link ConePlayer}) ist, dass {@link HumanoidPlayer#shoot()} überschrieben wird, da es
 * keinen Sinn ergibt, im Hauptmenü schießen zu können.
 * Die 4 {@link MiniGame}s werden als ein Array gespeichert. Beim Drücken von WASD wird {@code targetMinigame} auf das
 * jeweilige {@link MiniGame} gesetzt und der Spieler läuft darauf zu. Wenn er es erreicht wird {@link #isMoving()}
 * {@code false} und wenn die Leertaste gedrückt wird {@link MiniGame#loadWorld()} auf dem jeweiligen {@link MiniGame}
 * aufgerufen.
 *
 * @author Faris
 * @see HumanoidPlayer
 * @see GameSelection
 * @see MiniGame
 */
public class GameSelectionPlayer extends HumanoidPlayer {
    private final MiniGame[] miniGames;
    private MiniGame targetMinigame = null;

    public GameSelectionPlayer(MiniGame[] miniGames) {
        this.miniGames = miniGames;
        speed = 4f;
    }

    /**
     * @return Die Position des {@code targetMinigame}. {@link Vector2D#MIDDLE} wenn es {@code null} ist.
     */
    private Vector2D targetPosition() {
        return targetMinigame != null ? targetMinigame.vec() : Vector2D.MIDDLE;
    }

    /**
     * Wird gerufen, wenn das aktuelle Minigame endet.
     */
    public void reset() {
        setLocation(Point2D.MIDDLE);
        targetMinigame = null;
    }

    @Override
    protected boolean isMoving() {
        return !vec().point().equals(targetPosition().point());
    }

    @Override
    public void act() {
        super.act();

        for (int i = 0; i < speed; i++) {
            move(towards(targetPosition()));
        }

        if (Greenfoot.isKeyDown("space") && !isMoving()) {
            Actor intersectingMinigame = getOneIntersectingObject(MiniGame.class);
            if (intersectingMinigame != null) {
                ((MiniGame) intersectingMinigame).loadWorld();
            }
        }
    }

    @Override
    protected void shoot() {}

    @Override
    protected void moveUp() {
        targetMinigame = miniGames[1];
    }

    @Override
    protected void moveDown() {
        targetMinigame = miniGames[3];
    }

    @Override
    protected void moveLeft() {
        targetMinigame = miniGames[0];
    }

    @Override
    protected void moveRight() {
        targetMinigame = miniGames[2];
    }
}
