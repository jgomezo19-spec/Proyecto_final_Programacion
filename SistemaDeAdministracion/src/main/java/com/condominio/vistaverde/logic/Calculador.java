package com.condominio.vistaverde.logic;

import com.condominio.vistaverde.model.Casa;
import com.condominio.vistaverde.model.Pago;
import java.util.List;

public class Calculador {
    
    public static double calcularTotalPagadoAnio(Casa casa, int anio) {
        double total = 0;
        if (casa.getPagos() != null) {
            for (Pago pago : casa.getPagos()) {
                if (pago.getAnio() == anio) {
                    total += pago.getMonto();
                }
            }
        }
        return total;
    }
    
    public static double calcularTotalRecaudadoMes(List<Casa> casas, int mes, int anio, double cuotaMensual) {
        double total = 0;
        for (Casa casa : casas) {
            if (casa.haPagado(mes, anio)) {
                total += cuotaMensual;
            }
        }
        return total;
    }
    
    public static double calcularTotalEsperadoMes(List<Casa> casas, double cuotaMensual) {
        int casasOcupadas = 0;
        for (Casa casa : casas) {
            if (casa.tienePropietario()) {
                casasOcupadas++;
            }
        }
        return casasOcupadas * cuotaMensual;
    }
    
    public static double calcularDiferencia(double recaudado, double esperado) {
        return recaudado - esperado;
    }
    
    public static double calcularPorcentajeRecaudacion(double recaudado, double esperado) {
        if (esperado == 0) return 0;
        return (recaudado / esperado) * 100;
    }
    
    public static int contarPagosMes(List<Casa> casas, int mes, int anio) {
        int contador = 0;
        for (Casa casa : casas) {
            if (casa.haPagado(mes, anio)) {
                contador++;
            }
        }
        return contador;
    }
    
    public static int contarCasasOcupadas(List<Casa> casas) {
        int contador = 0;
        for (Casa casa : casas) {
            if (casa.tienePropietario()) {
                contador++;
            }
        }
        return contador;
    }
}
