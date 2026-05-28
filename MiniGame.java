import greenfoot.Color;
import greenfoot.GreenfootImage;

import java.util.function.Supplier;

public class MiniGame extends BaseActor {
    private final Supplier<BaseWorld> worldSupplier;
    private ImageHolder area;

    public MiniGame(Supplier<BaseWorld> baseWorldSupplier, int color) {
        this.worldSupplier = baseWorldSupplier;
        GreenfootImage image = new GreenfootImage("images/minigame" + color + ".png");

        int width = color % 2 == 1 ? 200 : (int) (200 * (2f / 3));
        int height = color % 2 == 0 ? 200 : (int) (200 * (2f / 3));
        image.scale(width, height);
        image.setColor(Color.RED);
        image.drawRect(0, 0, width - 1, height - 1);
        area.setImage(image);
    }

    @Override
    protected ImageHolder[] images() {
        area = new ImageHolder(Misc.blank);
        return new ImageHolder[]{area};
    }

    public void loadWorld() {
        getWorld().removeObject(this);
        Misc.loadWorld(worldSupplier.get());
    }
}
