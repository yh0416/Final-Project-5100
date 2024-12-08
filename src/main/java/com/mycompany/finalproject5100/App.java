package com.mycompany.finalproject5100;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    private static Scene scene;
    private static Stage primaryStage;

    @Override
    public void start(Stage stage) {
        try {
            primaryStage = stage;

            // Load the login FXML
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/mycompany/finalproject5100/login.fxml"));
            Parent root = fxmlLoader.load();

            // Create the scene and configure the stage
            scene = new Scene(root, 1200, 800);
            stage.setMinWidth(1200);
            stage.setMinHeight(800);

            // Add stylesheet
            String css = getClass().getResource("/com/mycompany/finalproject5100/styles.css").toExternalForm();
            scene.getStylesheets().add(css);

            stage.setTitle("FlowCart");
            stage.setScene(scene);
            stage.setResizable(true);
            stage.show();

        } catch (Exception e) {
            System.out.println("Error loading FXML: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void setRoot(String fxml) throws Exception {
        System.out.println("Attempting to navigate to: " + fxml + ".fxml");

        try {
            Parent root = loadFXML(fxml);

            // Update the root of the scene
            scene.setRoot(root);

            // Add stylesheet to the scene
            String css = App.class.getResource("/com/mycompany/finalproject5100/styles.css").toExternalForm();
            if (!scene.getStylesheets().contains(css)) {
                scene.getStylesheets().add(css);
            }
        } catch (Exception e) {
            System.out.println("Error navigating to: " + fxml + ".fxml");
            e.printStackTrace();
            throw e;
        }
    }

    private static Parent loadFXML(String fxml) throws Exception {
        System.out.println("Full resource path: " + "/com/mycompany/finalproject5100/" + fxml + ".fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/com/mycompany/finalproject5100/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch();
    }
}
