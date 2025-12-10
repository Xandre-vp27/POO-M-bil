package com.example.pt6;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;

public class NetworkUtils {

    /**
     * Comprueba si hay conectividad a internet disponible.
     * @return true si hay internet (Wifi, Datos o Ethernet), false si no.
     */
    public static boolean hayInternet(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm == null) return false;

        // Obtenemos la red activa actualmente
        android.net.Network activeNetwork = cm.getActiveNetwork();
        if (activeNetwork == null) return false;

        // Comprobamos las capacidades de esa red
        NetworkCapabilities capabilities = cm.getNetworkCapabilities(activeNetwork);
        if (capabilities == null) return false;

        return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET);
    }
}