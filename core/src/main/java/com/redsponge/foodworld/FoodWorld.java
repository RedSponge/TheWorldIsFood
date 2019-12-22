package com.redsponge.foodworld;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.redsponge.foodworld.game.GameScreen;
import com.redsponge.redengine.EngineGame;
import com.redsponge.redengine.screen.components.RenderComponent;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class FoodWorld extends EngineGame {

	@Override
	public void init() {
		setScreen(new GameScreen(ga));
	}

}