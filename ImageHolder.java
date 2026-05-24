import greenfoot.GreenfootImage;

public class ImageHolder extends Collider {
    private MirrorFlags mirrorFlags;
    private boolean isCollider = true;
    private int offsetX;
    private int offsetY;
    private Vector2 basePosition;

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

    public ImageHolder(String image, int offsetX, int offsetY) {
        this(image, offsetX, offsetY, false);
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
    public void mirrorHorizontally() {
        super.mirrorHorizontally();

        offsetX *= -1;
        updatePosition(basePosition);
        mirrorFlags.toggleHorizontal();
    }

    @Override
    public void mirrorVertically() {
        super.mirrorVertically();

        offsetY *= -1;
        updatePosition(basePosition);
        mirrorFlags.toggleVertical();
    }

    public int getOffsetX() {
        return offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }

    public void updatePosition(IGetVector2 newPosition) {
        int x = (int) newPosition.position().x();
        int y = (int) newPosition.position().y();

        basePosition = newPosition.position();

        setLocation(offsetX + x, offsetY + y);
    }
}
