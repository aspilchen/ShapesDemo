package ca.cmpt213.as4.SimpleShapeModel;

/**
 * Simple cartesian coordinates to hold an x,y position.
 */

public class Cartesian {
    private final int x;
    private final int y;

    public Cartesian(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }
}
