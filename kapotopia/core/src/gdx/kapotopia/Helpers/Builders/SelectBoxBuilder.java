package gdx.kapotopia.Helpers.Builders;

import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;

import gdx.kapotopia.Fonts.Font;
import gdx.kapotopia.Fonts.FontHelper;
import gdx.kapotopia.Kapotopia;

public class SelectBoxBuilder<T> {
    private Kapotopia game;
    // Actor Common Attributes

    // SelectBox
    private Skin skin;
    private String styleName;
    private SelectBox.SelectBoxStyle style;
    private float x, y;
    private float width, height;
    private boolean isVisible;
    private Font titleFont;
    private Font elemsFont;
    private T selectedItem;
    private Array<T> items;
    private ArrayList<EventListener> eventListeners;

    public SelectBoxBuilder(Kapotopia game) {
        this.game = game;
        skin = null; styleName = null; style = null;
        x = 0; y = 0;
        width = -1; height = -1;
        isVisible = true;
        titleFont = null; elemsFont = null;
        selectedItem = null;
        items = null;
        eventListeners = new ArrayList<EventListener>();
    }

    public SelectBoxBuilder withSkin(Skin skin) {
        this.skin = skin;
        return this;
    }

    public SelectBoxBuilder withSkin(Skin skin, String styleName) {
        this.skin = skin;
        this.styleName = styleName;
        return this;
    }

    public SelectBoxBuilder withSelectBoxStyle(SelectBox.SelectBoxStyle style) {
        this.style = style;
        return this;
    }

    public SelectBoxBuilder withPosition(float x, float y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public SelectBoxBuilder withSize(float width, float height) {
        this.width = width;
        this.height = height;
        return this;
    }

    public SelectBoxBuilder isVisible(boolean isVisible) {
        this.isVisible = isVisible;
        return this;
    }

    public SelectBoxBuilder withTitleFont(Font font) {
        titleFont = font;
        return this;
    }

    public SelectBoxBuilder withElemsFont(Font font) {
        elemsFont = font;
        return this;
    }

    public SelectBoxBuilder withSelectedItem(T item) {
        this.selectedItem = item;
        return this;
    }

    public SelectBoxBuilder withItems(Array<T> items) {
        this.items = items;
        return this;
    }

    public SelectBoxBuilder addListener(EventListener listener) {
        this.eventListeners.add(listener);
        return this;
    }

    public SelectBox<T> build() {
        final SelectBox<T> selectBox;
        if (skin != null) {
            if (styleName != null) selectBox = new SelectBox<T>(skin, styleName);
            else selectBox = new SelectBox<T>(skin);
        }
        else if (style != null) selectBox = new SelectBox<T>(style);
        else throw new IllegalArgumentException("Neither skin, nor style are set");

        selectBox.setPosition(x, y);
        if (width >= 0 && height >= 0)
            selectBox.setSize(width, height);
        selectBox.setVisible(isVisible);

        if (titleFont != null) {
            selectBox.getStyle().font = game.ass.get(titleFont.getFont());
        }
        if (elemsFont != null) {
            selectBox.getStyle().listStyle.font = game.ass.get(elemsFont.getFont());
        }
        if (selectedItem != null) selectBox.setSelected(selectedItem);

        if (items != null)
            selectBox.setItems(items);

        for (EventListener listener : eventListeners)
            selectBox.addListener(listener);

        return selectBox;
    }
}
