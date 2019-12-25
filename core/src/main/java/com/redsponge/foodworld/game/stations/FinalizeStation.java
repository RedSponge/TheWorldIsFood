package com.redsponge.foodworld.game.stations;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.redsponge.foodworld.game.GameScreen;
import com.redsponge.foodworld.game.Planet;
import com.redsponge.foodworld.game.ScreenButtonRunnable;
import com.redsponge.redengine.assets.AssetSpecifier;
import com.redsponge.redengine.assets.Fonts;
import com.redsponge.redengine.utils.Logger;

public class FinalizeStation extends GameStation {

    private TextureRegion background;

    private DelayedRemovalArray<Planet> planets;

    private Planet modifiedPlanet;

    private BitmapFont font;

    private static final Object[] BUTTON_CONFIG = {
        "growFetchButton", 24, 56,
        "buttonOk", 295, 90,
        "prepareAddIce", 94, 13,
        "finalizeAddVolcanic", 226, 13,
        "buttonTrash", 24, 124
    };

    private final Runnable[] BUTTON_ACTIONS = {
        this::fetchPlanet,
        this::finishPlanet,
        this::addIce,
        this::addVolcanic,
        this::trash
    };



    private static final int BUTTON_FETCH = 0;
    private static final int BUTTON_FINISH = 1;
    private static final int BUTTON_ICE = 2;
    private static final int BUTTON_VOLCANIC = 3;
    private static final int BUTTON_TRASH = 4;

    private ScreenButtonRunnable[] buttons;

    public FinalizeStation(GameScreen screen, ShapeRenderer shapeRenderer, SpriteBatch batch, FitViewport viewport) {
        super(screen, shapeRenderer, batch, viewport);

        buttons = new ScreenButtonRunnable[BUTTON_ACTIONS.length];
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new ScreenButtonRunnable(batch, shapeRenderer, (int) viewport.getWorldWidth(), (int) viewport.getWorldHeight(), (String) BUTTON_CONFIG[i * 3], (int) BUTTON_CONFIG[i * 3 + 1], 180 - (int)BUTTON_CONFIG[i * 3 + 2]);
            buttons[i].setOnClick(BUTTON_ACTIONS[i]);
        }
        planets = new DelayedRemovalArray<>();

        font = Fonts.getFont("pixelmix", 8);
    }

    public void updateButtons() {
        buttons[BUTTON_FETCH].setActive(planets.size > 0 && modifiedPlanet == null);
        buttons[BUTTON_FINISH].setActive(modifiedPlanet != null);
        buttons[BUTTON_ICE].setActive(modifiedPlanet != null && !modifiedPlanet.isFrozen());
        buttons[BUTTON_VOLCANIC].setActive(modifiedPlanet != null && !modifiedPlanet.isVolcanic());
        buttons[BUTTON_TRASH].setActive(modifiedPlanet != null);
    }

    @Override
    public void show() {
        for (int i = 0; i < buttons.length; i++) {
            screen.addEntity(buttons[i]);
        }
        updateButtons();
    }

    @Override
    public void loadAssets(AssetSpecifier as) {
        background = as.getTextureRegion("finalizeBackground");
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

        batch.begin();
        batch.setColor(Color.WHITE);
        batch.draw(background, 0, 0);
        if(modifiedPlanet != null) {
            modifiedPlanet.render(batch);
        }
        font.draw(batch, "Pending\n" + planets.size, 20, 180 - 68, 9, Align.center, false);
        batch.end();
    }

    @Override
    public void hide() {
        for (int i = 0; i < buttons.length; i++) {
            screen.removeEntity(buttons[i]);
        }
    }

    private void fetchPlanet() {
        if(planets.size > 0 && modifiedPlanet == null) {
            modifiedPlanet = planets.first();
            modifiedPlanet.setPosition((int) viewport.getWorldWidth() / 2 - 16, (int) viewport.getWorldHeight() / 2 - 16);
            modifiedPlanet.setScale(2);
            planets.removeIndex(0);
        }
        updateButtons();
    }

    private void finishPlanet() {
        modifiedPlanet = null;
        updateButtons();
    }

    private void addIce() {
        modifiedPlanet.setFrozen(true);
        updateButtons();
    }

    private void addVolcanic() {
        modifiedPlanet.setVolcanic(true);
        updateButtons();
    }

    private void trash() {
        modifiedPlanet = null;
        updateButtons();
    }


    public void addPlanet(Planet p) {
        Logger.log(this, "GOT NEW PLANET", p);
        planets.add(p);
    }
}
