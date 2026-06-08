import greenfoot.Greenfoot;

import java.util.Map;

public abstract class Player extends BaseActor {

    @Override
    public void act() {
        super.act();

        Map<String, Runnable> keymap = Map.of(
            "w",
            this::moveUp,
            "a",
            this::moveLeft,
            "s",
            this::moveDown,
            "d",
            this::moveRight
        );

        for (Map.Entry<String, Runnable> entry : keymap.entrySet()) {
            if (Greenfoot.isKeyDown(entry.getKey())) {
                entry.getValue().run();
            }
        }
    }

    @Override
    protected void deathHandler() {
        Misc.getCurrentWorld().lost();
    }
}
