package com.example.pt13_fruitatemps;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

public class SplashScreen implements Screen {
    private final FruitesDelTemps game;
    private float timer = 0;
    private Texture splashImage;

    public SplashScreen(FruitesDelTemps game) {
        this.game = game;
    }

    @Override
    public void show() {
        // Forcem la càrrega immediata de la imatge de la poma per al splash
        game.assetManager.finishLoadingAsset("manzana.png");
        splashImage = game.assetManager.get("manzana.png", Texture.class);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        // Dibuixem la fruita al centre
        game.batch.draw(splashImage, 
            Gdx.graphics.getWidth() / 2f - splashImage.getWidth() / 2f, 
            Gdx.graphics.getHeight() / 2f - splashImage.getHeight() / 2f);
        game.batch.end();

        timer += delta;
        if (timer > 2f) {
            game.setScreen(new MenuScreen(game));
        }
    }

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {}
}