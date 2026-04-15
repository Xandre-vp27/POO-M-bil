package com.example.pt12_variant2drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

/**
 * Pantalla inicial que muestra una imagen durante 2 segundos.
 */
public class SplashScreen implements Screen {
    private final DinoGame game;
    private float timer = 0;
    private Texture meteorTexture;

    public SplashScreen(DinoGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        // Forzar carga de recursos necesarios para la splash
        game.manager.finishLoading();
        meteorTexture = game.manager.get("meteor.png", Texture.class);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        // Dibujar el meteorito centrado
        game.batch.draw(meteorTexture, 
            (Gdx.graphics.getWidth() - meteorTexture.getWidth()) / 2f, 
            (Gdx.graphics.getHeight() - meteorTexture.getHeight()) / 2f);
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
