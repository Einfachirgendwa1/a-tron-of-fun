import greenfoot.Actor;
import greenfoot.GreenfootImage;

public class ImageHolder extends Actor {
    private final GreenfootImage image;
    private final int offsetX;
    private final int offsetY;

    private ImageHolder(GreenfootImage image, int offsetX, int offsetY) {
        this.image = image;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
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

    public void act() {
        setImage(image);
    }
}
