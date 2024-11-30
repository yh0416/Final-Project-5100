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
    System.out.println("Attempting to navigate to dashboard for role: Store Owner");
    System.out.println("Attempting to load: " + fxml + ".fxml");
    Scene scene = App.scene;
    if (scene == null) {
        System.out.println("Scene is null!");
        return;
    }
    Parent root = loadFXML(fxml);
    // Add stylesheet to the new root
    root.getStylesheets().add(App.class.getResource("styles.css").toExternalForm());
    scene.setRoot(root);
}

 private static Parent loadFXML(String fxml) throws Exception {
    System.out.println("Full resource path: " + App.class.getResource(fxml + ".fxml"));
    System.out.println("Attempting to load: " + fxml + ".fxml");
    FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
    try {
        return fxmlLoader.load();
    } catch (Exception e) {
        System.out.println("FXML Load Error: " + e.getMessage());
        e.printStackTrace();
        throw e;
    }
}

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch();
    }
}