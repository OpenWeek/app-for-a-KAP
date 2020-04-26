package gdx.kapotopia.Helpers.Builders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;

import gdx.kapotopia.Fonts.FontHelper;
import gdx.kapotopia.Kapotopia;

public class PopUpBuilder {

    private Kapotopia game;
    private Stage stage;
    private final Dialog dialog;
    private float btnSize = 90f;
    private int x;
    private int y;
    private Label title;

    public PopUpBuilder(final Kapotopia game, Stage stage) {
        this.game = game;
        this.stage = stage;
        x = 0;
        y = 0;

        // /////////////////
        //TODO use assetmanager to stock the following skin
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

        if(title != null){
            dialog.getContentTable().removeActor(this.title);
        }
        this.title = new LabelBuilder(game, title).withStyle(FontHelper.CLASSIC_BOLD_NORMAL_WHITE).build();
        this.title.setAlignment(Align.center);
        dialog.getContentTable().add(this.title).padTop(40f);
    }

    public void addButton(TextButton btn){
        dialog.getButtonTable().add(btn).height(btnSize).center().padBottom(20f).row();
    }

    public void show(){
        dialog.show(stage).setPosition(x,y);
        stage.addActor(dialog);
    }


}
