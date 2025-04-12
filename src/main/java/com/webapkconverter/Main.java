package com.webapkconverter;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.InputStream;

public class Main extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/webapkconverter/main.fxml"));
        Parent root = loader.load();
        
        // Load application icon if available
        try {
            InputStream iconStream = getClass().getResourceAsStream("/com/webapkconverter/app_icon.png");
            if (iconStream != null) {
                Image appIcon = new Image(iconStream);
                primaryStage.getIcons().add(appIcon);
            } else {
                System.out.println("Warning: app_icon.png not found in resources");
            }
        } catch (Exception e) {
            System.err.println("Error loading application icon: " + e.getMessage());
        }
        
        primaryStage.setTitle("Web to APK Converter");
        primaryStage.setScene(new Scene(root, 650, 550));
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
