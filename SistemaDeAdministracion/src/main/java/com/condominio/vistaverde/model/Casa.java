package com.condominio.vistaverde.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Casa implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private int numero;
    private Propietario propietario;
    private List<Pago> pagos;
    
    public Casa(int numero) {
        this.numero = numero;
        this.propietario = null;
        this.pagos = new ArrayList<>();
    }
    
    public int getNumero() {
        return numero;
    }
    
    public void setNumero(int numero) {
        this.numero = numero;
    }
    
    public Propietario getPropietario() {
        return propietario;
    }
    
    public void setPropietario(Propietario propietario) {
        this.propietario = propietario;
    }
    
    public List<Pago> getPagos() {
        return pagos;
    }
    
    public void setPagos(List<Pago> pagos) {
        this.pagos = pagos;
    }
    
    public boolean tienePropietario() {
        return propietario != null;
    }
    
    public boolean registrarPago(Pago pago) {
        for (Pago p : pagos) {
            if (p.getMes() == pago.getMes() && p.getAnio() == pago.getAnio()) {
                return false;
            }
        }
        pagos.add(pago);
        return true;
    }
    
    public boolean haPagado(int mes, int anio) {
        for (Pago p : pagos) {
            if (p.getMes() == mes && p.getAnio() == anio) {
                return true;
            }
        }
        return false;
    }
    
    
    public boolean estaAlDia(int mesActual, int anioActual) {
        if (propietario == null) {
            return false;
        }
        
        LocalDate fechaIngreso = propietario.getFechaIngreso();
        if (fechaIngreso == null) {
            return false;
        }
        
        int anioIngreso = fechaIngreso.getYear();
        int mesIngreso = fechaIngreso.getMonthValue();
        
        
        for (int anio = anioIngreso; anio <= anioActual; anio++) {
            int mesInicio = (anio == anioIngreso) ? mesIngreso : 1;
            int mesFin = (anio == anioActual) ? mesActual : 12;
            
            for (int mes = mesInicio; mes <= mesFin; mes++) {
                if (!haPagado(mes, anio)) {
                    return false;
                }
            }
        }
        return true;
    }
    
   
    public boolean estaMorosa(int mesActual, int anioActual) {
        return tienePropietario() && !haPagado(mesActual, anioActual);
    }
    
    public List<Pago> getMesesPagados(int anio) {
        List<Pago> pagados = new ArrayList<>();
        for (Pago p : pagos) {
            if (p.getAnio() == anio) {
                pagados.add(p);
            }
        }
        return pagados;
    }
    
    public List<Integer> getMesesPendientes(int mesActual, int anioActual) {
        List<Integer> pendientes = new ArrayList<>();
        if (propietario == null) {
            return pendientes;
        }
        
        LocalDate fechaIngreso = propietario.getFechaIngreso();
        if (fechaIngreso == null) {
            return pendientes;
        }
        
        int anioIngreso = fechaIngreso.getYear();
        int mesIngreso = fechaIngreso.getMonthValue();
        
        for (int anio = anioIngreso; anio <= anioActual; anio++) {
            int mesInicio = (anio == anioIngreso) ? mesIngreso : 1;
            int mesFin = (anio == anioActual) ? mesActual : 12;
            
            for (int mes = mesInicio; mes <= mesFin; mes++) {
                if (!haPagado(mes, anio)) {
                    pendientes.add(mes);
                }
            }
        }
        return pendientes;
    }
    
    public double getTotalPagadoAnio(int anio) {
        double total = 0;
        for (Pago p : pagos) {
            if (p.getAnio() == anio) {
                total += p.getMonto();
            }
        }
        return total;
    }
    
    @Override
    public String toString() {
        if (propietario != null) {
            return "Casa " + numero + " - " + propietario.getNombre();
        }
        return "Casa " + numero + " - Sin propietario";
    }
}