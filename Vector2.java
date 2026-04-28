import greenfoot.Actor;

public class Vector2 {
    public static final Vector2 ZERO = new Vector2(0, 0);
    private float x;
    private float y;

    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2(Actor actor) {
        this.x = actor.getX();
        this.y = actor.getY();
    }

    public static Vector2 towards(Actor actor, Vector2 b) {
        Vector2 unorm = new Vector2(actor).minus(b);

        return unorm.normalize();
    }

    public void move(Actor actor) {
        Vector2 newPos = new Vector2(actor).plus(this);
        actor.setLocation(Math.round(newPos.x), Math.round(newPos.y));
    }

    public float magnitude() {
        return (float) Math.sqrt(x * x + y * y);
    }

    public Vector2 normalize() {
        float mag = magnitude();
        return mag != 0 ? new Vector2(x / mag, y / mag) : Vector2.ZERO;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public Vector2 plus(Vector2 other) {
        return new Vector2(this.x + other.x, this.y + other.y);
    }

    public Vector2 minus(Vector2 other) {
        return new Vector2(this.x - other.x, this.y - other.y);
    }

    public Vector2 times(float d) {
        return new Vector2(this.x * d, this.y * d);
    }
}
