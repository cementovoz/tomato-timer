package com.github.cementovoz.tomatotimer;


import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class TimerPane extends AnchorPane{

    public TimerPane() {
        getStyleClass().addAll("display4", "red-600");
        Label label = new Label("25:00");
        label.getStyleClass().add("timer");
        HBox panel = new HBox() {{
            setAlignment(Pos.BASELINE_CENTER);
            setSpacing(15.0);
            getChildren().addAll(new JFXButton("",
                    Icons.create(MaterialDesignIcon.PLAY, "2em", Color.WHITE)) {{
                getStyleClass().add("red-700");
            }}, new JFXButton("",
                    Icons.create(MaterialDesignIcon.STOP, "2em", Color.WHITE)) {{
                getStyleClass().add("red-700");
            }});
        }};
        VBox vBox = new VBox() {{
            setSpacing(15.0);
            getChildren().add(label);
            getChildren().add(panel);
            setAlignment(Pos.BASELINE_CENTER);
        }};
        AnchorPane.setLeftAnchor(vBox, 0d);
        AnchorPane.setRightAnchor(vBox, 0d);

        JFXButton settings = new JFXButton("",
                Icons.create(MaterialDesignIcon.SETTINGS, Color.WHITE)) {{
            getStyleClass().add("red-700");
        }};

        JFXButton menu = new JFXButton("",
                Icons.create(MaterialDesignIcon.MENU, Color.WHITE)) {{
            getStyleClass().add("red-700");
        }};

        AnchorPane.setRightAnchor(settings, 50d);
        AnchorPane.setTopAnchor(settings, 10d);

        AnchorPane.setRightAnchor(menu, 10d);
        AnchorPane.setTopAnchor(menu, 10d);

        getChildren().addAll(vBox, settings, menu);
    }
}
