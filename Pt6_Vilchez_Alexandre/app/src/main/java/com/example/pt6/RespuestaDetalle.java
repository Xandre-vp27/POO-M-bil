package com.example.pt6;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class RespuestaDetalle {

    @SerializedName("data")
    private List<DetalleEquipo> data;

    public List<DetalleEquipo> getData() {
        return data;
    }
}