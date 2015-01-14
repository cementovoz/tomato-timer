package com.github.cementovoz.tomatofx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Created by cementovoz on 13.01.15.
 */
public class Launcher extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        TimerController timer = new TimerController();
        Scene scene = new Scene(timer, 350, 420);
        scene.getStylesheets().add(Launcher.class.getResource("/com/github/cemenetovoz/tomatofx/css/styles.css").toExternalForm());
        scene.setFill(null);
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
