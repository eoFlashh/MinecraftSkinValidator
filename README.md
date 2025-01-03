# Skin Validator

Skin Validator is a Java-based tool designed to provide a simple and efficient alternative to the **Skin Assistance** tool from Bedrock Explorer. This application verifies the integrity and validity of skin files, ensuring they meet specific criteria before use.

## Features
- **PNG Verification**: Ensures the uploaded file is a valid PNG image.
- **Pixel Validation**: Checks individual pixels for correctness.
- **Transparency Check**: Verifies that transparency is either fully opaque or fully transparent, avoiding unexpected results.
- **Comparison Mode**: Compares user-provided skins against a reference image (`correct.png`) to highlight discrepancies.

## Requirements
- Java 8 or higher
- A valid `correct.png` reference file for comparison
- Input skin files in PNG format


- If you want 4px arm skins (steve version), change to `correct.png`
