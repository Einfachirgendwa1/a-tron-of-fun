import greenfoot.Greenfoot;
import greenfoot.World;
import greenfoot.Actor;

public class LightCyclePlayer extends Player {
    private int direction = 2;
    private boolean isPressed = false;
    private boolean crashed = false;

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
        setImage("boom_1.png");
        Greenfoot.delay(10);
        setImage("boom_2.png");
        Greenfoot.delay(10);
        setImage("boom_3.png");
        Greenfoot.delay(10);
        setImage("boom_4.png");

        LightCyclesWorld world = (LightCyclesWorld) getWorld();
        world.gameOver();
    }

    public boolean isCrashed() {
        LightCycleEnemy enemy = (LightCycleEnemy) getOneIntersectingObject(LightCycleEnemy.class);
        Trail trail = (Trail) getOneIntersectingObject(Trail.class);
        if (enemy != null || trail != null) {
            crashed = true;
        }
        System.out.println(crashed);
        return crashed;
    }

    @Override
    public boolean intersects(Actor other) {
        return super.intersects(other); // bypass the MultipleImages version
    }

    @Override
    protected ImageHolder[] defaultImages() {
        return new ImageHolder[]{
            new ImageHolder("lightcycle_player.png",75, 350)
        };
    }

    public void act() {

        isCrashed();

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
        } else if (result == 0) {
            moveLeft();
        }

        //Steuerung mit A und D, relativ zur Bewegungsrichtung des Spielers nach links oder Rechts
        if (Greenfoot.isKeyDown("a") && !isPressed) {
            direction = direction - 1;
            getImage().rotate(-90);
            isPressed = true;
        }

        if (Greenfoot.isKeyDown("d") && !isPressed) {
            direction = direction + 1;
            getImage().rotate(90);
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
