import greenfoot.GreenfootImage;

public class ImageHolder extends Collider {
    protected int offsetX;
    protected int offsetY;

    protected Vector2 basePosition;
    protected int mirror = 0;

    public ImageHolder(GreenfootImage image, int offsetX, int offsetY) {
        super();

        this.offsetX = offsetX;
        this.offsetY = offsetY;

        setImage(image);
    }

    public ImageHolder(String image, int offsetX, int offsetY) {
        this(new GreenfootImage("images/" + image), offsetX, offsetY);
    }

    @Override
    public void setImage(GreenfootImage image) {
        Misc.applyRotationFlags(getImage(), mirror);
        Misc.applyRotationFlags(image, mirror);
        super.setImage(image);
    }

    public int getOffsetX() {
        return offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }

    public void rotate(int degrees) { // Damit man Bilder rotieren kann
        getImage().rotate(degrees);
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
        mirror ^= Misc.HORIZONTAL;

        offsetX *= -1;
        updatePosition(basePosition);
    }

    @Override
    public void mirrorVertically() {
        super.mirrorVertically();
        mirror ^= Misc.VERTICAL;

        offsetY *= -1;
        updatePosition(basePosition);
    }
}
