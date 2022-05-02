package ca.cmpt213.as4.SimpleShapeModel;

/**
 * Manages all ShapeModel objects and provides an interface to convert ShapeModels
 * to DrawableShapes for the GUI/Canvas to use.
 */

import ca.cmpt213.as4.UI.ShapeManager;
import ca.cmpt213.as4.UI.DrawableShape;
import com.google.gson.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SimpleShapeManager implements ShapeManager {
    private List<ShapeModel> shapeModels;
    private List<DrawableShape> drawableShapes;

    public SimpleShapeManager() {
        shapeModels = new ArrayList<>();
        drawableShapes = new ArrayList<>();
    }

    @Override
    public void populateFromJSON(File jsonFile) {
        shapeModels.clear();
        Gson gson = new Gson();
        FileReader fReader = null;
        try {
            fReader = new FileReader(jsonFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        JsonElement jsonElement = JsonParser.parseReader(fReader);
        JsonArray jsonArray = jsonElement.getAsJsonObject().get("shapes").getAsJsonArray();
        for(JsonElement element: jsonArray) {
            JsonObject jsonBox = element.getAsJsonObject();
            SimpleBox box = new SimpleBox();
            box = addBorder(box, jsonBox);
            box = addColor(box);
            box = addFill(box, jsonBox);
            box = addBackground(box, jsonBox);
            box.populateFromJSON(jsonBox);
            shapeModels.add(box);
        }
    }

    @Override
    public void redact() {
        for(ShapeModel shape: shapeModels) {
            shape.redact();
        }
    }

    @Override
    public Iterator<? extends DrawableShape> iterator() {
        drawableShapes.clear();
        for(ShapeModel shape: shapeModels) {
            drawableShapes.add(shape.toDrawable());
        }
        return drawableShapes.iterator();
    }

    public void addShape(ShapeModel shape) {
        shapeModels.add(shape);
    }

    private SimpleBox addBorder(SimpleBox box, JsonObject jsonObject) {
        String boarderType = jsonObject.get("line").getAsString();
        SimpleBox decoratedBox = box;
        if(boarderType.equals("ascii line")) {
            decoratedBox = new BorderAscii(box);
        } else {
            decoratedBox = new BorderString(box);
        }
        return decoratedBox;
    }

    private SimpleBox addBackground(SimpleBox box, JsonObject boxJSON) {
        String background = boxJSON.get("background").getAsString();
        SimpleBox decoratedBox = box;
        if(background.equals("checker")) {
            decoratedBox = new BackgroundChecker(box);
        } else if(background.equals("triangle")) {
            decoratedBox = new BackgroundTriangle(box);
        }
        return decoratedBox;
    }

    private SimpleBox addColor(SimpleBox box) {
        return new ColorBox(box);
    }

    private SimpleBox addFill(SimpleBox box, JsonObject boxJSON) {
        String fill = boxJSON.get("fill").getAsString();
        SimpleBox filledBox = box;
        if(fill.equals("solid")) {
            filledBox = new FillSolid(box);
        } else if(fill.equals("wrapper")) {
            filledBox = new FillWrapped(box);
        }
        return filledBox;
    }
}
