package com.condominio.vistaverde.utils;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public class DateUtils {
    
    public static int getMesActual() {
        return LocalDate.now().getMonthValue();
    }
    
    public static int getAnioActual() {
        return LocalDate.now().getYear();
    }
    
    public static String getNombreMes(int mes) {
        String[] meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
                          "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
        return meses[mes - 1];
    }
    
    public static String formatearFecha(LocalDate fecha) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return fecha.format(formatter);
    }
    
    public static String getMesAnioFormateado(int mes, int anio) {
        return getNombreMes(mes) + " " + anio;
    }
    
    public static boolean esMesAnterior(int mes, int anio) {
        YearMonth fecha = YearMonth.of(anio, mes);
        YearMonth actual = YearMonth.now();
        return fecha.isBefore(actual);
    }
    
    public static boolean esMesActual(int mes, int anio) {
        return mes == getMesActual() && anio == getAnioActual();
    }
    
    public static boolean validarAnio(int anio) {
        int anioActual = getAnioActual();
        return anio >= 2026 && anio <= anioActual + 1;
    }
}
