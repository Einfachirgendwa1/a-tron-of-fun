import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class MultipleImages {
    private final ArrayList<ImageHolder> images = new ArrayList<>();

    public MultipleImages(ImageHolder[] defaultImages) {
        for (ImageHolder image : defaultImages) {
            Misc.getCurrentWorld().addObject(image, image.getOffsetX(), image.getOffsetY());
            images.add(image);
        }
    }

    public boolean anyWithCollision(Predicate<ImageHolder> predicate) {
        return images.stream().filter(ImageHolder::isCollider).anyMatch(predicate);
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
