import greenfoot.World;

public class TankLabyrinthWorld extends World {
    public TankLabyrinthWorld() {
        super(600, 400, 1);
        addObject(new TankLabyrinthPlayer(this), 300, 200);
    }
}
