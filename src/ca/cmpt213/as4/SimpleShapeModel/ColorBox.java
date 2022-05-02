package ca.cmpt213.as4.SimpleShapeModel;

/**
 * Color decorator for SimpleBox. Fills the box with a single color.
 */

import ca.cmpt213.as4.UI.Canvas;
import ca.cmpt213.as4.UI.DrawableShape;
import com.google.gson.*;

import java.util.HashMap;
import java.util.Map;

public class ColorBox extends SimpleBox {
    private static final Map<String, java.awt.Color> COLORS = new HashMap<>() {{
        put("white", java.awt.Color.WHITE);
        put("light gray", java.awt.Color.LIGHT_GRAY);
        put("gray", java.awt.Color.GRAY);
        put("dark gray", java.awt.Color.DARK_GRAY);
        put("black", java.awt.Color.BLACK);
        put("red", java.awt.Color.RED);
        put("pink", java.awt.Color.PINK);
        put("orange", java.awt.Color.ORANGE);
        put("yellow", java.awt.Color.YELLOW);
        put("green", java.awt.Color.GREEN);
        put("magenta", java.awt.Color.MAGENTA);
        put("cyan", java.awt.Color.CYAN);
        put("blue", java.awt.Color.BLUE);
    }};
    private java.awt.Color color = java.awt.Color.WHITE;
    private SimpleBox box;

    public ColorBox(SimpleBox box) {
        this.box = box;
    }

    public ColorBox(SimpleBox box, java.awt.Color color) {
        super(box);
        this.box = box;
        this.color = color;
    }

    @Override
    public void populateFromJSON(JsonObject jsonObject) {
        super.populateFromJSON(jsonObject);
        box.populateFromJSON(jsonObject);
        String colorName = jsonObject.getAsJsonObject().get("backgroundColor").getAsString();
        this.color = COLORS.get(colorName);
    }

    @Override
    public void redact() {
        this.color = java.awt.Color.LIGHT_GRAY;
        box.redact();
    }

    @Override
    public DrawableShape toDrawable() {
        return new DrawableShape() {
            @Override
            public void draw(Canvas canvas) {
                box.toDrawable().draw(canvas);
                 for(int y = topLeft.y(); y <= bottomRight.y(); y++) {
                     for(int x = topLeft.x(); x <= bottomRight.x(); x++) {
                         canvas.setCellColor(x, y, color);
                     }
                 }
            }
        };
    }
}
