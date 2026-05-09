all valid# Skin Validator

# This project is no longer maintained (the code gives me a headache). It still works like a charm, it’s just ugly.

Skin Validator is a Java-based tool designed to provide a simple and efficient alternative to the Bedrock Explorer **Skin Assistance** tool. This application checks the integrity and validity of skin files, ensuring that they meet specific criteria before use.

## Warning
- Before starting, rename the skin file with the ending corresponding to the arm width and desired resolution:

* **For 16x16 skins:** Use the ending `_a` for 3px arm (Alex version) or `_s` for 4px arm (Steve version).
* **For 32x32 skins:** Use the ending `_a32` for 6px arm (Alex HD version) or `_s32` for 8px arm (Steve HD version).

If the skin file does not have any of these endings, it will automatically be considered a 16x16 skin with a 4px arm (Steve version).

## Face map
- When a face is irregular, the code indicates the name of the face that is wrong. To know which face it is, this is the face map.
  <div align="center">
    <img src="https://github.com/user-attachments/assets/7f203f2d-e580-4596-b23b-713869a04b3d" alt="MapAllFaces" width="400"/>
  </div>

## Features
- **Pixel Validation**: Checks individual pixels for correctness.
- **Transparency Check**: Checks whether transparency is fully opaque or fully transparent, avoiding unexpected results.
- **Face Validation**: Checks if each face has all pixels the same color.

## Usage
```powershell
java -cp out\production\FSkinValidate skinvalidator.Main <file-or-folder> [--fix] [--recursive]
```

- `--fix`: creates a corrected copy prefixed with `✓`, fixing only forbidden pixels and partial alpha pixels.
- `--recursive`: when the input is a folder, scans PNG files in subfolders too.

## Requirements
- Java 22 or higher

## Build
```powershell
javac -encoding UTF-8 -d out\production\FSkinValidate <all .java files under src>
```
