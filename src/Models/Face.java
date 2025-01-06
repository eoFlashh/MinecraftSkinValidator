package Models;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Face {

    public String name;
    public Color color;
    public List<Pixel> pixels = new ArrayList<>();

    public Face(String name, Color color) {
        this.name = name;
        this.color = color;
    }

    public List<String> getFaceLocations() {
        List<String> locations = new ArrayList<>();
        for (Pixel p : pixels) {
            locations.add(p.pixelLocation());
        }

        return locations;
    }
}
