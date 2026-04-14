import greenfoot.Greenfoot;
import greenfoot.World;

import java.lang.reflect.Constructor;

public class GameSelection extends World {
    public static GameSelection instance;
    private final ICreateWorld[] miniGames = new Constructor[]{
            () -> new ConeShooterWorld(),
            ConeShooterWorld.class.getConstructor(),
            GridBugsWorld.class.getConstructor(),
            TankLabyrinthWorld.class.getConstructor()
    };
    private final GameSelectionPlayer player;

    public GameSelection() throws NoSuchMethodException {
        super(600, 400, 1);

        instance = this;

        TankLabyrinthWorld world = new TankLabyrinthWorld();
        int x = world.counter;

        player = new GameSelectionPlayer(this);
        addObject(player, 300, 200);
    }

    public void enterMinigame(int minigameIndex) {
        try {
            Greenfoot.setWorld(miniGames[minigameIndex].newInstance());
        } catch (Exception e) {
            System.out.println("Error creating minigame " + minigameIndex);
        }
    }

    public void exitMinigame() {
        Greenfoot.setWorld(this);
        player.setLocation(300, 200);
    }

    interface ICreateWorld {
        World apply();
    }
}
