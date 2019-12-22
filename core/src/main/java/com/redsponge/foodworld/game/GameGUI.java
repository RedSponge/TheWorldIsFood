package com.redsponge.foodworld.game;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.redsponge.redengine.screen.components.TextureComponent;
import com.redsponge.redengine.screen.entity.ScreenEntity;

public class GameGUI extends ScreenEntity {

    private TextureRegion foreground;
    private Animation<TextureRegion> buttons;

    public GameGUI(SpriteBatch batch, ShapeRenderer shapeRenderer) {
        super(batch, shapeRenderer);
    }

    @Override
    public void loadAssets() {
        foreground = assets.getTextureRegion("uiForeground");
        buttons = assets.getAnimation("uiButtons");
        pos.set(0, 0);
        render.setUseRegW(true).setUseRegH(true);

        add(new TextureComponent(foreground));
    }

    @Override
    public void added() {
        pos.setZ(10);

        for (int i = 0; i < 4; i++) {
            screen.addEntity(new ScreenButton(batch, shapeRenderer, i, (int) GameScreen.WIDTH, (int) GameScreen.HEIGHT));
        }
    }

//    public void resize(int width, int height) {
//        this.viewport.update(width, height, true);
//    }
}
