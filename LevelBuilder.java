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
    private final ArrayList<Point2D> currentPoints = new ArrayList<>();
    private final Class<? extends BaseWorld> world = TankLabyrinthWorld.class;
    private final Path output = LevelLoader.path(world);
    private final List<Line> writtenLines;
    private final GreenfootImage background;

    private int deleteCooldown = 0;

    public LevelBuilder() {
        try {
            world.getField("levelBuilder").set(null, true);
            BaseWorld instance = world.getConstructor().newInstance();
            background = instance.getBackground();
            writtenLines = new ArrayList<>(instance.getLines());
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
            imageHolder.updatePosition(new Vector2D(0, 0));
        }
    }

    private static void lines(ArrayList<Point2D> points, BiConsumer<Point2D, Point2D> consumer) {
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

        GreenfootImage scratchBackground = new GreenfootImage(background);
        scratchBackground.setColor(Color.RED);
        setBackground(scratchBackground);

        writtenLines.forEach(line -> line.draw(getBackground()));

        Optional<Point2D> mousePoint = mousePoint();
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

    private void drawLine(Point2D a, Point2D b) {
        getBackground().drawLine(a.x(), a.y(), b.x(), b.y());
    }

    private Optional<Point2D> mousePoint() {
        if (currentPoints.isEmpty()) return Misc.mousePosition();

        Point2D last = currentPoints.getLast();
        Optional<Point2D> mousePos = Misc.mousePosition();
        return mousePos.map(p -> {
            double angle = last.position().angle(p);

            if (angle < 0.125 || angle > 0.875 || (angle > 0.375 && angle < 0.525)) {
                return new Point2D(p.x(), last.y());
            } else {
                return new Point2D(last.x(), p.y());
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

