public class TankLabyrinthWorld extends BaseWorld {
    public TankLabyrinthWorld() {
        super();
        MultipleImages.createActor(TankLabyrinthPlayer::new);
    }
}
