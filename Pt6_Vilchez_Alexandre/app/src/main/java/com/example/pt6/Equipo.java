package com.example.pt6;

import com.google.gson.annotations.SerializedName;

public class Equipo {

    @SerializedName("team_id")
    private int id;

    @SerializedName("team_name")
    private String nombre;

    @SerializedName("team_abbreviation")
    private String abreviatura;

    // Getters
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getAbreviatura() { return abreviatura; }

    // Metodo auxiliar para obtener la URL del escudo
    public String getUrlEscudo(String codigoLiga) {
        // Asumiendo la estructura: base + "leagues/" + liga + "/" + abreviatura + ".png"
        return "https://www.vidalibarraquer.net/android/leagues/" + codigoLiga + "/" + abreviatura.toLowerCase() + ".png";
    }
}
