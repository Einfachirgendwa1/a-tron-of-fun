import greenfoot.Greenfoot;
import greenfoot.World;

public class GameSelection extends World {
    public static GameSelection instance;
    private final ICreateWorld[] miniGames = new ICreateWorld[]{
            ConeShooterWorld::new,
            LightCyclesWorld::new,
            GridBugsWorld::new,
            TankLabyrinthWorld::new
    };
    private final GameSelectionPlayer player;

    public GameSelection() {
        super(600, 400, 1);

        instance = this;

        player = new GameSelectionPlayer(this);
        addObject(player, 300, 200);
    }

    public void enterMinigame(int minigameIndex) {
        try {
            Greenfoot.setWorld(miniGames[minigameIndex].apply());
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
