package com.redsponge.foodworld;

import com.redsponge.foodworld.game.GameScreen;
import com.redsponge.redengine.EngineGame;

import java.util.Scanner;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class FoodWorld extends EngineGame {

	@Override
	public void init() {
		setScreen(new GameScreen(ga));
	}

}