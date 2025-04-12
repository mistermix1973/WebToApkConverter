# Step-by-Step Icon Creation Guide

This guide provides detailed instructions for creating the two icons required by the WebToApkConverter application. It uses free tools that are accessible to everyone.

## Quick Reference

| Icon | Purpose | Size | Colors | Style |
|------|---------|------|--------|-------|
| app_icon.png | Application icon | 256×256 pixels | Blue (#3498db), Green (#2ecc71) | Web-to-mobile conversion |
| default_icon.png | Default for generated APKs | 256×256 pixels | Gray (#95a5a6) | Simple Android icon |

## Using GIMP (Free and Open-Source)

### Creating app_icon.png

1. **Setup**
   - Download and install GIMP from [gimp.org](https://www.gimp.org/downloads/)
   - Start GIMP and create a new image: File > New
   - Set width and height to 256 pixels
   - Set Background to "Transparency"
   - Click "Create"

2. **Create Browser Window**
   - Create a new layer: Right-click on Layers panel > New Layer
   - Select Rectangle Select Tool (R)
   - Draw a rectangle covering most of the canvas
   - Select the Bucket Fill Tool (Shift+B)
   - Set fill color to #3498db (blue)
   - Click inside the rectangle to fill it

3. **Add Address Bar**
   - Create a new layer
   - Draw a smaller rectangle at the top of the browser window
   - Fill with white (#ffffff)
   - Add simple browser details (circles for buttons, etc.)

4. **Create Phone Shape**
   - Create a new layer
   - Draw a phone outline on the right side
   - Fill with #2ecc71 (green)
   - Add simple details like a screen

5. **Add Conversion Arrow**
   - Create a new layer
   - Draw an arrow from the browser to the phone
   - Use white (#ffffff) for visibility

6. **Export**
   - File > Export As
   - Name it "app_icon.png"
   - Select PNG format
   - Check "Save transparency"
   - Click "Export"

### Creating default_icon.png

1. **Setup**
   - Create a new 256×256 pixel image with transparency

2. **Create Android Icon Shape**
   - Create a new layer
   - Use the Ellipse Select Tool to create a rounded square shape
   - Fill with #95a5a6 (gray)

3. **Add Simple Details**
   - Add minimal Android robot features if desired
   - Keep it very simple and clean
   - Use darker gray (#7f8c8d) for details

4. **Export**
   - Export as "default_icon.png" in PNG format with transparency

## Using Online Tools

If you prefer an online solution:

1. Visit [Android Asset Studio](https://romannurik.github.io/AndroidAssetStudio/icons-launcher.html)
2. For app_icon.png:
   - Select "Image" and upload a web/browser image
   - Set foreground scale to around 80%
   - Set the background color to #3498db
   - Download the icon pack and extract the highest resolution version

3. For default_icon.png:
   - Select "Text" or "Clipart"
   - Enter "A" or select a simple Android clipart
   - Set background color to #95a5a6
   - Download and extract

## Final Steps

1. Save both PNG files to the `src/main/resources/com/webapkconverter/` directory
2. Verify they load correctly in the application
3. Ensure they are 256×256 pixels with transparency

