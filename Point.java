public record Point(int x, int y) implements IGetVector2 {
    public Point(Vector2 vector) {
        this(Math.round(vector.x()), Math.round(vector.y()));
    }

    @Override
    public Vector2 position() {
        return new Vector2(x, y);
    }
}
