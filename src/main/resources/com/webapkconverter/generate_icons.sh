#!/bin/bash
# Script to generate PNG icons from SVG files for Linux/macOS users
# Requires either ImageMagick, Inkscape, or another SVG converter

RESOURCE_DIR="$(dirname "$0")"
echo "Generating PNG icons from SVG files..."
echo "Working directory: $RESOURCE_DIR"

APP_ICON_SVG="$RESOURCE_DIR/app_icon.svg"
APP_ICON_PNG="$RESOURCE_DIR/app_icon.png"
DEFAULT_ICON_SVG="$RESOURCE_DIR/default_icon.svg"
DEFAULT_ICON_PNG="$RESOURCE_DIR/default_icon.png"

# Function to check if a command exists
command_exists() {
    command -v "$1" >/dev/null 2>&1
}

# Try different methods to convert SVG to PNG
convert_svg_to_png() {
    local svg_file="$1"
    local png_file="$2"
    
    if [ ! -f "$svg_file" ]; then
        echo "ERROR: SVG file not found: $svg_file"
        return 1
    fi
    
    # Try ImageMagick (convert or magick)
    if command_exists convert; then
        echo "Using ImageMagick convert to generate PNG..."
        convert "$svg_file" -resize 256x256 "$png_file"
        if [ -f "$png_file" ]; then
            echo "Successfully converted $svg_file to $png_file using ImageMagick"
            return 0
        fi
    elif command_exists magick; then
        echo "Using ImageMagick magick to generate PNG..."
        magick convert "$svg_file" -resize 256x256 "$png_file"
        if [ -f "$png_file" ]; then
            echo "Successfully converted $svg_file to $png_file using ImageMagick"
            return 0
        fi
    fi
    
    # Try Inkscape
    if command_exists inkscape; then
        echo "Using Inkscape to generate PNG..."
        inkscape --export-filename="$png_file" --export-width=256 --export-height=256 "$svg_file"
        if [ -f "$png_file" ]; then
            echo "Successfully converted $svg_file to $png_file using Inkscape"
            return 0
        fi
    fi
    
    # Try librsvg (rsvg-convert)
    if command_exists rsvg-convert; then
        echo "Using rsvg-convert to generate PNG..."
        rsvg-convert -w 256 -h 256 "$svg_file" -o "$png_file"
        if [ -f "$png_file" ]; then
            echo "Successfully converted $svg_file to $png_file using rsvg-convert"
            return 0
        fi
    fi
    
    echo "ERROR: Could not convert $svg_file to PNG. Please install ImageMagick, Inkscape, or use an online converter."
    return 1
}

# Convert app icon
convert_svg_to_png "$APP_ICON_SVG" "$APP_ICON_PNG"
APP_ICON_RESULT=$?

# Convert default icon
convert_svg_to_png "$DEFAULT_ICON_SVG" "$DEFAULT_ICON_PNG"
DEFAULT_ICON_RESULT=$?

# Check results
if [ $APP_ICON_RESULT -eq 0 ] && [ $DEFAULT_ICON_RESULT -eq 0 ]; then
    echo "SUCCESS: Both PNG icons were created successfully!"
    
    # Display file sizes
    APP_ICON_SIZE=$(stat -c%s "$APP_ICON_PNG" 2>/dev/null || stat -f%z "$APP_ICON_PNG" 2>/dev/null)
    DEFAULT_ICON_SIZE=$(stat -c%s "$DEFAULT_ICON_PNG" 2>/dev/null || stat -f%z "$DEFAULT_ICON_PNG" 2>/dev/null)
    
    echo "app_icon.png: $APP_ICON_SIZE bytes"
    echo "default_icon.png: $DEFAULT_ICON_SIZE bytes"
    
    echo "Icons are ready to use in the application."
else
    echo "Warning: Some icons were not created properly."
    echo ""
    echo "Alternative methods to create the icons:"
    echo "1. Use an online converter like https://convertio.co/svg-png/"
    echo "2. Open the SVG in a web browser and take a screenshot"
    echo "3. Create the icons manually following the specifications in the .spec files"
    echo ""
    echo "Make sure the final icons are saved as:"
    echo "$APP_ICON_PNG"
    echo "$DEFAULT_ICON_PNG"
fi

