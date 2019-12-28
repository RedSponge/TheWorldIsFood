package com.redsponge.foodworld.game;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.redsponge.foodworld.game.stations.GameStation;
import com.redsponge.redengine.assets.AssetSpecifier;
import com.redsponge.redengine.assets.Fonts;
import com.redsponge.redengine.screen.AbstractScreen;
import com.redsponge.redengine.screen.systems.RenderSystem;
import com.redsponge.redengine.utils.GameAccessor;
import com.redsponge.redengine.utils.Logger;

import java.lang.reflect.Field;

public class GameScreen extends AbstractScreen {

    public static final float RATIO = 9/16f;
    public static final int WIDTH = 320;
    public static final int HEIGHT = (int) (WIDTH * RATIO);
    public static GameScreen instance;

    private RenderSystem renderSystem;

    private GameGUI gui;
    private GameStations stations;

    private InputMultiplexer inputMultiplexer;
    public Engine _engine;

    public static final Vector2 mousePos = new Vector2();
    private Stage stage;
    private TextureRegion orderLine;

    private Music bgMusic;
    private int orderCount;

    public static int score;
    private float timeLeft;

    private BitmapFont pixelMix;

    private boolean paused;
    private Texture pauseBG;

    private FitViewport pauseViewport;

    public GameScreen(GameAccessor ga) {
        super(ga);
    }

    @Override
    public void show() {
        super.show();
        try {
            Field am = AssetSpecifier.class.getDeclaredField("am");
            am.setAccessible(true);
            ((AssetManager) am.get(assets)).finishLoading();
        } catch (Exception e) {
            e.printStackTrace();
        }

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

        orderLine = assets.getTextureRegion("orderLine");

        bgMusic = assets.get("backgroundMusic", Music.class);
        bgMusic.setLooping(true);
        bgMusic.play();

        timeLeft = 60 * 3;
        pixelMix = Fonts.getFont("pixelmix", 8);
        pauseBG = assets.get("menuBackground", Texture.class);
        paused = true;
        pauseViewport = new FitViewport(640, 360);
    }

    public Order createOrder() {
        boolean hasSeeds = MathUtils.randomBoolean(0.3f);
        boolean hasWater = MathUtils.randomBoolean() || hasSeeds;
        boolean hasHuman = MathUtils.randomBoolean() || !hasSeeds;

        int humanLevel = 0;
        int seedLevel = 0;

        if(hasHuman) {
            humanLevel = MathUtils.random(2, 5);
            if(hasSeeds) {
                seedLevel = humanLevel - MathUtils.random(2); // humanLevel or humanLevel - 1
            }
        }
        else {
            seedLevel = MathUtils.random(4, 5);
        }

        boolean frozen = MathUtils.randomBoolean();
        boolean volcanic = MathUtils.randomBoolean();

        Logger.log(this, humanLevel);
        return new Order(batch, shapeRenderer, humanLevel, seedLevel, frozen, volcanic, hasWater, orderCount++ % 9); // 0->8 then increment
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
        if(Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
            paused = !paused;
        }
        if(!paused && timeLeft >= 0) {
            mousePos.set(Gdx.input.getX(), Gdx.input.getY());
            renderSystem.getViewport().unproject(mousePos);

            tickEntities(v);
            updateEngine(v);

            stage.act(v);
            stage.setDebugAll(true);
            stage.draw();
            timeLeft -= v;

        } else if(timeLeft < 0) {
            if(Gdx.input.isKeyJustPressed(Keys.SPACE)) {
                timeLeft = 3 * 60;
                score = 0;
            }
        }
        MouseInput.getInstance().allOff();
    }

    @Override
    public void render() {
        if(timeLeft < 0) {
            pauseViewport.apply();
            batch.setProjectionMatrix(pauseViewport.getCamera().combined);
            batch.begin();


            int w = 320 * 2;
            int h = 180 * 2;

            Gdx.gl.glClearColor(0, 0, 0, 1.0f);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            batch.setColor(Color.WHITE);
            batch.draw(pauseBG, 0, 0, w, h);

            pixelMix.getData().setScale(2f);
            drawCentered(w, h - 20, "PLANET BAKER", batch, pixelMix);

            drawCentered(w, h - 40, "It's a Wrap!", batch, pixelMix);

            drawCentered(w, h - 80, "TOTAL SCORE: " + score, batch, pixelMix);

            pixelMix.getData().setScale(1.2f);
            drawCentered(w, h - 320, "Press Space to Replay!", batch, pixelMix);
        }
        else if(!paused) {
            renderSystem.getViewport().apply();
            batch.setProjectionMatrix(renderSystem.getCamera().combined);
            batch.begin();
            pixelMix.getData().setScale(1);
            pixelMix.draw(batch, "Score\n" + score, 11, renderSystem.getViewport().getWorldHeight() - 12, 10, Align.center, true);
            String time = (int) (timeLeft / 60) + ":" + (int) (timeLeft % 60);
            pixelMix.draw(batch, "Time\n" + time, renderSystem.getViewport().getWorldWidth() - 21, renderSystem.getViewport().getWorldHeight() - 12, 10, Align.center, true);
        } else {
            pauseViewport.apply();
            batch.setProjectionMatrix(pauseViewport.getCamera().combined);
            batch.begin();


            int w = 320 * 2;
            int h = 180 * 2;

            Gdx.gl.glClearColor(0, 0, 0, 1.0f);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            batch.draw(pauseBG, 0, 0, w, h);
            pixelMix.getData().setScale(2f);
            drawCentered(w, h - 20, "PLANET BAKER", batch, pixelMix);

            pixelMix.getData().setScale(1.2f);
            drawCentered(w, h - 40, "Welcome to planet baker! This is the place where we take that one weird dream you once had of eating a planet, and turn it into a job!", batch, pixelMix);

            drawCentered(w, h - 90, "First take the order\nThen build it in the Prepare station\nThen grow it just the right amount in the Grow station\nAnd then add some spicy volcanoes, or make the planet cold and crunchy, with a sudden ice age!", batch, pixelMix);

            drawCentered(w, h - 150, "IMPORTANT: Use an ice age at the Prepare station to prevent plants from growing at the first growth iteration! This is useful for orders where the humans and plants seem to need different growth amounts!", batch, pixelMix);

            drawCentered(w, h - 200, "The Growth Stages are:", batch, pixelMix);

            drawCentered(w, h - 300, "You can always refer to these by using ESC to toggle this menu!\nOtherwise, Good luck!", batch, pixelMix);

            drawCentered(w, h - 340, "[just in case it weren't clear: use ESC to start playing :)]", batch, pixelMix);
        }
        batch.end();
    }

    void drawCentered(int w, int y, String txt, SpriteBatch batch, BitmapFont fnt) {
        fnt.draw(batch, txt, 10, y, w - 20, Align.center, true);
    }

    @Override
    public void reSize(int width, int height) {
        renderSystem.resize(width, height);
        pauseViewport.update(width, height, true);
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
