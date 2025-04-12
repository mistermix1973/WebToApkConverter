# Icon Fallback Method

If you're unable to create PNG icons using the provided scripts or SVG files, you can use these fallback methods to quickly create functional placeholder icons.

## Method 1: Download Pre-made Icons

1. Download these pre-made icons that match the specifications:
   - [App Icon Direct Link](https://iili.io/JX0iHb2.png)
   - [Default Icon Direct Link](https://iili.io/JX0ivls.png)

2. Save them as:
   - `src/main/resources/com/webapkconverter/app_icon.png`
   - `src/main/resources/com/webapkconverter/default_icon.png`

## Method 2: Create Minimal Placeholder Icons

If you just need basic placeholders to make the application functional:

### Using Paint (Windows)

1. Open Paint
2. Create a new 256×256 pixel image
3. Fill with blue (#3498db) for app_icon.png or gray (#95a5a6) for default_icon.png
4. Save as PNG in the correct directory

### Using Preview (macOS)

1. Create a new image with 256×256 pixels
2. Fill with the appropriate color
3. Export as PNG to the correct location

## Method 3: Use Emoji-Based Icons

If you have an image editor that supports text and export to PNG:

1. Create a 256×256 pixel image with transparent background
2. For app_icon.png: Add 🌐➡️📱 emojis
3. For default_icon.png: Add 📱 or 🤖 emoji
4. Export as PNG

## Testing Your Icons

After creating your icons, test them by running the application. If they don't appear:

1. Make sure they're exactly 256×256 pixels
2. Ensure they're saved in the correct directory with the exact filenames
3. Check that they're in PNG format with transparency
4. Restart the application

When successful, you should see your icons in the application window and in the taskbar/dock when the app is running.

