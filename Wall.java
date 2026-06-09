import greenfoot.GreenfootImage;

/**
 * Eine Wand in TankLabyrinth und LightCycles.
 */
public class Wall extends BaseActor {
    private final Line2D line;
    private ImageHolder wallCollider;

    public Wall(Line2D line) {
        this.line = line;

        int width = Math.abs(line.start().x() - line.end().x());
        int height = Math.abs(line.start().y() - line.end().y());

        width = width != 0 ? width : Misc.wallThickness;
        height = height != 0 ? height : Misc.wallThickness;

        GreenfootImage image = new GreenfootImage(Misc.blank);
        image.scale(width, height);

        wallCollider.setImage(image);
    }

    public Vector2D getPosition() {
        Vector2D startToMiddle = line.end().vec().minus(line.start().vec()).multiply(.5f);
        return line.start().vec().plus(startToMiddle);
    }

    @Override
    protected ImageHolder[] images() {
        wallCollider = new ImageHolder(Misc.blank);
        return new ImageHolder[]{wallCollider};
    }

    @Override
    public void takeDamage(int amount) {}

    @Override
    public String toString() {
        return "Wall{" + "line=" + line + ", wallCollider=" + wallCollider + '}';
    }
}
