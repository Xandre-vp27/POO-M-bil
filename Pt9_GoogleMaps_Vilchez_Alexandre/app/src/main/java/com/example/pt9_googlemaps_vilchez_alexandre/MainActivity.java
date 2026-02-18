package com.example.pt9_googlemaps_vilchez_alexandre;

import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;


import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnCoordenades = findViewById(R.id.btnCoordenades);
        Button btnPoblacio = findViewById(R.id.btnPoblacio);
        Button btnPosicioActual = findViewById(R.id.btnPosicioActual);

        btnCoordenades.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, CoordenadesActivity.class));
        });

        btnPoblacio.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, PoblacioActivity.class));
        });

        btnPosicioActual.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, ActualActivity.class));
        });
    }
}