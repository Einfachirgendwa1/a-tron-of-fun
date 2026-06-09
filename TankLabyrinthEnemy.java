import greenfoot.Color;
import greenfoot.GreenfootImage;

import java.util.List;

/**
 * Der gegnerische Panzer im {@link TankLabyrinthWorld} Minispiel.
 *
 * @author Faris
 */
public class TankLabyrinthEnemy extends Enemy {
    private final TankLabyrinthPlayer player;
    public PathFinder pathFinder;
    private Point2D target = null;
    private List<Point2D> path;
    private ImageHolder body;
    private final Animator animator = new Animator(this::spawn);

    {
        speed = 1;
        health = 60;
    }

    public TankLabyrinthEnemy(PathFinder pathFinder, TankLabyrinthPlayer player) {
        this.pathFinder = pathFinder;
        this.player = player;
    }

    @Override
    protected ImageHolder[] images() {
        body = new ImageHolder("tank_enemy.png", 0, 0, true);

        return new ImageHolder[]{body};
    }

    @Override
    protected void onPlayerContact() {
        Misc.getCurrentWorld().lost();
    }

    @Override
    public void act() {
        animator.update();
        super.act();
    }

    private void spawn(Animator animator) {
        animator.addThread().wait(20).execute(() -> animator.switchState(this::hunt));
    }

    private void hunt(Animator animator) {
        animator.addThread().execute(() -> {
            Vector2D bulletMovement = towards(player).multiply(4);
            Misc.addObject(new Bullet(bulletMovement, Player.class), this);
        }).wait(50).repeat();

        animator.addThread().execute(() -> {
            if ((target == null || target.equals(point())) && player.getLastGoodPoint() != null) {
                path = pathFind(point(), player.getLastGoodPoint());
                if (path.size() >= 2) {
                    target = path.get(path.size() - 2);
                }
            }

            if (target != null) {
                body.setRotation((int) (Vector2D.ZERO.point().angle(towards(target)) * 360));
                moveWithSpeed(towards(target), false);
            }
        }).wait(1).repeat();

        animator.addThread().repeat(() -> {
            GreenfootImage frame = Misc.getCurrentWorld().getFrame();
            frame.setColor(Color.BLUE);
            LevelBuilder.lines(path, (a, b) -> new Line2D(a, b).draw(frame));
        });
    }

    public List<Point2D> pathFind(Position2D start, Position2D end) {
        return pathFinder.pathFind(start, end, TankLabyrinthWorld.paths);
    }
}
