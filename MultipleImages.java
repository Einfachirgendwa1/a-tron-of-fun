import greenfoot.Actor;
import greenfoot.World;

import java.util.ArrayList;

public class MultipleImages {
    private final ArrayList<ImageHolder> images = new ArrayList<>();

    public MultipleImages(World world, ImageHolder[] defaultImages) {
        for (ImageHolder image : defaultImages) {
            world.addObject(image, image.getOffsetX(), image.getOffsetY());
            images.add(image);
        }
    }

    private static <T> T suppressIllegalState(Producer<T> producer, T fallback) {
        try {
            return producer.apply();
        } catch (IllegalStateException e) {
            return fallback;
        }
    }

    public void updateImages(int x, int y) {
        for (ImageHolder image : images) {
            image.setLocation(image.getOffsetX() + x, image.getOffsetY() + y);
        }
    }

    public void updateImagesActor(Actor actor) {
        int x = suppressIllegalState(actor::getX, 300);
        int y = suppressIllegalState(actor::getY, 200);

        updateImages(x, y);
    }

    public boolean intersects(Actor other) {
        return images.stream().anyMatch(image -> {
            return image.intersects(other);
        });
    }

    private interface Producer<T> {
        T apply();
    }
}
