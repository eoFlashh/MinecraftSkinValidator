package skinvalidator.validation;

import skinvalidator.model.Pixel;
import skinvalidator.model.PixelCoordinate;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public final class SkinImageLoader {
    public BufferedImage read(Path path) throws IOException {
        BufferedImage image = ImageIO.read(path.toFile());
        if (image == null) {
            throw new IOException("Unsupported image format: " + path);
        }
        return image;
    }

    public List<Pixel> loadVisiblePixels(Path path) throws IOException {
        BufferedImage image = read(path);
        List<Pixel> pixels = new ArrayList<>();

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                Color color = new Color(image.getRGB(x, y), true);
                if (color.getRGB() != 0) {
                    pixels.add(new Pixel(new PixelCoordinate(x, y), color));
                }
            }
        }

        return pixels;
    }
}
