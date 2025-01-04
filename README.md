# Skin Validator

Skin Validator is a Java-based tool designed to provide a simple and efficient alternative to the Bedrock Explorer **Skin Assistance** tool. This application checks the integrity and validity of skin files, ensuring that they meet specific criteria before use.

##warning
- before starting, rename the skin file with the ending _a for 3px arm (alex version) or with the ending _s for 4px arm (steve version)

##Features
- **Pixel Validation**: Checks individual pixels for correctness.
- **Transparency Check**: Checks whether transparency is fully opaque or fully transparent, avoiding unexpected results.
- **Comparison Mode**: Compares user-provided skins to the reference image (`correct.png`) to highlight discrepancies.

##Requirements
- Java 8 or higher
- A valid `correct.png` reference file for comparison
- Input skin files in PNG format
