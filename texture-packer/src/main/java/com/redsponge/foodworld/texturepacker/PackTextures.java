package com.redsponge.foodworld.texturepacker;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;

public class PackTextures {

    public static final String INPUT = "raw/";
    public static final String OUTPUT = "../assets/textures/";

    public static void main(String[] args) {
        proc("planet");
        proc("ui");
        proc("prepare");
        proc("grow");
        proc("finalize");
        proc("general");
    }

    public static void proc(String name) {
        TexturePacker.processIfModified(INPUT + name, OUTPUT, name);
    }

}
