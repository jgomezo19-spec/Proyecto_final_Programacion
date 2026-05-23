package com.condominio.vistaverde.model;

import java.io.Serializable;
import java.time.LocalDate;

public class Propietario implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String nombre;
    private String dpi;
    private String telefono;
    private String email;
    private int numeroCasa;
    private LocalDate fechaIngreso;
    
    public Propietario() {
        this.fechaIngreso = LocalDate.now();
    }
    
    public Propietario(String nombre, String dpi, String telefono, String email, int numeroCasa) {
        this.nombre = nombre;
        this.dpi = dpi;
        this.telefono = telefono;
        this.email = email;
        this.numeroCasa = numeroCasa;
        this.fechaIngreso = LocalDate.now();
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getDpi() {
        return dpi;
    }
    
    public void setDpi(String dpi) {
        this.dpi = dpi;
    }
    
    public String getTelefono() {
        return telefono;
    }
    
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public int getNumeroCasa() {
        return numeroCasa;
    }
    
    public void setNumeroCasa(int numeroCasa) {
        this.numeroCasa = numeroCasa;
    }
    
    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }
    
    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }
    
    @Override
    public String toString() {
        return nombre + " (Casa " + numeroCasa + ")";
    }
}
