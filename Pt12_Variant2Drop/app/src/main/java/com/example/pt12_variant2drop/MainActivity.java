package com.example.pt12_variant2drop;

import android.os.Bundle;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

/**
 * Lanzador principal para Android. Configura e inicia el motor LibGDX.
 */
public class MainActivity extends AndroidApplication {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        // Desactivamos sensores que no usamos para optimizar batería
        config.useAccelerometer = false;
        config.useCompass = false;
        
        // Inicializamos el juego con nuestra clase principal
        initialize(new DinoGame(), config);
    }
}
