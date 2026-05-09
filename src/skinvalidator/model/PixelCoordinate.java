package skinvalidator.model;

import java.util.Objects;

public final class PixelCoordinate {
    private final int x;
    private final int y;

    public PixelCoordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PixelCoordinate)) {
            return false;
        }
        PixelCoordinate that = (PixelCoordinate) o;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return x + "," + y;
    }
}
