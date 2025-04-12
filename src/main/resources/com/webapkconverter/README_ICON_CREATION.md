# Icon Creation Guide for WebToApkConverter

This document provides a step-by-step guide for creating the required icons for the WebToApkConverter application.

## Required Icons

This application needs two icon files:

1. **app_icon.png** - The main application icon
2. **default_icon.png** - Default icon for generated APKs

## Creation Methods

### Option 1: Using Online Icon Generators

For a quick solution, you can use these online tools:

#### For app_icon.png:
1. Visit [Android Asset Studio](https://romannurik.github.io/AndroidAssetStudio/index.html)
2. Select "Launcher icons" from the options
3. Use the following settings:
   - Foreground: Upload a web browser or conversion image
   - Background color: #3498db
   - Padding: 16%
   - Shape: Square
4. Download the ZIP file and extract the highest resolution version (xxxhdpi)
5. Add a phone shape element in green (#2ecc71) using an image editor

#### For default_icon.png:
1. Visit [Android Asset Studio](https://romannurik.github.io/AndroidAssetStudio/index.html)
2. Select "Launcher icons" from the options
3. Use the following settings:
   - Foreground: Text "A" or a simple Android symbol
   - Text color (if using text): #7f8c8d
   - Background color: #95a5a6
   - Padding: 16%
   - Shape: Square
4. Download the ZIP file and extract the highest resolution version (xxxhdpi)

### Option 2: Using GIMP (Free Image Editor)

#### For app_icon.png:
1. Download and install [GIMP](https://www.gimp.org/downloads/)
2. Create a new 256×256 image with transparency
3. Create layers for the browser, phone, and arrow elements
4. Use the Rectangle Select tool to create the browser shape
5. Fill with #3498db color
6. Add a white rectangle at the top for the address bar
7. Create a phone shape on a new layer
8. Fill with #2ecc71 color
9. Add a white arrow connecting the two
10. Export as PNG with transparency

#### For default_icon.png:
1. Create a new 256×256 image with transparency
2. Use the Rectangle Select tool with rounded corners
3. Fill with #95a5a6 color
4. Add simple details with #7f8c8d color
5. Export as PNG with transparency

### Option 3: Using Adobe Photoshop/Illustrator

If you have access to Adobe products, detailed steps are included in the .spec files.

## Verification

After creating your icons:

1. Place them in the `src/main/resources/com/webapkconverter/` directory
2. Run the application to verify they appear correctly
3. Test at different sizes to ensure clarity

## Specifications

For complete technical specifications, please see:
- app_icon.png.spec
- default_icon.png.spec

## Need Help?

If you're not able to create these icons yourself, consider:

1. Using placeholder icons temporarily
2. Hiring a designer on Fiverr or Upwork
3. Using icon sets from [Material Design Icons](https://materialdesignicons.com/) (ensure proper licensing)

