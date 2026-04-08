package com.example.pt11_admob;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.MobileAds;

public class InterstitialActivity extends AppCompatActivity {

    private static final String TAG = "InterstitialActivity";
    private InterstitialAd mInterstitialAd;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interstitial);

        MobileAds.initialize(this, initializationStatus -> {
            // no-op
        });

        progressBar = findViewById(R.id.progressBar);
        TextView status = findViewById(R.id.statusText);

        if (!NetworkUtils.isConnected(this)) {
            status.setText("No internet connection. Interstitial will not load.");
            progressBar.setVisibility(View.GONE);
            Toast.makeText(this, "Sin conexión a Internet", Toast.LENGTH_SHORT).show();
            return;
        }

        AdRequest adRequest = new AdRequest.Builder().build();
        progressBar.setVisibility(View.VISIBLE);
        InterstitialAd.load(this, "ca-app-pub-3940256099942544/1033173712", adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(InterstitialAd interstitialAd) {
                mInterstitialAd = interstitialAd;
                progressBar.setVisibility(View.GONE);
                mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        // Optionally finish activity or show UI
                        finish();
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                        Log.e(TAG, "Ad failed to show: " + adError);
                    }
                });

                mInterstitialAd.show(InterstitialActivity.this);
            }

            @Override
            public void onAdFailedToLoad(LoadAdError loadAdError) {
                Log.e(TAG, "Failed to load interstitial: " + loadAdError);
                progressBar.setVisibility(View.GONE);
                status.setText("Failed to load interstitial ad.");
            }
        });
    }
}
