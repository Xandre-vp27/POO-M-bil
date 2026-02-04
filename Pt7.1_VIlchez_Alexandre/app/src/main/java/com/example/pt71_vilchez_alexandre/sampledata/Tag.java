package com.example.pt71_vilchez_alexandre.sampledata;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tag")
public class Tag {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "nombre")
    private String nombre;

    public Tag(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}