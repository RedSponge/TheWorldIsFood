package com.redsponge.foodworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.redsponge.foodworld.game.GameAssets;
import com.redsponge.redengine.assets.AssetSpecifier;
import com.redsponge.redengine.screen.AbstractScreen;
import com.redsponge.redengine.utils.GameAccessor;

public class DragDropTestScreen extends AbstractScreen {

    public DragDropTestScreen(GameAccessor ga) {
        super(ga);
    }

    private Stage stage;
    private FitViewport viewport;

    @Override
    public void show() {
        super.show();
        viewport = new FitViewport(320, 180);
        stage = new Stage(viewport, batch);

        Gdx.input.setInputProcessor(stage);

        ImageButton img = new ImageButton(new TextureRegionDrawable(assets.getTextureRegion("planetBase")));

        DragAndDrop dnd = new DragAndDrop();
        dnd.addSource(new DragAndDrop.Source(img) {
            @Override
            public DragAndDrop.Payload dragStart(InputEvent inputEvent, float x, float y, int pointer) {
                DragAndDrop.Payload p = new DragAndDrop.Payload();
                p.setDragActor(img);
                p.setInvalidDragActor(img);
                p.setObject("hello");
                p.setValidDragActor(img);

                return p;
            }
        });

        stage.addActor(img);

    }

    @Override
    public void tick(float v) {
        stage.act(v);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.draw();
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public Class<? extends AssetSpecifier> getAssetSpecsType() {
        return GameAssets.class;
    }

    @Override
    public void reSize(int width, int height) {
        viewport.update(width, height, true);
    }
}
