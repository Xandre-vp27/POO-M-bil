package com.example.pt2_viclhez_alexandre;

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

        Button btnTemp = findViewById(R.id.buttonTemperatura);
        Button btnLong = findViewById(R.id.buttonLongitud);
        Button btnMass = findViewById(R.id.buttonMassa);

        btnTemp.setOnClickListener(v -> {
            Intent intent = new Intent(this, Temperatura.class);
            startActivity(intent);
        });

        btnLong.setOnClickListener(v -> {
            Intent intent = new Intent(this, Longitud.class);
            startActivity(intent);
        });

        btnMass.setOnClickListener(v -> {
            Intent intent = new Intent(this, Massa.class);
            startActivity(intent);
        });


    }



}