package com.example.pt13_fruitatemps;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FruitesDelTemps extends Game {
    public SpriteBatch batch;
    public AssetManager assetManager;

    @Override
    public void create() {
        batch = new SpriteBatch();
        assetManager = new AssetManager();

        // Càrrega d'assets
        assetManager.load("fresa.png", Texture.class);
        assetManager.load("manzana.png", Texture.class);
        assetManager.load("pera.png", Texture.class);
        assetManager.load("platano.png", Texture.class);
        assetManager.load("sandia.png", Texture.class);
        assetManager.load("bomba.png", Texture.class);
        assetManager.load("landscape.png", Texture.class);
        assetManager.load("hit.wav", Sound.class);

        // Passem a la SplashScreen
        setScreen(new SplashScreen(this));
    }

    @Override
    public void dispose() {
        if (batch != null) batch.dispose();
        if (assetManager != null) assetManager.dispose();
    }
}