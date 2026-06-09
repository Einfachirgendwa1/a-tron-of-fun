import greenfoot.GreenfootImage;

/**
 * Speichert, ob horizontal bzw. vertikal gespiegelt wurde.
 *
 * @author Faris
 */
public class MirrorFlags {
    private boolean horizontal = false;
    private boolean vertical = false;

    public void toggleHorizontal() {
        horizontal = !horizontal;
    }

    public void toggleVertical() {
        vertical = !vertical;
    }

    public void apply(GreenfootImage image) {
        if (horizontal) image.mirrorHorizontally();
        if (vertical) image.mirrorVertically();
    }
}
