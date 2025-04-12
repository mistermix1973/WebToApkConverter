# Default APK Icon Specification (default_icon.png)

This file contains specifications for creating the default icon used for generated APKs.

## Technical Requirements
- Dimensions: 256×256 pixels
- Format: PNG with alpha channel transparency
- Color Depth: 32-bit (8 bits per channel including alpha)
- Primary Colors:
  * Main Fill: #95a5a6 (RGB: 149, 165, 166)
  * Details/Shadows: #7f8c8d (RGB: 127, 140, 141)
  * Highlights: #ffffff (RGB: 255, 255, 255) with reduced opacity

## Design Elements
1. **Adaptive Icon Shape**:
   - Use the "squircle" shape (square with heavily rounded corners)
   - This follows Android's adaptive icon guidelines
   - Shape should have 16px padding from all canvas edges
   - Fill color: #95a5a6 (gray)

2. **Simple Android Elements**:
   - Minimalist Android robot features or app symbol
   - Subtle details using #7f8c8d (darker gray)
   - Optional: simple geometric pattern or grid
   - Keep details minimal for clarity at small sizes

3. **Material Design Elements**:
   - Subtle drop shadow (1dp elevation)
   - Very slight gradient from bottom-right to top-left
   - Clean, geometric shapes
   - Subtle highlight on top edge (#ffffff at 10% opacity)

4. **Layout & Spacing**:
   - 16px padding from all edges of the canvas
   - Icon should be centered within the canvas
   - Proper optical centering (may need slight adjustment from mathematical center)

## Optimizations
- Icon should remain recognizable at small sizes (16×16, 32×32)
- Test on both light and dark backgrounds
- Ensure transparency renders correctly
- Simple enough to serve as a placeholder for any generated app

When complete, save as default_icon.png in the src/main/resources/com/webapkconverter/ directory.

