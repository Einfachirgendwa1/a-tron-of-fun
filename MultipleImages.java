import greenfoot.Actor;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Predicate;
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

    public boolean any(Predicate<ImageHolder> predicate) {
        return images.stream().anyMatch(predicate);
    }

    public void forEach(Consumer<ImageHolder> consumer) {
        images.forEach(consumer);
    }

    public ArrayList<ImageHolder> getImages() {
        return images;
    }

    public void updateImages(IGetVector2 vector) {
        for (ImageHolder image : images) {
            image.updatePosition(vector);
        }
    }
}
