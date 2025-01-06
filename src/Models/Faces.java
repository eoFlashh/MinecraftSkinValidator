package Models;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

public class Faces {
    public HashMap<String, String> listFaces() {
        HashMap<String, String> faces = new HashMap<>();

        faces.put("255,216,0", "Head Right");
        faces.put("0,19,127", "RLeg Right");
        faces.put("124,142,0", "Rleg 2 Right");
        faces.put("38,32,0", "LLeg 2 Right");
        faces.put("0,255,255", "Rleg Top");
        faces.put("0,38,255", "RLeg Front");
        faces.put("255,54,0", "Rleg 2 Top");
        faces.put("248,255,0", "RLeg 2 Front");
        faces.put("76,0,0", "LLeg 2 Top");
        faces.put("38,0,0", "LLeg 2 Front");
        faces.put("255,0,0", "Head Top");
        faces.put("255,106,0", "Head Front");
        faces.put("0,127,127", "RLeg Bottom");
        faces.put("0,74,127", "Rleg Left");
        faces.put("142,27,0", "RLeg 2 Bottom");
        faces.put("142,92,0", "RLeg 2 Left");
        faces.put("76,32,0", "LLeg 2 Bottom");
        faces.put("38,15,0", "LLeg 2 Left");
        faces.put("0,148,255", "RLeg Back");
        faces.put("255,186,0", "RLeg 2 Back");
        faces.put("76,65,0", "LLeg 2 Back");
        faces.put("127,0,0", "Head Bottom");
        faces.put("127,51,0", "Head Left");
        faces.put("72,0,255", "Body Right");
        faces.put("112,255,0", "Body 2 Right");
        faces.put("95,79,0", "LLeg Right");
        faces.put("178,0,255", "Body Top");
        faces.put("33,0,127", "Body Front");
        faces.put("0,255,0", "Body 2 Top");
        faces.put("59,142,0", "Body 2 Front");
        faces.put("191,0,0", "LLeg Top");
        faces.put("95,0,0", "LLeg Front");
        faces.put("127,106,0", "Head Back");
        faces.put("191,79,0", "LLeg Bottom");
        faces.put("95,38,0", "LLeg Left");
        faces.put("87,0,127", "Body Bottom");
        faces.put("255,0,220", "Body Left");
        faces.put("0,142,0", "Body 2 Bottom");
        faces.put("0,255,97", "Body 2 Left");
        faces.put("191,162,0", "LLeg Back");
        faces.put("182,255,0", "Head 2 Right");
        faces.put("127,0,110", "Body Back");
        faces.put("0,142,48", "Body 2 Back");
        faces.put("0,95,52", "LArm Right");
        faces.put("136,191,0", "LArm Top");
        faces.put("68,95,0", "LArm Front");
        faces.put("0,255,144", "Head 2 Top");
        faces.put("91,127,0", "Head 2 Front");
        faces.put("255,0,110", "RArm Right");
        faces.put("0,255,233", "RArm 2 Right");
        faces.put("57,191,0", "LArm Bottom");
        faces.put("0,95,10", "LArm Left");
        faces.put("255,127,127", "RArm Top");
        faces.put("127,0,55", "RArm Front");
        faces.put("112,228,255", "RArm 2 Top");
        faces.put("0,142,116", "RArm 2 Front");
        faces.put("0,191,108", "LArm Back");
        faces.put("0,127,70", "Head 2 Bottom");
        faces.put("76,255,0", "Head 2 Left");
        faces.put("127,63,63", "RArm Bottom");
        faces.put("255,178,127", "RArm Left");
        faces.put("55,113,135", "RArm 2 Bottom");
        faces.put("112,165,255", "RArm 2 Left");
        faces.put("0,66,36", "LArm 2 Right");
        faces.put("255,233,127", "RArm Back");
        faces.put("127,112,255", "RArm 2 Back");
        faces.put("95,134,0", "LArm 2 Top");
        faces.put("48,66,0", "LArm 2 Front");
        faces.put("38,127,0", "Head 2 Back");
        faces.put("40,134,0", "LArm 2 Bottom");
        faces.put("0,66,7", "LArm 2 Left");
        faces.put("0,134,76", "LArm 2 Back");

        return faces;
    }

    public List<Face> mapsFaces(String path, HashMap<String, String> faces) throws IOException {
        Convert convert = new Convert();
        List<Pixel> skin = convert.convertSkin(path);
        List<Face> mapFaces = new ArrayList<>();

        for (int i = 0; i < skin.size(); i++) {
            Pixel pixel = skin.get(i);
            Color pixelRGB = new Color(pixel.color.getRGB());
            StringBuilder sb = new StringBuilder();
            sb.append(pixelRGB.getRed()).append(",").append(pixelRGB.getGreen()).append(",").append(pixelRGB.getBlue());
            if (faces.containsKey(sb.toString())) {
                if (verifyFace(pixelRGB, mapFaces)) {
                    Face face = getFace(pixelRGB, mapFaces);
                    face.pixels.add(pixel);
                } else {
                    Face face = new Face(faces.get(sb.toString()), pixelRGB);
                    face.pixels.add(pixel);
                    mapFaces.add(face);
                }
            }
        }
        return mapFaces;
    }

    public String getFaceName(String location, List<Face> facesCords) {
        String faceName = "none";
        for (Face face : facesCords) {
            for (Pixel pixel : face.pixels) {
                if (pixel.pixelLocation().equals(location)) {
                    faceName = face.name;
                }
            }
        }
        return faceName;
    }

    private boolean verifyFace(Color color, List<Face> faces) {
        return faces.stream().anyMatch(face -> face.color.getRGB() == color.getRGB());
    }

    private Face getFace(Color color, List<Face> faces) {
        return faces.stream().filter(face -> face.color.getRGB() == color.getRGB()).findFirst().orElse(null);
    }
}
