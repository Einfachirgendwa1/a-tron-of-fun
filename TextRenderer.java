import greenfoot.GreenfootImage;

import java.awt.*;
import java.awt.font.GlyphVector;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * Rendert Text mit anpassbarer Schriftart, Schriftgröße und Farbe.
 * Greenfoot's Unterstützung für andere Schriftarten ist eher limitiert: Man kann mittels
 * {@link greenfoot.Font#Font(String, int)} neue Fonts laden, aber nur wenn diese auf dem Computer installiert sind.
 * Für unsere Zwecke wollen wir aber nicht voraussetzen, dass unsere spezifische Schriftart bereits installiert ist,
 * weshalb wir sie gerne einfach aus einer Datei laden würden. Die library die Greenfoot zum Rendern benutzt,
 * {@link java.awt} unterstützt das sogar out of the box, über die {@link java.awt.Font#createFont(int, File)} Methode,
 * die ein {@link File} als Parameter nimmt. Jedoch benutzt Greenfoot nicht {@link java.awt.Font} direkt, sondern
 * stattdessen {@link greenfoot.Font}. {@link greenfoot.Font} ist intern nichts anderes als ein einfacher Wrapper um
 * die {@link java.awt.Font} Klasse und es gibt tatsächlich einen Konstruktor der eine {@link java.awt.Font} direkt
 * nimmt: {@link greenfoot.Font#Font(java.awt.Font)}. Das einzige Problem damit ist, dass dieser Konstruktor {@code
 * private} ist. Hier müssen wir ein bisschen Trickserei benutzen: Mithilfe von Reflection können wir den {@code
 * private} Konstruktor rufen. Im {@code static} Konstruktor dieser Klasse wird mithilfe von
 * {@link Class#getDeclaredConstructor(Class[])} der Konstruktor geholt und mit
 * {@link Method#setAccessible(boolean)} benutzbar gemacht. Dieser Konstruktor wird dann in
 * {@link #fromAwtFont(java.awt.Font)} gerufen um aus einer {@link java.awt.Font} eine {@link greenfoot.Font} zu machen.
 * Ein ähnlicher Trick wird auch an einer zweiten Stelle hier benutzt: Um die Größe von gerendertem Text zu messen.
 * Die {@link #dimensions()} Methode benötigt Zugriff auf das interne {@link Graphics2D} Objekt um schließlich
 * {@link GlyphVector#getVisualBounds()} benutzen zu können. Das {@link Graphics2D} Objekt wird durch das Rufen der
 * privaten {@link GreenfootImage#getGraphics()} Methode auf dem surface image geholt.
 *
 * @author Faris
 * @see Font
 * @see greenfoot.Font
 */
public class TextRenderer {
    private static final Font awtFont;
    private static final Constructor<greenfoot.Font> fontConstructor;
    private static final Method getGraphics;
    private static final String fontName = "DepartureMonoNerdFont-Regular.otf";
    private final GreenfootImage surface;
    private final Font instanceAwt;
    private final String text;

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

    /**
     * Erstellt einen TextRenderer.
     *
     * @param text     Der zu rendernde Text.
     * @param surface  Das Bild, auf das der Text gerendert werden wird.
     * @param fontSize Die Schriftgröße.
     * @param color    Die Farbe des Textes.
     */
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
            System.err.println("Error: greenfoot couldn't load font " + fontName + ": " + e.getMessage());
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

    /**
     * Rendert den Text mit den im Konstruktor spezifizierten Eigenschaften.
     *
     * @param position Die Stelle an die gerendert werden soll.
     */
    public void render(Point2D position) {
        surface.setFont(fromAwtFont(instanceAwt));
        surface.drawString(text, position.x(), position.y());
    }

    /**
     * Die Abmessungen des Textes.
     *
     * @return Einen {@link Vector2D} der die Breite und die Höhe des Textes darstellt.
     */
    public Vector2D dimensions() {
        GlyphVector vector = instanceAwt.createGlyphVector(getGraphics2D(surface).getFontRenderContext(), text);
        Rectangle2D bounds = vector.getVisualBounds();

        return new Vector2D((float) bounds.getWidth(), (float) bounds.getHeight());
    }
}
