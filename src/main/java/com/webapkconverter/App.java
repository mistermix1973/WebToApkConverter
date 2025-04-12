package com.webapkconverter;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;

/**
 * Main JavaFX Application class for the Web to APK Converter.
 */
public class App extends Application {

    private static Scene scene;

    /**
     * Start method called by the JavaFX runtime.
     * 
     * @param stage The primary stage for this application
     * @throws IOException If FXML loading fails
     */
    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("main"), 800, 600);
        stage.setScene(scene);
        stage.setTitle("Web To APK Converter");
        stage.setMinWidth(650);
        stage.setMinHeight(500);
        
        // Set application icon
        InputStream iconStream = getClass().getResourceAsStream("/com/webapkconverter/app_icon.png");
        if (iconStream != null) {
            stage.getIcons().add(new Image(iconStream));
        }
        
        stage.show();
    }

    /**
     * Loads an FXML file.
     * 
     * @param fxml The name of the FXML file to load (without extension)
     * @return The loaded FXML as a Parent object
     * @throws IOException If loading fails
     */
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    /**
     * Sets the root of the scene to the specified FXML file.
     * 
     * @param fxml The name of the FXML file (without extension)
     * @throws IOException If loading fails
     */
    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    /**
     * Main method that launches the JavaFX application.
     * 
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        launch();
    }
}
