import greenfoot.GreenfootImage;

public record Line2D(Point2D start, Point2D end) {
    public void draw(GreenfootImage image) {
        image.drawLine(start.x(), start.y(), end.x(), end.y());
    }

    public Point2D snap(Position2D position) {
        Vector2D vec = position.vec();
        Point2D point = vec.point();

        if (horizontal()) {
            if (inside(point.y())) {
                return new Point2D(start.x(), point.y());
            } else {
                float distToStart = vec.minus(start).magnitude();
                float distToEnd = vec.minus(end).magnitude();
                return distToStart < distToEnd ? start : end;
            }
        } else if (vertical()) {
            if (inside(point.x())) {
                return new Point2D(point.x(), start.y());
            } else {
                float distToStart = vec.minus(start).magnitude();
                float distToEnd = vec.minus(end).magnitude();
                return distToStart < distToEnd ? start : end;
            }
        } else {
            throw new RuntimeException("Kann nur Abstand von horizontalem/vertikalem Liniensegment bilden.");
        }
    }

    public boolean horizontal() {
        return start.y() == end.y();
    }

    public boolean vertical() {
        return start.x() == end.x();
    }

    public int lowerBound() {
        if (horizontal()) {
            return Math.min(start.x(), end.x());
        } else {
            return Math.min(start.y(), end.y());
        }
    }

    public int upperBound() {
        if (horizontal()) {
            return Math.max(start.x(), end.x());
        } else {
            return Math.max(start.y(), end.y());
        }
    }

    public boolean inside(int coord) {
        return lowerBound() <= coord && coord <= upperBound();
    }
}
