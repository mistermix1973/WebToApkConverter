package com.webapkconverter;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.DirectoryChooser;
import javafx.concurrent.Task;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainController {
    @FXML private TextField urlTextField;
    @FXML private TextField appNameTextField;
    @FXML private TextField packageNameTextField;
    @FXML private Button selectIconButton;
    @FXML private ImageView iconPreviewImageView;
    @FXML private Button convertButton;
    @FXML private ProgressIndicator progressIndicator;
    @FXML private Label statusLabel;
    
    private File selectedIconFile;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    
    @FXML
    private void initialize() {
        // Initialize components and set up event handlers
        urlTextField.textProperty().addListener((obs, old, newValue) -> validateInputs());
        appNameTextField.textProperty().addListener((obs, old, newValue) -> validateInputs());
        packageNameTextField.textProperty().addListener((obs, old, newValue) -> validateInputs());
        
        // Hide progress indicator initially
        progressIndicator.setVisible(false);
        
        // Initial validation
        validateInputs();
        
        // Load default icon
        loadDefaultIcon();
    }
    
    /**
     * Loads the default icon for the icon preview if available
     */
    private void loadDefaultIcon() {
        try {
            InputStream iconStream = getClass().getResourceAsStream("/com/webapkconverter/default_icon.png");
            if (iconStream != null) {
                Image defaultIcon = new Image(iconStream);
                iconPreviewImageView.setImage(defaultIcon);
            }
        } catch (Exception e) {
            System.err.println("Error loading default icon: " + e.getMessage());
        }
    }
    
    @FXML
    private void handleSelectIcon() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Icon Image");
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );
        
        File file = fileChooser.showOpenDialog(selectIconButton.getScene().getWindow());
        if (file != null) {
            try {
                selectedIconFile = file;
                Image icon = new Image(file.toURI().toString());
                iconPreviewImageView.setImage(icon);
                validateInputs();
            } catch (Exception e) {
                showErrorAlert("Invalid image file", "Please select a valid PNG or JPG image.");
            }
        }
    }

    /**
     * Handles the conversion process when the Convert button is clicked.
     */
    @FXML
    private void handleConvert() {
        if (!validateInputs()) {
            return;
        }
        
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Output Directory");
        File outputDirectory = directoryChooser.showDialog(convertButton.getScene().getWindow());
        
        if (outputDirectory == null) {
            return;
        }
        
        // Disable UI components during conversion
        setUIDisabled(true);
        progressIndicator.setVisible(true);
        statusLabel.setText("Creating APK...");
        
        Task<File> task = new Task<>() {
            @Override
            protected File call() throws Exception {
                updateProgress(0.1, 1.0);
                updateMessage("Creating APK structure...");
                
                ApkGenerator generator = new ApkGenerator();
                
                String url = urlTextField.getText().trim();
                String appName = appNameTextField.getText().trim();
                String packageName = packageNameTextField.getText().trim();
                
                generator.setOutputDir(outputDirectory.toPath());
                generator.setAppName(appName);
                generator.setPackageName(packageName);
                generator.setUrl(url);
                
                if (selectedIconFile != null) {
                    generator.setIconFile(selectedIconFile.toPath());
                }
                
                updateProgress(0.3, 1.0);
                updateMessage("Generating Android project...");
                generator.createProject();
                
                updateProgress(0.5, 1.0);
                updateMessage("Injecting web content...");
                generator.injectWebContent();
                
                updateProgress(0.7, 1.0);
                updateMessage("Building APK...");
                generator.buildApk();
                
                updateProgress(0.9, 1.0);
                updateMessage("Signing APK...");
                File outputApk = generator.signApk();
                
                updateProgress(1.0, 1.0);
                updateMessage("APK created successfully!");
                
                return outputApk;
            }
            
            @Override
            protected void succeeded() {
                try {
                    File apkFile = get();
                    statusLabel.setText("APK created: " + apkFile.getName());
                    showInfoAlert("Success", "APK created successfully at:\n" + apkFile.getAbsolutePath());
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    setUIDisabled(false);
                    progressIndicator.setVisible(false);
                }
            }
            
            @Override
            protected void failed() {
                Throwable e = getException();
                statusLabel.setText("Error: " + e.getMessage());
                showErrorAlert("Error", "Failed to create APK: " + e.getMessage());
                setUIDisabled(false);
                progressIndicator.setVisible(false);
            }
        };
        
        // Bind progress UI
        progressIndicator.progressProperty().bind(task.progressProperty());
        statusLabel.textProperty().bind(task.messageProperty());
        
        // Start the task
        executorService.submit(task);
    }

    /**
     * Shows an error dialog with the given message.
     */
    private void showError(String title, String message, Throwable e) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message + (e != null ? "\n\nError: " + e.getMessage() : ""));
            alert.showAndWait();
        });
    }
    
    /**
     * Validates the inputs and returns whether they are valid.
     * 
     * @return true if all inputs are valid, false otherwise
     */
    private boolean validateInputs() {
        boolean isValid = true;
        StringBuilder errorMessage = new StringBuilder();
        
        // Validate URL
        if (urlTextField.getText().trim().isEmpty()) {
            isValid = false;
            errorMessage.append("URL is required.\n");
        } else if (!isValidUrl(urlTextField.getText().trim())) {
            isValid = false;
            errorMessage.append("Invalid URL format.\n");
        }
        
        // Validate app name
        if (appNameTextField.getText().trim().isEmpty()) {
            isValid = false;
            errorMessage.append("App name is required.\n");
        }
        
        // Validate package name
        if (packageNameTextField.getText().trim().isEmpty()) {
            isValid = false;
            errorMessage.append("Package name is required.\n");
        } else if (!isValidPackageName(packageNameTextField.getText().trim())) {
            isValid = false;
            errorMessage.append("Invalid package name format. Use format like 'com.example.app'.\n");
        }
        
        // Update UI
        convertButton.setDisable(!isValid);
        statusLabel.setText(isValid ? "Ready to convert" : errorMessage.toString().trim());
        
        return isValid;
    }

    /**
     * Checks if a URL is valid.
     * 
     * @param url The URL to validate
     * @return true if the URL is valid, false otherwise
     */
    private boolean isValidUrl(String url) {
        try {
            new URI(url);
            return url.startsWith("http://") || url.startsWith("https://");
        } catch (URISyntaxException e) {
            return false;
        }
    }

    /**
     * Checks if a package name is valid.
     * 
     * @param packageName The package name to validate
     * @return true if the package name is valid, false otherwise
     */
    private boolean isValidPackageName(String packageName) {
        return packageName.matches("^[a-z]+(\\.[a-z][a-z0-9_]*)+$");
    }

    /**
     * Shows an error alert dialog.
     * 
     * @param title The title of the alert
     * @param content The content of the alert
     */
    private void showErrorAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Shows an information alert dialog.
     * 
     * @param title The title of the alert
     * @param content The content of the alert
     */
    private void showInfoAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Enables or disables the UI components.
     * 
     * @param disabled true to disable, false to enable
     */
    private void setUIDisabled(boolean disabled) {
        urlTextField.setDisable(disabled);
        appNameTextField.setDisable(disabled);
        packageNameTextField.setDisable(disabled);
        selectIconButton.setDisable(disabled);
        convertButton.setDisable(disabled);
    }
}
