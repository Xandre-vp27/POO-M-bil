package com.example.pt12_variant2drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * Pantalla principal del juego con la lógica de esquivar meteoritos.
 */
public class GameScreen implements Screen {
    private final DinoGame game;
    private Stage stage;
    private TRexActor trex;
    private Array<MeteorActor> meteors;
    private float spawnTimer;
    private float score;
    private int lives = 3;
    private Sound hitSound;

    // Elementos de UI
    private Label scoreLabel;
    private Label livesLabel;

    public GameScreen(DinoGame game) {
        this.game = game;
        this.stage = new Stage(new ScreenViewport());
        this.meteors = new Array<>();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        // Crear al T-Rex
        Texture trexTexture = game.manager.get("t-rex.png", Texture.class);
        trex = new TRexActor(trexTexture);
        trex.setPosition((Gdx.graphics.getWidth() - trex.getWidth()) / 2f, 20);
        stage.addActor(trex);

        hitSound = game.manager.get("hit.wav", Sound.class);

        // Configurar UI con Labels
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = game.font;

        livesLabel = new Label("Vidas: " + lives, labelStyle);
        scoreLabel = new Label("Puntuacion: 0", labelStyle);

        Table table = new Table();
        table.top().left();
        table.setFillParent(true);
        table.add(livesLabel).pad(10).align(Align.left).row();
        table.add(scoreLabel).pad(10).align(Align.left);
        
        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Actualizar puntuación (segundos sobrevividos)
        score += delta;
        scoreLabel.setText("Puntuacion: " + (int)score);
        livesLabel.setText("Vidas: " + lives);

        // Generar meteoritos periódicamente
        spawnTimer += delta;
        if (spawnTimer > 0.8f) {
            MeteorActor meteor = new MeteorActor(game.manager.get("meteor.png", Texture.class));
            stage.addActor(meteor);
            meteors.add(meteor);
            spawnTimer = 0;
        }

        // Lógica de colisiones
        for (int i = 0; i < meteors.size; i++) {
            MeteorActor m = meteors.get(i);
            
            if (m.getStage() == null) {
                meteors.removeIndex(i);
                continue;
            }

            if (m.getBounds().overlaps(trex.getBounds())) {
                hitSound.play();
                lives--;
                m.remove();
                meteors.removeIndex(i);
                
                if (lives <= 0) {
                    game.setScreen(new MenuScreen(game));
                    return;
                }
            }
        }

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
