package com.redsponge.foodworld.game.stations;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class PrepareStation extends GameStation {

    public PrepareStation(ShapeRenderer shapeRenderer, SpriteBatch batch, FitViewport viewport) {
        super(shapeRenderer, batch, viewport);
    }

    @Override
    public void tick(float delta) {

    }

    @Override
    public void render() {
        shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);
        shapeRenderer.setColor(0.4f, 0.4f, 0.9f, 1.0f);
        shapeRenderer.begin(ShapeType.Filled);
        shapeRenderer.rect(0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        shapeRenderer.end();
    }
}
