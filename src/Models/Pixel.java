package Models;

import java.awt.*;

public class Pixel {
    public Color color;
    public int x;
    public int y;

    public Pixel(Color color, int x, int y) {
        this.color = color;
        this.x = x;
        this.y = y;
    }

    public String pixelLocation(){
        StringBuilder sb = new StringBuilder();
        sb.append(x).append(",").append(y);
        return sb.toString();
    }
}
