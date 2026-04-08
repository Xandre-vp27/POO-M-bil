// Crear BannerActivity que carga un AdView (banner) si hay conexión a Internet
package com.example.pt11_admob;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class BannerActivity extends AppCompatActivity {

    private AdView adView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);

        // Inicializar Mobile Ads SDK
        MobileAds.initialize(this, initializationStatus -> {
            // no-op
        });

        TextView status = findViewById(R.id.statusText);
        adView = findViewById(R.id.adView);

        if (!NetworkUtils.isConnected(this)) {
            status.setText("No internet connection. Banner will not load.");
            if (adView != null) adView.setVisibility(View.GONE);
            Toast.makeText(this, "Sin conexión a Internet", Toast.LENGTH_SHORT).show();
            return;
        }

        AdRequest adRequest = new AdRequest.Builder().build();
        if (adView != null) adView.loadAd(adRequest);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adView != null) adView.resume();
    }

    @Override
    protected void onPause() {
        if (adView != null) adView.pause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (adView != null) adView.destroy();
        super.onDestroy();
    }
}

