package Main;

import Models.Analise;
import Models.Face;
import Models.Faces;
import Models.Pixel;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the file path or path: ");
        String path = sc.nextLine();
        sc.close();
        File file = new File(path);

        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    System.out.println(f.getName());
                    if (f.isFile() && f.getName().toLowerCase().endsWith(".png")) {
                        analiseVeredict(f.getAbsolutePath());
                    } else {
                        System.out.println("Invalid image.");
                    }
                    System.out.println(" ");
                }
            }
        } else if (!path.toLowerCase().endsWith(".png")) {
            System.out.println("Invalid image.");
        } else {
            analiseVeredict(path);
        }
    }

    private static void analiseVeredict(String path) {
        try {
            Analise analise = new Analise();
            List<Pixel> wrongPixels = analise.wrongPixels(path);
            List<Pixel> skin = analise.loadSkin(path);
            String skinTypeMap = analise.identifyFacesTipe(path);

            Faces faces = new Faces();
            System.out.println(skinTypeMap);

            HashMap<String, String> listFaces = faces.listFaces();
            List<Face> mapFaces = faces.mapsFaces(skinTypeMap, listFaces);
            List<String> errors = analise.verdict(wrongPixels, skin, mapFaces);
            for (String error : errors) {
                System.out.println("   " + error);
            }

        } catch (Exception e) {
            System.out.println("Invalid image.");
        }
    }
}