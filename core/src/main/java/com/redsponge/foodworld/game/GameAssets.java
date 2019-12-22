package com.redsponge.foodworld.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.redsponge.redengine.assets.Asset;
import com.redsponge.redengine.assets.AssetSpecifier;
import com.redsponge.redengine.assets.atlas.AtlasAnimation;
import com.redsponge.redengine.assets.atlas.AtlasFrame;

public class GameAssets extends AssetSpecifier {

    public GameAssets(AssetManager am) {
        super(am);
    }

    // PLANETS

    @Asset(value = "textures/planet.atlas")
    private TextureAtlas planetAtlas;

    @AtlasFrame(frameName = "base", atlas = "planetAtlas")
    private TextureRegion planetBase;

    @AtlasFrame(frameName = "ice", atlas = "planetAtlas")
    private TextureRegion planetIce;

    @AtlasFrame(frameName = "water", atlas = "planetAtlas")
    private TextureRegion planetWater;

    @AtlasAnimation(animationName = "human", atlas = "planetAtlas", length = 5)
    private Animation<TextureRegion> planetHuman;

    @AtlasAnimation(animationName = "seeds", atlas = "planetAtlas", length = 4)
    private Animation<TextureRegion> planetSeeds;


    // BUTTONS

    @Asset("textures/ui.atlas")
    private TextureAtlas uiAtlas;

    @AtlasFrame(frameName = "foreground", atlas = "uiAtlas")
    private TextureRegion uiForeground;

    @AtlasAnimation(animationName = "button", atlas = "uiAtlas", length = 4)
    private Animation<TextureRegion> uiButtons;
}
