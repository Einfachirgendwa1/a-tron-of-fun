import greenfoot.Actor;
import greenfoot.World;

public abstract class Enemy extends Actor {
    private final MultipleImages multipleImages;

    public Enemy(World world) {
        multipleImages = new MultipleImages(world, defaultImages());
    }

    protected abstract ImageHolder[] defaultImages();

    public void act() {
        multipleImages.updateImagesActor(this);
    }
}