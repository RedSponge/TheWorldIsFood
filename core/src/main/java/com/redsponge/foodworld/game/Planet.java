package com.redsponge.foodworld.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.redsponge.foodworld.Utils;
import com.redsponge.redengine.assets.AssetSpecifier;
import com.redsponge.redengine.screen.components.RenderRunnableComponent;
import com.redsponge.redengine.screen.components.TextureComponent;

import java.util.function.Consumer;

public class Planet {

    private Image actor;
    private boolean draggable;

    private boolean frozen;
    private int seedsLevel;
    private int humanLevel;
    private boolean hasWater;

    private TextureRegion baseTex;
    private TextureRegion waterTex;
    private Animation<TextureRegion> seedsTex;
    private Animation<TextureRegion> humanTex;
    private TextureRegion iceTex;

    private Consumer<Planet> onDragRelease;

    private Circle c;

    private int timeSpent;
    private boolean dragged;

    public Planet() {
        c = new Circle();
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
    }

    public void addToStage(Stage stage) {
        stage.addActor(actor);
    }

    public void render(SpriteBatch batch) {

        batch.setColor(Color.WHITE);
        batch.draw(baseTex, actor.getX(), actor.getY());

        if(frozen) {
            batch.draw(iceTex, actor.getX(), actor.getY());
        }
        if(hasWater) {
            batch.draw(waterTex, actor.getX(), actor.getY());
        }
        if(seedsLevel > 0) {
            batch.draw(Utils.getFrame(seedsTex, seedsLevel - 1), actor.getX(), actor.getY());
        }
        if(humanLevel > 0) {
            batch.draw(Utils.getFrame(humanTex, humanLevel - 1), actor.getX(), actor.getY());
        }


//        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
//        shapeRenderer.setColor(Color.RED);
//        shapeRenderer.circle(pos.getX() + size.getX() / 2f, pos.getY() + size.getY() / 2f, 10);
//        shapeRenderer.end();
    }

    public void setPosition(int x, int y) {
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
}
