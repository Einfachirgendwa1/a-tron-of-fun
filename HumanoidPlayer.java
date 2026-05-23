import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public abstract class HumanoidPlayer extends Player {
    private static final GreenfootImage legsStand = new GreenfootImage("man_stand_legs.png");
    private static final GreenfootImage bodyStand = new GreenfootImage("man_stand_body.png");
    private static final GreenfootImage legsWalk = new GreenfootImage("man_go_legs.png");
    private static final GreenfootImage bodyWalk = new GreenfootImage("man_go_body.png");
    private static final List<GreenfootImage> leftArm = new ArrayList<>();
    private static final GreenfootImage throwInactive = new GreenfootImage("man_arm_bent_down.png");
    private static final GreenfootImage throwActive = new GreenfootImage("man_arm_bent_left.png");

    static {
        for (int i = 0; i < 10; i++) {
            leftArm.add(new GreenfootImage("man_arm_" + i + ".png"));
        }
    }

    protected boolean allowShooting = true;
    private boolean horizontalFlip = false;
    private boolean verticalFlip = false;
    private boolean onCooldown = false;
    private ImageHolder body;
    private ImageHolder legs;
    private ImageHolder pointArm;
    private ImageHolder throwArm;
    private final DelayedExecution shootTimer = new DelayedExecution(
            15,
            () -> {
                throwArm.setImage(throwInactive);
                onCooldown = false;
            }
    );

    public void setVerticalFlip(boolean verticalFlip) {
        if (verticalFlip != this.verticalFlip) {
            pointArm.mirrorVertically();
        }

        this.verticalFlip = verticalFlip;
    }

    public void setHorizontalFlip(boolean horizontalFlip) {
        if (horizontalFlip != this.horizontalFlip) {
            mirrorHorizontally();
        }

        this.horizontalFlip = horizontalFlip;
    }

    protected boolean isMoving() {
        return Stream.of("w", "a", "s", "d").anyMatch(Greenfoot::isKeyDown);
    }

    @Override
    protected ImageHolder[] images() {
        // These attribute initializations need to happen in defaultImages() and *NOT* in the constructor!
        // The Player class calls this function in its constructor and then passes all the ImageHolders we return to 
        // MultipleImages, but because Player is our superclass, its constructor actually runs earlier than ours.

        legs = new ImageHolder(legsStand, 0, 31);
        body = new ImageHolder(bodyStand, 0, 0);

        throwArm = new ImageHolder(throwInactive, 0, 6);
        pointArm = new ImageHolder(leftArm.get(4), -13, -11);

        return new ImageHolder[]{body, legs, pointArm, throwArm};
    }

    @Override
    public void act() {
        super.act();
        shootTimer.update();

        Map<String, Runnable> keymap = Map.of(
                "w", this::moveUp,
                "a", this::moveLeft,
                "s", this::moveDown,
                "d", this::moveRight
        );

        legs.setImage(isMoving() ? legsWalk : legsStand);
        body.setImage(isMoving() ? bodyWalk : bodyStand);

        double percentage = Misc.angleToMouse(this).orElse(0.);

        // between 0 and 9
        int i = (int) Math.round(percentage * 9);

        if (percentage <= 0.25) {
            setHorizontalFlip(false);
            setVerticalFlip(true);
        } else if (percentage <= 0.5) {
            setHorizontalFlip(true);
            setVerticalFlip(true);
        } else if (percentage <= 0.75) {
            setHorizontalFlip(true);
            setVerticalFlip(false);
        } else {
            setHorizontalFlip(false);
            setVerticalFlip(false);
        }

        // if (i >= 0 && i < leftArm.size()) pointArm.setImage(leftArm.get(i));

        if (Greenfoot.mouseClicked(null) && !onCooldown && allowShooting) {
            shoot();
        }

        for (Map.Entry<String, Runnable> entry : keymap.entrySet()) {
            if (Greenfoot.isKeyDown(entry.getKey())) {
                entry.getValue().run();
            }
        }
    }

    private void shoot() {
        onCooldown = true;
        throwArm.setImage(throwActive);

        Misc.mousePosition().ifPresent(mousePosition -> {
            Vector2 bulletMovement = towards(mousePosition).scale(2);
            Bullet bullet = new Bullet(bulletMovement, false);
            getWorld().addObject(bullet, getX(), getY());

            shootTimer.reset();
        });
    }
}
