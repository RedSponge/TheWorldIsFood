package com.redsponge.foodworld.game.stations;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.redsponge.foodworld.game.GameScreen;
import com.redsponge.foodworld.game.Planet;
import com.redsponge.foodworld.game.ScreenButtonRunnable;
import com.redsponge.redengine.assets.AssetSpecifier;
import com.redsponge.redengine.utils.Logger;

public class PrepareStation extends GameStation {

    private Planet planet;
    private TextureRegion background;

    private static final int BUTTON_NEW = 0;
    private static final int BUTTON_WATER = 1;
    private static final int BUTTON_PLANTS = 2;
    private static final int BUTTON_SEEDS = 3;
    private static final int BUTTON_SPARSE = 4;
    private static final int BUTTON_TONS = 5;
    private static final int BUTTON_ICE = 6;
    private static final int BUTTON_HUMANS = 7;
    private static final int BUTTON_TRASH = 8;
    private static final int BUTTON_ACCEPT = 9;

    private final Object[] buttonConfig = {
            160, 19, "prepareNew",
            42, 21, "prepareAddWater",
            79, 21, "prepareAddPlants",
            23, 42, "preparePlantsSeeds",
            60, 42, "preparePlantsSparse",
            97, 42, "preparePlantsTons",
            220, 30, "prepareAddIce",
            283, 30, "prepareAddHumanity",
            25, 132, "buttonTrash",
            295, 132, "buttonOk"
    };

    private final Runnable[] buttonActions = {
            this::newPlanet,
            this::addWater,
            this::addPlants,
            this::addPlantsSeeds,
            this::addPlantsSparse,
            this::addPlantsTons,
            this::addIce,
            this::addHumanity,
            this::trash,
            this::okPlanet,
    };

    private void okPlanet() {
        ((GrowStation) screen.getStations().getStations()[2]).addPlanet(planet);
        trash();

        updateButtons();
    }

    private void trash() {
        planet.getActor().remove();
        screen.removeEntity(planet);
        planet = null;
        updateButtons();
    }

    private ScreenButtonRunnable[] buttons;
    private boolean chooseSeeds;

    public PrepareStation(GameScreen screen, ShapeRenderer shapeRenderer, SpriteBatch batch, FitViewport viewport) {
        super(screen, shapeRenderer, batch, viewport);
        planet = null;
        buttons = new ScreenButtonRunnable[buttonConfig.length / 3];
        createButtons();
    }

    private void createButtons() {
        for (int i = 0; i < buttons.length; i++) {
            int x = (int) buttonConfig[i * 3];
            int y = (int) buttonConfig[i * 3 + 1];
            String tex = (String) buttonConfig[i * 3 + 2];
            buttons[i] = new ScreenButtonRunnable(batch, shapeRenderer, (int) viewport.getWorldWidth(), (int) viewport.getWorldHeight(), tex, x, (int) viewport.getWorldHeight() - y);
            buttons[i].setOnClick(buttonActions[i]);
        }
    }

    @Override
    public void show() {
        for (int i = 0; i < buttons.length; i++) {
            screen.addEntity(buttons[i]);
        }
        if(planet != null) {
            screen.addEntity(planet);
            planet.setPosition((int) viewport.getWorldWidth() / 2 - 16, (int) viewport.getWorldHeight() / 2 - 16);
        }
        
        Logger.log(this, buttons[BUTTON_WATER]);

        updateButtons();
    }

    private void updateButtons() {
        buttons[0].setActive(planet == null);
        for(int i = 1; i < buttons.length; i++) { // 0 is new
            buttons[i].setActive(planet != null);
        }

        if(planet == null) {
            return;
        }

        buttons[BUTTON_WATER].setActive(!planet.hasWater());
        if(!planet.hasWater() || planet.getSeedsLevel() > 0) {
            for(int i = BUTTON_PLANTS; i < BUTTON_TONS + 1; i++) {
                buttons[i].setActive(false);
            }
        }

        if(chooseSeeds) {
            for (int i = 0; i < buttons.length; i++) {
                buttons[i].setActive(i >= BUTTON_SEEDS && i <= BUTTON_TONS);
            }
        }

        if(planet.getHumanLevel() > 0){
            buttons[BUTTON_HUMANS].setActive(false);
        }

        if(planet.isFrozen()) {
            buttons[BUTTON_ICE].setActive(false);
        }
    }

    @Override
    public void loadAssets(AssetSpecifier as) {
        background = as.getTextureRegion("prepareBackground");
    }

    @Override
    public void hide() {
        for (int i = 0; i < buttons.length; i++) {
            screen.removeEntity(buttons[i]);
        }
        if(planet != null) {
            screen.removeEntity(planet);
        }
    }

    @Override
    public void tick(float delta) {
        stage.act(delta);
    }

    private void newPlanet() {
        planet = new Planet(batch, shapeRenderer);
        screen.addEntity(planet);
        planet.addToStage(stage);

        planet.setPosition((int) viewport.getWorldWidth() / 2 - 16, (int) viewport.getWorldHeight() / 2 - 16);
        updateButtons();
    }

    private void addWater() {
        planet.setHasWater(true);
        buttons[BUTTON_WATER].setActive(false);
        buttons[BUTTON_PLANTS].setActive(true);
    }

    private void addPlants() {
        chooseSeeds = true;
        updateButtons();
    }

    private void addPlantsSeeds() {
        planet.setSeedsLevel(1);
        chooseSeeds = false;
        updateButtons();
    }

    private void addPlantsSparse() {
        planet.setSeedsLevel(2);
        chooseSeeds = false;
        updateButtons();
    }

    private void addPlantsTons() {
        planet.setSeedsLevel(3);
        chooseSeeds = false;
        updateButtons();
    }

    private void addIce() {
        planet.setFrozen(true);
        updateButtons();
    }

    private void addHumanity() {
        planet.setHumanLevel(1);
        updateButtons();
    }

    @Override
    public void render() {
        shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);
        shapeRenderer.setColor(0.4f, 0.4f, 0.9f, 1.0f);
        shapeRenderer.begin(ShapeType.Filled);
        shapeRenderer.rect(0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        shapeRenderer.end();

        batch.begin();
        batch.setColor(Color.WHITE);
        batch.draw(background, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        batch.end();

        stage.draw();
    }
}
