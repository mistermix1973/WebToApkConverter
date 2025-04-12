# Icon Placeholders and Creation Instructions

This file provides placeholder descriptions and precise specifications for creating the two icon files needed by the WebToApkConverter application.

## Required Icons

### 1. app_icon.png
This is the main application icon that represents the Web to APK conversion functionality.

**Technical Specifications:**
- Dimensions: 256×256 pixels
- Format: PNG with alpha channel transparency
- Color mode: RGB
- Key colors: 
  * Blue: #3498db (RGB: 52, 152, 219)
  * Green: #2ecc71 (RGB: 46, 204, 113)
  * White: #ffffff (RGB: 255, 255, 255)
- Padding: 16px from all edges

**Design Description:**
- The icon should show a browser window (blue) transforming into a mobile phone (green)
- Left side: A simplified browser window in blue (#3498db)
- Right side: An Android phone outline in green (#2ecc71)
- Center: A transformation arrow or motion effect in white (#ffffff)
- Style: Material Design with flat shapes and minimal shadows
- The icon should be recognizable even when scaled down to 48×48 pixels

### 2. default_icon.png
This is the default icon used for generated APK files.

**Technical Specifications:**
- Dimensions: 256×256 pixels
- Format: PNG with alpha channel transparency
- Color mode: RGB
- Key colors:
  * Main gray: #95a5a6 (RGB: 149, 165, 166)
  * Darker gray: #7f8c8d (RGB: 127, 140, 141)
  * White: #ffffff (RGB: 255, 255, 255) for details
- Padding: 16px from all edges

**Design Description:**
- The icon should follow Android's adaptive icon shape (squircle)
- Main background in light gray (#95a5a6)
- Simple Android-style design elements in darker gray (#7f8c8d)
- Minimal details to ensure clarity at small sizes
- Clean, professional appearance suitable for any generated app

## Creation Methods

You can create these icons using:

1. **Professional design tools:**
   - Adobe Photoshop/Illustrator
   - Figma
   - Sketch
   - GIMP or Inkscape (free options)

2. **Online tools:**
   - [Android Asset Studio](https://romannurik.github.io/AndroidAssetStudio/icons-launcher.html)
   - [Iconscout](https://iconscout.com/icon-editor)

3. **Generated placeholders:**
   If you need temporary placeholders while developing, use:
   ```
   # For app_icon.png placeholder
   convert -size 256x256 xc:none -fill "#3498db" -draw "rectangle 16,16 240,240" -fill "#2ecc71" -draw "circle 192,128 192,196" app_icon.png
   
   # For default_icon.png placeholder
   convert -size 256x256 xc:none -fill "#95a5a6" -draw "roundrectangle 16,16 240,240 40,40" default_icon.png
   ```

See the `CREATE_ICONS.md` and `INSTALLATION_TIPS.md` files for more detailed creation instructions.

