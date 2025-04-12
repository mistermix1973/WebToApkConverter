# WebToApkConverter

A desktop application that converts websites and web applications into native Android APK files. WebToApkConverter creates standalone Android applications that display your chosen website in a WebView, allowing users to install and use web content as if it were a native app.

![WebToApkConverter Interface](src/main/resources/com/webapkconverter/app_icon.png)

## Features

- **Simple Conversion Process**: Convert any website to an Android APK with just a few clicks
- **Custom App Names**: Set your own application name for the generated APK
- **Custom Package Names**: Specify custom package identifiers for your Android application
- **Custom Icons**: Add your own app icons or use the default icon provided
- **Modern Android Support**: Generates APKs compatible with modern Android versions (Android 5.0+)
- **WebView Optimizations**: Configures Android WebView with optimal settings for web applications
- **Desktop GUI**: User-friendly JavaFX interface for easy configuration

## Requirements

- Java 17 or higher
- Android SDK installed (with build tools version 35.0.0 or compatible)
- Gradle (automatically downloaded during build process)
- For building real APKs: Android build tools and SDK configured

## Installation

### Option 1: Using the pre-built JAR

1. Download the latest WebToApkConverter JAR from the releases page
2. Ensure you have Java 17+ installed on your system
3. Run the application with:
   ```
   java -jar WebToApkConverter-1.0-SNAPSHOT-jar-with-dependencies.jar
   ```

### Option 2: Build from source

1. Clone this repository
   ```
   git clone https://github.com/yourusername/WebToApkConverter.git
   cd WebToApkConverter
   ```

2. Build with Maven
   ```
   mvn clean package
   ```

3. Run the application
   ```
   java -jar target/WebToApkConverter-1.0-SNAPSHOT-jar-with-dependencies.jar
   ```

## Usage Instructions

1. **Launch the application** using one of the installation methods above
2. **Enter the website URL** you want to convert (must begin with http:// or https://)
3. **Enter an app name** for your Android application
4. **Enter a package name** (e.g., com.example.myapp) - must follow Java package naming conventions
5. **Select an icon** (optional) - click "Select Icon" to choose a custom icon, or use the default
6. **Click "Convert"** and select an output directory
7. **Wait for processing** - the application will generate the Android project and build the APK
8. **Install the APK** on your Android device once conversion is complete

## Android SDK Configuration

This application requires the Android SDK to be properly installed. The application looks for the Android SDK at:

```
%LOCALAPPDATA%\Android\sdk (Windows)
```

You can install Android Studio to get the complete SDK, or install the command-line tools separately.

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Technical Details

WebToApkConverter creates a complete Android project with the following components:

- Android manifest with proper permissions
- WebView activity to display web content
- Resource files and layout elements
- Gradle build configuration
- Icon resources in various resolutions

The application then builds the Android project using the Android SDK build tools and signs the APK with a debug key.

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## Troubleshooting

- **Build Errors**: Make sure Android SDK is installed and the BUILD_TOOLS_VERSION in the code matches your installed version
- **Signing Errors**: Debug keystore is automatically generated if needed
- **WebView Issues**: Some websites may not display properly in WebView due to their specific requirements

