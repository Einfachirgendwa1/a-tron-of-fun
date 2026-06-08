/**
 * Die Welt, in der man die verschiedenen Minigames auswählen kann und zu der man nach jedem Minigame zurückkehrt.
 */
public class GameSelection extends BaseWorld {
    /**
     * Die verschiedenen Minigames.
     */
    private MiniGame[] miniGames = createMiniGames();

    /**
     * Der Spieler.
     */
    private final GameSelectionPlayer player = Misc.addObject(new GameSelectionPlayer(miniGames), Vector2D.MIDDLE);

    /**
     * Erstellt die verschiedenen Minigames.
     *
     * @return Die erstellten Minigames.
     */
    private MiniGame[] createMiniGames() {
        return new MiniGame[]{
            Misc.addObject(new MiniGame(ConeShooterWorld::new, 0), minigamePosition(Vector2D.LEFT)),
            Misc.addObject(new MiniGame(LightCyclesWorld::new, 1), minigamePosition(Vector2D.UP)),
            Misc.addObject(new MiniGame(GridBugsWorld::new, 2), minigamePosition(Vector2D.RIGHT)),
            Misc.addObject(new MiniGame(TankLabyrinthWorld::new, 3), minigamePosition(Vector2D.DOWN))
        };
    }

    /**
     * Berechnet die Position eines Minigames.
     *
     * @param direction Die Richtung, in der es verschoben werden soll.
     * @return Die finale Position.
     */
    private Vector2D minigamePosition(Vector2D direction) {
        return Vector2D.MIDDLE.plus(direction.multiply(150));
    }

    /**
     * Verlässt das aktuelle Minigame.
     */
    public void exitMinigame() {
        Misc.loadWorld(this);

        if (getObjects(MiniGame.class).isEmpty()) {
            miniGames = createMiniGames();
        }

        player.reset();
    }
}
