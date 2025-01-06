package Main;

import Models.Analise;
import Models.Face;
import Models.Faces;
import Models.Pixel;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String args[]) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the file path: ");
        String path = sc.nextLine();
        sc.close();
        Analise analise = new Analise();
        List<Pixel> wrongPixels;
        List<Pixel> skin;

        try {
            if (!path.endsWith(".png")) {
                System.out.println("Invalid image.");
            } else {
                wrongPixels = analise.wrongPixels(path);
                skin = analise.loadSkin(path);
                String skinTypeMap = analise.identifyFacesTipe(path);
                Faces faces = new Faces();
                System.out.println(skinTypeMap);

                HashMap<String, String> listFaces = faces.listFaces();
                List<Face> mapFaces = faces.mapsFaces(skinTypeMap, listFaces);
                List<String> errors = analise.verdict(wrongPixels, skin, mapFaces);
                for (int i = 0; i < errors.size(); i++) {
                    System.out.println(errors.get(i));
                }
            }
        } catch (Exception e) {
            System.out.println("Invalid image.");
        }
    }
}