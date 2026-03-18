package net.vidalibarraquer.profe.notificacions;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.messaging.FirebaseMessaging;

public class SubscripcioActivity extends AppCompatActivity {

    private Spinner spinner;
    private Button btnAfegir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscripcio);

        spinner = findViewById(R.id.spinnerGrups);
        btnAfegir = findViewById(R.id.btnAfegir);

        // 1. Definim les opcions de l'enunciat
        String[] grups = {
                "Administració i Gestió",
                "Comerç i Màrqueting",
                "Informàtica i Comunicacions",
                "Serveis a la Comunitat"
        };

        // 2. Configurem l'adaptador del Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, grups);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // 3. Lògica del botó afegir
        btnAfegir.setOnClickListener(v -> {
            subscriureATopic();
        });
    }

    private void subscriureATopic() {
        String seleccionat = spinner.getSelectedItem().toString();

        // Netegem el text per a Firebase (sense espais ni caràcters especials)
        // Ex: "Informàtica i Comunicacions" -> "Informatica_i_Comunicacions"
        String topic = seleccionat.replaceAll("[^a-zA-Z0-9]", "_");

        FirebaseMessaging.getInstance().subscribeToTopic(topic)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String msg = "Subscrit a: " + seleccionat;
                        Log.d("Subscripcio", msg);
                        Toast.makeText(SubscripcioActivity.this, msg, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(SubscripcioActivity.this, "Error en la subscripció", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}