package com.example.pt7_bbdd_locals_vilchez_alex;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText etNombre, etApellidos, etTelefono, etMarcaVehiculo, etModeloVehiculo, etMatricula;
    private Button btnInsertar, btnActualizar, btnEliminar, btnBuscar;
    private RecyclerView viewLlista;
    private VehiculoAdapter adapter;
    private VehiculosDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dao = new VehiculosDAO(this);
        
        // Inicializar vistas
        etNombre = findViewById(R.id.etNombre);
        etApellidos = findViewById(R.id.etApellidos);
        etTelefono = findViewById(R.id.etTelefono);
        etMarcaVehiculo = findViewById(R.id.etMarcaVehiculo);
        etModeloVehiculo = findViewById(R.id.etModeloVehiculo);
        etMatricula = findViewById(R.id.etMatricula);
        
        btnInsertar = findViewById(R.id.btnInsertar);
        btnActualizar = findViewById(R.id.btnActualizar);
        btnEliminar = findViewById(R.id.btnEliminar);
        btnBuscar = findViewById(R.id.btnBuscar);
        
        viewLlista = findViewById(R.id.viewLlista);
        viewLlista.setLayoutManager(new LinearLayoutManager(this));

        // Configurar Adapter
        adapter = new VehiculoAdapter(new ArrayList<>(), v -> {
            // Al clicar en la lista, rellenamos los campos para editar/borrar
            etNombre.setText(v.getNombre());
            etApellidos.setText(v.getApellidos());
            etTelefono.setText(v.getTelefono());
            etMarcaVehiculo.setText(v.getMarcaVehiculo());
            etModeloVehiculo.setText(v.getModeloVehiculo());
            etMatricula.setText(v.getMatricula());
        });
        viewLlista.setAdapter(adapter);

        refreshList();

        // --- LISTENERS ---

        btnInsertar.setOnClickListener(v -> {
            String mat = etMatricula.getText().toString();
            if (mat.isEmpty()) return;
            
            if (dao.existeMatricula(mat)) {
                Toast.makeText(this, "Error: Matrícula duplicada", Toast.LENGTH_SHORT).show();
            } else {
                dao.addVehiculo(getVehiculoFromForm());
                clearForm();
                refreshList();
                Toast.makeText(this, "Vehículo registrado", Toast.LENGTH_SHORT).show();
            }
        });

        btnActualizar.setOnClickListener(v -> {
            dao.updateVehiculo(getVehiculoFromForm());
            refreshList();
            Toast.makeText(this, "Datos actualizados", Toast.LENGTH_SHORT).show();
        });

        btnEliminar.setOnClickListener(v -> {
            Vehiculo vehiculo = new Vehiculo();
            vehiculo.setMatricula(etMatricula.getText().toString());
            dao.deleteVehiculo(vehiculo);
            clearForm();
            refreshList();
            Toast.makeText(this, "Vehículo eliminado", Toast.LENGTH_SHORT).show();
        });

        btnBuscar.setOnClickListener(v -> {
            String mat = etMatricula.getText().toString();
            Vehiculo buscado = dao.getVehiculoByMatricula(mat);
            if (buscado != null) {
                etNombre.setText(buscado.getNombre());
                etApellidos.setText(buscado.getApellidos());
                etTelefono.setText(buscado.getTelefono());
                etMarcaVehiculo.setText(buscado.getMarcaVehiculo());
                etModeloVehiculo.setText(buscado.getModeloVehiculo());
                Toast.makeText(this, "Propietario: " + buscado.getNombre(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "No encontrado", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void refreshList() {
        adapter.updateData(dao.getAllVehiculos());
    }

    private void clearForm() {
        etNombre.setText(""); etApellidos.setText(""); etTelefono.setText("");
        etMarcaVehiculo.setText(""); etModeloVehiculo.setText(""); etMatricula.setText("");
    }

    private Vehiculo getVehiculoFromForm() {
        return new Vehiculo(
            etNombre.getText().toString(),
            etApellidos.getText().toString(),
            etTelefono.getText().toString(),
            etMarcaVehiculo.getText().toString(),
            etModeloVehiculo.getText().toString(),
            etMatricula.getText().toString()
        );
    }
}