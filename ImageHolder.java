import greenfoot.Actor;
import greenfoot.GreenfootImage;

public class ImageHolder extends Actor implements Position2D {
    private MirrorFlags mirrorFlags;
    private boolean isCollider = true;
    private int offsetX;
    private int offsetY;
    private Point2D basePosition;

    public ImageHolder(GreenfootImage image, int offsetX, int offsetY, boolean isCollider) {
        this(image, offsetX, offsetY);
        this.isCollider = isCollider;
    }

    public ImageHolder(GreenfootImage image, int offsetX, int offsetY) {
        this.offsetX = offsetX;
        this.offsetY = offsetY;

        setImage(image);
    }

    public ImageHolder(GreenfootImage image) {
        this(image, 0, 0);
    }

    public ImageHolder(String image, int offsetX, int offsetY, boolean collider) {
        this(new GreenfootImage("images/" + image), offsetX, offsetY, collider);
    }

    public boolean isCollider() {
        return isCollider;
    }

    @Override
    public void setImage(GreenfootImage image) {
        if (mirrorFlags == null) {
            mirrorFlags = new MirrorFlags();
        }

        GreenfootImage newImage = new GreenfootImage(image);
        mirrorFlags.apply(newImage);
        super.setImage(newImage);
    }

    @Override
    public boolean intersects(Actor other) {
        if (!isCollider) return false;
        if (other instanceof ImageHolder && !((ImageHolder) other).isCollider) return false;

        return super.intersects(other);
    }

    public void mirrorHorizontally() {
        getImage().mirrorHorizontally();

        offsetX *= -1;
        updatePosition(basePosition);
        mirrorFlags.toggleHorizontal();
    }

    public void mirrorVertically() {
        getImage().mirrorVertically();

        offsetY *= -1;
        updatePosition(basePosition);
        mirrorFlags.toggleVertical();
    }

    @Override
    public Vector2D position() {
        return new Vector2D(getX(), getY());
    }

    public int getOffsetX() {
        return offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }

    public void updatePosition(Position2D newPosition) {
        basePosition = new Point2D(newPosition);
        setLocation(offsetX + basePosition.x(), offsetY + basePosition.y());
    }
}
