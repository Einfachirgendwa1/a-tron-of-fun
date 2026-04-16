import greenfoot.Actor;
import greenfoot.World;

import java.util.ArrayList;

public abstract class MultipleImages extends Actor {
    private final ArrayList<ImageHolder> imageHolders = new ArrayList<>();

    public MultipleImages(World world) {
        for (ImageHolder imageHolder : defaultImageHolders()) {
            world.addObject(imageHolder, imageHolderX(imageHolder), imageHolderY(imageHolder));
            imageHolders.add(imageHolder);
        }
    }

    protected abstract ImageHolder[] defaultImageHolders();

    private int getOrDefault(GetInt getInt, int defaultValue) {
        try {
            return getInt.get();
        } catch (IllegalStateException e) {
            return defaultValue;
        }
    }

    private int imageHolderX(ImageHolder imageHolder) {
        return imageHolder.getOffsetX() + getOrDefault(this::getX, 300);
    }

    private int imageHolderY(ImageHolder imageHolder) {
        return imageHolder.getOffsetY() + getOrDefault(this::getY, 200);
    }

    public void updateImages() {
        for (ImageHolder imageHolder : imageHolders) {
            imageHolder.setLocation(imageHolderX(imageHolder), imageHolderY(imageHolder));
        }
    }

    interface GetInt {
        int get();
    }
}
