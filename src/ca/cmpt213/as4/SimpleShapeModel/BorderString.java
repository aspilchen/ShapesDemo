package ca.cmpt213.as4.SimpleShapeModel;

/**
 * A string border decorator for a SimpleBox. Creates a repeating sequence of
 * characters around the perimeter of the box.
 */

import ca.cmpt213.as4.UI.Canvas;
import ca.cmpt213.as4.UI.DrawableShape;
import com.google.gson.JsonObject;

public class BorderString extends SimpleBox {
    private String sequence;
    private Cartesian topRight;
    private Cartesian bottomLeft;
    private SimpleBox box;

    public BorderString(SimpleBox box) {
        super(box);
        this.box = box;
    }

    public BorderString(SimpleBox box, String sequence) {
        super(box);
        this.box = box;
        this.sequence = sequence;
    }

    @Override
    public void populateFromJSON(JsonObject jsonObject) {
        super.populateFromJSON(jsonObject);
        box.populateFromJSON(jsonObject);
        String lineType = jsonObject.get("line").getAsString();
        if(lineType.equals("char")) {
            sequence = jsonObject.get("lineChar").getAsString();
        } else {
            sequence = "12345";
        }
    }


    @Override
    public void redact() {
        box.redact();
        sequence = "+";
    }

    @Override
    public DrawableShape toDrawable() {
        return new DrawableShape() {
            private int seq = 0;
            @Override
            public void draw(Canvas canvas) {
                box.toDrawable().draw(canvas);
                calculateDimensions();
                int width = getWidth();
                int height = getHeight();
                seq = 0;
                hLine(canvas, topLeft, width);
                if(height > 2) {
                    vLine(canvas, topRight, height - 2);
                }
                if(height > 1) {
                    hLine(canvas, bottomRight, -width);
                }
                if (height > 2) {
                    vLine(canvas, bottomLeft, -(height - 2));
                }
            }

            private void vLine(Canvas canvas, Cartesian from, int length) {
                int modifier = 0;
                if(length > 0) {
                    modifier = 1;
                } else if (length < 0) {
                    modifier = -1;
                }
                int absLen = Math.abs(length);
                for(int i = 0; i < absLen; i++) {
                    int y = from.y() + (i * modifier);
                    canvas.setCellText(from.x(), y, sequence.charAt(seq));
                    seq = (seq + 1) % sequence.length();
                }
            }

            private void hLine(Canvas canvas, Cartesian from, int length) {
                int modifier = 0;
                if(length > 0) {
                    modifier = 1;
                } else if (length < 0) {
                    modifier = -1;
                }
                int absLen = Math.abs(length);
                for(int i = 0; i < absLen; i++) {
                    int x = from.x() + (i * modifier);
                    canvas.setCellText(x, from.y(), sequence.charAt(seq));
                    seq = (seq + 1) % sequence.length();
                }
            }
        };
    }

    private void calculateDimensions() {
        topRight = new Cartesian(bottomRight.x(), topLeft.y() + 1);
        bottomLeft = new Cartesian(topLeft.x(), bottomRight.y() - 1);
    }
}
