package Main;

import Models.Analise;
import Models.Pixel;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String args[]) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the file path: ");
        String path = sc.nextLine();
        Analise analise = new Analise();
        List<Pixel> wrongPixels;
        List<Pixel> skin;

        try {
            if (!path.endsWith(".png")) {
                System.out.println("Invalid image.");
            } else {
                wrongPixels = analise.wrongPixels(path);
                skin = analise.loadSkin(path);
                String status = analise.compare(wrongPixels, skin);
                System.out.println(status);
            }
        } catch (Exception e) {
            System.out.println("Invalid image.");
        }
    }
}