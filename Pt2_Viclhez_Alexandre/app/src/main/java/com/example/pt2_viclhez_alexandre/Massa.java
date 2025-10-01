package com.example.pt2_viclhez_alexandre;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class Massa extends AppCompatActivity {

    private EditText inputMassa;
    private RadioGroup groupConversionMassa;
    private Button btnConvertirMassa, btnBack;
    private TextView txtResultadoMassa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_massa);

        inputMassa = findViewById(R.id.inputMassa);
        groupConversionMassa = findViewById(R.id.groupConversionMassa);
        btnConvertirMassa = findViewById(R.id.btnConvertirMassa);
        txtResultadoMassa = findViewById(R.id.txtResultadoMassa);
        btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(v -> finish());
        btnConvertirMassa.setOnClickListener(v -> convertirMassa());
    }

    private void convertirMassa() {
        String valorStr = inputMassa.getText().toString().trim();

        if (valorStr.isEmpty()) {
            txtResultadoMassa.setText("⚠ Introduce un valor");
            return;
        }

        double valor;
        try {
            valor = Double.parseDouble(valorStr);
        } catch (NumberFormatException e) {
            txtResultadoMassa.setText("⚠ Valor no válido");
            return;
        }

        int selectedId = groupConversionMassa.getCheckedRadioButtonId();
        if (selectedId == -1) {
            txtResultadoMassa.setText("⚠ Selecciona una conversión");
            return;
        }

        double resultado = 0;
        String textoResultado = "";

        if (selectedId == R.id.rbKgLb) {
            resultado = valor * 2.20462262;
            textoResultado = valor + " Kg = " + resultado + " lb";
        } else if (selectedId == R.id.rbLbKg) {
            resultado = valor * 0.45359237;
            textoResultado = valor + " lb = " + resultado + " Kg";
        } else if (selectedId == R.id.rbKgOz) {
            resultado = valor * 35.27396;
            textoResultado = valor + " Kg = " + resultado + " oz";
        } else if (selectedId == R.id.rbOzKg) {
            resultado = valor * 0.02833;
            textoResultado = valor + " oz = " + resultado + " Kg";
        } else if (selectedId == R.id.rbKgSt) {
            resultado = valor * 0.157473;
            textoResultado = valor + " Kg = " + resultado + " st";
        } else if (selectedId == R.id.rbStKg) {
            resultado = valor * 6.35029318;
            textoResultado = valor + " st = " + resultado + " Kg";
        }

        txtResultadoMassa.setText(textoResultado);
    }
}
