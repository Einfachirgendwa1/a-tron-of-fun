import greenfoot.GreenfootImage;

public record Line(Point start, Point end) {
    public void draw(GreenfootImage image) {
        image.drawLine(start.x(), start.y(), end.x(), end.y());
    }
}
