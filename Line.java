import greenfoot.GreenfootImage;

public record Line(Point2D start, Point2D end) {
    public void draw(GreenfootImage image) {
        image.drawLine(start.x(), start.y(), end.x(), end.y());
    }
}
