package com.example.pt12_variant2drop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
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
        manager.load("landscape.png", Texture.class);
        manager.load("hit.wav", Sound.class);
        
        // Configuración de la fuente
        setupFont();

        // Iniciar con la SplashScreen
        this.setScreen(new SplashScreen(this));
    }

    private void setupFont() {
        // Intentamos cargar una fuente personalizada 'pixel.ttf' (estilo retro videojuego)
        // Si no existe, buscamos 'font.ttf'
        String fontPath = "pixel.ttf";
        if (!Gdx.files.internal(fontPath).exists()) {
            fontPath = "font.ttf";
        }

        if (Gdx.files.internal(fontPath).exists()) {
            FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(fontPath));
            FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
            
            parameter.size = 56; // Un poco más grande para que destaque
            parameter.color = Color.WHITE;
            parameter.borderWidth = 3; // Añadimos un borde negro para mejor legibilidad
            parameter.borderColor = Color.BLACK;
            parameter.shadowOffsetX = 3; // Sombra para darle estilo retro
            parameter.shadowOffsetY = 3;
            parameter.shadowColor = new Color(0, 0, 0, 0.5f);
            
            font = generator.generateFont(parameter);
            generator.dispose();
        } else {
            // Fuente por defecto si no hay archivos .ttf, pero con estilo mejorado
            font = new BitmapFont();
            font.getData().setScale(3.5f);
            font.setColor(Color.YELLOW); // Color amarillo para que destaque más que el blanco simple
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
