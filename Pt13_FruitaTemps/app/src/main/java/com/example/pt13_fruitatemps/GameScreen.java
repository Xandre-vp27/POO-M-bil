package com.example.pt13_fruitatemps;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class GameScreen implements Screen {
    private final FruitesDelTemps game;
    private GameStage stage;
    private BitmapFont font;
    
    private int score = 0;
    private int lives = 3;
    private float spawnTimer = 0;
    private float spawnInterval = 1.5f;
    private int lastLifeBonusScore = 0;

    // Assets paths
    private final String[] fruitTextures = {"fresa.png", "manzana.png", "pera.png", "platano.png", "sandia.png"};

    public GameScreen(FruitesDelTemps game) {
        this.game = game;
    }

    // Classe interna per gestionar el Stage i les col·lisions de clics
    public class GameStage extends Stage {
        public GameStage(ScreenViewport viewport) {
            super(viewport);
        }

        public void onActorClicked(FruitActor actor) {
            if (actor.isBomb()) {
                lives--;
                // Si les vides arriben a 0, tornem al menú
                if (lives <= 0) {
                    game.setScreen(new MenuScreen(game));
                }
            } else {
                score++;
                game.assetManager.get("hit.wav", Sound.class).play();
                
                // Bonificació: Cada 100 punts, una vida extra (màxim 3)
                if (score >= lastLifeBonusScore + 100) {
                    if (lives < 3) {
                        lives++;
                    }
                    lastLifeBonusScore += 100;
                }
                
                // Reduïm l'interval de generació per augmentar la dificultat
                spawnInterval = Math.max(0.5f, 1.5f - (score / 50f) * 0.1f);
            }
        }
    }

    @Override
    public void show() {
        stage = new GameStage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // Fons
        Image background = new Image(game.assetManager.get("landscape.png", Texture.class));
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.addActor(background);

        // Font per a la UI
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("pixel.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 40;
        font = generator.generateFont(parameter);
        generator.dispose();
    }

    private void spawnObject() {
        boolean isBomb = MathUtils.random() < 0.2f; // 20% de probabilitat de bomba
        String textureName = isBomb ? "bomba.png" : fruitTextures[MathUtils.random(fruitTextures.length - 1)];
        
        float x = MathUtils.random(50, Gdx.graphics.getWidth() - 100);
        FruitActor actor = new FruitActor(game.assetManager.get(textureName, Texture.class), isBomb, x);
        stage.addActor(actor);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Lògica de spawn
        spawnTimer += delta;
        if (spawnTimer >= spawnInterval) {
            spawnObject();
            spawnTimer = 0;
        }

        stage.act(delta);
        stage.draw();

        // Dibuixem la UI (Score i Vides)
        game.batch.begin();
        font.draw(game.batch, "Punts: " + score, 20, Gdx.graphics.getHeight() - 20);
        font.draw(game.batch, "Vides: " + lives, 20, Gdx.graphics.getHeight() - 70);
        game.batch.end();
    }

    @Override public void resize(int width, int height) { stage.getViewport().update(width, height, true); }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override
    public void dispose() {
        if (stage != null) stage.dispose();
        if (font != null) font.dispose();
    }
}