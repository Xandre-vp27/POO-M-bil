package com.example.pt2_viclhez_alexandre;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class Temperatura extends AppCompatActivity {

    private EditText inputTemperatura;
    private RadioGroup groupConversionTemp;
    private Button btnConvertirTemp;
    private TextView txtResultadoTemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperatura);


        inputTemperatura = findViewById(R.id.inputTemperatura);
        groupConversionTemp = findViewById(R.id.groupConversionTemp);
        btnConvertirTemp = findViewById(R.id.btnConvertirTemp);
        txtResultadoTemp = findViewById(R.id.txtResultadoTemp);


        btnConvertirTemp.setOnClickListener(v -> convertirTemperatura());

        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());
    }

    private void convertirTemperatura() {
        String valorStr = inputTemperatura.getText().toString().trim();

        if (valorStr.isEmpty()) {
            txtResultadoTemp.setText("⚠ Introduce un valor");
            return;
        }

        //Pasar el string a double
        double valor;
        try {
            valor = Double.parseDouble(valorStr);
        } catch (NumberFormatException e) {
            txtResultadoTemp.setText("⚠ Valor no válido");
            return;
        }

        //Comprobar que se ha seleccionado una opción
        int selectedId = groupConversionTemp.getCheckedRadioButtonId();
        if (selectedId == -1) {
            txtResultadoTemp.setText("⚠ Selecciona una conversión");
            return;
        }

        double resultado = 0;
        String textoResultado = "";

        if (selectedId == R.id.rbCelsiusFahrenheit) {
            resultado = (valor * 9/5) + 32;
            textoResultado = valor + " °C = " + resultado + " °F";
        } else if (selectedId == R.id.rbFahrenheitCelsius) {
            resultado = (valor - 32) * 5/9;
            textoResultado = valor + " °F = " + resultado + " °C";
        } else if (selectedId == R.id.rbCelsiusKelvin) {
            resultado = valor + 273.15;
            textoResultado = valor + " °C = " + resultado + " K";
        } else if (selectedId == R.id.rbKelvinCelsius) {
            resultado = valor - 273.15;
            textoResultado = valor + " K = " + resultado + " °C";
        }

        txtResultadoTemp.setText(textoResultado);
    }


}
