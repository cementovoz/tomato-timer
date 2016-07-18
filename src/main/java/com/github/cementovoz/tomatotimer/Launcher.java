package com.github.cementovoz.tomatotimer;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Launcher extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        TimerPane root = new TimerPane();
        Scene scene = new Scene(root, 350, 250);
        setStylesheets(scene);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    private void setStylesheets(Scene scene) {
        scene.getStylesheets().add(getClass().getResource("/resources/css/jfoenix-fonts.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("/resources/css/jfoenix-design.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
    }
}
