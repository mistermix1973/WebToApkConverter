# Quick Icon Conversion Guide

For the WebToApkConverter application to function properly, you need to convert the SVG icon files to PNG format. Here are the quickest ways to do this:

## Method 1: Using Web Browsers (Easiest)

1. **Open SVG in browser:**
   - Right-click `app_icon.svg` and open with Chrome, Firefox, or Edge
   - The SVG will render in the browser window

2. **Take a screenshot:**
   - Zoom in/out until the icon is clear and the correct size
   - Take a screenshot (Windows: Win+Shift+S, Mac: Cmd+Shift+4)
   - Crop to 256×256 pixels in any image editor

3. **Save as PNG:**
   - Save the cropped image as `app_icon.png`
   - Make sure to save with transparency if your screenshot tool supports it
   - Repeat for `default_icon.svg`

## Method 2: Online SVG to PNG Converters

1. Visit one of these free online converters:
   - [SVGtoPNG.com](https://svgtopng.com/)
   - [Convertio](https://convertio.co/svg-png/)
   - [CloudConvert](https://cloudconvert.com/svg-to-png)

2. Upload your SVG files
3. Set output to 256×256 pixels
4. Download the PNG files with the correct names

## Method 3: Using Developer Tools (For Technical Users)

If you have Node.js installed, you can use the SVG to PNG converter:

```
npm install -g svg-to-png
svg-to-png app_icon.svg --output ./ --width 256 --height 256
svg-to-png default_icon.svg --output ./ --width 256 --height 256
```

## Method 4: Using Pre-made PNG Files

If you don't want to convert the files yourself, you can download pre-made PNG icons that match similar specifications from these resources:

1. [Material Design Icons](https://materialdesignicons.com/)
2. [Flaticon](https://www.flaticon.com/search?word=web%20to%20app)
3. [Iconfinder](https://www.iconfinder.com/search/?q=web%20app)

Make sure to rename the downloaded files to `app_icon.png` and `default_icon.png`.

## Final Step

After obtaining the PNG files, place them in the following directory:
```
src/main/resources/com/webapkconverter/
```

Verify they appear correctly when running the application.

