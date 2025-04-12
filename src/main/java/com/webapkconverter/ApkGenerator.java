package com.webapkconverter;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.model.enums.CompressionLevel;
import net.lingala.zip4j.model.enums.CompressionMethod;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Class responsible for generating APK files from websites or HTML content.
 */
public class ApkGenerator {
    private Path outputDir;
    private String appName;
    private String packageName;
    private String url;
    private Path iconFile;
    private Path projectDir;
    private Path buildDir;
    private Path resourcesDir;
    
    private static final String DEFAULT_ICON_RES = "/com/webapkconverter/default_icon.png";
    private static final String ANDROID_SDK_PATH = System.getenv("LOCALAPPDATA") + "\\Android\\sdk";
    private static final String BUILD_TOOLS_VERSION = "35.0.0"; // Use the latest detected version
    private boolean simulationMode = false; // Flag to run in simulation mode without real build tools
    
    /**
     * Sets the output directory for the APK.
     * 
     * @param outputDir The output directory
     */
    public void setOutputDir(Path outputDir) {
        this.outputDir = outputDir;
    }
    
    /**
     * Sets the app name.
     * 
     * @param appName The app name
     */
    public void setAppName(String appName) {
        this.appName = appName;
    }
    
    /**
     * Sets the package name.
     * 
     * @param packageName The package name
     */
    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
    
    /**
     * Sets the URL of the website to be converted.
     * 
     * @param url The URL
     */
    public void setUrl(String url) {
        this.url = url;
    }
    
    /**
     * Sets the icon file for the app.
     * 
     * @param iconFile Path to the icon file
     */
    public void setIconFile(Path iconFile) {
        this.iconFile = iconFile;
    }
    
    /**
     * Sets whether to run in simulation mode (without real build tools)
     * 
     * @param simulationMode true to run in simulation mode
     */
    public void setSimulationMode(boolean simulationMode) {
        this.simulationMode = simulationMode;
    }
    
    /**
     * Creates the Android project structure.
     * 
     * @throws IOException If an I/O error occurs
     */
    public void createProject() throws IOException {
        // Create project directories
        String sanitizedAppName = appName.replaceAll("[^a-zA-Z0-9]", "");
        projectDir = outputDir.resolve(sanitizedAppName + "_AndroidProject");
        
        if (Files.exists(projectDir)) {
            FileUtils.deleteDirectory(projectDir.toFile());
        }
        
        buildDir = projectDir.resolve("build");
        Path srcDir = projectDir.resolve("src");
        Path mainDir = srcDir.resolve("main");
        Path javaDir = mainDir.resolve("java");
        resourcesDir = mainDir.resolve("res");
        
        // Create package directories
        String[] packageParts = packageName.split("\\.");
        Path packageDir = javaDir;
        for (String part : packageParts) {
            packageDir = packageDir.resolve(part);
        }
        
        // Create directories
        Files.createDirectories(packageDir);
        Files.createDirectories(resourcesDir.resolve("values"));
        Files.createDirectories(resourcesDir.resolve("layout"));
        Files.createDirectories(resourcesDir.resolve("drawable"));
        Files.createDirectories(resourcesDir.resolve("mipmap-hdpi"));
        Files.createDirectories(resourcesDir.resolve("mipmap-mdpi"));
        Files.createDirectories(resourcesDir.resolve("mipmap-xhdpi"));
        Files.createDirectories(resourcesDir.resolve("mipmap-xxhdpi"));
        Files.createDirectories(resourcesDir.resolve("mipmap-xxxhdpi"));
        Files.createDirectories(buildDir);
        
        // Create main activity
        createMainActivity(packageDir);
        
        // Create manifest
        createAndroidManifest(mainDir);
        
        // Create resources
        createResources();
        
        // Create build files
        createBuildFiles();
    }
    
    /**
     * Creates the MainActivity.java file.
     * 
     * @param packageDir The package directory
     * @throws IOException If an I/O error occurs
     */
    private void createMainActivity(Path packageDir) throws IOException {
        Path mainActivityFile = packageDir.resolve("MainActivity.java");
        
        String activityContent = "package " + packageName + ";\n\n" +
                "import android.os.Bundle;\n" +
                "import android.webkit.WebSettings;\n" +
                "import android.webkit.WebView;\n" +
                "import android.webkit.WebViewClient;\n" +
                "import androidx.appcompat.app.AppCompatActivity;\n\n" +
                "public class MainActivity extends AppCompatActivity {\n" +
                "    private WebView webView;\n\n" +
                "    @Override\n" +
                "    protected void onCreate(Bundle savedInstanceState) {\n" +
                "        super.onCreate(savedInstanceState);\n" +
                "        setContentView(R.layout.activity_main);\n\n" +
                "        webView = findViewById(R.id.webview);\n" +
                "        webView.setWebViewClient(new WebViewClient());\n\n" +
                "        WebSettings webSettings = webView.getSettings();\n" +
                "        webSettings.setJavaScriptEnabled(true);\n" +
                "        webSettings.setDomStorageEnabled(true);\n" +
                "        webSettings.setDatabaseEnabled(true);\n" +
                "        webSettings.setAppCacheEnabled(true);\n" +
                "        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);\n\n" +
                "        // Load the website\n" +
                "        webView.loadUrl(\"" + url + "\");\n" +
                "    }\n\n" +
                "    @Override\n" +
                "    public void onBackPressed() {\n" +
                "        if (webView.canGoBack()) {\n" +
                "            webView.goBack();\n" +
                "        } else {\n" +
                "            super.onBackPressed();\n" +
                "        }\n" +
                "    }\n" +
                "}\n";
        
        Files.write(mainActivityFile, activityContent.getBytes(StandardCharsets.UTF_8));
    }
    
    /**
     * Creates the AndroidManifest.xml file.
     * 
     * @param mainDir The main directory
     * @throws IOException If an I/O error occurs
     */
    private void createAndroidManifest(Path mainDir) throws IOException {
        Path manifestFile = mainDir.resolve("AndroidManifest.xml");
        
        String manifestContent = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<manifest xmlns:android=\"http://schemas.android.com/apk/res/android\"\n" +
                "    package=\"" + packageName + "\">\n\n" +
                "    <uses-permission android:name=\"android.permission.INTERNET\" />\n" +
                "    <uses-permission android:name=\"android.permission.ACCESS_NETWORK_STATE\" />\n\n" +
                "    <application\n" +
                "        android:allowBackup=\"true\"\n" +
                "        android:icon=\"@mipmap/ic_launcher\"\n" +
                "        android:label=\"@string/app_name\"\n" +
                "        android:supportsRtl=\"true\"\n" +
                "        android:theme=\"@style/AppTheme\"\n" +
                "        android:usesCleartextTraffic=\"true\">\n" +
                "        <activity\n" +
                "            android:name=\".MainActivity\"\n" +
                "            android:exported=\"true\"\n" +
                "            android:configChanges=\"orientation|keyboardHidden|screenSize\"\n" +
                "            android:label=\"@string/app_name\">\n" +
                "            <intent-filter>\n" +
                "                <action android:name=\"android.intent.action.MAIN\" />\n" +
                "                <category android:name=\"android.intent.category.LAUNCHER\" />\n" +
                "            </intent-filter>\n" +
                "        </activity>\n" +
                "    </application>\n" +
                "</manifest>\n";
        
        Files.write(manifestFile, manifestContent.getBytes(StandardCharsets.UTF_8));
    }
    
    /**
     * Creates the necessary resource files.
     * 
     * @throws IOException If an I/O error occurs
     */
    private void createResources() throws IOException {
        // Create layout
        Path layoutFile = resourcesDir.resolve("layout/activity_main.xml");
        String layoutContent = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<RelativeLayout xmlns:android=\"http://schemas.android.com/apk/res/android\"\n" +
                "    xmlns:tools=\"http://schemas.android.com/tools\"\n" +
                "    android:layout_width=\"match_parent\"\n" +
                "    android:layout_height=\"match_parent\"\n" +
                "    tools:context=\".MainActivity\">\n\n" +
                "    <WebView\n" +
                "        android:id=\"@+id/webview\"\n" +
                "        android:layout_width=\"match_parent\"\n" +
                "        android:layout_height=\"match_parent\" />\n\n" +
                "</RelativeLayout>\n";
        Files.createDirectories(layoutFile.getParent());
        Files.write(layoutFile, layoutContent.getBytes(StandardCharsets.UTF_8));
        
        // Create strings.xml
        Path stringsFile = resourcesDir.resolve("values/strings.xml");
        String stringsContent = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<resources>\n" +
                "    <string name=\"app_name\">" + appName + "</string>\n" +
                "</resources>\n";
        Files.createDirectories(stringsFile.getParent());
        Files.write(stringsFile, stringsContent.getBytes(StandardCharsets.UTF_8));
        
        // Create styles.xml
        Path stylesFile = resourcesDir.resolve("values/styles.xml");
        String stylesContent = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<resources>\n" +
                "    <style name=\"AppTheme\" parent=\"Theme.AppCompat.Light.DarkActionBar\">\n" +
                "        <item name=\"colorPrimary\">#3F51B5</item>\n" +
                "        <item name=\"colorPrimaryDark\">#303F9F</item>\n" +
                "        <item name=\"colorAccent\">#FF4081</item>\n" +
                "    </style>\n" +
                "</resources>\n";
        Files.write(stylesFile, stylesContent.getBytes(StandardCharsets.UTF_8));
        
        // Prepare app icon
        prepareAppIcon();
    }
    
    /**
     * Prepares the app icon.
     * 
     * @throws IOException If an I/O error occurs
     */
    private void prepareAppIcon() throws IOException {
        InputStream iconStream;
        if (iconFile != null && Files.exists(iconFile)) {
            iconStream = Files.newInputStream(iconFile);
        } else {
            // Use default icon
            iconStream = getClass().getResourceAsStream(DEFAULT_ICON_RES);
            if (iconStream == null) {
                throw new IOException("Default icon resource not found");
            }
        }
        
        // Copy to mipmap directories
        Path[] iconDirs = {
                resourcesDir.resolve("mipmap-mdpi"),
                resourcesDir.resolve("mipmap-hdpi"),
                resourcesDir.resolve("mipmap-xhdpi"),
                resourcesDir.resolve("mipmap-xxhdpi"),
                resourcesDir.resolve("mipmap-xxxhdpi")
        };
        
        for (Path dir : iconDirs) {
            Path iconPath = dir.resolve("ic_launcher.png");
            Files.copy(iconStream, iconPath, StandardCopyOption.REPLACE_EXISTING);
            iconStream.reset();
        }
        
        iconStream.close();
    }
    
    /**
     * Creates the build files.
     * 
     * @throws IOException If an I/O error occurs
     */
    private void createBuildFiles() throws IOException {
        // Create gradle wrapper properties
        Path gradleDir = projectDir.resolve("gradle/wrapper");
        Files.createDirectories(gradleDir);
        
        Path gradleWrapperProps = gradleDir.resolve("gradle-wrapper.properties");
        String gradleWrapperContent = "distributionBase=GRADLE_USER_HOME\n" +
                "distributionPath=wrapper/dists\n" +
                "distributionUrl=https\\://services.gradle.org/distributions/gradle-8.2-bin.zip\n" +
                "zipStoreBase=GRADLE_USER_HOME\n" +
                "zipStorePath=wrapper/dists\n";
        Files.write(gradleWrapperProps, gradleWrapperContent.getBytes(StandardCharsets.UTF_8));
        
        // Create settings.gradle
        Path settingsGradle = projectDir.resolve("settings.gradle");
        String settingsContent = "rootProject.name = '" + appName.replaceAll("[^a-zA-Z0-9]", "") + "'\n" +
                "include ':app'\n";
        Files.write(settingsGradle, settingsContent.getBytes(StandardCharsets.UTF_8));
        
        // Create build.gradle
        Path buildGradle = projectDir.resolve("build.gradle");
        String buildGradleContent = "// Top-level build file\n\n" +
                "buildscript {\n" +
                "    repositories {\n" +
                "        google()\n" +
                "        mavenCentral()\n" +
                "    }\n" +
                "    dependencies {\n" +
                "        classpath 'com.android.tools.build:gradle:8.1.0'\n" +
                "    }\n" +
                "}\n\n" +
                "allprojects {\n" +
                "    repositories {\n" +
                "        google()\n" +
                "        mavenCentral()\n" +
                "    }\n" +
                "}\n\n" +
                "apply plugin: 'com.android.application'\n\n" +
                "android {\n" +
                "    compileSdkVersion 34\n" +
                "    defaultConfig {\n" +
                "        applicationId \"" + packageName + "\"\n" +
                "        minSdkVersion 21\n" +
                "        targetSdkVersion 34\n" +
                "        versionCode 1\n" +
                "        versionName \"1.0\"\n" +
                "    }\n" +
                "    buildTypes {\n" +
                "        release {\n" +
                "            minifyEnabled false\n" +
                "        }\n" +
                "    }\n" +
                "    namespace \"" + packageName + "\"\n" +
                "}\n\n" +
                "dependencies {\n" +
                "    implementation 'androidx.appcompat:appcompat:1.6.1'\n" +
                "    implementation 'androidx.core:core-ktx:1.12.0'\n" +
                "    implementation 'com.google.android.material:material:1.10.0'\n" +
                "}\n";
        Files.write(buildGradle, buildGradleContent.getBytes(StandardCharsets.UTF_8));
        
        // Create local.properties
        Path localProps = projectDir.resolve("local.properties");
        String localPropsContent = "sdk.dir=" + ANDROID_SDK_PATH.replace("\\", "\\\\") + "\n";
        Files.write(localProps, localPropsContent.getBytes(StandardCharsets.UTF_8));
    }
    /**
     * Injects web content.
     * 
     * @throws IOException If an I/O error occurs
     */
    public void injectWebContent() throws IOException {
        // For a URL, no need to inject actual content as it's loaded dynamically
        // If it was an HTML file, we would extract and inject it here
        
        if (simulationMode) {
            // In simulation mode, just create a placeholder file
            Path webviewFile = projectDir.resolve("src/main/java/" + 
                              packageName.replace('.', '/') + "/WebViewActivity.java");
            
            // Create parent directories if they don't exist
            Files.createDirectories(webviewFile.getParent());
            
            // Create a simple placeholder file
            Files.writeString(webviewFile, "// Generated by WebToApkConverter in simulation mode\n");
        }
    }
    
    /**
     * Builds the APK.
     * 
     * @throws IOException If an I/O error occurs
     * @throws InterruptedException If the build process is interrupted
     */
    public void buildApk() throws IOException, InterruptedException {
        if (simulationMode) {
            // In simulation mode, just create a placeholder APK file
            Path debugApkDir = projectDir.resolve("build/outputs/apk/debug");
            Files.createDirectories(debugApkDir);
            Path debugApkPath = debugApkDir.resolve("app-debug.apk");
            Files.writeString(debugApkPath, "// Simulated APK file\n");
            return;
        }
        
        // Execute Gradle build
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.directory(projectDir.toFile());
        
        // Check if Gradle wrapper exists
        Path gradlew = projectDir.resolve("gradlew.bat");
        if (!Files.exists(gradlew)) {
            // Create a minimal gradlew.bat
            String gradlewContent = "@echo off\n" +
                    "gradle %*\n";
            Files.write(gradlew, gradlewContent.getBytes(StandardCharsets.UTF_8));
            try {
                Files.setPosixFilePermissions(gradlew, PosixFilePermissions.fromString("rwxr-xr-x"));
            } catch (UnsupportedOperationException e) {
                // Windows might not support POSIX permissions, so ignore
            }
        }
        
        // Build the APK
        processBuilder.command("cmd.exe", "/c", "gradlew.bat", "assembleRelease");
        Process process = processBuilder.start();
        
        // Capture output for debugging
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        StringBuilder output = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            output.append(line).append("\n");
        }
        
        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new IOException("APK build failed with exit code " + exitCode + "\nOutput: " + output.toString());
        }
    }
    
    /**
     * Signs the APK.
     * 
     * @return The path to the signed APK
     * @throws IOException If an I/O error occurs
     * @throws InterruptedException If the signing process is interrupted
     */
    public File signApk() throws IOException, InterruptedException {
        if (simulationMode) {
            // In simulation mode, create a simulated signed APK
            String appNameSanitized = appName.replaceAll("[^a-zA-Z0-9]", "");
            Path signedApkPath = outputDir.resolve(appNameSanitized + ".apk");
            Files.writeString(signedApkPath, "// Simulated signed APK file\n");
            return signedApkPath.toFile();
        }
        
        // Find the unsigned APK
        Path unsignedApkPath = Files.find(projectDir, 
                Integer.MAX_VALUE,
                (path, attrs) -> path.toString().endsWith("-release-unsigned.apk"))
                .findFirst()
                .orElseThrow(() -> new IOException("Unsigned APK not found"));
                
        // Create a debug keystore if it doesn't exist
        String userHome = System.getProperty("user.home");
        Path debugKeystorePath = Paths.get(userHome, ".android", "debug.keystore");
        if (!Files.exists(debugKeystorePath)) {
            Files.createDirectories(debugKeystorePath.getParent());
            
            // Generate a debug keystore
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.command(
                    "keytool",
                    "-genkeypair",
                    "-keystore", debugKeystorePath.toString(),
                    "-storepass", "android",
                    "-alias", "androiddebugkey",
                    "-keypass", "android",
                    "-keyalg", "RSA",
                    "-validity", "10000",
                    "-dname", "CN=Android Debug,O=Android,C=US"
            );
            Process process = processBuilder.start();
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                throw new IOException("Failed to create debug keystore");
            }
        }
        
        // Sign the APK
        String apkSigner = ANDROID_SDK_PATH + "\\build-tools\\" + BUILD_TOOLS_VERSION + "\\apksigner.bat";
        
        // Output signed APK path
        String appNameSanitized = appName.replaceAll("[^a-zA-Z0-9]", "");
        File outputApk = outputDir.resolve(appNameSanitized + ".apk").toFile();
        
        // Sign the APK using apksigner
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command(
                apkSigner,
                "sign",
                "--ks", debugKeystorePath.toString(),
                "--ks-pass", "pass:android",
                "--ks-key-alias", "androiddebugkey",
                "--key-pass", "pass:android",
                "--out", outputApk.getAbsolutePath(),
                unsignedApkPath.toString()
        );
        
        Process process = processBuilder.start();
        int exitCode = process.waitFor();
        
        if (exitCode != 0) {
            // Capture error output
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            StringBuilder errorOutput = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                errorOutput.append(line).append("\n");
            }
            
            throw new IOException("APK signing failed with exit code " + exitCode + ": " + errorOutput.toString());
        }
        
        // If signing succeeded, return the signed APK
        if (!outputApk.exists()) {
            throw new IOException("Signed APK file not found at: " + outputApk.getAbsolutePath());
        }
        
        // Clean up temporary files
        cleanup();
        
        return outputApk;
    }
    
    /**
     * Cleans up temporary build files.
     */
    public void cleanup() {
        try {
            // We don't delete the entire project directory to keep it for reference
            // But we can delete large build directories to save space
            Path buildOutput = projectDir.resolve("build");
            if (Files.exists(buildOutput)) {
                FileUtils.deleteDirectory(buildOutput.toFile());
            }
            
            // Delete temporary files
            Path tmpDir = projectDir.resolve("tmp");
            if (Files.exists(tmpDir)) {
                FileUtils.deleteDirectory(tmpDir.toFile());
            }
        } catch (IOException e) {
            // Just log a warning, don't throw
            System.err.println("Warning: Failed to clean up temporary files: " + e.getMessage());
        }
    }
    
    /**
     * Creates a default icon file if one is not provided.
     * This is a helper method that would create a default app icon if necessary.
     * 
     * @param targetPath The path where the icon should be saved
     * @throws IOException If an I/O error occurs
     */
    private void createDefaultIcon(Path targetPath) throws IOException {
        // Create a basic default icon - a solid color square
        // In a real implementation, this would create an actual image file
        // For this example, we'll just copy from a resource if it exists
        
        InputStream iconStream = getClass().getResourceAsStream(DEFAULT_ICON_RES);
        if (iconStream != null) {
            Files.copy(iconStream, targetPath, StandardCopyOption.REPLACE_EXISTING);
            iconStream.close();
        } else {
            // If we don't have a resource, create a minimal PNG
            // If we don't have a resource, create a minimal PNG
            byte[] minimalPng = {
                (byte) 0x89, 0x50, 0x4E, 0x47, 0x0D, 0x0A, 0x1A, 0x0A, 0x00, 0x00, 0x00, 0x0D,
                0x49, 0x48, 0x44, 0x52, 0x00, 0x00, 0x00, 0x10, 0x00, 0x00, 0x00, 0x10, 0x08,
                0x06, 0x00, 0x00, 0x00, (byte) 0x1F, (byte) 0xF3, (byte) 0xFF, (byte) 0x61, 0x00, 0x00, 0x00, 0x0E,
                0x49, 0x44, 0x41, 0x54, (byte) 0x78, (byte) 0x9C, (byte) 0x63, (byte) 0xF8, 0x0F, 0x04, 0x0C,
                (byte) 0xFF, (byte) 0xFF, 0x03, 0x00, 0x09, (byte) 0xFE, 0x02, (byte) 0xFD, (byte) 0xE3,
                (byte) 0xB3, (byte) 0x71, 0x00, 0x00, 0x00, 0x00, 0x49, 0x45, 0x4E, 0x44, (byte) 0xAE,
                0x42, (byte) 0x60, (byte) 0x82
            };
            Files.write(targetPath, minimalPng);
        }
    }
}
