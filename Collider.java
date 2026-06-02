import greenfoot.Actor;
import greenfoot.GreenfootImage;

public class Collider extends Actor implements Position2D {
    @Override
    public void setImage(GreenfootImage image) {
        super.setImage(new GreenfootImage(image));
    }

    public boolean intersects(Actor other) {
        return super.intersects(other);
    }

    public void mirrorHorizontally() {
        getImage().mirrorHorizontally();
    }

    public void mirrorVertically() {
        getImage().mirrorVertically();
    }

    public Vector2D vec() {
        return new Vector2D(getX(), getY());
    }
}
