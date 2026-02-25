package com.example.pt8_vilchez_alexandre;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditorActivity extends AppCompatActivity {
    EditText etTitol, etContingut;
    Button btnGuardar;
    DatabaseReference dbRef;
    Nota notaEditar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        etTitol = findViewById(R.id.etTitol);
        etContingut = findViewById(R.id.etContingut);
        btnGuardar = findViewById(R.id.btnGuardar);

        // Usamos la URL que ya tienes configurada
        String url = "https://pt8-vilchez-alexandre-default-rtdb.firebaseio.com";
        dbRef = FirebaseDatabase.getInstance(url).getReference("notas");

        // Comprobamos si venimos de un click para editar
        notaEditar = (Nota) getIntent().getSerializableExtra("nota");
        if (notaEditar != null) {
            etTitol.setText(notaEditar.getTitol());
            etContingut.setText(notaEditar.getContingut());
        }

        btnGuardar.setOnClickListener(v -> guardarNota());
    }

    private void guardarNota() {
        String t = etTitol.getText().toString();
        String c = etContingut.getText().toString();

        if (t.isEmpty()) return;

        if (notaEditar == null) {
            // CREAR
            String id = dbRef.push().getKey();
            Nota n = new Nota(id, t, c);
            dbRef.child(id).setValue(n);
        } else {
            // EDITAR
            notaEditar.setTitol(t);
            notaEditar.setContingut(c);
            dbRef.child(notaEditar.getId()).setValue(notaEditar);
        }
        finish();
    }
}