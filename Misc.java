import greenfoot.*;
import greenfoot.Color;
import greenfoot.MouseInfo;

import java.awt.*;
import java.awt.Font;
import java.awt.font.GlyphVector;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Optional;

public class Misc {
    public static final int worldWidth = 600;
    public static final int worldHeight = 400;
    public static final GreenfootImage blank = new GreenfootImage("images/blank.png");
    public static final int wallThickness = 3;
    private static final String fontName = "DepartureMonoNerdFont-Regular.otf";
    private static final boolean debug = false;
    private static final Font awtFont;
    private static final Constructor<greenfoot.Font> fontConstructor;
    private static final Method getGraphics;
    private static GameSelection gameSelection;
    private static BaseWorld currentWorld = null;

    static {
        try {
            awtFont = Font.createFont(Font.TRUETYPE_FONT, new File(fontName));

            fontConstructor = greenfoot.Font.class.getDeclaredConstructor(Font.class);
            fontConstructor.setAccessible(true);

            getGraphics = GreenfootImage.class.getDeclaredMethod("getGraphics");
            getGraphics.setAccessible(true);
        } catch (FontFormatException | IOException fontException) {
            throw new RuntimeException("Error: could not load font " + fontName, fontException);
        } catch (Exception reflectionException) {
            throw new RuntimeException("Reflection failure", reflectionException);
        }
    }

    private static greenfoot.Font fromAwtFont(Font awtFont) {
        try {
            return fontConstructor.newInstance(awtFont);
        } catch (Exception e) {
            System.err.println("Error: could not load font " + fontName + ": " + e.getMessage());
            return null;
        }
    }

    private static Graphics2D getGraphics2D(GreenfootImage image) {
        try {
            return (Graphics2D) getGraphics.invoke(image);
        } catch (Exception e) {
            throw new RuntimeException("Failed to acquire Graphics2D via reflection!", e);
        }
    }

    private static Vector2 textDimensions(Font font, String text) {
        GreenfootImage surface = currentWorld.getBackground();
        GlyphVector vector = font.createGlyphVector(getGraphics2D(surface).getFontRenderContext(), text);
        Rectangle2D bounds = vector.getVisualBounds();

        return new Vector2((float) bounds.getWidth(), (float) bounds.getHeight());
    }

    public static void drawText(String text, IGetVector2 vector, float fontSize, Color color) {
        Font scaledAwt = awtFont.deriveFont(fontSize);

        currentWorld.getBackground().setFont(fromAwtFont(scaledAwt));
        currentWorld.getBackground().setColor(color);

        Vector2 topLeft = vector.position().minus(textDimensions(scaledAwt, text).scale(.5f));
        Point position = new Point(topLeft);
        currentWorld.getBackground().drawString(text, position.x(), position.y());
    }

    public static void setWorld(BaseWorld world) {
        currentWorld = world;

        if (world instanceof GameSelection) {
            gameSelection = (GameSelection) world;
        }
    }

    public static void loadWorld(BaseWorld world) {
        Greenfoot.setWorld(world);
        setWorld(world);
    }

    public static void exitMinigame() {
        if (gameSelection == null) {
            gameSelection = new GameSelection();
            Misc.loadWorld(gameSelection);
        } else {
            gameSelection.exitMinigame();
        }
    }

    public static void enterMinigame(int id) {
        gameSelection.enterMinigame(id);
    }

    public static BaseWorld getCurrentWorld() {
        return currentWorld;
    }

    public static Optional<Point> mousePosition() {
        MouseInfo mouseInfo = Greenfoot.getMouseInfo();
        if (mouseInfo == null) return Optional.empty();
        return Optional.of(new Point(mouseInfo.getX(), mouseInfo.getY()));
    }

    public static void debugPrint(String message) {
        if (debug) System.out.println(message);
    }

    public static <T extends Actor> T addObject(T actor, int x, int y) {
        currentWorld.addObject(actor, x, y);
        return actor;
    }

    public static <T extends Actor> T addObject(T actor, IGetVector2 vector) {
        Point position = new Point(vector.position());
        return addObject(actor, position.x(), position.y());
    }

    public static Optional<Double> angleToMouse(IGetVector2 start) {
        return mousePosition().map(point -> start.position().angle(point));
    }
}
