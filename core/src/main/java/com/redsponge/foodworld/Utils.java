package com.redsponge.foodworld;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Utils {

    public static TextureRegion getFrame(Animation<TextureRegion> anim, int index) {
        return anim.getKeyFrame(anim.getFrameDuration() * index);
    }

}
