package com.example.pt4_vilchez_alexandre;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AlarmaActivity extends AppCompatActivity {

    private EditText etHora, etMinuto, etMensaje;
    private Button btnCrearAlarma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarma);

        etHora = findViewById(R.id.etHora);
        etMinuto = findViewById(R.id.etMinuto);
        etMensaje = findViewById(R.id.etMensaje);
        btnCrearAlarma = findViewById(R.id.btnCrearAlarma);

        btnCrearAlarma.setOnClickListener(v -> crearAlarma());
    }

    private void crearAlarma() {
        String horaStr = etHora.getText().toString();
        String minutoStr = etMinuto.getText().toString();
        String mensaje = etMensaje.getText().toString();

        if (horaStr.isEmpty() || minutoStr.isEmpty() || mensaje.isEmpty()) {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        int hora = Integer.parseInt(horaStr);
        int minuto = Integer.parseInt(minutoStr);

        if (hora < 0 || hora > 23 || minuto < 0 || minuto > 59) {
            Toast.makeText(this, "Introduce una hora y minuto válidos", Toast.LENGTH_SHORT).show();
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM)
                    .putExtra(AlarmClock.EXTRA_HOUR, hora)
                    .putExtra(AlarmClock.EXTRA_MINUTES, minuto)
                    .putExtra(AlarmClock.EXTRA_MESSAGE, mensaje)
                    .putExtra(AlarmClock.EXTRA_SKIP_UI, false);

            // Comprobar si hay una app que pueda manejarlo
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            } else {
                Toast.makeText(this, "⚠️ No se encontró una app de reloj compatible. Simulando alarma...", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Tu versión de Android no soporta alarmas", Toast.LENGTH_SHORT).show();
        }
    }
}