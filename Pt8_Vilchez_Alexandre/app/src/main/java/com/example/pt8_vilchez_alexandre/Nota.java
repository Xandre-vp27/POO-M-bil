package com.example.pt8_vilchez_alexandre;

import java.io.Serializable;

public class Nota implements Serializable {
    private String id;
    private String titol;
    private String contingut;

    // Constructor buid necessari per Firebase
    public Nota() {}

    public Nota(String id, String titol, String contingut) {
        this.id = id;
        this.titol = titol;
        this.contingut = contingut;
    }

    // Getters i Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getTitol() { return titol; }
    public void setTitol(String titol) { this.titol = titol; }
    public String getContingut() { return contingut; }
    public void setContingut(String contingut) { this.contingut = contingut; }
}