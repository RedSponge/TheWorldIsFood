package com.redsponge.foodworld.game.stations;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Circle;
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
    private Circle docker;
    private Circle output;

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

        docker = new Circle(55, 180 - 100, 16);
        output = new Circle(320 - 55, 180 - 100, 16);
        updateButton();
    }

    private void updateButton() {
        fetchButton.setActive(!basePlanets.isEmpty() && fetchedPlanet == null);
    }

    private void fetchPlanet() {
        fetchedPlanet = basePlanets.first();
        basePlanets.removeIndex(0);

        stage.addActor(fetchedPlanet.getActor());
        fetchedPlanet.setPosition((int) docker.x - 16, (int) docker.y - 16);
        fetchedPlanet.setDraggable(true);
        fetchedPlanet.setOnDragRelease((p) -> {
            boolean found = false;
            Grower from = null;
            for (int i = 0; i < growers.length; i++) {
                if(growers[i].canContain(p)) {
                    growers[i].setContent(p);
                    found = true;
                    if(fetchedPlanet == p) {
//                        fetchedPlanet = null;
                        fetchedPlanet = null;
                    }
                } else if(growers[i].getContents() == p){
                    growers[i].setContent(null);
                    from = growers[i];
                }
            }
            if(!found) {
                if(output.overlaps(p.asCircle())) {
                    if(from != null) {
                        from.setContent(null);
                    } else if(fetchedPlanet == p) {
                        fetchedPlanet = null;
                    }
                    p.getActor().remove();
                    ((FinalizeScreen)(screen.getStations().getStations()[3])).addPlanet(p);
                    return;
                }

                if(fetchedPlanet != p) {
                    if(from == null) throw new RuntimeException("Couldn't find from but fetchedPlanet was taken! for " + p);
                    from.setContent(p);
                } else {
                    p.setPosition((int) docker.x - 16, (int) docker.y - 16);
                    fetchedPlanet = p;
                }
            }
        });

        updateButton();
    }

    public void addPlanet(Planet planet) {
        basePlanets.add(planet);
    }

    @Override
    public void loadAssets(AssetSpecifier as) {
        if(fetchedPlanet != null) {
            fetchedPlanet.loadAssets(as);
        }
        for (int i = 0; i < growers.length; i++) {
            growers[i].loadAssets(as);
        }
        background = as.getTextureRegion("growBackground");
    }

    @Override
    public void show() {
        screen.addEntity(fetchButton);
        for (int i = 0; i < growers.length; i++) {
            if(growers[i].getContents() != null) {
                growers[i].setContent(growers[i].getContents());
            }
        }
        updateButton();
    }

    public void updateGrowers(float delta) {
        for (int i = 0; i < growers.length; i++) {
            growers[i].tick(delta);
        }
    }

    @Override
    public void tick(float delta) {
        stage.act(delta);
        updateButton();
    }

    @Override
    public void render() {
        shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);
        shapeRenderer.setColor(0.8f, 0.5f, 0.0f, 1.0f);
        shapeRenderer.begin(ShapeType.Filled);
        shapeRenderer.rect(0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        shapeRenderer.end();

        batch.begin();
        batch.setColor(Color.WHITE);
        batch.draw(background, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        for (int i = 0; i < growers.length; i++) {
            growers[i].render(shapeRenderer, batch);
        }
        if(fetchedPlanet != null) {
            fetchedPlanet.render(batch);
        }
        batch.setColor(Color.WHITE);
        batch.end();

        shapeRenderer.begin(ShapeType.Filled);
        for (int i = 0; i < growers.length; i++) {
            growers[i].renderProgress(shapeRenderer);
        }
        shapeRenderer.end();

        shapeRenderer.begin(ShapeType.Line);
        shapeRenderer.setColor(Color.YELLOW);
        shapeRenderer.circle(docker.x, docker.y, docker.radius);
        shapeRenderer.circle(output.x, output.y, output.radius);
        shapeRenderer.end();
    }

    @Override
    public void hide() {
        screen.removeEntity(fetchButton);
    }
}
