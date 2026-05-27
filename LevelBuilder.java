import greenfoot.Actor;
import greenfoot.Color;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;

@SuppressWarnings("unused")
public class LevelBuilder extends BaseWorld {
    private final ArrayList<Point> currentPoints = new ArrayList<>();
    private final Class<? extends BaseWorld> world = GridBugsWorld.class;
    private final Path output = LevelLoader.path(world);
    private final List<Line> writtenLines;

    private int deleteCooldown = 0;

    public LevelBuilder() {
        try {
            world.getField("levelBuilder").set(null, true);
            writtenLines = world.getConstructor().newInstance().getLines();
        } catch (Exception e) {
            throw new RuntimeException("Failed to reflect on " + world.getName(), e);
        }

        ArrayList<ImageHolder> staticClones = new ArrayList<>();
        for (Actor actor : getObjects(Actor.class)) {
            GreenfootImage image = actor.getImage();
            if (image == Misc.blank) continue;

            ImageHolder clone = new ImageHolder(image, actor.getX(), actor.getY());
            staticClones.add(clone);

            removeObjectUnchecked(actor);
        }

        for (ImageHolder imageHolder : staticClones) {
            addObject(imageHolder, 0, 0);
            imageHolder.updatePosition(new Vector2(0, 0));
        }
    }

    private static void lines(ArrayList<Point> points, BiConsumer<Point, Point> consumer) {
        for (int n = 0; n < points.size() - 1; n++) {
            consumer.accept(points.get(n), points.get(n + 1));
        }
    }

    @Override
    public void act() {
        if (!currentPoints.isEmpty()) {
            if (Greenfoot.isKeyDown("d") && deleteCooldown <= 0) {
                currentPoints.removeLast();
                deleteCooldown = 15;
            }

            if (Greenfoot.isKeyDown("r")) {
                currentPoints.clear();
            }
        }

        deleteCooldown--;

        GreenfootImage background = new GreenfootImage(Misc.blank);
        setBackground(background);
        getBackground().setColor(Color.RED);

        writtenLines.forEach(line -> line.draw(getBackground()));

        Optional<Point> mousePoint = mousePoint();
        mousePoint.ifPresent(currentPoints::add);

        lines(currentPoints, this::drawLine);

        //noinspection unused
        mousePoint.ifPresent(p -> {
            if (!Greenfoot.mouseClicked(null)) {
                currentPoints.removeLast();
            }

            if (Greenfoot.isKeyDown("s")) {
                writePoints();

                lines(currentPoints, (a, b) -> writtenLines.add(new Line(a, b)));
                currentPoints.clear();
            }
        });
    }

    private void drawLine(Point a, Point b) {
        getBackground().drawLine(a.x(), a.y(), b.x(), b.y());
    }

    private Optional<Point> mousePoint() {
        if (currentPoints.isEmpty()) return Misc.mousePosition();

        Point last = currentPoints.getLast();
        Optional<Point> mousePos = Misc.mousePosition();
        return mousePos.map(p -> {
            double angle = last.position().angle(p);

            if (angle < 0.125 || angle > 0.875 || (angle > 0.375 && angle < 0.525)) {
                return new Point(p.x(), last.y());
            } else {
                return new Point(last.x(), p.y());
            }
        });
    }

    public void writePoints() {
        if (currentPoints.isEmpty()) return;

        StringBuilder output = new StringBuilder();
        lines(
            currentPoints,
            (a, b) -> output.append(a.x())
                .append(",")
                .append(a.y())
                .append(",")
                .append(b.x())
                .append(",")
                .append(b.y())
                .append("\n")
        );

        try {
            Files.writeString(this.output, output.toString(), StandardOpenOption.APPEND, StandardOpenOption.CREATE);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write output file", e);
        }
    }
}

