# Application Icon Specification (app_icon.png)

This file contains specifications for creating the main application icon.

## Technical Requirements
- Dimensions: 256×256 pixels
- Format: PNG with alpha channel transparency
- Color Depth: 32-bit (8 bits per channel including alpha)
- Primary Colors:
  * Browser Window: #3498db (RGB: 52, 152, 219)
  * Phone Shape: #2ecc71 (RGB: 46, 204, 113)
  * Details/Accents: #ffffff (RGB: 255, 255, 255)
  * Shadows: Semi-transparent black (#000000 with 20-30% opacity)

## Design Elements
1. **Browser Window**:
   - Rectangle with slightly rounded corners (2px radius)
   - Fill color: #3498db (blue)
   - Position: Left side of the icon, slightly overlapping with phone
   - Size: Approximately 60% of the total icon width
   - White address bar at top (height ~10% of browser height)
   - Simple navigation buttons (circles) in top-left corner

2. **Phone Shape**:
   - Material Design phone outline
   - Fill color: #2ecc71 (green)
   - Position: Right side of the icon, slightly overlapping with browser
   - Size: Approximately 60% of the total icon height
   - Screen area slightly darker (#27ae60)
   - Rounded corners (8px radius)

3. **Transformation Arrow/Effect**:
   - White curved arrow from browser to phone
   - Alternatively: motion lines or transformation effect
   - Should visually indicate conversion/transformation
   - Color: #ffffff (white) with subtle gradient/shadow

4. **Layout & Spacing**:
   - 16px padding from all edges of the canvas
   - Browser and phone elements should slightly overlap
   - Visual weight balanced between left and right sides

5. **Material Design Elements**:
   - Subtle drop shadow (2dp elevation)
   - Clean, geometric shapes
   - Flat design with minimal 3D effects
   - Proper anti-aliasing on all edges

## Optimizations
- Icon should remain recognizable at small sizes (16×16, 32×32)
- Test on both light and dark backgrounds
- Ensure transparency renders correctly
- Verify that the color contrast is sufficient for accessibility

When complete, save as app_icon.png in the src/main/resources/com/webapkconverter/ directory.

