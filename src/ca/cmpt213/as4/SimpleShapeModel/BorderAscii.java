package ca.cmpt213.as4.SimpleShapeModel;

/**
 * Border decorator for SimpleBox. Uses ascii symbols to create a double lined
 * border around the perimeter of the SimpleBox.
 */

import ca.cmpt213.as4.UI.Canvas;
import ca.cmpt213.as4.UI.DrawableShape;
import com.google.gson.JsonObject;

import java.util.*;

public class BorderAscii extends SimpleBox {
    private Cartesian topRight;
    private Cartesian bottomLeft;
    private SimpleBox box;
    private BorderSymbol hChar = BorderSymbol.HORIZONTAL;
    private BorderSymbol vChar = BorderSymbol.VERTICAL;
    private BorderSymbol tlCorner = BorderSymbol.TL_CORNER;
    private BorderSymbol trCorner = BorderSymbol.TR_CORNER;
    private BorderSymbol brCorner = BorderSymbol.BR_CORNER;
    private BorderSymbol blCorner = BorderSymbol.BL_CORNER;
    private boolean isRedacted = false;

    private static final Map<BorderSymbol, Character> borderSymbols = new EnumMap<>(BorderSymbol.class) {{
        put(BorderSymbol.HORIZONTAL, (char) 0x2550);
        put(BorderSymbol.VERTICAL,   (char) 0x2551);
        put(BorderSymbol.TL_CORNER,  (char) 0x2554);
        put(BorderSymbol.TR_CORNER,  (char) 0x2557);
        put(BorderSymbol.BR_CORNER,  (char) 0x255D);
        put(BorderSymbol.BL_CORNER,  (char) 0x255A);
        put(BorderSymbol.REDACT,               '+');
        put(BorderSymbol.ERR,        (char) 0x25A0);
    }};

    public BorderAscii(SimpleBox box) {
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
        hChar = BorderSymbol.REDACT;
        vChar = BorderSymbol.REDACT;
        tlCorner = BorderSymbol.REDACT;
        trCorner = BorderSymbol.REDACT;
        brCorner = BorderSymbol.REDACT;
        blCorner = BorderSymbol.REDACT;
    }

    @Override
    public DrawableShape toDrawable() {
        return new DrawableShape() {
            @Override
            public void draw(Canvas canvas) {
                calculateDimensions();
                int height = getHeight();
                int width = getWidth();
                box.toDrawable().draw(canvas);
                hLine(canvas, topLeft, width);
                vLine(canvas, topRight, height);
                hLine(canvas, bottomRight, -width);
                vLine(canvas, bottomLeft, -height);
                canvas.setCellText(topLeft.x(), topLeft.y(), borderSymbols.get(tlCorner));
                canvas.setCellText(topRight.x(), topRight.y(), borderSymbols.get(trCorner));
                canvas.setCellText(bottomRight.x(), bottomRight.y(), borderSymbols.get(brCorner));
                canvas.setCellText(bottomLeft.x(), bottomLeft.y(), borderSymbols.get(blCorner));
            }

            private void vLine(Canvas canvas, Cartesian from, int length) {
                final char c = borderSymbols.get(vChar);
                int modifier = 0;
                if(length > 0) {
                    modifier = 1;
                } else if (length < 0) {
                    modifier = -1;
                }
                int absLen = Math.abs(length);
                for(int i = 0; i < absLen; i++) {
                    int y = from.y() + (i * modifier);
                    canvas.setCellText(from.x(), y, c);
                }
            }

            private void hLine(Canvas canvas, Cartesian from, int length) {
                final char c = borderSymbols.get(hChar);
                int modifier = 0;
                if(length > 0) {
                    modifier = 1;
                } else if (length < 0) {
                    modifier = -1;
                }
                int absLen = Math.abs(length);
                for(int i = 0; i < absLen; i++) {
                    int x = from.x() + (i * modifier);
                    canvas.setCellText(x, from.y(), c);
                }
            }
        };
    }

    private void calculateDimensions() {
        topRight = new Cartesian(bottomRight.x(), topLeft.y());
        bottomLeft = new Cartesian(topLeft.x(), bottomRight.y());
        if((getHeight() < 2 || getWidth() < 2) && !isRedacted) {
            hChar    = BorderSymbol.ERR;
            vChar    = BorderSymbol.ERR;
            tlCorner = BorderSymbol.ERR;
            trCorner = BorderSymbol.ERR;
            brCorner = BorderSymbol.ERR;
            blCorner = BorderSymbol.ERR;
        }
    }

    private enum BorderSymbol {
        VERTICAL
        ,HORIZONTAL
        ,TL_CORNER
        ,TR_CORNER
        ,BL_CORNER
        ,BR_CORNER
        ,REDACT
        ,ERR
    }
}
