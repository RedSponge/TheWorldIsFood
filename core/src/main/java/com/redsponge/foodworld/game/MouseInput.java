package com.redsponge.foodworld.game;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.redsponge.redengine.utils.Logger;

import java.util.Arrays;

public class MouseInput extends InputAdapter {

    private static MouseInput instance;

    private boolean[] buttons;

    public static final int RIGHT = 0;
    public static final int LEFT = 1;
    public static final int MIDDLE = 2;

    public static MouseInput getInstance() {
        if(instance == null) {
            instance = new MouseInput();
        }
        return instance;
    }

    public MouseInput() {
        this.buttons = new boolean[128];
    }

    public static boolean isKeyPressed(int index) {
        return instance.buttons[index];
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        buttons[button] = true;
        return super.touchDown(screenX, screenY, pointer, button);
    }

    public void allOff() {
        Arrays.fill(buttons, false);
    }
}
