public class GameSelection extends BaseWorld {
    private MiniGame[] miniGames = createMiniGames();
    private final GameSelectionPlayer player = Misc.addObject(new GameSelectionPlayer(miniGames), Vector2D.MIDDLE);

    public GameSelection() {
    }

    private MiniGame[] createMiniGames() {
        return new MiniGame[]{
            Misc.addObject(new MiniGame(ConeShooterWorld::new, 0), minigamePosition(Vector2D.LEFT)),
            Misc.addObject(new MiniGame(LightCyclesWorld::new, 1), minigamePosition(Vector2D.UP)),
            Misc.addObject(new MiniGame(GridBugsWorld::new, 2), minigamePosition(Vector2D.RIGHT)),
            Misc.addObject(new MiniGame(TankLabyrinthWorld::new, 3), minigamePosition(Vector2D.DOWN))
        };
    }

    private Vector2D minigamePosition(Vector2D direction) {
        return Vector2D.MIDDLE.plus(direction.multiply(150));
    }

    public void exitMinigame() {
        Misc.loadWorld(this);

        if (getObjects(MiniGame.class).isEmpty()) {
            miniGames = createMiniGames();
        }

        player.reset();
    }
}
