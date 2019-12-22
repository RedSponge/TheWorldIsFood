package com.redsponge.foodworld.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;

public abstract class GameStation {

    protected ShapeRenderer shapeRenderer;
    protected SpriteBatch batch;
    protected FitViewport viewport;

    public GameStation(ShapeRenderer shapeRenderer, SpriteBatch batch, FitViewport viewport) {
        this.shapeRenderer = shapeRenderer;
        this.batch = batch;
        this.viewport = viewport;
    }

    public abstract void tick(float delta);
    public abstract void render();

}
