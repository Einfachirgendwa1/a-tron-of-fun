import greenfoot.GreenfootImage;

public class ImageHolder extends Collider {
    private MirrorFlags mirrorFlags;

    private int offsetX;
    private int offsetY;
    private Vector2 basePosition;

    public ImageHolder(GreenfootImage image, int offsetX, int offsetY) {
        this.offsetX = offsetX;
        this.offsetY = offsetY;

        setImage(image);
    }

    public ImageHolder(String image, int offsetX, int offsetY) {
        this(new GreenfootImage("images/" + image), offsetX, offsetY);
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
}
