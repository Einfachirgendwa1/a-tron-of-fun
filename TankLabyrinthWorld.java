public class TankLabyrinthWorld extends BaseWorld {
    private final TankLabyrinthPlayer player;

    public TankLabyrinthWorld() {
        super();
        player = Misc.addObject(new TankLabyrinthPlayer(), new Vector2D(300, 100));
    }

    @Override
    public void act() {
        won();
    }
}
