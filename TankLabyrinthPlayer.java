import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;

import java.util.List;
import java.util.Map;

public class TankLabyrinthPlayer extends Player {
    private Point2D stopConsumed = Vector2D.ZERO.point();
    private Point2D currentDirection = Point2D.RIGHT;
    private Point2D bufferedDirection = Vector2D.ZERO.point();
    private Point2D lastKnownPoint = null;
    private Point2D committedDirection = null;
    private boolean onCooldown = false;

    private final StateThread shootTimer = new StateThread() {{
        wait(15).execute(() -> onCooldown = false);
    }};
    private ImageHolder tankBody;

    {
        speed = 1;
        health = 100;
    }

    public Point2D getLastGoodPoint() {
        return lastKnownPoint;
    }

    @Override
    protected ImageHolder[] images() {
        tankBody = new ImageHolder(new GreenfootImage("tank_player.png"));
        return new ImageHolder[]{tankBody};
    }

    @Override
    protected void moveUp() {
        buffer(Point2D.UP);
    }

    @Override
    protected void moveDown() {
        buffer(Point2D.DOWN);
    }

    @Override
    protected void moveLeft() {
        buffer(Point2D.LEFT);
    }

    @Override
    protected void moveRight() {
        buffer(Point2D.RIGHT);
    }

    private void buffer(Point2D direction) {
        boolean stop = direction.multiply(-1).point().equals(currentDirection);

        if (stop && !direction.equals(stopConsumed)) {
            currentDirection = Vector2D.ZERO.point();
            bufferedDirection = Vector2D.ZERO.point();
            stopConsumed = direction;
        } else if (!direction.equals(stopConsumed)) {
            stopConsumed = Vector2D.ZERO.point();
            bufferedDirection = direction;
        }
    }

    private boolean sameDirection(Position2D a, Point2D b) {
        return (a.point().equals(b.point()) || a.plus(b).zero()) && a.nonZero();
    }

    public void act() {
        super.act();

        shootTimer.update();

        if (TankLabyrinthWorld.nodes.stream().anyMatch(point()::equals)) {
            lastKnownPoint = point();

            List<Point2D> allowedDirections = TankLabyrinthWorld.paths.get(point())
                .stream()
                .map(target -> towards(target).point())
                .toList();

            if (allowedDirections.contains(bufferedDirection)) {
                setCurrentDirection(bufferedDirection);
            } else {
                setCurrentDirection(Vector2D.ZERO);
            }

            committedDirection = currentDirection;
        }

        if (sameDirection(bufferedDirection, committedDirection) && currentDirection.zero()) {
            currentDirection = bufferedDirection;
        }

        if (Greenfoot.mouseClicked(null) && !onCooldown) {
            Misc.mousePosition().ifPresent(mousePosition -> {
                Vector2D bulletMovement = towards(mousePosition).multiply(4);
                Misc.addObject(new Bullet(bulletMovement, Enemy.class), this);

                onCooldown = true;
                shootTimer.reset();
            });
        }

        Map<Point2D, String> map = Map.of(
            Vector2D.LEFT.point(),
            "a",
            Vector2D.UP.point(),
            "w",
            Vector2D.DOWN.point(),
            "s",
            Vector2D.RIGHT.point(),
            "d"
        );

        if (stopConsumed.nonZero() && !Greenfoot.isKeyDown(map.get(stopConsumed))) {
            stopConsumed = Vector2D.ZERO.point();
        }

        moveWithSpeed(currentDirection, false);
    }

    public void setCurrentDirection(Position2D newDirection) {
        this.currentDirection = newDirection.point();

        if (newDirection.nonZero()) {
            tankBody.setRotation((int) (Vector2D.ZERO.angle(newDirection) * 360));
        }
    }
}
