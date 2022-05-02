package ca.cmpt213.as4.SimpleShapeModel;

/**
 * Define a model for some shape.
 * Should not directly contain any interface details.
 */

import ca.cmpt213.as4.UI.DrawableShape;
import com.google.gson.JsonObject;

public interface ShapeModel {
    void populateFromJSON(JsonObject jsonObject);
    void redact();
    DrawableShape toDrawable();
}
