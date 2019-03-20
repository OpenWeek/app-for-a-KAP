package gdx.kapotopia.Game4;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import gdx.kapotopia.Utils;

public class Choice extends Actor {

    private Button choix;

    public Choice (String choiceName, int x, int y) {

        TextButton.TextButtonStyle style = Utils.getStyleFont("SEASRN__.ttf");
        choix = new TextButton(choiceName, style);
        this.setPosition(x, y);
    }

}

