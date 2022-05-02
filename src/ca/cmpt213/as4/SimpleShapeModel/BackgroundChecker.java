package ca.cmpt213.as4.SimpleShapeModel;

/**
 * Checkered background decorator for the SimpleBox class.
 *
 * Provide a ColoredBox on construction for best results.
 */

import ca.cmpt213.as4.UI.Canvas;
import ca.cmpt213.as4.UI.DrawableShape;
import com.google.gson.JsonObject;

public class BackgroundChecker extends SimpleBox {
    private SimpleBox box;
    private boolean isRedacted = false;

    public BackgroundChecker(SimpleBox box) {
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
                int yPattern = 0;
                for(int y = topLeft.y(); y <= bottomRight.y(); y++) {
                    int xPattern = 0;
                    for(int x = topLeft.x(); x <= bottomRight.x(); x++) {
                        if((yPattern == 0) && (xPattern == 1)) {
                            canvas.setCellColor(x, y, java.awt.Color.WHITE);
                        } else if((yPattern == 1) && (xPattern == 0)) {
                            canvas.setCellColor(x, y, java.awt.Color.WHITE);
                        }
                        xPattern = (xPattern + 1) % 2;
                    }
                    yPattern = (yPattern + 1) % 2;
                }
            }
        };
    }

}
