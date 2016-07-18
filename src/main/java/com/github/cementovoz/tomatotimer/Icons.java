package com.github.cementovoz.tomatotimer;

import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.utils.MaterialDesignIconFactory;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class Icons {

    public static Node create(MaterialDesignIcon icon) {
        return MaterialDesignIconFactory.get().createIcon(icon);
    }

    public static Node create(MaterialDesignIcon icon, String size) {
        return MaterialDesignIconFactory.get().createIcon(icon, size);
    }

    public static Node create(MaterialDesignIcon icon, String size, Color white) {
        Text icon1 = MaterialDesignIconFactory.get().createIcon(icon, size);
        icon1.setFill(white);
        return icon1;
    }


    public static Node create(MaterialDesignIcon icon, Color white) {
        Text icon1 = MaterialDesignIconFactory.get().createIcon(icon);
        icon1.setFill(white);
        return icon1;
    }
}
