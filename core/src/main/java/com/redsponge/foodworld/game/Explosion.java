package com.redsponge.foodworld.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.redsponge.redengine.screen.components.AnimationComponent;
import com.redsponge.redengine.screen.components.RenderCentering;
import com.redsponge.redengine.screen.entity.ScreenEntity;
import com.redsponge.redengine.utils.Logger;

public class Explosion extends ScreenEntity {
    private float x, y;

    private static Animation<TextureRegion> kaboom;
    private AnimationComponent animC;

    public Explosion(SpriteBatch b, ShapeRenderer sr, float x, float y) {
        super(b, sr);
        this.x = x;
        this.y = y;
    }

    @Override
    public void added() {
        pos.set(x - 16, y - 16, 100);
        size.set(64, 64);
//        render.setCentering(RenderCentering.CENTER);
        Logger.log(this, "HELLO");
    }

    @Override
    public void loadAssets() {
        super.loadAssets();
        if(kaboom == null) {
            Texture bam = assets.get("explosion", Texture.class);
            TextureRegion[] frames = new TextureRegion[9];
            for (int i = 0; i < frames.length; i++) {
                frames[i] = new TextureRegion(bam, i * 96, 0, 96, 96);
            }
            kaboom = new Animation<TextureRegion>(0.1f, frames);
        }
        add((animC = new AnimationComponent(kaboom)));
    }

    @Override
    public void additionalTick(float delta) {
        super.additionalTick(delta);
        Logger.log(this, "tick", pos);
        if(kaboom.isAnimationFinished(animC.getAnimationTime())) {
            Logger.log(this, "removed");
            remove();
        }
    }
}
