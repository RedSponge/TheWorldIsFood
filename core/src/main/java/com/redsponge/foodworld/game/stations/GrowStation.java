package com.redsponge.foodworld.game.stations;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.redsponge.foodworld.game.GameScreen;
import com.redsponge.foodworld.game.Planet;
import com.redsponge.foodworld.game.ScreenButtonRunnable;
import com.redsponge.redengine.assets.AssetSpecifier;

public class GrowStation extends GameStation {

    private Grower[] growers;
    private TextureRegion background;
    private ScreenButtonRunnable fetchButton;
    private Planet fetchedPlanet;

    private static final int[] GROWER_POSITIONS = {
        63, 60,
        191, 60
    };

    private DelayedRemovalArray<Planet> basePlanets;

    public GrowStation(GameScreen screen, ShapeRenderer shapeRenderer, SpriteBatch batch, FitViewport viewport) {
        super(screen, shapeRenderer, batch, viewport);
        growers = new Grower[GROWER_POSITIONS.length / 2];
        for (int i = 0; i < GROWER_POSITIONS.length / 2; i++) {
            growers[i] = new Grower(GROWER_POSITIONS[i * 2] + 34 , 180 - GROWER_POSITIONS[i * 2 + 1]);
        }

        basePlanets = new DelayedRemovalArray<>();
        fetchButton = new ScreenButtonRunnable(batch, shapeRenderer, (int) viewport.getWorldWidth(), (int) viewport.getWorldHeight(), "growFetchButton", 24, 180 - 133);
        fetchButton.setOnClick(this::fetchPlanet);
        updateButton();
    }

    private void updateButton() {
        fetchButton.setActive(!basePlanets.isEmpty());
    }

    private void fetchPlanet() {
        fetchedPlanet = basePlanets.first();
        basePlanets.removeIndex(0);

        screen.addEntity(fetchedPlanet);

        stage.addActor(fetchedPlanet.getActor());
        fetchedPlanet.setPosition(66 - 16, 180-101 - 16);

        updateButton();
    }

    public void addPlanet(Planet planet) {
        basePlanets.add(planet);
    }

    @Override
    public void loadAssets(AssetSpecifier as) {
        for (int i = 0; i < growers.length; i++) {
            growers[i].loadAssets(as);
        }
        background = as.getTextureRegion("growBackground");
    }

    @Override
    public void show() {
        screen.addEntity(fetchButton);
        updateButton();
    }

    @Override
    public void tick(float delta) {
        for (int i = 0; i < growers.length; i++) {
            growers[i].tick(delta);
        }
    }

    @Override
    public void render() {
//        shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);
//        shapeRenderer.setColor(0.8f, 0.5f, 0.0f, 1.0f);
//        shapeRenderer.begin(ShapeType.Filled);
//        shapeRenderer.rect(0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
//        shapeRenderer.end();

        batch.begin();
        batch.setColor(Color.WHITE);
        batch.draw(background, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        for (int i = 0; i < growers.length; i++) {
            growers[i].render(shapeRenderer, batch);
        }
        batch.setColor(Color.WHITE);
        batch.end();
    }

    @Override
    public void hide() {
        screen.removeEntity(fetchButton);
    }
}
