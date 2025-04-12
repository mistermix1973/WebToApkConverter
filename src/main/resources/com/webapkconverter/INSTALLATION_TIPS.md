# Installation and Icon Creation Tips

## Installing Required Tools to Create Icons

1. **For professional-quality icons**, consider installing one of the following tools:
   - Adobe Photoshop or Illustrator (commercial)
   - Figma (free for basic use, web-based)
   - GIMP (free and open-source)
   - Inkscape (free and open-source, vector-based)

2. **For quick icon creation**, you can use online tools:
   - [Android Asset Studio](https://romannurik.github.io/AndroidAssetStudio/index.html)
   - [Iconscout](https://iconscout.com/icon-editor)
   - [Canva](https://www.canva.com)

## Creating the Icons

### App Icon (app_icon.png)
1. Create a new 256×256 pixel document with transparency
2. Create a browser-style window with #3498db (blue) color
3. Add a phone shape using #2ecc71 (green) color
4. Add transformation/arrow elements showing the conversion process
5. Use white (#ffffff) for details and highlights
6. Export as PNG with transparency

### Default Icon (default_icon.png)
1. Create a new 256×256 pixel document with transparency
2. Draw a squircle or Android adaptive icon shape
3. Fill with #95a5a6 (gray) color
4. Add simple highlights with #7f8c8d (darker gray)
5. Keep the design minimal and clean
6. Export as PNG with transparency

## Placing the Icons
Save both files in the `src/main/resources/com/webapkconverter/` directory.

## Alternative: Using Placeholder Icons

If you're developing and need temporary icons, you can use simple placeholder images until proper icons are designed. However, for production use, professional-quality icons are recommended.

## Resources

For more information on Material Design icons, see:
- [Material Design Icons Guidelines](https://material.io/design/iconography/system-icons.html)
- [Android Icon Design Guidelines](https://developer.android.com/guide/practices/ui_guidelines/icon_design)

