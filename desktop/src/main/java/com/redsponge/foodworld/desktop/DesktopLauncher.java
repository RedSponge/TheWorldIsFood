package com.redsponge.foodworld.desktop;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.redsponge.foodworld.FoodWorld;

/** Launches the desktop (LWJGL) application. */
public class DesktopLauncher {
	public static void main(String[] args) {
		createApplication();
	}

	private static LwjglApplication createApplication() {
		return new LwjglApplication(new FoodWorld(), getDefaultConfiguration());
	}

	private static LwjglApplicationConfiguration getDefaultConfiguration() {
		LwjglApplicationConfiguration configuration = new LwjglApplicationConfiguration();
		configuration.title = "Planet Baker";
		configuration.width = 1280;
		configuration.height = 720;
		for (int size : new int[] { 128, 64, 32, 16 }) {
			configuration.addIcon("planet_" + size + ".png", FileType.Internal);
		}
		return configuration;
	}
}