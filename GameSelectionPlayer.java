import greenfoot.Greenfoot;

public class GameSelectionPlayer extends HumanoidPlayer {
    private int currentMinigame = -1;
    private int targetMinigame = -1;

    {
        allowShooting = false;
    }

    @Override
    protected boolean isMoving() {
        return currentMinigame != targetMinigame;
    }

    @Override
    public void act() {
        super.act();

        Vector2 target = switch (targetMinigame) {
            case 0 -> new Vector2(150, 200);
            case 1 -> new Vector2(300, 100);
            case 2 -> new Vector2(450, 200);
            case 3 -> new Vector2(300, 300);
            default -> Vector2.MIDDLE;
        };

        for (int i = 0; i < defaultSpeed; i++) {
            Vector2 movementVector = towards(target);

            if (movementVector.x() != 0 && movementVector.y() != 0) {
                target = Vector2.MIDDLE;
                movementVector = towards(target);
            }

            if (movementVector.isZero()) {
                currentMinigame = targetMinigame;
            } else {
                moveWithSpeed(movementVector);
            }
        }

        if (Greenfoot.isKeyDown("space") && currentMinigame != -1) {
            Misc.enterMinigame(currentMinigame);
        }
    }

    @Override
    protected void moveLeft() {
        if (!isMoving()) targetMinigame = 0;
    }

    @Override
    protected void moveUp() {
        if (!isMoving()) targetMinigame = 1;
    }

    @Override
    protected void moveRight() {
        if (!isMoving()) targetMinigame = 2;
    }

    @Override
    protected void moveDown() {
        if (!isMoving()) targetMinigame = 3;
    }
}
