package com.redsponge.foodworld.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
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

    @AtlasAnimation(animationName = "seeds", atlas = "planetAtlas", length = 5)
    private Animation<TextureRegion> planetSeeds;

    @AtlasFrame(frameName = "volcano", atlas = "planetAtlas")
    private TextureRegion planetVolcano;


    // BUTTONS

    @Asset("textures/ui.atlas")
    private TextureAtlas uiAtlas;

    @AtlasFrame(frameName = "foreground", atlas = "uiAtlas")
    private TextureRegion uiForeground;

    @AtlasAnimation(animationName = "button", atlas = "uiAtlas", length = 4)
    private Animation<TextureRegion> uiButtons;

    @AtlasFrame(frameName = "trash", atlas = "uiAtlas")
    private TextureRegion buttonTrash;

    @AtlasFrame(frameName = "done", atlas = "uiAtlas")
    private TextureRegion buttonOk;

    // PREPARE
    @Asset("textures/prepare.atlas")
    private TextureAtlas prepareAtlas;

    @AtlasFrame(frameName = "new_planet", atlas = "prepareAtlas")
    private TextureRegion prepareNew;

    @AtlasFrame(frameName = "add_water", atlas = "prepareAtlas")
    private TextureRegion prepareAddWater;

    @AtlasFrame(frameName = "add_freeze", atlas = "prepareAtlas")
    private TextureRegion prepareAddFreeze;

    @AtlasFrame(frameName = "add_humanity", atlas = "prepareAtlas")
    private TextureRegion prepareAddHumanity;

    @AtlasFrame(frameName = "add_freeze", atlas = "prepareAtlas")
    private TextureRegion prepareAddIce;

    @AtlasFrame(frameName = "add_plants", atlas = "prepareAtlas")
    private TextureRegion prepareAddPlants;

    @AtlasFrame(frameName = "plants_seeds", atlas = "prepareAtlas")
    private TextureRegion preparePlantsSeeds;

    @AtlasFrame(frameName = "plants_sparse", atlas = "prepareAtlas")
    private TextureRegion preparePlantsSparse;

    @AtlasFrame(frameName = "plants_tons", atlas = "prepareAtlas")
    private TextureRegion preparePlantsTons;

    @AtlasFrame(frameName = "background", atlas = "prepareAtlas")
    private TextureRegion prepareBackground;


    // GROW

    @Asset("textures/grow.atlas")
    private TextureAtlas growAtlas;

    @AtlasFrame(frameName = "grower_base", atlas = "growAtlas")
    private TextureRegion growerBase;

    @AtlasFrame(frameName = "grower_light", atlas = "growAtlas")
    private TextureRegion growerLight;

    @AtlasFrame(frameName = "background", atlas = "growAtlas")
    private TextureRegion growBackground;

    @AtlasFrame(frameName = "fetch", atlas = "growAtlas")
    private TextureRegion growFetchButton;

    // FINALIZE

    @Asset("textures/finalize.atlas")
    private TextureAtlas finalizeAtlas;

    @AtlasFrame(frameName = "add_volcano", atlas = "finalizeAtlas")
    private TextureRegion finalizeAddVolcanic;

    @AtlasFrame(frameName = "background", atlas = "finalizeAtlas")
    private TextureRegion finalizeBackground;

    // GENERAL

    @Asset("textures/general.atlas")
    private TextureAtlas generalAtlas;

    @AtlasFrame(frameName = "order_paper", atlas = "generalAtlas")
    private TextureRegion orderPaper;

    @AtlasFrame(frameName = "order_clip", atlas = "generalAtlas")
    private TextureRegion orderClip;

    @AtlasFrame(frameName = "order_line", atlas = "generalAtlas")
    private TextureRegion orderLine;

    // MUSIC
    @Asset("music/making_planets.ogg")
    private Music backgroundMusic;

    // SOUNDS
    @Asset("sounds/click.ogg")
    private Sound clickSound;

    @Asset("sounds/pick.ogg")
    private Sound pickSound;

    @Asset("sounds/pop.ogg")
    private Sound popSound;

    @Asset("sounds/boom.wav")
    private Sound boomSound;

    // EXPLOSION
    @Asset("textures/explosion.png")
    private Texture explosion;
}
