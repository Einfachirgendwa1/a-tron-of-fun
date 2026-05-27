import java.util.List;
import java.util.function.Supplier;

public class GameSelection extends BaseWorld {
    private final GameSelectionPlayer player = Misc.addObject(new GameSelectionPlayer(), Vector2.MIDDLE);

    private final List<Supplier<BaseWorld>> miniGames = List.of(
        ConeShooterWorld::new,
        LightCyclesWorld::new,
        GridBugsWorld::new,
        TankLabyrinthWorld::new
    );

    public void enterMinigame(int minigameIndex) {
        Misc.loadWorld(miniGames.get(minigameIndex).get());
    }

    public void exitMinigame() {
        Misc.loadWorld(this);
        player.teleport(Vector2.MIDDLE);
    }
}
