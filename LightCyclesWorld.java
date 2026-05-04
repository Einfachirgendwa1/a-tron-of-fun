public class LightCyclesWorld extends BaseWorld {
    public final LightCyclePlayer player = MultipleImages.createActor(LightCyclePlayer::new, 75, 350);
    public final LightCycleEnemy enemy = MultipleImages.createActor(LightCycleEnemy::new, 525, 50);

    @Override
    public void act() {
        super.act();
        if (player.touchesEnemy(enemy)) {
            Misc.exitMinigame();
        }
    }

    public void gameOver() {
        Misc.exitMinigame();
    }
}