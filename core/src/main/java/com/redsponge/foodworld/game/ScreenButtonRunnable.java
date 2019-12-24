package com.redsponge.foodworld.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.redsponge.redengine.screen.components.RenderCentering;
import com.redsponge.redengine.screen.components.TextureComponent;
import com.redsponge.redengine.screen.entity.ScreenEntity;
import com.redsponge.redengine.utils.Logger;

public class ScreenButtonRunnable extends ScreenEntity {

    private TextureComponent texture;
    private int screenWidth, screenHeight;
    private Rectangle buttonHitbox;

    private Runnable onClick;
    private boolean active;

    private String texName;

    private int x, y;

    public ScreenButtonRunnable(SpriteBatch batch, ShapeRenderer shapeRenderer, int screenWidth, int screenHeight, String texName, int x, int y) {
        super(batch, shapeRenderer);
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.x = x;
        this.y = y;
        this.active = true;
        this.texName = texName;
    }

    @Override
    public void loadAssets() {
        texture = new TextureComponent(assets.getTextureRegion(texName));
        add(texture);

        Logger.log(this, texture.getTexture());
        pos.set(x, y, 15);
        size.set(texture.getTexture().getRegionWidth(), texture.getTexture().getRegionHeight());

        buttonHitbox = new Rectangle(pos.getX() - size.getX() / 2f, pos.getY() - size.getY() / 2f, size.getX(), size.getY());
        render.setCentering(RenderCentering.CENTER);
    }

    private boolean selected = true;

    @Override
    public void additionalTick(float delta) {
        if(!active) {
            render.setColor(Color.GRAY);
            render.setScaleX(1.0f).setScaleY(1.0f);
            selected = true;
            return;
        }

        if(buttonHitbox.contains(GameScreen.mousePos)) {
            size.set(texture.getTexture().getRegionWidth(), texture.getTexture().getRegionHeight());
            if(!selected) {
                render.setScaleX(1.2f).setScaleY(1.2f).setColor(Color.WHITE);
            }
            selected = true;
            if(MouseInput.isKeyPressed(MouseInput.RIGHT)) {
                if(onClick != null) {
                    onClick.run();
                }
            }
        } else {
            size.set(texture.getTexture().getRegionWidth(), texture.getTexture().getRegionHeight());
            if(selected) {
                render.setScaleX(1.0f).setScaleY(1.0f).setColor(Color.LIGHT_GRAY);
            }
            selected = false;
        }
    }

    public Runnable getOnClick() {
        return onClick;
    }

    public ScreenButtonRunnable setOnClick(Runnable onClick) {
        this.onClick = onClick;
        return this;
    }

    public boolean isActive() {
        return active;
    }

    public ScreenButtonRunnable setActive(boolean active) {
        this.active = active;
        return this;
    }
}
