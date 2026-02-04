package com.example.pt71_vilchez_alexandre;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar; // Importante para el menú
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pt71_vilchez_alexandre.sampledata.DataBase;
import com.example.pt71_vilchez_alexandre.sampledata.Tag;
import com.example.pt71_vilchez_alexandre.sampledata.Tasca;
import com.example.pt71_vilchez_alexandre.sampledata.TascaAmbTag; // Importar el nuevo POJO
import com.example.pt71_vilchez_alexandre.sampledata.TascaTag;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private static DataBase db;
    private RecyclerView recycler;
    private TascaAdapter adapter;
    private Spinner spinnerFilter;
    private List<Tag> listaTags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Configuración de Toolbar para que el menú funcione
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = DataBase.getDbInstance(this);

        // 1. Configurar RecyclerView con la lista de tipo TascaAmbTag
        recycler = findViewById(R.id.recyclerTasques);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TascaAdapter(new ArrayList<>(), t -> mostrarConfirmacioBorrat(t));
        recycler.setAdapter(adapter);

        spinnerFilter = findViewById(R.id.spinnerFilter);
        findViewById(R.id.fabAdd).setOnClickListener(v -> obrirDialogAfegirTasca());

        carregarConfiguracioFiltre();
    }

    // Método corregido para usar TascaAmbTag
    private void carregarTasques(int tagId) {
        Executors.newSingleThreadExecutor().execute(() -> {
            List<TascaAmbTag> dades; // Cambio de tipo de lista
            if (tagId == -1) {
                // Usamos el nuevo método del DAO
                dades = db.tascaDAO().getAllTasquesAmbTags();
            } else {
                // Usamos el nuevo método de filtrado con relación M:N
                dades = db.tascaDAO().getTasquesByTagAmbTags(tagId);
            }

            // Actualizar el adaptador con los nuevos datos
            runOnUiThread(() -> adapter.updateData(dades));
        });
    }

    private void carregarConfiguracioFiltre() {
        Executors.newSingleThreadExecutor().execute(() -> {
            listaTags = db.tagDAO().getAllTag();
            List<String> nombresTags = new ArrayList<>();
            nombresTags.add("Totes les tasques");
            for (Tag t : listaTags) nombresTags.add(t.getNombre());

            runOnUiThread(() -> {
                ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_item, nombresTags);
                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerFilter.setAdapter(spinnerAdapter);

                spinnerFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (position == 0) carregarTasques(-1);
                        else carregarTasques(listaTags.get(position - 1).getId());
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {}
                });
            });
        });
    }

    private void obrirDialogAfegirTasca() {
        // 1. Obtenemos los tags disponibles de la base de datos
        Executors.newSingleThreadExecutor().execute(() -> {
            List<Tag> todosLosTags = db.tagDAO().getAllTag();
            String[] nombresTags = new String[todosLosTags.size()];
            boolean[] seleccionados = new boolean[todosLosTags.size()];

            for (int i = 0; i < todosLosTags.size(); i++) {
                nombresTags[i] = todosLosTags.get(i).getNombre();
            }

            runOnUiThread(() -> {
                EditText inputTitol = new EditText(this);
                inputTitol.setHint("Títol de la tasca");

                new AlertDialog.Builder(this)
                        .setTitle("Nova Tasca amb Tags")
                        .setView(inputTitol)
                        .setMultiChoiceItems(nombresTags, seleccionados, (dialog, which, isChecked) -> {
                            seleccionados[which] = isChecked;
                        })
                        .setPositiveButton("Guardar", (dialog, which) -> {
                            String titol = inputTitol.getText().toString();
                            if (!titol.isEmpty()) {
                                guardarTascaAmbTags(titol, todosLosTags, seleccionados);
                            }
                        })
                        .setNegativeButton("Cancel·lar", null)
                        .show();
            });
        });
    }

    private void obrirDialogAfegirTag() {
        EditText inputTag = new EditText(this);
        new AlertDialog.Builder(this)
                .setTitle("Nou Tag")
                .setMessage("Nom del tag (ex: Gimnàs):")
                .setView(inputTag)
                .setPositiveButton("Crear", (dialog, which) -> {
                    String nom = inputTag.getText().toString().trim();
                    if (!nom.isEmpty()) {
                        Executors.newSingleThreadExecutor().execute(() -> {
                            db.tagDAO().insertTag(new Tag(nom));
                            carregarConfiguracioFiltre(); // Refresca el Spinner de la pantalla principal
                        });
                    }
                })
                .setNegativeButton("Cancel·lar", null)
                .show();
    }

    private void guardarTascaAmbTags(String titol, List<Tag> todosLosTags, boolean[] seleccionados) {
        Executors.newSingleThreadExecutor().execute(() -> {
            // A. Insertar la tarea y recuperar su ID
            Tasca nova = new Tasca(titol, "Pendent", new Date(), new Date());
            long tascaId = db.tascaDAO().insertTasca(nova);

            // B. Insertar en la tabla intermedia TascaTag
            for (int i = 0; i < seleccionados.length; i++) {
                if (seleccionados[i]) {
                    db.tascaTagDAO().insertTascaTag(new TascaTag((int) tascaId, todosLosTags.get(i).getId()));
                }
            }

            // C. Refrescar UI
            runOnUiThread(() -> {
                spinnerFilter.setSelection(0);
                carregarTasques(-1);
            });
        });
    }

    private void mostrarConfirmacioBorrat(Tasca t) {
        new AlertDialog.Builder(this)
                .setTitle("Eliminar tasca")
                .setMessage("Estàs segur que vols eliminar la tasca: " + t.getTitol() + "?")
                .setPositiveButton("Eliminar", (dialog, which) -> eliminarTasca(t))
                .setNegativeButton("Cancel·lar", null)
                .show();
    }

    private void eliminarTasca(Tasca t) {
        Executors.newSingleThreadExecutor().execute(() -> {
            // Borrar de la base de datos (las relaciones en tasca_tag se borran por CASCADE)
            db.tascaDAO().deleteTasca(t);

            // Refrescar la lista según el filtro actual
            runOnUiThread(() -> {
                int selectedTagPosition = spinnerFilter.getSelectedItemPosition();
                if (selectedTagPosition == 0) {
                    carregarTasques(-1);
                } else {
                    carregarTasques(listaTags.get(selectedTagPosition - 1).getId());
                }
            });
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add_tag) {
            obrirDialogAfegirTag();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
