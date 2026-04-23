import greenfoot.Greenfoot;
import greenfoot.World;

public class LightCyclePlayer extends Player {
    private int direction = 1;
    private boolean isPressed = false;

    public LightCyclePlayer(World world) {
        super(world);
    }

    @Override
    protected ImageHolder[] defaultImages() {
        return new ImageHolder[]{
        };
    }

    public void act() {
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

        if (!Greenfoot.isKeyDown("a") && !Greenfoot.isKeyDown("d")) { //Wenn keine der beiden Tasten gedrückt wird, kann die nächste Eingabe registriert werden.
            isPressed = false;
        }
    }
}
