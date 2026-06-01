import greenfoot.GreenfootImage;

import java.util.function.Supplier;

public class MiniGame extends BaseActor {
    private final Supplier<BaseWorld> worldSupplier;
    private ImageHolder area;

    public MiniGame(Supplier<BaseWorld> baseWorldSupplier, int color) {
        this.worldSupplier = baseWorldSupplier;
        GreenfootImage image = new GreenfootImage("images/minigame" + color + ".png");

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
