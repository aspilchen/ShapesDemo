package ca.cmpt213.as4.SimpleShapeModel;

/**
 * A text fill decorator for the SimpleBox. Will put wrapped, center aligned text inside the box.
 * Line breaks will be on spaces where possible.
 * Text will not repeat.
 */

import ca.cmpt213.as4.UI.Canvas;
import ca.cmpt213.as4.UI.DrawableShape;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class FillWrapped extends SimpleBox {
    private String fillText;
    private SimpleBox box;
    private boolean isRedacted = false;

    public FillWrapped(SimpleBox box) {
        super(box);
        this.box = box;
    }

    public FillWrapped(SimpleBox box, String fillText) {
        super(box);
        this.box = box;
        this.fillText = fillText;
    }

    @Override
    public void populateFromJSON(JsonObject jsonObject) {
        super.populateFromJSON(jsonObject);
        box.populateFromJSON(jsonObject);
        fillText = jsonObject.get("fillText").getAsString();
    }

    @Override
    public void redact() {
        isRedacted = true;
        box.redact();
    }

    @Override
    public int getWidth() {
        return super.getWidth() - 2;
    }

    @Override
    public int getHeight() {
        return super.getHeight() - 2;
    }

    @Override
    public DrawableShape toDrawable() {
        return new DrawableShape() {
            @Override
            public void draw(Canvas canvas) {
                box.toDrawable().draw(canvas);
                if(getWidth() < 1 || getHeight() < 1) {
                    return;
                }
                if(isRedacted) {
                    drawRedacted(canvas);
                } else {
                    List<String> rowText = getSubStrings();
                    drawCenteredText(canvas, rowText);
                }
            }

            private List<String> getSubStrings() {
                List<String> textRows = new ArrayList<>();
                int start = 0;
                while(start < fillText.length()) {
                    while(start < fillText.length() && fillText.charAt(start) == ' ') {
                        start++;
                    }
                    if(start == fillText.length()) {
                        break;
                    }

                    String subStr;
                    int end = start + getWidth();
                    if(end < fillText.length()) {
                        subStr = fillText.substring(start, end).strip();
                    } else {
                        subStr = fillText.substring(start).strip();
                        textRows.add(subStr);
                        break;
                    }

                    end = start + subStr.length();
                    boolean badWordBoundary = fillText.charAt(end-1) != ' ' && fillText.charAt(end) != ' ';
                    int spaceIdx = subStr.lastIndexOf(' ');
                    if(badWordBoundary && spaceIdx > 0) {
                        subStr = subStr.substring(0, spaceIdx);
                        end = start + subStr.length();
                    }
                    textRows.add(subStr);
                    start = end;
                }
                return textRows;
            }

            private void drawCenteredText(Canvas canvas, List<String> text) {
                for(int yOffset = 0; yOffset < text.size(); yOffset++) {
                    if(getHeight() <= yOffset) {
                        break;
                    }
                    String s = text.get(yOffset);
                    int y = topLeft.y() + 1 + yOffset;
                    int diff = getWidth() - s.length();
                    int start = topLeft.x() + (diff / 2) + 1;
                    if(diff % 2 != 0) {
                        start += 1;
                    }
                    for(int xOffset = 0; xOffset < s.length(); xOffset++) {
                        int x = start + xOffset;
                        canvas.setCellText(x, y, s.charAt(xOffset));
                    }
                }
            }

            private void drawRedacted(Canvas canvas) {
                for(int y = topLeft.y() + 1; y < bottomRight.y(); y++) {
                    for(int x = topLeft.x() + 1; x < bottomRight.x(); x++) {
                        canvas.setCellText(x, y, 'X');
                    }
                }
            }
        };
    }


}
