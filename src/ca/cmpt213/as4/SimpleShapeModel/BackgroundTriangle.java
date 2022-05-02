package ca.cmpt213.as4.SimpleShapeModel;
/**
 * Triangle background decorator for a SimpleBox.
 *
 * Provide a ColoredBox on construction for best results.
 */

import ca.cmpt213.as4.UI.Canvas;
import ca.cmpt213.as4.UI.DrawableShape;
import com.google.gson.JsonObject;

import java.awt.Color;

public class BackgroundTriangle extends SimpleBox {
    private SimpleBox box;
    private boolean isRedacted = false;

    public BackgroundTriangle(SimpleBox box) {
        super(box);
        this.box = box;
    }

    @Override
    public void populateFromJSON(JsonObject jsonObject) {
        super.populateFromJSON(jsonObject);
        box.populateFromJSON(jsonObject);
    }

    @Override
    public void redact() {
        box.redact();
        isRedacted = true;
    }

    @Override
    public DrawableShape toDrawable() {
        return new DrawableShape() {
            @Override
            public void draw(Canvas canvas) {
                box.toDrawable().draw(canvas);
                if(isRedacted) {
                    return;
                }
                int height = getHeight();
                for(int y = 0; y <= height; y++) {
                    for(int x = 0; x < y; x++) {
                        canvas.setCellColor(topLeft.x() + x, topLeft.y() + y, Color.WHITE);
                    }
                }
            }
        };
    }

}
