import greenfoot.World;

public class GameSelection extends World {
    private final MiniGame[] miniGames = new MiniGame[]{};
    private final GameSelectionPlayer player;

    public GameSelection() {
        super(600, 400, 1);

        player = new GameSelectionPlayer(this);
        addObject(player, 300, 200);
    }

    public void enterMinigame(int minigameIndex) {

    }
}
