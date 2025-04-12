# Converting SVG Icons to PNG

The application requires PNG format icons, but we've provided SVG versions which need to be converted. Here are multiple ways to convert the SVG files to PNG:

## Option 1: Using Online Converters

1. Visit one of these free online SVG to PNG converters:
   - [Convertio](https://convertio.co/svg-png/)
   - [SVG2PNG](https://svgtopng.com/)
   - [FreeConvert](https://www.freeconvert.com/svg-to-png)

2. Upload the SVG file
3. Set the dimensions to 256×256 pixels
4. Download the PNG file
5. Make sure to save it with the correct filename in the proper directory

## Option 2: Using Inkscape (Free Software)

1. Install [Inkscape](https://inkscape.org/release/inkscape-1.2/) (free and open-source)
2. Open the SVG file in Inkscape
3. Go to File > Export PNG Image
4. Set the dimensions to 256×256 pixels
5. Choose export area as "Page"
6. Set filename to app_icon.png or default_icon.png
7. Click "Export"

## Option 3: Using ImageMagick (Command Line)

If you have [ImageMagick](https://imagemagick.org/script/download.php) installed:

```
magick convert app_icon.svg -resize 256x256 app_icon.png
magick convert default_icon.svg -resize 256x256 default_icon.png
```

## Option 4: Using Adobe Illustrator

1. Open the SVG file in Illustrator
2. Go to File > Export > Export As
3. Choose PNG format
4. Set dimensions to 256×256 pixels
5. Set resolution to 72 ppi
6. Make sure transparency is checked
7. Click "Export"

## Verifying the Icons

After conversion:

1. Check that the PNG files have transparent backgrounds
2. Verify they are exactly 256×256 pixels
3. Place them in the src/main/resources/com/webapkconverter/ directory
4. Run the application to make sure they appear correctly

## Note on Placeholder Quality

These SVG files are intended as placeholders. For production-quality icons, consider:

1. Refining these designs in a proper design tool
2. Creating the icons from scratch following the specifications
3. Hiring a professional designer for polished results

