package Models;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Analise {
    Convert convert = new Convert();

    public List<Pixel> wrongPixels(String path) throws IOException {
        List<Pixel> wrongpixels;

        if (path.endsWith("_a.png")) {
            wrongpixels = convert.convertWrong("./correct_a.png");
        } else {
            wrongpixels = convert.convertWrong("./correct_s.png");
        }
        return wrongpixels;
    }

    public String identifyFacesTipe(String path) {
        String resultPath = "correct_s.png";
        if (path.endsWith("_a.png")) {
            resultPath = "./correct_a.png";
        }
        return resultPath;
    }

    public List<Pixel> loadSkin(String path) throws IOException {
        return convert.convertSkin(path);
    }

    public List<String> verdict(List<Pixel> wrongPixels, List<Pixel> skin, List<Face> MAPSFACES) {
        List<String> result = new ArrayList<>();
        Faces faces = new Faces();

        for (int i = 0; i < skin.size(); i++) {
            Pixel pixel = skin.get(i);
            if (wrongPixels.stream().anyMatch(p -> p.pixelLocation().equals(pixel.pixelLocation()))) {
                result.add("(wrong pixel place)");
            } else if (pixel.color.getAlpha() != 255 && pixel.color.getAlpha() != 0) {
                result.add("(wrong pixel alpha in " + faces.getFaceName(pixel.pixelLocation(), MAPSFACES) + ")");
            }
        }
        List<String> faceResult = validateFace(MAPSFACES, skin);
        result.addAll(faceResult);

        if (result.isEmpty()) {
            result.add("Valid skin");
        }
        return result;
    }

    private List<String> validateFace(List<Face> mapFaces, List<Pixel> skin) {
        List<String> veredict = new ArrayList<>();
        for (Face face : mapFaces) {
            List<String> faceLocation = face.getFaceLocations();
            Color firstColor = null;
            List<Pixel> facePixels = new ArrayList<>();

            for (Pixel pixel : skin) {
                if (faceLocation.contains(pixel.pixelLocation())) {
                    facePixels.add(pixel);
                    if (firstColor == null) {
                        firstColor = pixel.color;
                    }
                }
            }

            List<String> missingLocations = new ArrayList<>(faceLocation);
            for (Pixel pixel : facePixels) {
                missingLocations.remove(pixel.pixelLocation());
            }

            if (!missingLocations.isEmpty() && !face.name.contains("2")) {
                veredict.add("(Missing pixel in " + face.name + ")");
            }

            Color finalFirstColor = firstColor;
            if ((facePixels.stream().allMatch(p -> p.color.equals(finalFirstColor)) && !face.name.contains("2"))) {
                veredict.add("(Wrong face " + face.name + " single color face)");
            }
        }
        return veredict;
    }
}
