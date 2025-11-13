package com.example.pt5_starwars;

import android.os.Bundle;
import androidx.preference.PreferenceFragmentCompat;
import com.example.pt5_starwars.R;

public class PreferenciasFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferencias, rootKey);
    }
}