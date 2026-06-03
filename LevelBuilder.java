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
    private final Path output = LevelLoader.path(world, "path");
    private final List<Line> writtenLines;

    private int deleteCooldown = 0;

    public LevelBuilder() {
        try {
            world.getField("levelBuilder").set(null, true);
            BaseWorld instance = world.getConstructor().newInstance();
            setBackground(instance.getBackground());
            writtenLines = LevelLoader.getLevelData(output);
        } catch (Exception e) {
            throw new RuntimeException("Failed to reflect on " + world.getName(), e);
        }

        ArrayList<ImageHolder> staticClones = new ArrayList<>();
        for (Actor actor : getObjects(Actor.class)) {
            GreenfootImage image = actor.getImage();
            if (actor instanceof FrameSurface) continue;

            if (!(actor instanceof BaseActor)) {
                ImageHolder clone = new ImageHolder(image, actor.getX(), actor.getY());
                staticClones.add(clone);
            }

            removeObjectUnchecked(actor);
        }

        for (ImageHolder imageHolder : staticClones) {
            addObject(imageHolder, 0, 0);
            imageHolder.updatePosition(Vector2D.ZERO);
        }
    }

    private static void lines(ArrayList<Point2D> points, BiConsumer<Point2D, Point2D> consumer) {
        for (int n = 0; n < points.size() - 1; n++) {
            consumer.accept(points.get(n), points.get(n + 1));
        }
    }

    private Optional<Line> snapLine(Point2D mouse) {
        List<Line> snappingCandidates = writtenLines.stream().sorted((a, b) -> {
            float distToA = a.snap(mouse).vec().minus(mouse).magnitude();
            float distToB = b.snap(mouse).vec().minus(mouse).magnitude();

            return Math.round(distToA - distToB);
        }).toList();

        return snappingCandidates.isEmpty() ? Optional.empty() : Optional.of(snappingCandidates.getFirst());
    }

    @Override
    public void act() {
        super.act();

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

        getFrame().setColor(Color.RED);
        writtenLines.forEach(line -> line.draw(getFrame()));

        Optional<Point2D> mousePoint = mousePoint();
        mousePoint.ifPresent(e -> {
            Point2D b = e.vec().minus(new Vector2D(5, 5)).point();
            getFrame().setColor(Color.GREEN);
            getFrame().drawRect(b.x(), b.y(), 10, 10);
            currentPoints.add(e);
        });

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
        getFrame().drawLine(a.x(), a.y(), b.x(), b.y());
    }

    private Optional<Point2D> mousePoint() {
        final int snapThreshold = 25;

        Optional<Point2D> mousePos = Misc.mousePosition();
        if (mousePos.isEmpty()) return Optional.empty();
        Point2D mouse = mousePos.get();

        List<Point2D> snappingCandidates = writtenLines.stream().map(line -> line.snap(mouse)).sorted((a, b) -> {
            float distToA = a.vec().minus(mouse).magnitude();
            float distToB = b.vec().minus(mouse).magnitude();

            return Math.round(distToA - distToB);
        }).toList();

        if (!snappingCandidates.isEmpty()) {
            Vector2D potentialSnap = snappingCandidates.getFirst().vec();

            if (potentialSnap.minus(mouse).magnitude() < snapThreshold) {
                return Optional.of(potentialSnap.point());
            }
        }

        if (currentPoints.isEmpty()) return mousePos;

        Point2D last = currentPoints.getLast();
        double angle = last.vec().angle(mouse);

        if (angle < 0.125 || angle > 0.875 || (angle > 0.375 && angle < 0.525)) {
            return Optional.of(new Point2D(mouse.x(), last.y()));
        } else {
            return Optional.of(new Point2D(last.x(), mouse.y()));
        }
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

