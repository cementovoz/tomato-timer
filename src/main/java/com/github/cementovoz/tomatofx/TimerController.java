package com.github.cementovoz.tomatofx;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.AudioClip;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;


/**
 * Created by cementovoz on 13.01.15.
 */
public class TimerController extends AnchorPane {

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
        AudioClip plonkSound = new AudioClip(getClass().getResource("/com/github/cemenetovoz/tomatofx/media/timer.mp3").toExternalForm());
        plonkSound.play();
    }

    private void createButtons() {
        final ToggleButton mainButton = new ToggleButton("Start");
        mainButton.textProperty().bind(Bindings.createStringBinding(()->
                mainButton.selectedProperty().get() ? "Stop" : "Start",
                mainButton.selectedProperty()));
        started.bindBidirectional(mainButton.selectedProperty());
        HBox hBox = new HBox(){{
            getStyleClass().add("container-btn");
            getChildren().addAll(mainButton);
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
