import greenfoot.Actor;

public record Vector2(float x, float y) {
    public static final Vector2 ZERO = new Vector2(0, 0);
    public static final Vector2 ORIGIN = new Vector2(300, 200);

    public Vector2(Actor actor) {
        this(actor.getX(), actor.getY());
    }

    public static Vector2 towards(Vector2 end, Vector2 start) {
        return end.minus(start).normalize();
    }

    public boolean isZero() {
        return x == 0 && y == 0;
    }

    public Vector2 move(Actor actor) {
        Vector2 newPos = new Vector2(actor).plus(this);
        actor.setLocation(Math.round(newPos.x), Math.round(newPos.y));
        return this;
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

    public float getX() {
        return x;
    }

    public Vector2 plus(Vector2 other) {
        return new Vector2(this.x + other.x, this.y + other.y);
    }

    public Vector2 minus(Vector2 other) {
        return new Vector2(this.x - other.x, this.y - other.y);
    }

    public Vector2 scale(float d) {
        return new Vector2(this.x * d, this.y * d);
    }
}
