package skinvalidator.model;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

public final class Face {
    private final String name;
    private final int colorKey;
    private final Set<PixelCoordinate> coordinates = new LinkedHashSet<>();

    public Face(String name, int colorKey) {
        this.name = name;
        this.colorKey = colorKey;
    }

    public String name() {
        return name;
    }

    public int colorKey() {
        return colorKey;
    }

    public void add(PixelCoordinate coordinate) {
        coordinates.add(coordinate);
    }

    public Set<PixelCoordinate> coordinates() {
        return Collections.unmodifiableSet(coordinates);
    }

    public boolean isOverlayFace() {
        return name.contains("2");
    }
}
