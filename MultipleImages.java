import greenfoot.Actor;

import java.util.ArrayList;
import java.util.function.Supplier;

public class MultipleImages {
    private static int multiCreatorX;
    private static int multiCreatorY;

    private final ArrayList<ImageHolder> images = new ArrayList<>();

    public MultipleImages(ImageHolder[] defaultImages) {
        for (ImageHolder image : defaultImages) {
            Misc.getCurrentWorld().addObject(
                    image,
                    image.getOffsetX() + multiCreatorX,
                    image.getOffsetY() + multiCreatorY
            );

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

    public static <T extends Actor> T createActor(Supplier<T> supplier) {
        return createActor(supplier, Misc.worldWidth / 2, Misc.worldHeight / 2);
    }

    public static <T extends Actor> T createActor(Supplier<T> supplier, int x, int y) {
        multiCreatorX = x;
        multiCreatorY = y;

        T actor = supplier.get();
        Misc.getCurrentWorld().addObject(actor, x, y);

        multiCreatorX = 0;
        multiCreatorY = 0;

        return actor;
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
        return images.stream().anyMatch(image -> image.intersects(other));
    }

    private interface Producer<T> {
        T apply();
    }
}
