package ca.cmpt213.as4.SimpleShapeModel;

/**
 * The model for a SimpleBox shape. Defines the basic dimensions describing a box/square.
 * Use decorators to control the appearance.
 */

import ca.cmpt213.as4.UI.Canvas;
import ca.cmpt213.as4.UI.DrawableShape;
import com.google.gson.JsonObject;

public class SimpleBox implements ShapeModel {
    protected Cartesian topLeft;
    protected Cartesian bottomRight;

    public SimpleBox() {}

    public SimpleBox(SimpleBox box) {
        topLeft = box.topLeft;
        bottomRight = box.bottomRight;
    }

    public SimpleBox(Cartesian topLeft, Cartesian bottomRight) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
    }

    @Override
    public void populateFromJSON(JsonObject jsonObject) {
        int x1 = jsonObject.get("left").getAsInt();
        int x2 = jsonObject.get("width").getAsInt() + x1 - 1;
        int y1 = jsonObject.get("top").getAsInt();
        int y2 = jsonObject.get("height").getAsInt() + y1 - 1;
        topLeft = new Cartesian(x1, y1);
        bottomRight = new Cartesian(x2, y2);
    }

    @Override
    public void redact() {
        return;
    }

    @Override
    public DrawableShape toDrawable() {
        return new DrawableShape() {
            @Override
            public void draw(Canvas canvas) {
                return;
            }
        };
    }

    public int getHeight() {
        return bottomRight.y() - topLeft.y() + 1;
    }

    public int getWidth() {
        return bottomRight.x() - topLeft.x() + 1;
    }

}
