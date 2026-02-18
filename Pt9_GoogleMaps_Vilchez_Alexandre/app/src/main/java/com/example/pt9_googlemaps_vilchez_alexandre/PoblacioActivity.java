package com.example.pt9_googlemaps_vilchez_alexandre;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class PoblacioActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poblacio);

        // Inicializa mapa
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_poblacio);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        FloatingActionButton fab = findViewById(R.id.fabPoblacio);
        fab.setOnClickListener(v -> mostrarDialogPoblacio());
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        // Ubicación inicial
        LatLng bcn = new LatLng(41.3851, 2.1734);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(bcn, 10));
    }

    private void mostrarDialogPoblacio() {
        final EditText etPoblacio = new EditText(this);
        etPoblacio.setHint("Ex: Tarragona, Madrid, Paris...");

        new AlertDialog.Builder(this)
                .setTitle("Cercar Població")
                .setMessage("Introdueix el nom de la població:")
                .setView(etPoblacio)
                .setPositiveButton("Cercar", (dialog, which) -> {
                    String nomPoblacio = etPoblacio.getText().toString().trim();
                    if (!nomPoblacio.isEmpty()) {
                        geolocalitzarPoblacio(nomPoblacio);
                    }
                })
                .setNegativeButton("Cancel·lar", null)
                .show();
    }

    private void geolocalitzarPoblacio(String nom) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            // Intentamos obtener una lista de direcciones que coincidan con el nombre
            List<Address> adreces = geocoder.getFromLocationName(nom, 1);

            if (adreces != null && !adreces.isEmpty()) {
                Address ubicacio = adreces.get(0);
                LatLng posicio = new LatLng(ubicacio.getLatitude(), ubicacio.getLongitude());

                mMap.clear();
                mMap.addMarker(new MarkerOptions()
                        .position(posicio)
                        .title(ubicacio.getLocality() != null ? ubicacio.getLocality() : nom));

                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(posicio, 12));
            } else {
                Toast.makeText(this, "No s'ha trobat la població", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            // Control de errores de red o servicio Geocoder no disponible
            Toast.makeText(this, "Error de connexió al cercar la població", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}