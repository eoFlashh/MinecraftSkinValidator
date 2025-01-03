import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String args[]) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the file path: ");
        String path = sc.nextLine();
        File file = new File(path);

        if (!path.endsWith(".png")) {
            System.out.println("Invalid image");
        } else {
            List<Pixel> wrongpixels = new ArrayList();
            List<Pixel> skin = new ArrayList();
            BufferedImage img = ImageIO.read(file);
            BufferedImage correct = ImageIO.read(new File("./correct.png"));
            boolean valid = true;

            for (int y = 0; y < correct.getHeight(); y++) {
                for (int x = 0; x < correct.getWidth(); x++) {
                    int locate = correct.getRGB(x, y);
                    Color color = new Color(locate, true);
                    Pixel pixel = new Pixel(color, x, y);
                    if (color.getRGB() == 0)
                        wrongpixels.add(pixel);
                }
            }

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

            for (int i = 0; i < skin.size(); i++) {
                Pixel pixel = skin.get(i);
                if (compare(pixel, wrongpixels)) {
                    valid = false;
                }
                if (pixel.color.getAlpha() != 255 && pixel.color.getAlpha() != 0) {
                    valid = false;
                }
            }
            if (valid) {
                System.out.println("Valid skin");
            } else {
                System.out.println("Invalid skin");
            }
        }
    }

    public static boolean compare(Pixel pixel, List<Pixel> list) {
        return list.stream().anyMatch(p -> p.x == pixel.x && p.y == pixel.y);
    }
}