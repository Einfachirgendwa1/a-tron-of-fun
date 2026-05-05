import greenfoot.GreenfootImage;

public class ImageHolder extends Collider {
    private final int offsetX;
    private final int offsetY;

    public ImageHolder(GreenfootImage image, int offsetX, int offsetY) {
        this.offsetX = offsetX;
        this.offsetY = offsetY;

        setImage(image);
    }

    public ImageHolder(String image, int offsetX, int offsetY) {
        this(new GreenfootImage("images/" + image), offsetX, offsetY);
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
}
