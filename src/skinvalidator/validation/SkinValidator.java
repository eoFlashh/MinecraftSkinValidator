package skinvalidator.validation;

import skinvalidator.model.Face;
import skinvalidator.model.Pixel;
import skinvalidator.model.PixelCoordinate;
import skinvalidator.model.SkinType;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class SkinValidator {
    private final SkinImageLoader imageLoader = new SkinImageLoader();
    private final FaceMap faceMap = new FaceMap();
    private final Map<SkinType, Set<PixelCoordinate>> forbiddenPixelCache = new HashMap<>();

    public SkinValidationResult validate(Path path) throws IOException {
        SkinType skinType = SkinType.fromPath(path);
        List<Pixel> skinPixels = imageLoader.loadVisiblePixels(path);
        Set<PixelCoordinate> forbiddenPixels = forbiddenPixelsFor(skinType);
        List<Face> faces = faceMap.facesFor(skinType);

        List<String> errors = new ArrayList<>();
        List<PixelCoordinate> forbiddenMatches = new ArrayList<>();
        List<PixelCoordinate> partialAlphaPixels = new ArrayList<>();
        Map<PixelCoordinate, Pixel> skinByCoordinate = new HashMap<>();

        for (Pixel pixel : skinPixels) {
            PixelCoordinate coordinate = pixel.coordinate();
            skinByCoordinate.put(coordinate, pixel);

            if (forbiddenPixels.contains(coordinate)) {
                errors.add("(wrong pixel place)" + coordinate);
                forbiddenMatches.add(coordinate);
            } else if (pixel.color().getAlpha() != 255 && pixel.color().getAlpha() != 0) {
                String faceName = faceMap.faceNameFor(skinType, coordinate);
                errors.add("(wrong pixel alpha in " + faceName + ")" + coordinate);
                partialAlphaPixels.add(coordinate);
            }
        }

        errors.addAll(validateFaces(faces, skinByCoordinate));
        return new SkinValidationResult(skinType, errors, forbiddenMatches, partialAlphaPixels);
    }

    public Path fix(Path path, SkinValidationResult result) throws IOException {
        BufferedImage image = imageLoader.read(path);

        for (PixelCoordinate coordinate : result.forbiddenPixels()) {
            if (image.getRGB(coordinate.x(), coordinate.y()) != 0) {
                image.setRGB(coordinate.x(), coordinate.y(), 0);
                result.addCorrection("wrong pixel removed");
            }
        }

        for (PixelCoordinate coordinate : result.partialAlphaPixels()) {
            Color color = new Color(image.getRGB(coordinate.x(), coordinate.y()), true);
            Color opaqueColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), 255);
            image.setRGB(coordinate.x(), coordinate.y(), opaqueColor.getRGB());
            result.addCorrection("wrong pixel alpha changed");
        }

        Path parent = path.getParent();
        Path output = parent == null
                ? Path.of("✓" + path.getFileName())
                : parent.resolve("✓" + path.getFileName());
        ImageIO.write(image, "png", output.toFile());
        return output;
    }

    private Set<PixelCoordinate> forbiddenPixelsFor(SkinType skinType) throws IOException {
        Set<PixelCoordinate> cached = forbiddenPixelCache.get(skinType);
        if (cached != null) {
            return cached;
        }

        Set<PixelCoordinate> forbiddenPixels = new LinkedHashSet<>();
        BufferedImage reference = imageLoader.read(skinType.referencePath());
        for (int y = 0; y < reference.getHeight(); y++) {
            for (int x = 0; x < reference.getWidth(); x++) {
                Color color = new Color(reference.getRGB(x, y), true);
                if (color.getAlpha() == 0) {
                    forbiddenPixels.add(new PixelCoordinate(x, y));
                }
            }
        }

        forbiddenPixelCache.put(skinType, forbiddenPixels);
        return forbiddenPixels;
    }

    private List<String> validateFaces(List<Face> faces, Map<PixelCoordinate, Pixel> skinByCoordinate) {
        List<String> errors = new ArrayList<>();

        for (Face face : faces) {
            if (face.isOverlayFace()) {
                continue;
            }

            List<Pixel> facePixels = new ArrayList<>();
            for (PixelCoordinate coordinate : face.coordinates()) {
                Pixel pixel = skinByCoordinate.get(coordinate);
                if (pixel != null) {
                    facePixels.add(pixel);
                }
            }

            if (facePixels.size() != face.coordinates().size()) {
                errors.add("(Missing pixel in " + face.name() + ")");
                continue;
            }

            if (hasSingleColor(facePixels)) {
                errors.add("(Wrong face " + face.name() + " single color face)");
            }
        }

        return errors;
    }

    private boolean hasSingleColor(List<Pixel> pixels) {
        if (pixels.isEmpty()) {
            return false;
        }

        Color firstColor = pixels.get(0).color();
        for (Pixel pixel : pixels) {
            if (!pixel.color().equals(firstColor)) {
                return false;
            }
        }
        return true;
    }
}
