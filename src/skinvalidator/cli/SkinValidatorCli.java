package skinvalidator.cli;

import skinvalidator.validation.SkinValidationResult;
import skinvalidator.validation.SkinValidator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class SkinValidatorCli {
    private final SkinValidator validator = new SkinValidator();

    public void run(CommandLineOptions options) {
        if (options.error() != null) {
            System.out.println(options.error());
            printUsage();
            return;
        }

        if (options.help()) {
            printUsage();
            return;
        }

        Path inputPath = options.inputPath();
        if (!Files.exists(inputPath)) {
            System.out.println("Path not found: " + inputPath);
            return;
        }

        try {
            if (Files.isDirectory(inputPath)) {
                validateDirectory(inputPath, options);
            } else {
                validateFile(inputPath, options.fix(), false);
            }
        } catch (IOException e) {
            System.out.println("Unable to read path: " + inputPath);
        }
    }

    private void validateDirectory(Path directory, CommandLineOptions options) throws IOException {
        int depth = options.recursive() ? Integer.MAX_VALUE : 1;
        try (Stream<Path> paths = Files.walk(directory, depth)) {
            List<Path> pngFiles = paths
                    .filter(Files::isRegularFile)
                    .filter(this::isPng)
                    .filter(path -> !path.getFileName().toString().startsWith("✓"))
                    .sorted(Comparator.comparing(Path::toString))
                    .collect(Collectors.toList());

            if (pngFiles.isEmpty()) {
                System.out.println("No PNG files found.");
                return;
            }

            for (Path file : pngFiles) {
                validateFile(file, options.fix(), true);
                System.out.println();
            }
        }
    }

    private void validateFile(Path file, boolean fix, boolean fromDirectory) {
        if (!isPng(file)) {
            if (!fromDirectory) {
                System.out.println("Invalid image. Expected a .png file: " + file);
            }
            return;
        }

        System.out.println(file.getFileName());
        try {
            SkinValidationResult result = validator.validate(file);
            System.out.println("Skin type: " + result.skinType().displayName());

            for (String error : result.errors()) {
                System.out.println("   " + error);
            }

            if (fix && !result.valid()) {
                Path fixedFile = validator.fix(file, result);
                System.out.println("----");
                for (String correction : result.corrections()) {
                    System.out.println(correction);
                }
                System.out.println("Saved: " + fixedFile);
                System.out.println("----");
            }
        } catch (IOException e) {
            System.out.println("Invalid image.");
        }
    }

    private boolean isPng(Path path) {
        return path.getFileName().toString().toLowerCase().endsWith(".png");
    }

    private void printUsage() {
        System.out.println("Usage: java -cp out\\production\\FSkinValidate skinvalidator.Main <file-or-folder> [--fix] [--recursive]");
    }
}
