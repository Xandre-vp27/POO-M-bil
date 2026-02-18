package com.example.pt9_googlemaps_vilchez_alexandre;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
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

public class CoordenadesActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordenades);

        // Inicializar el mapa
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        FloatingActionButton fab = findViewById(R.id.fabCoordenades);
        fab.setOnClickListener(v -> mostrarDialogCoordenades());
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        // Posición inicial por defecto (Tarragona)
        LatLng tarragona = new LatLng(41.1189, 1.2445);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(tarragona, 12));
    }

    private void mostrarDialogCoordenades() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_coordenades, null);
        EditText etLat = view.findViewById(R.id.etLatitud);
        EditText etLon = view.findViewById(R.id.etLongitud);

        new AlertDialog.Builder(this)
                .setTitle("Anar a Coordenades")
                .setView(view)
                .setPositiveButton("Anar", (dialog, which) -> {
                    try {
                        double lat = Double.parseDouble(etLat.getText().toString());
                        double lon = Double.parseDouble(etLon.getText().toString());

                        actualitzarMapa(lat, lon);
                    } catch (NumberFormatException e) {
                        Toast.makeText(this, "Format de coordenades incorrecte", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel·lar", null)
                .show();
    }

    private void actualitzarMapa(double lat, double lon) {
        if (mMap != null) {
            LatLng nuevaPos = new LatLng(lat, lon);
            mMap.clear(); // Limpiar marcadores anteriores
            mMap.addMarker(new MarkerOptions().position(nuevaPos).title("Ubicació manual"));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(nuevaPos, 15));
        }
    }
}