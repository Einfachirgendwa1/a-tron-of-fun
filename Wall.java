import greenfoot.GreenfootImage;

public class Wall extends BaseActor {
    private final Line line;
    private ImageHolder wallCollider;

    public Wall(Line line) {
        this.line = line;

        int width = Math.abs(line.start().x() - line.end().x());
        int height = Math.abs(line.start().y() - line.end().y());

        width = width != 0 ? width : Misc.wallThickness;
        height = height != 0 ? height : Misc.wallThickness;

        GreenfootImage image = new GreenfootImage(Misc.blank);
        image.scale(width, height);

        wallCollider.setImage(image);
    }

    public Vector2 getPosition() {
        Vector2 startToMiddle = line.end().position().minus(line.start().position()).scale(.5f);
        return line.start().position().plus(startToMiddle);
    }

    @Override
    protected ImageHolder[] images() {
        wallCollider = new ImageHolder(Misc.blank);
        return new ImageHolder[]{wallCollider};
    }

    @Override
    public void takeDamage(int amount) {}

    public Line getLine() {
        return line;
    }
}
