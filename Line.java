import greenfoot.GreenfootImage;

public record Line(Point2D start, Point2D end) {
    public void draw(GreenfootImage image) {
        image.drawLine(start.x(), start.y(), end.x(), end.y());
    }

    public Point2D snap(Position2D position) {
        Vector2D vec = position.vec();
        Point2D point = vec.point();

        if (start.x() != end.x() && start.y() != end.y()) {
            throw new RuntimeException("Kann nur Abstand von horizontalem/vertikalem Liniensegment bilden.");
        }

        if (start.x() == end.x()) {
            int lowerBound = Math.min(start.y(), end.y());
            int upperBound = Math.max(start.y(), end.y());
            if (lowerBound <= point.y() && point.y() <= upperBound) {
                return new Point2D(start.x(), point.y());
            } else {
                float distToStart = vec.minus(start).magnitude();
                float distToEnd = vec.minus(end).magnitude();
                return distToStart < distToEnd ? start : end;
            }
        } else {
            int lowerBound = Math.min(start.x(), end.x());
            int upperBound = Math.max(start.x(), end.x());
            if (lowerBound <= point.x() && point.x() <= upperBound) {
                return new Point2D(point.x(), start.y());
            } else {
                float distToStart = vec.minus(start).magnitude();
                float distToEnd = vec.minus(end).magnitude();
                return distToStart < distToEnd ? start : end;
            }
        }
    }
}
