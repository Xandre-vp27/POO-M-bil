package com.example.pt12_variant2drop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.Gdx;

/**
 * Clase principal del juego que gestiona las pantallas y los recursos globales.
 */
public class DinoGame extends Game {
    public SpriteBatch batch;
    public AssetManager manager;
    public BitmapFont font;

    @Override
    public void create() {
        batch = new SpriteBatch();
        manager = new AssetManager();

        // Cargar texturas y sonidos
        manager.load("t-rex.png", Texture.class);
        manager.load("meteor.png", Texture.class);
        manager.load("hit.wav", Sound.class);
        
        // Carga inicial (la SplashScreen esperará a que termine)
        
        // Configuración de la fuente
        setupFont();

        // Iniciar con la SplashScreen
        this.setScreen(new SplashScreen(this));
    }

    private void setupFont() {
        if (Gdx.files.internal("font.ttf").exists()) {
            FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
            FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
            parameter.size = 48; // Aumentado de 24 a 48
            font = generator.generateFont(parameter);
            generator.dispose();
        } else {
            // Fuente por defecto si no existe el archivo .ttf
            font = new BitmapFont();
            font.getData().setScale(3f); // Escalar la fuente por defecto para que se vea más grande
        }
    }

    @Override
    public void render() {
        super.render(); // Importante para que las pantallas se dibujen
    }

    @Override
    public void dispose() {
        batch.dispose();
        manager.dispose();
        if (font != null) font.dispose();
    }
}
