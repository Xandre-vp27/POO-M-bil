package com.example.pt3_vilchez_alexandre;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private TextView tvOnCreate;
    private int contadorCreate = 0;
    private TextView tvOnStart;
    private int contadorStart = 0;
    private TextView tvOnRestart;
    private int contadorRestart = 0;
    private TextView tvOnResume;
    private int contadorResume = 0;
    private TextView tvOnPause;
    private int contadorPause = 0;
    private TextView tvOnStop;
    private int contadorStop = 0;
    private TextView tvOnDestroy;
    private int contadorDestroy = 0;

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

        tvOnCreate = findViewById(R.id.tvOnCreate);
        tvOnStart = findViewById(R.id.tvOnStart);
        tvOnRestart = findViewById(R.id.tvOnRestart);
        tvOnResume = findViewById(R.id.tvOnResume);
        tvOnPause = findViewById(R.id.tvOnPause);
        tvOnStop = findViewById(R.id.tvOnStop);
        tvOnDestroy = findViewById(R.id.tvOnDestroy);

        contadorCreate++;
        updateText();
    }

    @Override
    protected void onStart() {
        super.onStart();
        contadorStart++;
        updateText();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        contadorRestart++;
        updateText();
    }

    @Override
    protected void onResume() {
        super.onResume();
        contadorResume++;
        updateText();
    }

    @Override
    protected void onPause() {
        super.onPause();
        contadorPause++;
        updateText();
    }

    @Override
    protected void onStop() {
        super.onStop();
        contadorStop++;
        updateText();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        contadorDestroy++;
        updateText();
    }

    private void updateText() {
        tvOnCreate.setText(getString(R.string.oncreate_label) + contadorCreate);
        tvOnStart.setText(getString(R.string.onstart_label) + contadorStart);
        tvOnRestart.setText(getString(R.string.onrestart_label) + contadorRestart);
        tvOnResume.setText(getString(R.string.onresume_label) + contadorResume);
        tvOnPause.setText(getString(R.string.onpause_label) + contadorPause);
        tvOnStop.setText(getString(R.string.onstop_label) + contadorStop);
        tvOnDestroy.setText(getString(R.string.ondestroy_label) + contadorDestroy);
    }

}
