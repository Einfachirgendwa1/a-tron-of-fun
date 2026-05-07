import greenfoot.Actor;

public class Collider extends Actor implements IGetVector2 {
    public boolean intersects(Actor other) {
        return super.intersects(other);
    }

    public void mirrorHorizontally() {
        getImage().mirrorHorizontally();
    }

    public void mirrorVertically() {
        getImage().mirrorVertically();
    }

    public Vector2 position() {
        return new Vector2(getX(), getY());
    }
}
