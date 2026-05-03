public record Vector2(float x, float y) implements IGetVector2 {
    public static final Vector2 ZERO = new Vector2(0, 0);
    public static final Vector2 MIDDLE = new Vector2(Misc.worldWidth / 2f, Misc.worldHeight / 2f);
    public static final Vector2 UP = new Vector2(0, -1);
    public static final Vector2 DOWN = new Vector2(0, 1);
    public static final Vector2 LEFT = new Vector2(-1, 0);
    public static final Vector2 RIGHT = new Vector2(1, 0);

    @Override
    public Vector2 position() {
        return this;
    }

    public boolean isZero() {
        return x == 0 && y == 0;
    }

    public float magnitude() {
        return (float) Math.sqrt(x * x + y * y);
    }

    public Vector2 normalize() {
        float mag = magnitude();
        return mag != 0 ? new Vector2(x / mag, y / mag) : Vector2.ZERO;
    }

    public Vector2 plus(IGetVector2 other) {
        return new Vector2(this.x + other.position().x, this.y + other.position().y);
    }

    public Vector2 minus(IGetVector2 other) {
        return new Vector2(this.x - other.position().x, this.y - other.position().y);
    }

    public Vector2 scale(float d) {
        return new Vector2(this.x * d, this.y * d);
    }
}
