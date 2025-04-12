# WebToApkConverter Icon Specifications

This document provides detailed specifications for creating the two icon files required by the WebToApkConverter application.

## App Icon (app_icon.png)

The main application icon should represent the web-to-mobile conversion functionality with the following specifications:

- **Dimensions**: 256x256 pixels
- **Format**: PNG with alpha channel transparency
- **Design Style**: Material Design
- **Visual Concept**: Browser window transforming into a phone shape
- **Color Palette**:
  * Browser elements: #3498db (blue)
  * Phone elements: #2ecc71 (green)
  * Details/accents: #ffffff (white)
- **Design Elements**:
  * Browser window with simplified address bar
  * Arrow or transform effect showing conversion
  * Mobile phone outline
  * Clean, geometric shapes
  * Subtle shadow for depth (2dp elevation)
- **Padding**: 16px on all sides to ensure proper display across platforms

## Default Icon (default_icon.png)

This icon will be used as the default for generated APKs and should follow Android's icon design principles:

- **Dimensions**: 256x256 pixels
- **Format**: PNG with alpha channel transparency
- **Design Style**: Material Design Android app icon
- **Visual Concept**: Simple, recognizable Android app shape
- **Color Palette**:
  * Primary: #95a5a6 (gray)
  * Highlights: #7f8c8d (darker gray)
  * Details: #ffffff (white)
- **Design Elements**:
  * Standard Android app icon shape (squircle or adaptive icon shape)
  * Minimalist design
  * No text or complex details
  * Optional subtle gradient for depth
- **Padding**: 16px on all sides

## Design Guidelines

Both icons should:
- Follow Material Design guidelines for iconography
- Work well at both small and large sizes
- Have clean, crisp edges
- Use flat design with minimal shadows
- Be recognizable even at small sizes
- Have appropriate contrast for visibility

## Tools for Creation

Recommended tools for creating these icons:
- Adobe Illustrator or Photoshop
- Figma
- Sketch
- Inkscape (open source)
- GIMP (open source)

Save both files directly in the src/main/resources/com/webapkconverter/ directory.

