import greenfoot.GreenfootImage;

public class LightCycleEnemy extends Enemy {

    private final int direction = 4;
    private ImageHolder cycleImage;

    @Override
    protected ImageHolder[] images() {
        GreenfootImage img = new GreenfootImage("images/lightcycle_enemy.png");
        img.rotate(-90);
        cycleImage = new ImageHolder(img, 0, 0);
        return new ImageHolder[]{ cycleImage };
    }

    public void act() {

        super.act();

        int result = direction % 4;

        if (result == 3 || result == -1) {
            moveDown();
        } else if (result == 2 || result == -2) {
            moveRight();
        } else if (result == 1 || result == -3) {
            moveUp();
        } else if (result == 0) {
            moveLeft();
        }
    }
}
