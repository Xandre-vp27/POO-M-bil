package com.example.pt8_vilchez_alexandre;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NotaAdapter.OnNotaListener {
    RecyclerView rv;
    NotaAdapter adapter;
    List<Nota> lista;
    DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv = findViewById(R.id.rvNotas);
        rv.setLayoutManager(new LinearLayoutManager(this));
        lista = new ArrayList<>();
        adapter = new NotaAdapter(lista, this);
        rv.setAdapter(adapter);

        dbRef = FirebaseDatabase.getInstance("https://pt8-vilchez-alexandre-default-rtdb.firebaseio.com").getReference("notas");
        findViewById(R.id.btnAddNota).setOnClickListener(v -> {
            startActivity(new Intent(this, EditorActivity.class));
        });

        // LISTAR (Read)
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                lista.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
                    Nota n = data.getValue(Nota.class);
                    lista.add(n);
                }
                adapter.notifyDataSetChanged();
            }
            @Override public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    @Override
    public void onNotaClick(Nota nota) {
        // EDITAR (Update)
        Intent i = new Intent(this, EditorActivity.class);
        i.putExtra("nota", nota);
        startActivity(i);
    }

    @Override
    public void onNotaLongClick(Nota nota) {
        // ELIMINAR (Delete)
        new AlertDialog.Builder(this)
                .setTitle("Eliminar")
                .setMessage("Vols esborrar aquesta nota?")
                .setPositiveButton("Sí", (d, w) -> dbRef.child(nota.getId()).removeValue())
                .setNegativeButton("No", null)
                .show();
    }
}