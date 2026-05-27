import greenfoot.GreenfootImage;

import java.awt.*;
import java.awt.font.GlyphVector;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class TextRenderer {
    private static final Font awtFont;
    private static final Constructor<greenfoot.Font> fontConstructor;
    private static final Method getGraphics;
    private static final String fontName = "DepartureMonoNerdFont-Regular.otf";

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

    private final GreenfootImage surface;
    private final Font instanceAwt;
    private final String text;

    public TextRenderer(String text, GreenfootImage surface, float fontSize, greenfoot.Color color) {
        this.surface = surface;
        this.text = text;
        this.instanceAwt = awtFont.deriveFont(fontSize);

        surface.setColor(color);
    }

    public static greenfoot.Font fromAwtFont(Font awtFont) {
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

    public GreenfootImage getSurface() {
        return surface;
    }

    public void render(Point position) {
        surface.setFont(fromAwtFont(instanceAwt));
        surface.drawString(text, position.x(), position.y());
    }

    public Vector2 dimensions() {
        GlyphVector vector = instanceAwt.createGlyphVector(getGraphics2D(surface).getFontRenderContext(), text);
        Rectangle2D bounds = vector.getVisualBounds();

        return new Vector2((float) bounds.getWidth(), (float) bounds.getHeight());
    }
}
