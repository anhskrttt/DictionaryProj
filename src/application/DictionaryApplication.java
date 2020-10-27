package application;

import application.services.SceneSetup;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.speech.EngineException;

import static application.services.Utils.*;

public class DictionaryApplication extends Application {
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;

        VBox root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle(WINDOW_TITLE);
        stage.setOnCloseRequest(e -> {
            e.consume();
            try {
                closeProgram();
            } catch (EngineException ex) {
                ex.printStackTrace();
            }
        });
        stage.show();

        SceneSetup ss = new SceneSetup();
        ss.sceneSetup();
    }
}


