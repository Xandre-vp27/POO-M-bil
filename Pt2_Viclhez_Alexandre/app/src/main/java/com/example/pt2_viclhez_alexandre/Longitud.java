package com.example.pt2_viclhez_alexandre;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class Longitud extends AppCompatActivity {

    private EditText inputLongitud;
    private RadioGroup groupConversionLong;
    private Button btnConvertirLong, btnBack;
    private TextView txtResultadoLong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_longitud);

        inputLongitud = findViewById(R.id.inputLongitud);
        groupConversionLong = findViewById(R.id.groupConversionLong);
        btnConvertirLong = findViewById(R.id.btnConvertirLong);
        txtResultadoLong = findViewById(R.id.txtResultadoLong);
        btnBack = findViewById(R.id.btnBack);


        btnBack.setOnClickListener(v -> finish());
        btnConvertirLong.setOnClickListener(v -> convertirLongitud());
    }

    private void convertirLongitud() {
        String valorStr = inputLongitud.getText().toString().trim();

        if (valorStr.isEmpty()) {
            txtResultadoLong.setText("⚠ Introduce un valor");
            return;
        }

        double valor;
        try {
            valor = Double.parseDouble(valorStr);
        } catch (NumberFormatException e) {
            txtResultadoLong.setText("⚠ Valor no válido");
            return;
        }

        int selectedId = groupConversionLong.getCheckedRadioButtonId();
        if (selectedId == -1) {
            txtResultadoLong.setText("⚠ Selecciona una conversión");
            return;
        }

        double resultado = 0;
        String textoResultado = "";

        if (selectedId == R.id.rbKmMi) {
            resultado = valor * 0.6213712;
            textoResultado = valor + " Km = " + resultado + " mi";
        } else if (selectedId == R.id.rbMiKm) {
            resultado = valor * 1.609344;
            textoResultado = valor + " mi = " + resultado + " Km";
        } else if (selectedId == R.id.rbKmYd) {
            resultado = valor * 1093.613;
            textoResultado = valor + " Km = " + resultado + " yd";
        } else if (selectedId == R.id.rbYdKm) {
            resultado = valor * 0.0009144;
            textoResultado = valor + " yd = " + resultado + " Km";
        } else if (selectedId == R.id.rbMiYd) {
            resultado = valor * 1760;
            textoResultado = valor + " mi = " + resultado + " yd";
        } else if (selectedId == R.id.rbYdMi) {
            resultado = valor * 0.0005681818;
            textoResultado = valor + " yd = " + resultado + " mi";
        }

        txtResultadoLong.setText(textoResultado);
    }
}
