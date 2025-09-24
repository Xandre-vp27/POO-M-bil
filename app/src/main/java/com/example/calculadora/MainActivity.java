package com.example.calculadora;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.fathzer.soft.javaluator.DoubleEvaluator;



public class MainActivity extends AppCompatActivity {
    private TextView tvRes; // mostrar el resultat
    private StringBuilder expressio = new StringBuilder(); // ex: "33+5+15")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvRes = findViewById(R.id.tvRes);
        initialiceBtns();

        actualitzar();
    }

    private void initialiceBtns () {
        Button btn0 = findViewById(R.id.button0);
        Button btn1 = findViewById(R.id.button1);
        Button btn2 = findViewById(R.id.button2);
        Button btn3 = findViewById(R.id.button3);
        Button btn4 = findViewById(R.id.button4);
        Button btn5 = findViewById(R.id.button5);
        Button btn6 = findViewById(R.id.button6);
        Button btn7 = findViewById(R.id.button7);
        Button btn8 = findViewById(R.id.button8);
        Button btn9 = findViewById(R.id.button9);
        Button btnPlus = findViewById(R.id.buttonPlus);
        Button btnMinus = findViewById(R.id.buttonMinus);
        Button btnMultiply = findViewById(R.id.buttonMultiply);
        Button btnDivide = findViewById(R.id.buttonDivide);
        Button btnEquals = findViewById(R.id.buttonEqual);
        Button btnClear = findViewById(R.id.buttonClear);

        btn0.setOnClickListener(v -> afegirNum("0"));
        btn1.setOnClickListener(v -> afegirNum("1"));
        btn2.setOnClickListener(v -> afegirNum("2"));
        btn3.setOnClickListener(v -> afegirNum("3"));
        btn4.setOnClickListener(v -> afegirNum("4"));
        btn5.setOnClickListener(v -> afegirNum("5"));
        btn6.setOnClickListener(v -> afegirNum("6"));
        btn7.setOnClickListener(v -> afegirNum("7"));
        btn8.setOnClickListener(v -> afegirNum("8"));
        btn9.setOnClickListener(v -> afegirNum("9"));
        btnPlus.setOnClickListener(v -> operacio("+"));
        btnMinus.setOnClickListener(v -> operacio("-"));
        btnMultiply.setOnClickListener(v -> operacio("*"));
        btnDivide.setOnClickListener(v -> operacio("/"));
        btnEquals.setOnClickListener(v -> evaluar());
        btnClear.setOnClickListener(v -> clear());
    }

    private void afegirNum(String c) {
        expressio.append(c);
        tvRes.setText(expressio.toString());
        actualitzar();
    }

    private void operacio(String o) {

        if (comproveLastChar()) return;

        switch (o) {
            case "+":
                expressio.append("+");
                break;
            case "-":
                expressio.append("-");
                break;
            case "*":
                expressio.append("*");
                break;
            case "/":
                expressio.append("/");
                break;
        }

        tvRes.setText(expressio.toString());
        actualitzar();
    }

    private boolean comproveLastChar() {
        char lastChar = expressio.charAt(expressio.length()-1);
        if (lastChar == '+' || lastChar == '-' || lastChar == '*' || lastChar == '/') {
            return true;
        }
        return false;
    }

    private void evaluar() {
        // evaluem
        DoubleEvaluator evaluator = new DoubleEvaluator();
        Double result = evaluator.evaluate(expressio.toString());
        String resultInText = String.valueOf( result );
        String sortida = "";

        try {
            int resultat = Integer.parseInt( resultInText );
            sortida = String.valueOf( resultat );
        }
        catch( Exception e ) {
            sortida = String.valueOf( result );
        }

        tvRes.setText(sortida);


    }

    private void actualitzar() {
        tvRes.setText(expressio.toString());
    }

    private void clear() { expressio.setLength(0);
        actualitzar();
    }
}