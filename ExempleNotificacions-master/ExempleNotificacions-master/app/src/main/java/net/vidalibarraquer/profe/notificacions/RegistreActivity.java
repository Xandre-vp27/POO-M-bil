package net.vidalibarraquer.profe.notificacions;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.messaging.FirebaseMessaging;

public class RegistreActivity extends AppCompatActivity {

    private TextView tvStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registre);

        tvStatus = findViewById(R.id.tvStatusRegistre);

        registrarAFirebase();
    }

    private void registrarAFirebase() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w("RegistreActivity", "Error obtenint el token", task.getException());
                        tvStatus.setText("Error en el registre");
                        return;
                    }

                    // Obtenim el token (això és el que registra el dispositiu per rebre notificacions)
                    String token = task.getResult();
                    Log.d("RegistreActivity", "Token rebut: " + token);

                    tvStatus.setText("Dispositiu registrat correctament!");
                    Toast.makeText(this, "Registre completat", Toast.LENGTH_SHORT).show();
                });
    }
}