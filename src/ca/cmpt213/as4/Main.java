package ca.cmpt213.as4;

import ca.cmpt213.as4.SimpleShapeModel.*;
import ca.cmpt213.as4.UI.GUI;

/**
 * Application to display the "picture" to the UI.
 */
public class Main {
    public static void main(String[] args) {
        SimpleShapeManager manager = new SimpleShapeManager();
        GUI gui = new GUI(manager);
        gui.start();
    }
}
