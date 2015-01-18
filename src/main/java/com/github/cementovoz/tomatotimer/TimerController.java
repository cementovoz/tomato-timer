package com.github.cementovoz.tomatotimer;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.AudioClip;

import javax.swing.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;


/**
 * Created by cementovoz on 13.01.15.
 */
public class TimerController extends AnchorPane {

    private static final String PAUSE_ICON = "/com/github/cemenetovoz/tomatotimer/img/pause.png";
    private static final String START_ICON = "/com/github/cemenetovoz/tomatotimer/img/start.png";
    private static final String RESET_ICON = "/com/github/cemenetovoz/tomatotimer/img/reset.png";
    private long WORK_TIME = 25 * 60L;
    private long RELAX_TIME = 5 * 60L;

    private SimpleLongProperty timeProperty = new SimpleLongProperty(WORK_TIME);
    private SimpleBooleanProperty started = new SimpleBooleanProperty(false);
    private ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
    private ScheduledFuture future;

    private SimpleObjectProperty<TimeType> timeTypeProperty = new SimpleObjectProperty<>();


    public TimerController () {
        getStyleClass().add("timer");
        createLabels();
        createButtons();
        started.addListener((property, oldVal, newVal) -> {
            if (newVal) {
                future = service.scheduleAtFixedRate(nextTick(), 0L, 1L, TimeUnit.SECONDS);
            }
            else if (future != null) {
                future.cancel(true);
                future = null;
            }
        });
        timeTypeProperty.addListener((property, oldValue, newValue) -> {
            if (newValue == TimeType.WORK) {
                timeProperty.set(WORK_TIME);
            }
            else {
                timeProperty.set(RELAX_TIME);
            }
        });
    }

    private Runnable nextTick() {
            return () -> Platform.runLater(() -> {
                timeProperty.set(timeProperty.get() - 1);
                if (timeProperty.get() <= 0) {
                    started.set(false);
                    playSound();
                    timeTypeProperty.set(timeTypeProperty.get() == TimeType.WORK  ? TimeType.RELAX : TimeType.WORK);
                }
            });
    }

    private void playSound() {
        AudioClip plonkSound = new AudioClip(getClass().getResource("/com/github/cemenetovoz/tomatotimer/media/timer.mp3").toExternalForm());
        plonkSound.play();
    }

    private void createButtons() {
        final ToggleButton mainButton = new ToggleButton();
        final ImageView pauseIcon = new ImageView(new Image(getClass().getResource(PAUSE_ICON).toExternalForm()));
        final ImageView startIcon = new ImageView(new Image(getClass().getResource(START_ICON).toExternalForm()));
        mainButton.graphicProperty().bind(Bindings.createObjectBinding(() ->
                        mainButton.selectedProperty().get() ? pauseIcon : startIcon,
                mainButton.selectedProperty()));
        started.bindBidirectional(mainButton.selectedProperty());
        Button resetButton = new Button("", new ImageView(new Image(RESET_ICON)));
        resetButton.setDisable(true);
        resetButton.setTooltip(new Tooltip("Reset timer"));
        resetButton.disableProperty().bind(Bindings.createBooleanBinding(() -> {
                    return started.get() == true || (timeProperty.get() == WORK_TIME || timeProperty.get() == RELAX_TIME) ? true : false;
                },
                 timeProperty, started));
        resetButton.setOnAction(event -> {
            timeProperty.set(timeTypeProperty.get() == TimeType.WORK ? WORK_TIME : RELAX_TIME);
        });
        HBox hBox = new HBox(){{
            setSpacing(10);
            getStyleClass().add("container-btn");
            getChildren().addAll(mainButton, resetButton);
        }};
        AnchorPane.setTopAnchor(hBox, 300d);
        AnchorPane.setLeftAnchor(hBox, 0d);
        AnchorPane.setRightAnchor(hBox, 0d);
        hBox.setAlignment(Pos.BASELINE_CENTER);
        getChildren().add(hBox);
    }

    private void createLabels() {
        final Label seconds = new Label();
        final Label minutes = new Label();
        final Label separator = new Label(":");
        HBox hBox = new HBox() {{
            getStyleClass().add("container-text");
            getChildren().addAll(minutes, separator, seconds);
        }};
        AnchorPane.setTopAnchor(hBox, 170d);
        AnchorPane.setLeftAnchor(hBox, 0d);
        AnchorPane.setRightAnchor(hBox, 0d);
        hBox.setAlignment(Pos.BASELINE_CENTER);
        getChildren().add(hBox);
        minutes.textProperty().bind(Bindings.createStringBinding(() -> String.format("%1$02d", timeProperty.intValue() / 60), timeProperty));
        seconds.textProperty().bind(Bindings.createStringBinding(() -> String.format("%1$02d", timeProperty.intValue() % 60), timeProperty));
    }

    public SimpleLongProperty timeProperty() {
        return timeProperty;
    }


    /**
     * Shutdown executor service, and cancel or interrupt thread
     */
    public void stop() {
        if (future != null) {
            future.cancel(true);
            future = null;
        }
        service.shutdown();
    }

    private static enum TimeType {
        WORK, RELAX
    }
}
