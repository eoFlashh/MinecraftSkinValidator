package skinvalidator.model;

import java.awt.Color;

public final class Pixel {
    private final PixelCoordinate coordinate;
    private final Color color;

    public Pixel(PixelCoordinate coordinate, Color color) {
        this.coordinate = coordinate;
        this.color = color;
    }

    public PixelCoordinate coordinate() {
        return coordinate;
    }

    public Color color() {
        return color;
    }
}
