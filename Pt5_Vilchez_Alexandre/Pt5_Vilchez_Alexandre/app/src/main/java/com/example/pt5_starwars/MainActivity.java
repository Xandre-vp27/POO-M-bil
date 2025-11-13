package com.example.pt5_starwars;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private TextView tvResumen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvResumen = findViewById(R.id.tv_resumen);

        Button btnConfiguracion = findViewById(R.id.btn_configuracion);
        btnConfiguracion.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ConfiguracionActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mostrarPreferencias();
    }

    private void mostrarPreferencias() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        String nombre = prefs.getString("nom_cognoms", "No definit");
        Set<String> races = prefs.getStringSet("races_preferides", new HashSet<>());
        boolean notis = prefs.getBoolean("notificacions", false);
        String peli = prefs.getString("pelicula_preferida", "No definida");

        String resumen = "Nom: " + nombre + "\n" +
                "Races: " + (races.isEmpty() ? "Cap" : String.join(", ", races)) + "\n\n" +
                "Notícies: " + (notis ? "Activades" : "Desactivades") + "\n\n" +
                "Pel·lícula: " + peli;

        tvResumen.setText(resumen);
    }
}