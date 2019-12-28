package com.redsponge.foodworld.game;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.redsponge.foodworld.game.stations.GameStation;
import com.redsponge.redengine.assets.AssetSpecifier;
import com.redsponge.redengine.screen.AbstractScreen;
import com.redsponge.redengine.screen.systems.RenderSystem;
import com.redsponge.redengine.utils.GameAccessor;

import java.lang.reflect.Field;

public class GameScreen extends AbstractScreen {

    public static final float RATIO = 9/16f;
    public static final int WIDTH = 320;
    public static final int HEIGHT = (int) (WIDTH * RATIO);
    public static GameScreen instance;

    private FitViewport guiViewport;
    private RenderSystem renderSystem;

    private GameGUI gui;
    private GameStations stations;

    private InputMultiplexer inputMultiplexer;
    public Engine _engine;

    public static final Vector2 mousePos = new Vector2();
    private Stage stage;
    private TextureRegion orderLine;

    private Music bgMusic;

    public GameScreen(GameAccessor ga) {
        super(ga);
    }

    @Override
    public void show() {
        super.show();

        try {
            Field f = AbstractScreen.class.getDeclaredField("engine");
            f.setAccessible(true);
            _engine = (Engine) f.get(this);
            instance = this;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        inputMultiplexer = new InputMultiplexer();
        Gdx.input.setInputProcessor(inputMultiplexer);
        inputMultiplexer.addProcessor(MouseInput.getInstance());

        renderSystem = getEntitySystem(RenderSystem.class);
        stage = new Stage(renderSystem.getViewport(), batch);
        inputMultiplexer.addProcessor(stage);

        stations = new GameStations(batch, shapeRenderer);
        addEntity(stations);

        gui = new GameGUI(batch, shapeRenderer);
        addEntity(gui);

        addEntity(new OrderLine(batch, shapeRenderer));
        for (int i = 0; i < 8; i++) {
            addEntity(new Order(batch, shapeRenderer, MathUtils.random(6), MathUtils.random(5), MathUtils.randomBoolean(), MathUtils.randomBoolean(), MathUtils.randomBoolean(), i));
        }

        orderLine = assets.getTextureRegion("orderLine");

        bgMusic = assets.get("backgroundMusic", Music.class);
        bgMusic.setLooping(true);
        bgMusic.setVolume(0.1f);
        bgMusic.play();
    }


    @Override
    public int getScreenWidth() {
        return WIDTH;
    }

    @Override
    public int getScreenHeight() {
        return HEIGHT;
    }

    @Override
    public void tick(float v) {
        mousePos.set(Gdx.input.getX(), Gdx.input.getY());
        renderSystem.getViewport().unproject(mousePos);

        tickEntities(v);
        updateEngine(v);

        stage.act(v);
        stage.setDebugAll(true);
        stage.draw();

        MouseInput.getInstance().allOff();
    }

    @Override
    public void render() {
    }

    @Override
    public void reSize(int width, int height) {
        renderSystem.resize(width, height);
    }

    @Override
    public Class<? extends AssetSpecifier> getAssetSpecsType() {
        return GameAssets.class;
    }

    public void setStation(int index) {
        GameStation old = stations.getSelectedStation();
        inputMultiplexer.removeProcessor(old.getStage());
        stations.setSelectedIndex(index);
        inputMultiplexer.addProcessor(stations.getSelectedStation().getStage());
    }

    public Stage getStage() {
        return stage;
    }

    public GameStations getStations() {
        return stations;
    }
}
