package com.redsponge.foodworld.game.stations;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Plane;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.redsponge.foodworld.game.GameScreen;
import com.redsponge.foodworld.game.Planet;
import com.redsponge.redengine.assets.AssetSpecifier;

public abstract class GameStation {

    protected ShapeRenderer shapeRenderer;
    protected SpriteBatch batch;
    protected FitViewport viewport;

    protected GameScreen screen;

    protected Stage stage;

    public GameStation(GameScreen screen, ShapeRenderer shapeRenderer, SpriteBatch batch, FitViewport viewport) {
        this.screen = screen;
        this.shapeRenderer = shapeRenderer;
        this.batch = batch;
        this.viewport = viewport;

        stage = new Stage(viewport);
    }

    public void show() {

    }

    public abstract void tick(float delta);
    public abstract void render();

    public void loadAssets(AssetSpecifier as) {
    }

    public void hide() {
    }

    public Stage getStage() {
        return stage;
    }
}
