import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Predicate;

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
