import greenfoot.GreenfootImage;
import greenfoot.Color;
import greenfoot.Greenfoot;

public class LightCycleEnemy extends Enemy {

    private int direction = 4;
    private ImageHolder cycleImage;
    private boolean crashed = false;

    @Override
    protected ImageHolder[] images() {
        GreenfootImage img = new GreenfootImage("images/lightcycle_enemy.png");
        img.rotate(-90);
        cycleImage = new ImageHolder(img, 0, 0);
        return new ImageHolder[]{ cycleImage };
    }

    public boolean touchesPlayer(LightCyclePlayer player) {
        return intersects(player);
    }

    public boolean isCrashed() {
        LightCyclesWorld world = (LightCyclesWorld) getWorld();
        Trail trail = (Trail) getOneIntersectingObject(Trail.class);
        if (touchesPlayer(world.player)|| (getOneIntersectingObject(Trail.class) != null && trail.getAge() > 40)) {
            crashed = true;
        }
        return crashed;
    }

    //Explosionsanimation bei Kollision
    private void boomAnimation() {
        cycleImage.setImage("boom_1.png");
        Greenfoot.delay(10);
        cycleImage.setImage("boom_2.png");
        Greenfoot.delay(10);
        cycleImage.setImage("boom_3.png");
        Greenfoot.delay(10);
        cycleImage.setImage("boom_4.png");
        Greenfoot.delay(20);

        LightCyclesWorld world = (LightCyclesWorld) getWorld(); 
        world.gameOver();
    }

    private boolean isNearWall() {
        int x = getX();
        int y = getY();
        return x <= 10 || x >= getWorld().getWidth() - 10 
            || y <= 10 || y >= getWorld().getHeight() - 10;
    }

    public void act() {

        super.act();
        if (isCrashed()) {
            boomAnimation();
            return;
        }

        if (isNearWall()) {
            direction = direction + 1;
        }

        int result = direction % 4;

        if (result == 3 || result == -1) {
            moveDown();
        } else if (result == 2 || result == -2) {
            moveRight();
        } else if (result == 1 || result == -3) {
            moveUp();
        } else {
            moveLeft();
        }

        //Erzeugung eines Schweifs an der aktuellen Position
        getWorld().addObject(new Trail(Color.BLUE), getX(), getY());
    }
}
