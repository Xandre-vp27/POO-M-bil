package com.example.pt4_vilchez_alexandre;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button btnAlarma = findViewById(R.id.buttonAlarma);
        Button btnIntent2 = findViewById(R.id.buttonIntent2);

        // Botón para ir al Activity de la Alarma
        btnAlarma.setOnClickListener(v -> {
            Intent intent = new Intent(this, AlarmaActivity.class);
            startActivity(intent);
        });

        // Botón para ir al segundo Intent
        btnIntent2.setOnClickListener(v -> {
            Intent intent = new Intent(this, Intent2Activity.class);
            startActivity(intent);
        });
    }
}