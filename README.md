# Skin Validator

Skin Validator is a Java-based tool designed to provide a simple and efficient alternative to the Bedrock Explorer **Skin Assistance** tool. This application checks the integrity and validity of skin files, ensuring that they meet specific criteria before use.

## Warning
- Before starting, rename the skin file with the ending _a for 3px arm (alex version) or with the ending _s for 4px arm (steve version), if the skin does not have any of these endings it will be considered a 4px skin (steve version).

## Face map
- When a face is irregular, the code indicates the name of the face that is wrong. To know which face it is, this is the face map.
  <div align="center">
    <img src="https://github.com/user-attachments/assets/7f203f2d-e580-4596-b23b-713869a04b3d" alt="MapAllFaces" width="400"/>
  </div>

## Features
- **Pixel Validation**: Checks individual pixels for correctness.
- **Transparency Check**: Checks whether transparency is fully opaque or fully transparent, avoiding unexpected results.
- **Face Validation**: Checks if each face has all pixels the same color.

## Requirements
- Java 8 or higher
