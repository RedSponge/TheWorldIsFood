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
                new OrderStation(shapeRenderer, batch, viewport),
                new PrepareStation(shapeRenderer, batch, viewport),
                new GrowStation(shapeRenderer, batch, viewport),
                new FinalizeScreen(shapeRenderer, batch, viewport),
        };
        add(new RenderRunnableComponent(() -> {
            batch.end();
            stations[selectedIndex].render();
            batch.begin();
        }));
        pos.set(0, 0, 5);
        size.set(320, 180);

    }

    public void setSelectedIndex(int selectedIndex) {
        stations[this.selectedIndex].hide();
        this.selectedIndex = selectedIndex;
        stations[this.selectedIndex].show();
    }
}
