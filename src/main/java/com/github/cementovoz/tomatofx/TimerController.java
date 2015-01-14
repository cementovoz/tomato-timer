package com.github.cementovoz.tomatofx;

import javafx.beans.binding.Binding;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.util.converter.DateTimeStringConverter;
import javafx.util.converter.NumberStringConverter;


/**
 * Created by cementovoz on 13.01.15.
 */
public class TimerController extends AnchorPane {


    private final Label seconds = new Label();
    private final Label minutes = new Label();
    private final Label separator = new Label();

    private SimpleObjectProperty<Date>

    public TimerController () {
        getStyleClass().add("timer");
        Bindings.bindBidirectional(minutes.textProperty(),
                minuteProperty,
                new DateTimeStringConverter());

        HBox hBox = new HBox() {{
            getStyleClass().add("container-text");
            getChildren().addAll(minutes, separator, seconds);
        }};
        AnchorPane.setTopAnchor(hBox, 170d);
        AnchorPane.setLeftAnchor(hBox, 0d);
        AnchorPane.setRightAnchor(hBox, 0d);
        hBox.setAlignment(Pos.BASELINE_CENTER);
        getChildren().addAll(hBox);


    }

}
