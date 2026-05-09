package Main;

import Models.Analise;
import Models.Face;
import Models.Faces;
import Models.Pixel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the file path or path: ");
        String path = sc.nextLine();
        System.out.println("Type 1 if you want autocorrect");
        String autoCorrectString = sc.nextLine();
        sc.close();

        File file = new File(path);
        boolean autoCorrect = false;

        if (autoCorrectString.equals("1")) {
            autoCorrect = true;
        }

        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    System.out.println(f.getName());
                    if (f.isFile() && f.getName().toLowerCase().endsWith(".png") && !f.getName().contains("✓")) {
                        analiseVeredict(f.getAbsolutePath(), autoCorrect);
                    } else {
                        System.out.println("Invalid image.");
                    }
                    System.out.println("\n");
                }
            }
        } else if (!path.toLowerCase().endsWith(".png")) {
            System.out.println("Invalid image.");
        } else {
            analiseVeredict(path, autoCorrect);
        }
    }

    private static void analiseVeredict(String path, boolean autoCorrect) {
        try {
            Analise analise = new Analise();
            List<Pixel> wrongPixels = analise.wrongPixels(path);
            List<Pixel> skin = analise.loadSkin(path);
            String skinTypeMap = analise.identifyFacesTipe(path);

            File file = new File(path);
            BufferedImage skinArchive = ImageIO.read(file);

            Faces faces = new Faces();
            System.out.println(skinTypeMap);

            HashMap<String, String> listFaces = faces.listFaces();
            List<Face> mapFaces = faces.mapsFaces(skinTypeMap, listFaces);
            List<String> errors = analise.verdict(wrongPixels, skin, mapFaces);
            for (String error : errors) {
                System.out.println("   " + error);
            }

            if (autoCorrect && !errors.getFirst().contains("Valid skin")) {
                System.out.println("\n----");
                List<String> corrections = new ArrayList<>();

                for (Pixel pixel : wrongPixels) {
                    if (skinArchive.getRGB(pixel.x, pixel.y) != 0) {
                        skinArchive.setRGB(pixel.x, pixel.y, 0);
                        corrections.add("wrong pixel removed");
                    }
                }

                for (String error : errors) {
                    if (error.contains("(wrong pixel alpha in ")) {
                        String[] pixelLocation = error.substring(error.lastIndexOf(")") + 1).split(",");
                        int x = Integer.parseInt(pixelLocation[0].trim());
                        int y = Integer.parseInt(pixelLocation[1].trim());

                        Color color = new Color(skinArchive.getRGB(x, y));
                        skinArchive.setRGB(x, y, color.getRGB());
                        corrections.add("wrong pixel alpha changed");
                    }
                }

                if (corrections.isEmpty()) {
                    corrections.add("no corrections were made");
                }

                for (String correction : corrections) {
                    System.out.println(correction);
                }

                File destiny = new File(file.getParent() + "\\" + "✓" + file.getName());
                ImageIO.write(skinArchive, "png", destiny);

                System.out.println("----");
            }
        } catch (Exception e) {
            System.out.println("Invalid image.");
        }
    }
}