package Models;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Convert {
    public List<Pixel> convertWrong(String pathImg) throws IOException {
        File file = new File(pathImg);
        BufferedImage correct = ImageIO.read(file);
        List<Pixel> wrongpixels = new ArrayList();

        for (int y = 0; y < correct.getHeight(); y++) {
            for (int x = 0; x < correct.getWidth(); x++) {
                int locate = correct.getRGB(x, y);
                Color color = new Color(locate, true);
                Pixel pixel = new Pixel(color, x, y);
                if (color.getRGB() == 0)
                    wrongpixels.add(pixel);
            }
        }
        return wrongpixels;
    }
}
