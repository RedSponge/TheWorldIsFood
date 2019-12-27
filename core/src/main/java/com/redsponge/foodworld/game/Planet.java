package com.redsponge.foodworld.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.redsponge.foodworld.Utils;
import com.redsponge.redengine.assets.AssetSpecifier;
import com.redsponge.redengine.utils.Logger;

import java.util.function.Consumer;

public class Planet {

    private Image actor;
    private boolean draggable;

    private boolean frozen;
    private int seedsLevel;
    private int humanLevel;
    private boolean hasWater;
    private boolean volcanic;

    private TextureRegion baseTex;
    private TextureRegion waterTex;
    private Animation<TextureRegion> seedsTex;
    private Animation<TextureRegion> humanTex;
    private TextureRegion iceTex;
    private TextureRegion volcanicTex;

    private Consumer<Planet> onDragRelease;

    private Circle c;

    private int timeSpent;
    private boolean dragged;

    private float scale;

    public Planet() {
        c = new Circle();
        scale = 1;
    }


    public void loadAssets(AssetSpecifier assets) {
        actor = new Image(assets.getTextureRegion("planetBase"));

        actor.addListener(new DragListener() {
            public void drag(InputEvent event, float x, float y, int pointer) {
                if (draggable) {
                    actor.moveBy(x - actor.getWidth() / 2, y - actor.getHeight() / 2);
                }
                dragged = true;
            }

            @Override
            public void dragStop(InputEvent event, float x, float y, int pointer) {
                if(draggable && onDragRelease != null) {
                    onDragRelease.accept(Planet.this);
                }
                dragged = false;
            }
        });

        baseTex = assets.getTextureRegion("planetBase");
        waterTex = assets.getTextureRegion("planetWater");
        iceTex = assets.getTextureRegion("planetIce");
        seedsTex = assets.getAnimation("planetSeeds");
        humanTex = assets.getAnimation("planetHuman");
        volcanicTex = assets.getTextureRegion("planetVolcano");
    }

    public float getScale() {
        return scale;
    }

    public Planet setScale(float scale) {
        this.scale = scale;
        return this;
    }

    public void addToStage(Stage stage) {
        stage.addActor(actor);
    }

    public void render(SpriteBatch batch) {

        batch.setColor(Color.WHITE);
//        Logger.log(this, actor.getX() - actor.getWidth() * (scale - 1));

        float x = actor.getX() - (actor.getWidth() * (scale - 1)) / 2f;
        float y = actor.getY() - (actor.getHeight() * (scale - 1)) / 2f;
        float w = actor.getWidth() * scale;
        float h = actor.getHeight() * scale;

        batch.draw(baseTex, x, y, w, h);

        if(frozen) {
            batch.draw(iceTex, x, y, w, h);
        }
        if(hasWater) {
            batch.draw(waterTex, x, y, w, h);
        }
        if(seedsLevel > 0) {
            batch.draw(Utils.getFrame(seedsTex, seedsLevel - 1), x, y, w, h);
        }
        if(humanLevel > 0) {
            batch.draw(Utils.getFrame(humanTex, humanLevel - 1), x, y, w, h);
        }
        if(volcanic) {
            batch.draw(volcanicTex, x, y, w, h);
        }


//        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
//        shapeRenderer.setColor(Color.RED);
//        shapeRenderer.circle(pos.getX() + size.getX() / 2f, pos.getY() + size.getY() / 2f, 10);
//        shapeRenderer.end();
    }

    public void setPosition(float x, float y) {
        actor.setPosition(x, y);
    }

    public boolean isDraggable() {
        return draggable;
    }

    public Planet setDraggable(boolean draggable) {
        this.draggable = draggable;
        return this;
    }

    public boolean isFrozen() {
        return frozen;
    }

    public Planet setFrozen(boolean frozen) {
        this.frozen = frozen;
        return this;
    }

    public void progressTime() {
        if(dragged) return;
        timeSpent += 10;
        if(timeSpent % 1000 == 0) {
            progressPlanetStatus();
        }
    }

    private void progressPlanetStatus() {
        if(humanLevel != 0) {
            humanLevel++;
            if(humanLevel > 5) {
                humanLevel = 5;
            }
        }
        if(frozen) {
            frozen = false;
        } else if(seedsLevel > 0) {
            seedsLevel++;
            if(seedsLevel > 5) seedsLevel = 5;
        }

        if(timeSpent == 6000) {
            // TODO: KABOOM
        }
    }

    public int getSeedsLevel() {
        return seedsLevel;
    }

    public Planet setSeedsLevel(int seedsLevel) {
        this.seedsLevel = seedsLevel;
        return this;
    }

    public int getHumanLevel() {
        return humanLevel;
    }

    public Planet setHumanLevel(int humanLevel) {
        this.humanLevel = humanLevel;
        return this;
    }

    public boolean hasWater() {
        return hasWater;
    }

    public Planet setHasWater(boolean hasWater) {
        this.hasWater = hasWater;
        return this;
    }

    public Actor getActor() {
        return actor;
    }


    public Consumer<Planet> getOnDragRelease() {
        return onDragRelease;
    }

    public Planet setOnDragRelease(Consumer<Planet> onDragRelease) {
        this.onDragRelease = onDragRelease;
        return this;
    }

    public Circle asCircle() {
        c.set(actor.getX() + 16, actor.getY() + 16, 16);
        return c;
    }

    public int getTimeSpent() {
        return timeSpent;
    }

    public boolean isVolcanic() {
        return volcanic;
    }

    public Planet setVolcanic(boolean volcanic) {
        this.volcanic = volcanic;
        return this;
    }
}
