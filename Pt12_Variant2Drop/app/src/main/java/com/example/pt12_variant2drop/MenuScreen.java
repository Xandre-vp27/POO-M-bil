package com.example.pt12_variant2drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * Pantalla de menú con un botón para jugar.
 */
public class MenuScreen implements Screen {
    private final DinoGame game;
    private Stage stage;

    public MenuScreen(DinoGame game) {
        this.game = game;
        this.stage = new Stage(new ScreenViewport());
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        // Crear un estilo básico para el botón (usando la fuente cargada en DinoGame)
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = game.font;
        
        TextButton playButton = new TextButton("Jugar", style);
        playButton.setPosition(
            (Gdx.graphics.getWidth() - playButton.getWidth()) / 2f,
            (Gdx.graphics.getHeight() - playButton.getHeight()) / 2f
        );

        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game));
            }
        });

        stage.addActor(playButton);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override public void resize(int width, int height) { stage.getViewport().update(width, height, true); }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override
    public void dispose() {
        stage.dispose();
    }
}
