public class GameSelection extends BaseWorld {
    private final MiniGame[] miniGames = new MiniGame[]{
        Misc.addObject(new MiniGame(ConeShooterWorld::new, 0), minigamePosition(Vector2.LEFT)),
        Misc.addObject(new MiniGame(LightCyclesWorld::new, 1), minigamePosition(Vector2.UP)),
        Misc.addObject(new MiniGame(GridBugsWorld::new, 2), minigamePosition(Vector2.RIGHT)),
        Misc.addObject(new MiniGame(TankLabyrinthWorld::new, 3), minigamePosition(Vector2.DOWN))
    };

    private final GameSelectionPlayer player = Misc.addObject(new GameSelectionPlayer(miniGames), Vector2.MIDDLE);

    private Vector2 minigamePosition(Vector2 direction) {
        return Vector2.MIDDLE.plus(direction.scale(130));
    }

    public void exitMinigame() {
        Misc.loadWorld(this);
        player.reset();
    }
}
