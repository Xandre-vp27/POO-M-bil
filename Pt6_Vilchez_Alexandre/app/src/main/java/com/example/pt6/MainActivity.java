package com.example.pt6;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    public static final String PREFS_NAME = "MisPreferencias";
    public static final String KEY_URL_BASE = "url_base";
    private static final String URL_DEFAULT = "https://www.vidalibarraquer.net/android/sports/";
    private static final String URL_LEAGUES = "https://www.vidalibarraquer.net/android/leagues/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. Referencias a las vistas
        ImageView ivNba = findViewById(R.id.iv_nba);
        ImageView ivMlb = findViewById(R.id.iv_mlb);
        ImageView ivMls = findViewById(R.id.iv_mls);
        ImageView ivNfl = findViewById(R.id.iv_nfl);
        ImageView ivNhl = findViewById(R.id.iv_nhl);

        // 2. Listeners. Al hacer click se pasa el código que la URL necesaria
        ivNba.setOnClickListener(v -> abrirActivityLiga("nba"));
        ivMls.setOnClickListener(v -> abrirActivityLiga("mlb"));
        ivMlb.setOnClickListener(v -> abrirActivityLiga("mls"));
        ivNfl.setOnClickListener(v -> abrirActivityLiga("nfl"));
        ivNhl.setOnClickListener(v -> abrirActivityLiga("nhl"));
    }

    private void abrirActivityLiga(String liga) {
        Intent intent = new Intent(this, ListaEquiposActivity.class);

        // Pasamos el dato como Extra
        intent.putExtra("EXTRA_LIGA", liga);

        startActivity(intent);
    }

    // --- LÓGICA DEL MENÚ ---

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_config, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_config) {
            mostrarDialogoConfiguracion();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void mostrarDialogoConfiguracion() {
        // Texto actualizado como pediste
        String[] opciones = {"URL Por Defecto (sports)", "URL Leagues"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Seleccionar Fuente de Datos")
                .setItems(opciones, (dialog, which) -> {
                    String urlSeleccionada = (which == 0) ? URL_DEFAULT : URL_LEAGUES;
                    guardarPreferencia(urlSeleccionada);
                });
        builder.show();
    }

    private void guardarPreferencia(String nuevaUrl) {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        prefs.edit().putString(KEY_URL_BASE, nuevaUrl).apply();
        android.widget.Toast.makeText(this, "Configuración guardada", android.widget.Toast.LENGTH_SHORT).show();
    }
}





