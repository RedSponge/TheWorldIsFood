package com.redsponge.foodworld.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.redsponge.redengine.screen.components.RenderRunnableComponent;
import com.redsponge.redengine.screen.components.TextureComponent;
import com.redsponge.redengine.screen.entity.ScreenEntity;

public class Planet extends ScreenEntity {

    private Image actor;
    private TextureComponent tex;

    public Planet(SpriteBatch batch, ShapeRenderer shapeRenderer) {
        super(batch, shapeRenderer);
    }

    @Override
    public void added() {
        super.added();
        pos.set(100, 100, 4);
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
                actor.moveBy(x - actor.getWidth() / 2, y - actor.getHeight() / 2);
            }
        });

        ((GameScreen)screen).getStage().addActor(actor);
    }

    @Override
    public void additionalTick(float delta) {
        super.additionalTick(delta);
        pos.set(actor.getX(), actor.getY());
    }

    public void render() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.circle(pos.getX(), pos.getY(), 10);
        shapeRenderer.end();
    }
}
