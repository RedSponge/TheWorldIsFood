package com.redsponge.foodworld;

import com.badlogic.gdx.math.Interpolation;

import java.util.HashMap;

public class ReverseInterpolation extends Interpolation {

    private static HashMap<Interpolation, ReverseInterpolation> saved = new HashMap<>();
    public static ReverseInterpolation get(Interpolation i) {
        if(saved.containsKey(i)) {
            return saved.get(i);
        }

        ReverseInterpolation r = new ReverseInterpolation(i);
        saved.put(i, r);
        return r;
    }

    private Interpolation inter;

    private ReverseInterpolation(Interpolation inter) {
        this.inter = inter;
    }

    @Override
    public float apply(float v) {
        return 1 - inter.apply(v);
    }
}
