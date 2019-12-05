package gdx.kapotopia.Helpers.Builders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import gdx.kapotopia.AssetsManaging.FontHelper;
import gdx.kapotopia.AssetsManaging.UseFont;
import gdx.kapotopia.Kapotopia;
import gdx.kapotopia.ScreenType;
import gdx.kapotopia.Utils;

import java.util.ArrayList;

public class PopUpBuilder {

    private Stage stage;
    private final Dialog dialog;
    private float btnSize = 90f;
    private int x;
    private int y;

    public PopUpBuilder(final Kapotopia game, Stage stage) {
        this.stage = stage;
        x = 0;
        y = 0;

        // /////////////////
        Skin skinDialog = new Skin(Gdx.files.internal("defaultSkin/skin/uiskin.json"));
        dialog = new Dialog("", skinDialog) {
            @Override
            public float getPrefWidth() {
                return game.viewport.getWorldWidth();
            }

            @Override
            public float getPrefHeight() {
                return game.viewport.getWorldHeight() / 2.0f;
            }
        };
        dialog.setModal(true);
        dialog.setMovable(false);
        dialog.setResizable(false);

        dialog.setName("quitDialog");

    }

    public void setPosition(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void close()
    {
        dialog.cancel();
        dialog.hide();
        dialog.remove();
    }

    public void setTitle(String title){

        Label label1 = new LabelBuilder(title).withStyle(UseFont.CLASSIC_BOLD_NORMAL_WHITE).build();
        label1.setAlignment(Align.center);
        dialog.getContentTable().add(label1).padTop(40f);
    }

    public void addButton(TextButton btn){
        dialog.getButtonTable().add(btn).height(btnSize).center().padBottom(20f).row();
    }

    public void show(){
        dialog.show(stage).setPosition(x,y);
        stage.addActor(dialog);
    }


}
