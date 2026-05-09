package skinvalidator.model;

import java.nio.file.Path;

public enum SkinType {
    ALEX_64("_a.png", "correct_a.png", "Alex 64x64"),
    ALEX_128("_a32.png", "correct_a32.png", "Alex 128x128"),
    STEVE_128("_s32.png", "correct_s32.png", "Steve 128x128"),
    STEVE_64("_s.png", "correct_s.png", "Steve 64x64");

    private static final SkinType FALLBACK = STEVE_64;

    private final String suffix;
    private final String referenceFile;
    private final String displayName;

    SkinType(String suffix, String referenceFile, String displayName) {
        this.suffix = suffix;
        this.referenceFile = referenceFile;
        this.displayName = displayName;
    }

    public static SkinType fromPath(Path path) {
        String fileName = path.getFileName().toString().toLowerCase();
        if (fileName.endsWith(ALEX_128.suffix)) {
            return ALEX_128;
        }
        if (fileName.endsWith(STEVE_128.suffix)) {
            return STEVE_128;
        }
        if (fileName.endsWith(ALEX_64.suffix)) {
            return ALEX_64;
        }
        if (fileName.endsWith(STEVE_64.suffix)) {
            return STEVE_64;
        }
        return FALLBACK;
    }

    public Path referencePath() {
        return Path.of(referenceFile);
    }

    public String displayName() {
        return displayName;
    }
}
