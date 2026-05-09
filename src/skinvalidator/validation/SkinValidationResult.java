package skinvalidator.validation;

import skinvalidator.model.PixelCoordinate;
import skinvalidator.model.SkinType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class SkinValidationResult {
    private final SkinType skinType;
    private final List<String> errors;
    private final List<PixelCoordinate> forbiddenPixels;
    private final List<PixelCoordinate> partialAlphaPixels;
    private final List<String> corrections = new ArrayList<>();

    public SkinValidationResult(
            SkinType skinType,
            List<String> errors,
            List<PixelCoordinate> forbiddenPixels,
            List<PixelCoordinate> partialAlphaPixels
    ) {
        this.skinType = skinType;
        this.errors = errors.isEmpty()
                ? Collections.singletonList("Valid skin")
                : Collections.unmodifiableList(new ArrayList<>(errors));
        this.forbiddenPixels = Collections.unmodifiableList(new ArrayList<>(forbiddenPixels));
        this.partialAlphaPixels = Collections.unmodifiableList(new ArrayList<>(partialAlphaPixels));
    }

    public SkinType skinType() {
        return skinType;
    }

    public List<String> errors() {
        return errors;
    }

    public boolean valid() {
        return errors.size() == 1 && "Valid skin".equals(errors.get(0));
    }

    public List<PixelCoordinate> forbiddenPixels() {
        return forbiddenPixels;
    }

    public List<PixelCoordinate> partialAlphaPixels() {
        return partialAlphaPixels;
    }

    public void addCorrection(String correction) {
        corrections.add(correction);
    }

    public List<String> corrections() {
        if (corrections.isEmpty()) {
            return Collections.singletonList("no corrections were made");
        }
        return Collections.unmodifiableList(corrections);
    }
}
