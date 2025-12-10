package com.example.pt6;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class RespuestaLiga {

    @SerializedName("teams")
    private List<Equipo> listaEquipos;

    public List<Equipo> getListaEquipos() {
        return listaEquipos;
    }
}