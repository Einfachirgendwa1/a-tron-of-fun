import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;

public class LightCyclePlayer extends Player {
    private int direction = 2;
    private boolean isPressed = false;
    private boolean crashed = false;
    private ImageHolder cycleImage;

    public LightCyclePlayer() {
        super();
        getImage().rotate(90);
    }

    public boolean touchesTrail(Trail trail) {
        return intersects(trail);
    }

    public boolean touchesEnemy(LightCycleEnemy enemy) {
        return intersects(enemy);
    }

    private void boomAnimation() {
        cycleImage.setImage("boom_1.png");
        Greenfoot.delay(10);
        cycleImage.setImage("boom_2.png");
        Greenfoot.delay(10);
        cycleImage.setImage("boom_3.png");
        Greenfoot.delay(10);
        cycleImage.setImage("boom_4.png");

        LightCyclesWorld world = (LightCyclesWorld) getWorld();
        world.gameOver();
    }

    public boolean isCrashed() {
        LightCyclesWorld world = (LightCyclesWorld) getWorld();
        if (touchesEnemy(world.enemy)) {
            crashed = true;
        }
        return crashed;
    }


    @Override
    protected ImageHolder[] images() {
        GreenfootImage img = new GreenfootImage("images/lightcycle_player.png");
        img.rotate(90);
        cycleImage = new ImageHolder(img, 0, 0);
        return new ImageHolder[]{ cycleImage };
    }

    public void act() {

        super.act();
        if (isCrashed()) {
            boomAnimation();
            return;
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

        //Steuerung mit A und D, relativ zur Bewegungsrichtung des Spielers nach links oder Rechts
        if (Greenfoot.isKeyDown("a") && !isPressed) {
            direction = direction - 1;
            cycleImage.rotate(-90);
            isPressed = true;
        }

        if (Greenfoot.isKeyDown("d") && !isPressed) {
            direction = direction + 1;
            cycleImage.rotate(90);
            isPressed = true;
        }

        /*Die Frames sind zu schnell für die Tasteneingaben, 
        weswegen die isPressed Variable zu schnelle Eingaben verhindert, 
        die sonst ein falsches Verhalten verursachen würden.*/

        //Wenn keine der beiden Tasten gedrückt wird, kann die nächste Eingabe registriert werden.
        if (!Greenfoot.isKeyDown("a") && !Greenfoot.isKeyDown("d")) {
            isPressed = false;
        }
    }
}
