package com.redsponge.foodworld.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.redsponge.foodworld.Utils;
import com.redsponge.redengine.screen.components.RenderRunnableComponent;
import com.redsponge.redengine.screen.components.TextureComponent;
import com.redsponge.redengine.screen.entity.ScreenEntity;

public class Planet extends ScreenEntity {

    private Image actor;
    private TextureComponent tex;
    private boolean draggable;

    private boolean frozen;
    private int seedsLevel;
    private int humanLevel;
    private boolean hasWater;

    private TextureRegion waterTex;
    private Animation<TextureRegion> seedsTex;
    private Animation<TextureRegion> humanTex;
    private TextureRegion iceTex;

    public Planet(SpriteBatch batch, ShapeRenderer shapeRenderer) {
        super(batch, shapeRenderer);
    }

    @Override
    public void added() {
        super.added();
        pos.set(100, 100, 6);
        size.set(32, 32);

        add(new RenderRunnableComponent(() -> {
            batch.end();
            render();
            batch.begin();
        }));
    }

    @Override
    public void loadAssets() {
        actor = new Image(assets.getTextureRegion("planetBase"));
        actor.setPosition(pos.getX(), pos.getY());

        actor.addListener(new DragListener()
        {
            public void drag(InputEvent event, float x, float y, int pointer)
            {
                if(draggable)
                    actor.moveBy(x - actor.getWidth() / 2, y - actor.getHeight() / 2);
            }
        });

        waterTex = assets.getTextureRegion("planetWater");
        iceTex = assets.getTextureRegion("planetIce");
        seedsTex = assets.getAnimation("planetSeeds");
        humanTex = assets.getAnimation("planetHuman");
    }

    public void addToStage(Stage stage) {
        stage.addActor(actor);
    }

    @Override
    public void additionalTick(float delta) {
        super.additionalTick(delta);
        pos.set(actor.getX(), actor.getY());
    }

    public void render() {
        batch.begin();

        if(frozen) {
            batch.draw(iceTex, pos.getX(), pos.getY());
        }
        if(hasWater) {
            batch.draw(waterTex, pos.getX(), pos.getY());
        }
        if(seedsLevel > 0) {
            batch.draw(Utils.getFrame(seedsTex, seedsLevel - 1), pos.getX(), pos.getY());
        }
        if(humanLevel > 0) {
            batch.draw(Utils.getFrame(humanTex, humanLevel - 1), pos.getX(), pos.getY());
        }


        batch.end();


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
}
