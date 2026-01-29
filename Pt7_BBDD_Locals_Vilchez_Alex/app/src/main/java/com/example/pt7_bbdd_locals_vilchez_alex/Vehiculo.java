package com.example.pt7_bbdd_locals_vilchez_alex;

// Nom, Cognoms, Telèfon, Marca Vehicle, Model Vehicle, Matrícula

public class Vehiculo {

    private String nombre;
    private String apellidos;
    private String telefono;
    private String marcaVehiculo;
    private String modeloVehiculo;
    private String matricula;

    public Vehiculo() {
    }

    public Vehiculo(String nombre, String apellidos, String telefono, String marcaVehiculo, String modeloVehiculo, String matricula) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.marcaVehiculo = marcaVehiculo;
        this.modeloVehiculo = modeloVehiculo;
        this.matricula = matricula;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getMarcaVehiculo() {
        return marcaVehiculo;
    }

    public String getModeloVehiculo() {
        return modeloVehiculo;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setMarcaVehiculo(String marcaVehiculo) {
        this.marcaVehiculo = marcaVehiculo;
    }

    public void setModeloVehiculo(String modeloVehiculo) {
        this.modeloVehiculo = modeloVehiculo;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }
}
