package com.redsponge.foodworld.game.stations;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.redsponge.foodworld.game.GameScreen;
import com.redsponge.foodworld.game.ScreenButtonRunnable;
import com.redsponge.redengine.utils.Logger;

public class OrderStation extends GameStation {

    private ScreenButtonRunnable newOrder;
    public static int orderCount;

    public OrderStation(GameScreen screen, ShapeRenderer shapeRenderer, SpriteBatch batch, FitViewport viewport) {
        super(screen, shapeRenderer, batch, viewport);
        newOrder = new ScreenButtonRunnable(batch, shapeRenderer, (int) viewport.getWorldWidth(), (int) viewport.getWorldHeight(), "buttonNewOrder", (int) viewport.getWorldWidth() / 2, (int) viewport.getWorldHeight() / 2);
        Logger.log(this, newOrder);
        newOrder.setOnClick(() -> {
            screen.addEntity(screen.createOrder());
            updateButtons();
        });
    }

    public void updateButtons() {
        newOrder.setActive(orderCount < 8);
    }

    @Override
    public void show() {
        Logger.log(this, "show");
        screen.addEntity(newOrder);
    }

    @Override
    public void tick(float delta) {

    }

    @Override
    public void render() {
        shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);
        shapeRenderer.setColor(0.1f, 0.5f, 0.1f, 1.0f);
        shapeRenderer.begin(ShapeType.Filled);
        shapeRenderer.rect(0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        shapeRenderer.end();
    }

    @Override
    public void hide() {
        newOrder.remove();
    }
}
