package com.redsponge.foodworld;

import com.redsponge.foodworld.game.GameScreen;
import com.redsponge.redengine.EngineGame;
import com.redsponge.redengine.screen.splashscreen.SplashScreenScreen;
import com.redsponge.redengine.transitions.Transitions;

import java.util.Scanner;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class FoodWorld extends EngineGame {

	@Override
	public void init() {
		setScreen(new SplashScreenScreen(ga, new GameScreen(ga), Transitions.linearFade(.5f, batch, shapeRenderer)));
	}

}