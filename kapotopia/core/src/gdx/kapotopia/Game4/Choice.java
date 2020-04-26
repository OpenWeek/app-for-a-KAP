package gdx.kapotopia.Game4;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class Choice extends Actor {

    private Button choix;

    public Choice (String choiceName, int x, int y) {

        choix = new TextButton(choiceName, new TextButton.TextButtonStyle());
        this.setPosition(x, y);
    }

}

