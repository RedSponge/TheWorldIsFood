package com.redsponge.foodworld.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.redsponge.redengine.screen.components.RenderCentering;
import com.redsponge.redengine.screen.components.RenderComponent;
import com.redsponge.redengine.screen.components.TextureComponent;
import com.redsponge.redengine.screen.entity.ScreenEntity;
import com.redsponge.redengine.utils.Logger;

public class ScreenButton extends ScreenEntity {

    private TextureComponent texture;
    private int index;
    private int screenWidth, screenHeight;
    private Rectangle buttonHitbox;

    public ScreenButton(SpriteBatch batch, ShapeRenderer shapeRenderer, int index, int screenWidth, int screenHeight) {
        super(batch, shapeRenderer);
        this.index = index;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    @Override
    public void loadAssets() {
        Animation<TextureRegion> anim = assets.getAnimation("uiButtons");
        texture = new TextureComponent(anim.getKeyFrame(index * anim.getFrameDuration()));
        add(texture);

        pos.set((float) (screenWidth / 5 * (index + 1)), 20, 15);
        size.set(texture.getTexture().getRegionWidth(), texture.getTexture().getRegionHeight());

        buttonHitbox = new Rectangle(pos.getX() - size.getX() / 2f, pos.getY() - size.getY() / 2f, size.getX(), size.getY());
        render.setCentering(RenderCentering.CENTER);
    }

    private boolean selected = true;

    @Override
    public void additionalTick(float delta) {
        if(buttonHitbox.contains(GameScreen.mousePos)) {
//            Logger.log(this, index, "HELLO");
            size.set(texture.getTexture().getRegionWidth(), texture.getTexture().getRegionHeight());
            if(!selected) {
                render.setScaleX(1.2f).setScaleY(1.2f).setColor(Color.WHITE);
            }
            selected = true;
            if(MouseInput.isKeyPressed(MouseInput.RIGHT)) {
                Logger.log(this, "NYEH", index);
                ((GameScreen)screen).setStation(index);
            }
        } else {
            size.set(texture.getTexture().getRegionWidth(), texture.getTexture().getRegionHeight());
            if(selected) {
                render.setScaleX(1.0f).setScaleY(1.0f).setColor(Color.GRAY);
            }
            selected = false;
        }
    }
}
