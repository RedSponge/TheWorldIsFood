package com.redsponge.foodworld.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.redsponge.foodworld.game.stations.*;
import com.redsponge.redengine.screen.components.RenderRunnableComponent;
import com.redsponge.redengine.screen.entity.ScreenEntity;
import com.redsponge.redengine.screen.systems.RenderSystem;

public class GameStations extends ScreenEntity {

    private GameStation[] stations;
    private int selectedIndex;
    private RenderRunnableComponent renderRunnable;

    public GameStations(SpriteBatch batch, ShapeRenderer shapeRenderer) {
        super(batch, shapeRenderer);
    }

    @Override
    public void added() {
        FitViewport viewport = screen.getEntitySystem(RenderSystem.class).getViewport();
        stations = new GameStation[] {
                new OrderStation((GameScreen) screen, shapeRenderer, batch, viewport),
                new PrepareStation((GameScreen) screen, shapeRenderer, batch, viewport),
                new GrowStation((GameScreen) screen, shapeRenderer, batch, viewport),
                new FinalizeScreen((GameScreen) screen, shapeRenderer, batch, viewport),
        };
        add(new RenderRunnableComponent(() -> {
            batch.end();
            stations[selectedIndex].render();
            batch.begin();
        }));
        pos.set(0, 0, 5);
        size.set(320, 180);
    }

    @Override
    public void loadAssets() {
        for (int i = 0; i < stations.length; i++) {
            stations[i].loadAssets(assets);
        }
    }

    public void additionalTick(float delta) {
        stations[selectedIndex].tick(delta);
    }

    public void setSelectedIndex(int selectedIndex) {
        stations[this.selectedIndex].hide();
        this.selectedIndex = selectedIndex;
        stations[this.selectedIndex].show();
    }

    public GameStation getSelectedStation() {
        return stations[this.selectedIndex];
    }

    public GameStation[] getStations() {
        return stations;
    }
}
