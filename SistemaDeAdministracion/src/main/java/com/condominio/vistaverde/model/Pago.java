package com.condominio.vistaverde.model;

import java.io.Serializable;
import java.time.LocalDate;

public class Pago implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private int mes;
    private int anio;
    private double monto;
    private LocalDate fechaPago;
    
    public Pago() {
    }
    
    public Pago(int mes, int anio, double monto) {
        this.mes = mes;
        this.anio = anio;
        this.monto = monto;
        this.fechaPago = LocalDate.now();
    }
    
    public int getMes() {
        return mes;
    }
    
    public void setMes(int mes) {
        this.mes = mes;
    }
    
    public int getAnio() {
        return anio;
    }
    
    public void setAnio(int anio) {
        this.anio = anio;
    }
    
    public double getMonto() {
        return monto;
    }
    
    public void setMonto(double monto) {
        this.monto = monto;
    }
    
    public LocalDate getFechaPago() {
        return fechaPago;
    }
    
    public void setFechaPago(LocalDate fechaPago) {
        this.fechaPago = fechaPago;
    }
    
    public boolean esMismoMesYAnio(int mes, int anio) {
        return this.mes == mes && this.anio == anio;
    }
    
    public String getNombreMes() {
        String[] meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
                          "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
        return meses[mes - 1];
    }
    
    @Override
    public String toString() {
        return getNombreMes() + " " + anio + " - Q" + String.format("%,.2f", monto);
    }
}
