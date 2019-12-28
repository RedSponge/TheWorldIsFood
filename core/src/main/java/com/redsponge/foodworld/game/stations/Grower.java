package com.redsponge.foodworld.game.stations;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Circle;
import com.redsponge.foodworld.game.Explosion;
import com.redsponge.foodworld.game.GameScreen;
import com.redsponge.foodworld.game.Planet;
import com.redsponge.redengine.assets.AssetSpecifier;
import com.redsponge.redengine.screen.components.Mappers;
import com.redsponge.redengine.utils.Logger;

public class Grower  {

    private Circle detectorCircle;

    private TextureRegion base;
    private TextureRegion light;

    private float time;
    private Planet content;

    private GameScreen screen;

    public Grower(int x, int y, GameScreen screen) {
        this.screen = screen;
        detectorCircle = new Circle(x, y, 24);
        time = (float) Math.random();
    }

    public void loadAssets(AssetSpecifier as) {
        base = as.getTextureRegion("growerBase");
        light = as.getTextureRegion("growerLight");
    }

    public void tick(float delta) {
        time += delta;
        if(content != null) {
            content.progressTime();
        }
    }

    public void render(ShapeRenderer shapeRenderer, SpriteBatch batch) {
        batch.setColor(Color.WHITE);
        batch.draw(base, detectorCircle.x - base.getRegionWidth() / 2f, detectorCircle.y - base.getRegionHeight() / 2f + 20);

        if(content != null){
            content.render(batch);
        }

        float opa = (float) Math.sin(time * 10) + 1;
        opa /= 4;
        opa += 0.2f;

        batch.setColor(1, 1, 1, opa);
        batch.draw(light, detectorCircle.x - light.getRegionWidth() / 2f, detectorCircle.y - light.getRegionHeight() / 2f + 20);

        // DEBUG:
        batch.end();
        shapeRenderer.begin(ShapeType.Line);
        shapeRenderer.setColor(Color.YELLOW);
        shapeRenderer.circle(detectorCircle.x, detectorCircle.y, detectorCircle.radius);
        shapeRenderer.end();
        batch.begin();
    }

    public void renderProgress(ShapeRenderer sr) {
        if(content == null) return;

        sr.setColor(Color.BLACK);
        sr.rect(detectorCircle.x - 30, detectorCircle.y + 20, 60, 5);
        int timeSpent = content.getTimeSpent();
        Color[] barColors = {
                new Color(0.4f, 0.6f, 1f, 1.0f),
                Color.GREEN,
                Color.YELLOW,
                Color.ORANGE,
                Color.RED,
                new Color(0.3f, 0, 0, 1.0f),
        };
        try {
            Color c = barColors[timeSpent / 1000];
            float percent = timeSpent / 6000f;
            sr.setColor(c);
            sr.rect(detectorCircle.x - 30, detectorCircle.y + 20, 60 * percent, 5);
        } catch (IndexOutOfBoundsException e) {
            Logger.log(this, "out of bounds! exploding!");
            content.explode();
            content = null;
        }
    }

    public boolean canContain(Planet p) {
        return content == null && detectorCircle.overlaps(p.asCircle());
    }

    public void setContent(Planet p) {
        if(p != null) {
            p.setPosition((int) detectorCircle.x - 16, (int) detectorCircle.y - 16);
        }
        content = p;
    }

    public Planet getContents() {
        return content;
    }
}
