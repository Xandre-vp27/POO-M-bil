package com.example.pt6;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.app.AlertDialog;
import android.preference.PreferenceManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ListaEquiposActivity extends AppCompatActivity {

    // Constantes para SharedPreferences
    public static final String PREFS_NAME = "MisPreferencias";
    public static final String KEY_URL_BASE = "url_base";
    private static final String URL_DEFAULT = "https://www.vidalibarraquer.net/android/sports/";
    private RequestQueue requestQueue;
    private List<Equipo> listaEquipos;
    private String codigoLigaActual;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_equipos);

        requestQueue = Volley.newRequestQueue(this);
        listaEquipos = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerViewEquipos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Guardamos la liga en una variable de clase
        codigoLigaActual = getIntent().getStringExtra("EXTRA_LIGA");

        // Control de errores si hay Internet
        if (NetworkUtils.hayInternet(this)) {
            if (codigoLigaActual != null) {
                descargarDatos(codigoLigaActual);
            }
        } else {
            // Feedback al usuario si no hay red
            android.widget.Toast.makeText(this, "No hay conexión a Internet. Revisa tu red.", android.widget.Toast.LENGTH_LONG).show();
        }

        if (codigoLigaActual != null) {
            descargarDatos(codigoLigaActual);
        }
    }

    private void descargarDatos(String codigoLiga) {
        // La URL es https://www.vidalibarraquer.net/android/sports
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String baseUrl = prefs.getString(KEY_URL_BASE, URL_DEFAULT);

        // Construir URL dinámica
        String urlFinal = baseUrl + codigoLiga + ".json";

        Log.d("APP_DEBUG", "Solicitando a: " + urlFinal);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlFinal,
                response -> {
                    Log.d("APP_DEBUG", "Longitud respuesta: " + response.length());
                    parsearJson(response);
                },
                error -> {
                    Log.e("APP_DEBUG", "Error: " + error.toString());
                    Toast.makeText(this, "Error de conexión", Toast.LENGTH_SHORT).show();
                }
        );
        requestQueue.add(stringRequest);
    }

    private void parsearJson(String jsonResponse) {
        try {
            Gson gson = new Gson();

            // Parse al objeto contenedor RespuestaLiga, no a una List directa
            RespuestaLiga respuesta = gson.fromJson(jsonResponse, RespuestaLiga.class);

            if (respuesta != null && respuesta.getListaEquipos() != null) {
                listaEquipos = respuesta.getListaEquipos();

                EquipoAdapter adapter = new EquipoAdapter(listaEquipos, codigoLigaActual, this::alHacerClick);
                recyclerView.setAdapter(adapter);

            } else {
                Log.e("APP_DEBUG", "El JSON no contenía la lista 'teams'");
            }

        } catch (Exception e) {
            Log.e("APP_DEBUG", "Error GSON: " + e.getMessage());
        }
    }

    // Metodo que maneja el click
    private void alHacerClick(Equipo equipo) {
        Intent intent = new Intent(this, DetalleActivity.class);

        // Pasamos todos los datos necesarios para construir las URLs en la siguiente pantalla
        intent.putExtra("EXTRA_LIGA", codigoLigaActual);
        intent.putExtra("EXTRA_ABREV", equipo.getAbreviatura());
        intent.putExtra("EXTRA_NOMBRE", equipo.getNombre());

        startActivity(intent);
    }
}