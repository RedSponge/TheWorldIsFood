package com.redsponge.foodworld.game;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.Align;
import com.redsponge.foodworld.game.stations.FinalizeStation;
import com.redsponge.redengine.screen.components.RenderRunnableComponent;
import com.redsponge.redengine.screen.components.TextureComponent;
import com.redsponge.redengine.screen.entity.ScreenEntity;
import com.redsponge.redengine.utils.GeneralUtils;
import com.redsponge.redengine.utils.Logger;

public class Order extends ScreenEntity {

    private Actor actor;

    private TextureRegion paper;
    private TextureRegion clipper;

    private Planet p;

    private int index;

    private TextureComponent tex;
    private Color clipperColour;

    private Rectangle rct;

    private Sound pick, pop;

    public static final Color[] CLIPPER_COLOURS = {
            Color.RED,
            Color.BLUE,
            Color.GREEN,
            Color.GOLD,
            Color.YELLOW,
            Color.SKY,
            Color.SCARLET
    };
    private boolean dragged;

    public Order(SpriteBatch batch, ShapeRenderer sr, int human, int plants, boolean frozen, boolean volcanic, boolean water, int index) {
        super(batch, sr);
        this.index = index;
        p = new Planet(batch, sr);
        p.setHumanLevel(human);
        p.setSeedsLevel(plants);
        p.setFrozen(frozen);
        p.setVolcanic(volcanic);
        p.setHasWater(water);
        p.setDraggable(false);

       rollColour();
       rct = new Rectangle();
    }

    public void rollColour() {
        clipperColour = GeneralUtils.randomItem(CLIPPER_COLOURS);
    }

    @Override
    public void added() {
        pos.set((index) * 28 + 44, 180 - 28, 20 + index);
        size.set(16, 23);
        render.setScaleX(0.75f).setScaleY(0.75f);
        actor = new Actor();
        actor.setPosition(pos.getX(), pos.getY(), Align.center);
        actor.setSize(size.getX(), size.getY());
        actor.setOrigin(Align.center);

        ((GameScreen)screen).getStage().addActor(actor);
        actor.addListener(new DragListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                actor.moveBy(x - actor.getWidth() / 2, y - actor.getHeight() / 2);
                if(!dragged) {
                    actor.addAction(Actions.scaleTo(2f, 2f, 0.1f, Interpolation.exp5));
                }
                dragged = true;
                pick.play();

                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                actor.moveBy(x - actor.getWidth() / 2, y - actor.getHeight() / 2);
                super.touchDragged(event, x, y, pointer);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                float targetX = actor.getX() < 35 ? 35 : actor.getX() > 280 ? 280 : actor.getX();
                float targetY = 180 - 28;
                boolean done = false;

                if(((GameScreen) screen).getStations().getSelectedStation() instanceof FinalizeStation) {
                    rct.set(actor.getX(), actor.getY(), actor.getWidth(), actor.getHeight());
                    Rectangle r = ((FinalizeStation) ((GameScreen) screen).getStations().getSelectedStation()).getDoneRectangle();
                    if(r.overlaps(rct) && ((FinalizeStation) ((GameScreen) screen).getStations().getSelectedStation()).getModifiedPlanet() != null) {
                        targetX = r.x;
                        targetY = r.y;
                        done = true;
                    }
                }
                final boolean finalDone = done;

                actor.addAction(Actions.parallel(Actions.scaleTo(1, 1, 0.1f, Interpolation.exp5), Actions.sequence(Actions.moveTo(targetX, targetY, 0.2f, Interpolation.circleOut), Actions.run(() -> {
                    dragged = false;
                    if(finalDone) {
                        ((FinalizeStation)((GameScreen) screen).getStations().getSelectedStation()).finishOrder(Order.this);
                        remove();
                    }
                    pop.play();
                }))));
            }

            //            @Override
//            public void drag(InputEvent event, float x, float y, int pointer) {
//                actor.moveBy(x - actor.getWidth() / 2, y - actor.getHeight() / 2);
//                dragged = true;
//            }
//
//            @Override
//            public void dragStop(InputEvent event, float x, float y, int pointer) {
//                super.dragStop(event, x, y, pointer);
//                actor.addAction(Actions.moveTo(actor.getX() < 35 ? 35 : actor.getX() > 280 ? 280 : actor.getX(), 180 - 40, 0.2f, Interpolation.circleOut));
//                dragged = false;
//            }
        });
    }

    @Override
    public void additionalTick(float delta) {
        super.additionalTick(delta);
        //pos.set((int) actor.getX(), (int) actor.getY());
        p.setPosition(actor.getX(), actor.getY());
    }

    @Override
    public void loadAssets() {
        p.loadAssets(assets);
        p.setScale(0.4f);
        p.setPosition((int) (pos.getX() - 4), (int) (pos.getY()));

        paper = assets.getTextureRegion("orderPaper");
        clipper = assets.getTextureRegion("orderClip");

        add(new RenderRunnableComponent(() -> {
            float w = actor.getWidth() * actor.getScaleX();
            float h = actor.getHeight() * actor.getScaleY();
            float x = actor.getX() - ((actor.getScaleX() - 1) * (w / 4));
            float y = actor.getY() - ((actor.getScaleY() - 1) * (h / 4));

            batch.setColor(Color.WHITE);
            batch.draw(paper, x, y, w, h);
            if(!dragged) {
                batch.setColor(clipperColour);
                batch.draw(clipper, x, y, w, h);
            }
            batch.setColor(Color.WHITE);
            p.setPosition(x - (w / 2) * (2 - actor.getScaleX()), y - (h / 2) * (2 - actor.getScaleY()) + 2);
            p.setScale((actor.getScaleX() - 1.0f) / 2f + 0.4f);
            p.render(batch);
        }));

        pick = assets.get("clickSound", Sound.class);
        pop = assets.get("popSound", Sound.class);
    }

    public int compare(Planet modifiedPlanet) {
        byte score = 13;
        byte humanDiff = (byte) Math.abs(modifiedPlanet.getHumanLevel() - p.getHumanLevel()); // 0-5
        byte plantDiff = (byte) Math.abs(modifiedPlanet.getSeedsLevel() - p.getSeedsLevel()); // 0-5
        byte waterDiff = (byte) (modifiedPlanet.hasWater() ^ p.hasWater() ? 1 : 0); // 0-1
        byte frozenDiff = (byte) (modifiedPlanet.isFrozen() ^ p.isFrozen() ? 1 : 0); // 0-1
        byte volcanoDiff = (byte) (modifiedPlanet.isVolcanic() ^ p.isVolcanic() ? 1 : 0); // 0-1

        byte totalDiff = (byte) (humanDiff + plantDiff + waterDiff + frozenDiff + volcanoDiff);
        Logger.log(this, "ORDER PLANET:", p);
        Logger.log(this, "COMPARED:", modifiedPlanet);
        Logger.log(this, humanDiff, plantDiff, waterDiff, frozenDiff, volcanoDiff);
        Logger.log(this, totalDiff);
        score -= totalDiff;

        return (int) ((score / 13f) * 100);
    }

    @Override
    public void removed() {
        actor.remove();
    }
}
