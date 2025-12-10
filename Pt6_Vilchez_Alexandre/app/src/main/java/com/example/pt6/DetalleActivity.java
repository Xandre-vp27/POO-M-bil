package com.example.pt6;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

public class DetalleActivity extends AppCompatActivity {

    private TextView tvNombre, tvEstadio, tvTitulos;
    private ImageView ivEscudo;

    // Variables para datos recibidos
    private String codigoLiga;
    private String abrevEquipo;
    private String nombreEquipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);

        // 1. Declarar los textView
        tvNombre = findViewById(R.id.tvDetalleNombre);
        tvEstadio = findViewById(R.id.tvDetalleEstadio);
        tvTitulos = findViewById(R.id.tvDetalleTitulos);
        ivEscudo = findViewById(R.id.ivDetalleEscudo);

        // 2. Recibir datos del Intent
        if (getIntent() != null) {
            codigoLiga = getIntent().getStringExtra("EXTRA_LIGA");
            abrevEquipo = getIntent().getStringExtra("EXTRA_ABREV");
            nombreEquipo = getIntent().getStringExtra("EXTRA_NOMBRE");
        }

        // 3. Pintar lo que ya tenemos
        tvNombre.setText(nombreEquipo);

        // 4. Construir URL imagen
        if (codigoLiga != null && abrevEquipo != null) {
            String urlImagen = "https://www.vidalibarraquer.net/android/leagues/"
                    + codigoLiga + "/" + abrevEquipo.toLowerCase() + ".png";

            Glide.with(this)
                    .load(urlImagen)
                    .placeholder(R.mipmap.ic_launcher)
                    .into(ivEscudo);

            // LÓGICA DE CONTROL DE ERRORES SI NO HAY INTERNET
            if (NetworkUtils.hayInternet(this)) {
                // 5. Descargar el JSON de detalle
                descargarDetalles();
            } else {
                android.widget.Toast.makeText(this, "Sin conexión: No se pueden cargar los detalles.", android.widget.Toast.LENGTH_LONG).show();

                tvEstadio.setText("Estadio: Sin conexión");
                tvTitulos.setText("Títulos: -");
            }
        }
    }

    private void descargarDetalles() {
        // 1. Obtener la URL base configurada
        SharedPreferences prefs = getSharedPreferences("MisPreferencias", MODE_PRIVATE); // Mismo nombre que en el Main
        // Usamos la misma URL default
        String baseUrl = prefs.getString("url_base", "https://www.vidalibarraquer.net/android/sports/");

        // 2. Construir la URL dinámica
        String urlDetalle = baseUrl + codigoLiga + "/" + abrevEquipo.toLowerCase() + ".json";

        Log.d("APP_DEBUG", "Solicitando Detalle a: " + urlDetalle);

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, urlDetalle,
                response -> {
                    parsearDetalle(response);
                },
                error -> {
                    Toast.makeText(this, "Error de red al cargar detalles", Toast.LENGTH_SHORT).show();
                    Log.e("APP_DEBUG", "Volley Error: " + error.toString());
                }
        );
        queue.add(request);
    }

    private void parsearDetalle(String json) {
        try {
            Gson gson = new Gson();

            // Usamos la clase contenedora RespuestaDetalle
            RespuestaDetalle respuesta = gson.fromJson(json, RespuestaDetalle.class);

            // Verificamos que la lista 'data' exista y tenga al menos un elemento
            if (respuesta != null && respuesta.getData() != null && !respuesta.getData().isEmpty()) {

                // Extraemos el objeto real (índice 0)
                DetalleEquipo detalle = respuesta.getData().get(0);

                // Actualizamos los TextView
                tvEstadio.setText("Estadio: " + detalle.getEstadio());
                tvTitulos.setText("Títulos: " + detalle.getTitulos());

            } else {
                tvEstadio.setText("Estadio: No disponible");
                tvTitulos.setText("Títulos: No disponible");
            }

        } catch (Exception e) {
            Log.e("APP_DEBUG", "Error JSON Detalle: " + e.getMessage());
            Toast.makeText(this, "Error formato datos", Toast.LENGTH_SHORT).show();
        }
    }
}