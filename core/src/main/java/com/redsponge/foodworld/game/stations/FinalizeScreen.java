package com.redsponge.foodworld.game.stations;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.redsponge.foodworld.game.GameScreen;
import com.redsponge.foodworld.game.Planet;
import com.redsponge.redengine.utils.Logger;

public class FinalizeScreen extends GameStation {

    public FinalizeScreen(GameScreen screen, ShapeRenderer shapeRenderer, SpriteBatch batch, FitViewport viewport) {
        super(screen, shapeRenderer, batch, viewport);
    }

    @Override
    public void tick(float delta) {

    }

    @Override
    public void render() {
        shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);
        shapeRenderer.setColor(0.8f, 0.4f, 0.4f, 1.0f);
        shapeRenderer.begin(ShapeType.Filled);
        shapeRenderer.rect(0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        shapeRenderer.end();
    }

    public void addPlanet(Planet p) {
        Logger.log(this, "GOT NEW PLANET", p);
    }
}
