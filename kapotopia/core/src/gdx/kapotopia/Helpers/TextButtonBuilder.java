package gdx.kapotopia.Helpers;

import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import gdx.kapotopia.AssetsManaging.FontHelper;
import gdx.kapotopia.AssetsManaging.UsualFonts;

public class TextButtonBuilder {
    private TextButton.TextButtonStyle style;
    private EventListener listener;
    private String text;
    private float x, y;
    private float bx, by, bw, bh;
    private boolean visible;

    public TextButtonBuilder(String text) {
        this.text = text;
        this.style = null;
        this.x = 0;
        this.y = 0;
        this.visible = true;
        this.listener = null;
        this.bx = -1;
        this.by = -1;
        this.bw = -1;
        this.bh = -1;
    }

    public TextButtonBuilder withStyle(TextButton.TextButtonStyle style) {
        this.style = style;
        return this;
    }

    public TextButtonBuilder withStyle(UsualFonts type) {
        this.style = FontHelper.getStyleFont(type);
        return this;
    }

    public TextButtonBuilder withPosition(float x, float y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public TextButtonBuilder withBounds(float boundX, float boundY, float boundWeight, float boundHeight) {
        this.bx = boundX;
        this.by = boundY;
        this.bw = boundWeight;
        this.bh = boundHeight;
        return this;
    }

    public TextButtonBuilder withListener(EventListener event) {
        this.listener = event;
        return this;
    }

    public TextButtonBuilder isVisible(boolean visible) {
        this.visible = visible;
        return this;
    }

    public TextButton build() {
        TextButton tb = new TextButton(text, style);
        tb.setPosition(x, y);
        if(listener != null) {
            tb.addListener(listener);
        }
        tb.setVisible(visible);
        // It shouldn't be possible to have a negative height or weight
        if(bw >= 0 && bh >= 0) {
            tb.setBounds(bx, by, bw, by);
        }
        return tb;
    }
}
