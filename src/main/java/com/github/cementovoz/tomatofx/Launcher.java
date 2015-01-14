package com.github.cementovoz.tomatofx;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Created by cementovoz on 13.01.15.
 */
public class Launcher extends Application {

    private static final String CSS_STYLES = "/com/github/cemenetovoz/tomatofx/css/styles.css";
    private TimerController timer;


    @Override
    public void start(Stage primaryStage) throws Exception {
        timer = new TimerController();
        Scene scene = new Scene(timer, 350, 420);
        scene.getStylesheets().add(Launcher.class.getResource(CSS_STYLES).toExternalForm());
        scene.setFill(null);
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        Bindings.bindBidirectional(primaryStage.titleProperty(), timer.timeProperty(), new CustomTimeConverter());
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop() throws Exception {
        timer.stop();
    }
}
