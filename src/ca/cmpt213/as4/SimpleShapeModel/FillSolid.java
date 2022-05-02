package ca.cmpt213.as4.SimpleShapeModel;

/**
 * A solid fill decorator for the SimpleBox. Fills the inside of the box
 * with a repeating character sequence. Does not touch the border/perimeter.
 */

import ca.cmpt213.as4.UI.Canvas;
import ca.cmpt213.as4.UI.DrawableShape;
import com.google.gson.JsonObject;

public class FillSolid extends SimpleBox {
    private String sequence;
    private SimpleBox box;

    public FillSolid(SimpleBox box) {
        super(box);
        this.box = box;
    }

    public FillSolid(SimpleBox box, String sequence) {
        super(box);
        this.box = box;
        if(!sequence.isEmpty()) {
            this.sequence = sequence;
        }
    }

    @Override
    public void populateFromJSON(JsonObject jsonObject) {
        super.populateFromJSON(jsonObject);
        box.populateFromJSON(jsonObject);
        String fillText = jsonObject.get("fillText").getAsString();
        sequence = String.valueOf(fillText.charAt(0));
    }

    @Override
    public void redact() {
        box.redact();
        sequence = "X";
    }

    @Override
    public DrawableShape toDrawable() {
        return new DrawableShape() {
            @Override
            public void draw(Canvas canvas) {
                box.toDrawable().draw(canvas);
                int seq = 0;
                for(int y = topLeft.y() + 1; y < bottomRight.y(); y++) {
                    for(int x = topLeft.x() + 1; x < bottomRight.x(); x++) {
                        canvas.setCellText(x, y, sequence.charAt(seq));
                        seq = (seq + 1) % sequence.length();
                    }
                }
            }
        };
    }
}
