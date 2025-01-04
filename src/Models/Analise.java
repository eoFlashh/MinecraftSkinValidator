package Models;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Analise {
    Convert convert = new Convert();

    public List<Pixel> wrongPixels(String path) throws IOException {
        List<Pixel> wrongpixels;

        if (!path.endsWith("_a.png")) {
            wrongpixels = convert.convertWrong("./correct_a.png");
        } else if (path.endsWith("_s.png")) {
            wrongpixels = convert.convertWrong("./correct_s.png");
        } else {
            wrongpixels = convert.convertWrong("./correct_s.png");
        }
        return wrongpixels;
    }

    public List<Pixel> loadSkin(String path) throws IOException {
        List<Pixel> skin = new ArrayList();
        BufferedImage img = ImageIO.read(new File(path));
        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                int locate = img.getRGB(x, y);
                Color color = new Color(locate, true);
                Pixel pixel = new Pixel(color, x, y);
                if (pixel.color.getRGB() != 0) {
                    skin.add(pixel);
                }
            }
        }
        return skin;
    }

    public String compare(List<Pixel> wrongPixels, List<Pixel> skin) {
        String result = "Valid skin";
        for (int i = 0; i < skin.size(); i++) {
            Pixel pixel = skin.get(i);
            if (wrongPixels.stream().anyMatch(p -> p.x == pixel.x && p.y == pixel.y)) {
                result = "Invalid skin (wrong place)";
            } else if (pixel.color.getAlpha() != 255 && pixel.color.getAlpha() != 0) {
                result = "Invalid skin (wrong alpha)";
            }
        }
        return result;
    }
}
