package com.redsponge.foodworld.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.redsponge.redengine.screen.components.TextureComponent;
import com.redsponge.redengine.screen.entity.ScreenEntity;

public class OrderLine extends ScreenEntity {

    public OrderLine(SpriteBatch batch, ShapeRenderer shapeRenderer) {
        super(batch, shapeRenderer);
    }

    @Override
    public void loadAssets() {
        add(new TextureComponent(assets.getTextureRegion("orderLine")));
        pos.set(0, 0, 16);
        size.set(320, 180);
    }
}
