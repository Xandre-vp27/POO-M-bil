package com.example.pt6;

import com.google.gson.annotations.SerializedName;

public class DetalleEquipo {

    @SerializedName("titles")
    private String titulos;

    @SerializedName("team_stadium")
    private String estadio;

    public String getTitulos() { return titulos; }
    public String getEstadio() { return estadio; }
}