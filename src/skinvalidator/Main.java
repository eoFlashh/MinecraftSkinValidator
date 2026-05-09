package skinvalidator;

import skinvalidator.cli.CommandLineOptions;
import skinvalidator.cli.SkinValidatorCli;

public class Main {
    public static void main(String[] args) {
        CommandLineOptions options = CommandLineOptions.parse(args);
        new SkinValidatorCli().run(options);
    }
}
