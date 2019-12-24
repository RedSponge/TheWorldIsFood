package com.redsponge.foodworld.game.stations;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Circle;
import com.redsponge.foodworld.game.Planet;
import com.redsponge.redengine.assets.AssetSpecifier;

public class Grower  {

    private Planet heldPlanet;
    private Circle detectorCircle;

    private TextureRegion base;
    private TextureRegion light;

    private float time;

    public Grower(int x, int y) {
        detectorCircle = new Circle(x, y, 24);
        time = (float) Math.random();
    }

    public void loadAssets(AssetSpecifier as) {
        base = as.getTextureRegion("growerBase");
        light = as.getTextureRegion("growerLight");
    }

    public void tick(float delta) {
        time += delta;
    }

    public void render(ShapeRenderer shapeRenderer, SpriteBatch batch) {
        batch.setColor(Color.WHITE);
        batch.draw(base, detectorCircle.x - base.getRegionWidth() / 2f, detectorCircle.y - base.getRegionHeight() / 2f);
        float opa = (float) Math.sin(time * 10) + 1;
        opa /= 4;
        opa += 0.2f;

        batch.setColor(1, 1, 1, opa);
        batch.draw(light, detectorCircle.x - light.getRegionWidth() / 2f, detectorCircle.y - light.getRegionHeight() / 2f);

        // DEBUG:
        batch.end();
        shapeRenderer.begin(ShapeType.Line);
        shapeRenderer.setColor(Color.YELLOW);
        shapeRenderer.circle(detectorCircle.x, detectorCircle.y, detectorCircle.radius);
        shapeRenderer.end();
        batch.begin();
    }
}
