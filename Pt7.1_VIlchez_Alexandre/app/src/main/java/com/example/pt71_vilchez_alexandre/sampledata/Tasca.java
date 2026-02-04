package com.example.pt71_vilchez_alexandre.sampledata;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "tasca")
public class Tasca {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "titol")
    private String titol;

    @ColumnInfo(name = "estat")
    private String estat;

    @ColumnInfo(name = "dataCreacio")
    private Date dataCreacio;

    @ColumnInfo(name = "dataCanvi")
    private Date dataCanvi;


    public Tasca(String titol, String estat, Date dataCreacio, Date dataCanvi) {
        this.titol = titol;
        this.estat = estat;
        this.dataCreacio = dataCreacio;
        this.dataCanvi = dataCanvi;
    }

    public int getId() {
        return id;
    }

    public Date getDataCanvi() {
        return dataCanvi;
    }

    public Date getDataCreacio() {
        return dataCreacio;
    }

    public String getEstat() {
        return estat;
    }

    public String getTitol() {
        return titol;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitol(String titol) {
        this.titol = titol;
    }

    public void setEstat(String estat) {
        this.estat = estat;
    }

    public void setDataCanvi(Date dataCanvi) {
        this.dataCanvi = dataCanvi;
    }

    public void setDataCreacio(Date dataCreacio) {
        this.dataCreacio = dataCreacio;
    }
}
