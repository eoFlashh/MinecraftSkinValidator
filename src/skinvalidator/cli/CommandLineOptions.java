package skinvalidator.cli;

import java.nio.file.Path;
import java.nio.file.Paths;

public final class CommandLineOptions {
    private final Path inputPath;
    private final boolean fix;
    private final boolean recursive;
    private final boolean help;
    private final String error;

    private CommandLineOptions(Path inputPath, boolean fix, boolean recursive, boolean help, String error) {
        this.inputPath = inputPath;
        this.fix = fix;
        this.recursive = recursive;
        this.help = help;
        this.error = error;
    }

    public static CommandLineOptions parse(String[] args) {
        if (args.length == 0) {
            return new CommandLineOptions(null, false, false, true, null);
        }

        Path inputPath = null;
        boolean fix = false;
        boolean recursive = false;

        for (String arg : args) {
            switch (arg) {
                case "--fix":
                    fix = true;
                    break;
                case "--recursive":
                    recursive = true;
                    break;
                case "--help":
                case "-h":
                    return new CommandLineOptions(null, false, false, true, null);
                default:
                    if (arg.startsWith("--")) {
                        return new CommandLineOptions(null, false, false, false, "Unknown option: " + arg);
                    }
                    if (inputPath != null) {
                        return new CommandLineOptions(null, false, false, false, "Only one file or folder path is supported.");
                    }
                    inputPath = Paths.get(arg);
                    break;
            }
        }

        if (inputPath == null) {
            return new CommandLineOptions(null, false, false, true, null);
        }

        return new CommandLineOptions(inputPath, fix, recursive, false, null);
    }

    public Path inputPath() {
        return inputPath;
    }

    public boolean fix() {
        return fix;
    }

    public boolean recursive() {
        return recursive;
    }

    public boolean help() {
        return help;
    }

    public String error() {
        return error;
    }
}
