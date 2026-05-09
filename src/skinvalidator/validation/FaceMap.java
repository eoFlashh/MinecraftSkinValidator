package skinvalidator.validation;

import skinvalidator.model.Face;
import skinvalidator.model.Pixel;
import skinvalidator.model.PixelCoordinate;
import skinvalidator.model.SkinType;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class FaceMap {
    private static final Map<Integer, String> FACE_NAMES = createFaceNames();

    private final SkinImageLoader imageLoader = new SkinImageLoader();
    private final Map<SkinType, List<Face>> faceCache = new HashMap<>();
    private final Map<SkinType, Map<PixelCoordinate, String>> faceNameCache = new HashMap<>();

    public List<Face> facesFor(SkinType skinType) throws IOException {
        List<Face> cached = faceCache.get(skinType);
        if (cached != null) {
            return cached;
        }

        Map<Integer, Face> facesByColor = new LinkedHashMap<>();
        for (Pixel pixel : imageLoader.loadVisiblePixels(skinType.referencePath())) {
            int colorKey = colorKey(pixel.color());
            String faceName = FACE_NAMES.get(colorKey);
            if (faceName == null) {
                continue;
            }

            Face face = facesByColor.get(colorKey);
            if (face == null) {
                face = new Face(faceName, colorKey);
                facesByColor.put(colorKey, face);
            }
            face.add(pixel.coordinate());
        }

        List<Face> faces = Collections.unmodifiableList(new ArrayList<>(facesByColor.values()));
        faceCache.put(skinType, faces);
        faceNameCache.put(skinType, createFaceLookup(faces));
        return faces;
    }

    public String faceNameFor(SkinType skinType, PixelCoordinate coordinate) throws IOException {
        facesFor(skinType);
        return faceNameCache.get(skinType).getOrDefault(coordinate, "none");
    }

    private Map<PixelCoordinate, String> createFaceLookup(List<Face> faces) {
        Map<PixelCoordinate, String> lookup = new HashMap<>();
        for (Face face : faces) {
            for (PixelCoordinate coordinate : face.coordinates()) {
                lookup.put(coordinate, face.name());
            }
        }
        return lookup;
    }

    private static int colorKey(Color color) {
        return color.getRed() << 16 | color.getGreen() << 8 | color.getBlue();
    }

    private static Map<Integer, String> createFaceNames() {
        Map<Integer, String> faces = new HashMap<>();

        put(faces, 255, 216, 0, "Head Right");
        put(faces, 0, 19, 127, "RLeg Right");
        put(faces, 124, 142, 0, "Rleg 2 Right");
        put(faces, 38, 32, 0, "LLeg 2 Right");
        put(faces, 0, 255, 255, "Rleg Top");
        put(faces, 0, 38, 255, "RLeg Front");
        put(faces, 255, 54, 0, "Rleg 2 Top");
        put(faces, 248, 255, 0, "RLeg 2 Front");
        put(faces, 76, 0, 0, "LLeg 2 Top");
        put(faces, 38, 0, 0, "LLeg 2 Front");
        put(faces, 255, 0, 0, "Head Top");
        put(faces, 255, 106, 0, "Head Front");
        put(faces, 0, 127, 127, "RLeg Bottom");
        put(faces, 0, 74, 127, "Rleg Left");
        put(faces, 142, 27, 0, "RLeg 2 Bottom");
        put(faces, 142, 92, 0, "RLeg 2 Left");
        put(faces, 76, 32, 0, "LLeg 2 Bottom");
        put(faces, 38, 15, 0, "LLeg 2 Left");
        put(faces, 0, 148, 255, "RLeg Back");
        put(faces, 255, 186, 0, "RLeg 2 Back");
        put(faces, 76, 65, 0, "LLeg 2 Back");
        put(faces, 127, 0, 0, "Head Bottom");
        put(faces, 127, 51, 0, "Head Left");
        put(faces, 72, 0, 255, "Body Right");
        put(faces, 112, 255, 0, "Body 2 Right");
        put(faces, 95, 79, 0, "LLeg Right");
        put(faces, 178, 0, 255, "Body Top");
        put(faces, 33, 0, 127, "Body Front");
        put(faces, 0, 255, 0, "Body 2 Top");
        put(faces, 59, 142, 0, "Body 2 Front");
        put(faces, 191, 0, 0, "LLeg Top");
        put(faces, 95, 0, 0, "LLeg Front");
        put(faces, 127, 106, 0, "Head Back");
        put(faces, 191, 79, 0, "LLeg Bottom");
        put(faces, 95, 38, 0, "LLeg Left");
        put(faces, 87, 0, 127, "Body Bottom");
        put(faces, 255, 0, 220, "Body Left");
        put(faces, 0, 142, 0, "Body 2 Bottom");
        put(faces, 0, 255, 97, "Body 2 Left");
        put(faces, 191, 162, 0, "LLeg Back");
        put(faces, 182, 255, 0, "Head 2 Right");
        put(faces, 127, 0, 110, "Body Back");
        put(faces, 0, 142, 48, "Body 2 Back");
        put(faces, 0, 95, 52, "LArm Right");
        put(faces, 136, 191, 0, "LArm Top");
        put(faces, 68, 95, 0, "LArm Front");
        put(faces, 0, 255, 144, "Head 2 Top");
        put(faces, 91, 127, 0, "Head 2 Front");
        put(faces, 255, 0, 110, "RArm Right");
        put(faces, 0, 255, 233, "RArm 2 Right");
        put(faces, 57, 191, 0, "LArm Bottom");
        put(faces, 0, 95, 10, "LArm Left");
        put(faces, 255, 127, 127, "RArm Top");
        put(faces, 127, 0, 55, "RArm Front");
        put(faces, 112, 228, 255, "RArm 2 Top");
        put(faces, 0, 142, 116, "RArm 2 Front");
        put(faces, 0, 191, 108, "LArm Back");
        put(faces, 0, 127, 70, "Head 2 Bottom");
        put(faces, 76, 255, 0, "Head 2 Left");
        put(faces, 127, 63, 63, "RArm Bottom");
        put(faces, 255, 178, 127, "RArm Left");
        put(faces, 55, 113, 135, "RArm 2 Bottom");
        put(faces, 112, 165, 255, "RArm 2 Left");
        put(faces, 0, 66, 36, "LArm 2 Right");
        put(faces, 255, 233, 127, "RArm Back");
        put(faces, 127, 112, 255, "RArm 2 Back");
        put(faces, 95, 134, 0, "LArm 2 Top");
        put(faces, 48, 66, 0, "LArm 2 Front");
        put(faces, 38, 127, 0, "Head 2 Back");
        put(faces, 40, 134, 0, "LArm 2 Bottom");
        put(faces, 0, 66, 7, "LArm 2 Left");
        put(faces, 0, 134, 76, "LArm 2 Back");

        return Collections.unmodifiableMap(faces);
    }

    private static void put(Map<Integer, String> faces, int red, int green, int blue, String name) {
        faces.put(red << 16 | green << 8 | blue, name);
    }
}
