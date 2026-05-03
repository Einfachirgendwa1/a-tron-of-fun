import greenfoot.World;

import java.util.List;
import java.util.function.Supplier;

public class GameSelection extends BaseWorld {
    private final List<Supplier<World>> miniGames = List.of(
            ConeShooterWorld::new,
            LightCyclesWorld::new,
            GridBugsWorld::new,
            TankLabyrinthWorld::new
    );
    private final GameSelectionPlayer player;

    public GameSelection() {
        super();
        player = MultipleImages.createActor(GameSelectionPlayer::new);
    }

    public void enterMinigame(int minigameIndex) {
        Misc.loadWorld(miniGames.get(minigameIndex).get());
    }

    public void exitMinigame() {
        Misc.loadWorld(this);
        player.setLocation(300, 200);
    }
}
