package com.condominio.vistaverde.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Condominio implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private static final int TOTAL_CASAS = 30;
    private static final double CUOTA_INICIAL = 1500.00;
    
    private String nombre;
    private double cuotaMensual;
    private List<Casa> casas;
    private LocalDate fechaUltimoCambioCuota;
    
    public Condominio(String nombre) {
        this.nombre = nombre;
        this.cuotaMensual = CUOTA_INICIAL;
        this.casas = new ArrayList<>();
        this.fechaUltimoCambioCuota = LocalDate.now();
        for (int i = 1; i <= TOTAL_CASAS; i++) {
            casas.add(new Casa(i));
        }
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public double getCuotaMensual() {
        return cuotaMensual;
    }
    
    public LocalDate getFechaUltimoCambioCuota() {
        return fechaUltimoCambioCuota;
    }
    
    
    public boolean setCuotaMensual(double nuevaCuota) {
        LocalDate ahora = LocalDate.now();
        
        
        if (fechaUltimoCambioCuota != null && 
            fechaUltimoCambioCuota.getYear() == ahora.getYear() &&
            fechaUltimoCambioCuota.getMonthValue() == ahora.getMonthValue()) {
            return false; // Ya se cambió este mes
        }
        
        if (nuevaCuota > 0) {
            this.cuotaMensual = nuevaCuota;
            this.fechaUltimoCambioCuota = ahora;
            return true;
        }
        return false;
    }
    
    public List<Casa> getCasas() {
        return casas;
    }
    
    public Casa getCasa(int numero) {
        if (numero >= 1 && numero <= TOTAL_CASAS) {
            return casas.get(numero - 1);
        }
        return null;
    }
    
    public boolean registrarPropietario(int numeroCasa, Propietario propietario) {
        Casa casa = getCasa(numeroCasa);
        if (casa != null && !casa.tienePropietario()) {
            propietario.setNumeroCasa(numeroCasa);
            casa.setPropietario(propietario);
            return true;
        }
        return false;
    }
    
    public boolean registrarPago(int numeroCasa, int mes, int anio) {
        Casa casa = getCasa(numeroCasa);
        if (casa != null && casa.tienePropietario()) {
            Pago pago = new Pago(mes, anio, cuotaMensual);
            return casa.registrarPago(pago);
        }
        return false;
    }
    
    public double getTotalRecaudadoMes(int mes, int anio) {
        double total = 0;
        for (Casa casa : casas) {
            if (casa.haPagado(mes, anio)) {
                total += cuotaMensual;
            }
        }
        return total;
    }
    
    public double getTotalEsperadoMes() {
        int casasConPropietario = 0;
        for (Casa casa : casas) {
            if (casa.tienePropietario()) {
                casasConPropietario++;
            }
        }
        return casasConPropietario * cuotaMensual;
    }
    
    public List<Casa> getCasasMorosas(int mesActual, int anioActual) {
        List<Casa> morosas = new ArrayList<>();
        for (Casa casa : casas) {
            if (casa.estaMorosa(mesActual, anioActual)) {
                morosas.add(casa);
            }
        }
        return morosas;
    }
    
    public boolean emailExiste(String email, int numeroCasaExcluir) {
        for (Casa casa : casas) {
            if (casa.getPropietario() != null && casa.getNumero() != numeroCasaExcluir) {
                if (casa.getPropietario().getEmail().equalsIgnoreCase(email)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean dpiExiste(String dpi, int numeroCasaExcluir) {
        for (Casa casa : casas) {
            if (casa.getPropietario() != null && casa.getNumero() != numeroCasaExcluir) {
                if (casa.getPropietario().getDpi().equals(dpi)) {
                    return true;
                }
            }
        }
        return false;
    }
}