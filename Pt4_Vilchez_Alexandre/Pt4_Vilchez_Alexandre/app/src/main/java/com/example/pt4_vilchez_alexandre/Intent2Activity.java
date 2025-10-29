package com.example.pt4_vilchez_alexandre;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Intent2Activity extends AppCompatActivity {

    private EditText etDireccion;
    private Button btnAbrirMapa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent2);

        etDireccion = findViewById(R.id.etDireccion);
        btnAbrirMapa = findViewById(R.id.btnAbrirMapa);

        btnAbrirMapa.setOnClickListener(v -> abrirMapa());
    }

    private void abrirMapa() {
        String direccion = etDireccion.getText().toString().trim();

        if (direccion.isEmpty()) {
            Toast.makeText(this, "Introduce una dirección o ciudad", Toast.LENGTH_SHORT).show();
            return;
        }

        // Codificar la dirección en formato URL válido
        Uri ubicacion = Uri.parse("geo:0,0?q=" + Uri.encode(direccion));
        Intent intent = new Intent(Intent.ACTION_VIEW, ubicacion);
        intent.setPackage("com.google.android.apps.maps");

        // Control de errores: comprobar si existe una app que pueda manejarlo
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Toast.makeText(this, "No hay una app de mapas instalada", Toast.LENGTH_SHORT).show();
        }
    }
}