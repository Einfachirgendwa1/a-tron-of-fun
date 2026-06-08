import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Ein Spieler mit menschlichen Animationen.
 */
public abstract class HumanoidPlayer extends Player {
    private static final GreenfootImage legsStand = new GreenfootImage("man_stand_legs.png");
    private static final GreenfootImage bodyStand = new GreenfootImage("man_stand_body.png");
    private static final GreenfootImage legsWalk = new GreenfootImage("man_go_legs.png");
    private static final GreenfootImage bodyWalk = new GreenfootImage("man_go_body.png");
    private static final List<GreenfootImage> leftArm = new ArrayList<>();
    private static final GreenfootImage throwInactive = new GreenfootImage("man_arm_bent_down.png");
    private static final GreenfootImage throwActive = new GreenfootImage("man_arm_bent_left.png");
    private boolean horizontalFlip = false;
    private boolean verticalFlip = false;
    private boolean onCooldown = false;
    private ImageHolder body;
    private ImageHolder legs;

    private final StateThread walkingAnimation = new StateThread() {{
        final int walkDelay = 10;

        execute(() -> {
            legs.setImage(legsWalk);
            body.setImage(bodyWalk);
        }).wait(walkDelay).execute(() -> {
            legs.setImage(legsStand);
            body.setImage(bodyStand);
        }).wait(walkDelay).repeat();
    }};

    private ImageHolder pointArm;
    private ImageHolder throwArm;

    private final StateThread shootTimer = new StateThread() {{
        wait(15).execute(() -> {
            throwArm.setImage(throwInactive);
            onCooldown = false;
        });
    }};

    static {
        for (int i = 0; i < 10; i++) {
            leftArm.add(new GreenfootImage("man_arm_" + i + ".png"));
        }
    }

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

    /**
     * Bestimmt ob der Spieler sich bewegt oder nicht.
     */
    protected boolean isMoving() {
        return Stream.of("w", "a", "s", "d").anyMatch(Greenfoot::isKeyDown);
    }

    @Override
    protected ImageHolder[] images() {
        // These attribute initializations need to happen in defaultImages() and *NOT* in the constructor!
        // The Player class calls this function in its constructor and then passes all the ImageHolders we return to 
        // MultipleImages, but because Player is our superclass, its constructor actually runs earlier than ours.

        legs = new ImageHolder(legsStand, 0, 31, false);
        body = new ImageHolder(bodyStand, 0, 0, false);

        throwArm = new ImageHolder(throwInactive, 0, 6, false);
        pointArm = new ImageHolder(leftArm.get(4), -13, -11, false);

        GreenfootImage colliderImage = new GreenfootImage(Misc.blank);
        colliderImage.scale(10, 38);

        ImageHolder collider = new ImageHolder(colliderImage, 0, 9);
        return new ImageHolder[]{body, legs, pointArm, throwArm, collider};
    }

    @Override
    public void act() {
        super.act();
        shootTimer.update();

        if (isMoving()) {
            walkingAnimation.update();
        } else {
            walkingAnimation.reset();
        }

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

        if (Greenfoot.mouseClicked(null) && !onCooldown) {
            shoot();
        }
    }

    /**
     * Wird gerufen, wenn der Spieler schießen will.
     */
    protected void shoot() {
        onCooldown = true;
        throwArm.setImage(throwActive);

        Misc.mousePosition().ifPresent(mousePosition -> {
            Vector2D bulletMovement = towards(mousePosition).multiply(4);
            Misc.addObject(new Bullet(bulletMovement, Enemy.class), this);

            shootTimer.reset();
        });
    }
}
