import greenfoot.World;

public abstract class MiniGame {
    public abstract Player createPlayer();

    public abstract World createWorld();

    public abstract void miniGameStart();
}
