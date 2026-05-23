package com.condominio.vistaverde.logic;

import java.time.Year;

public class Validador {
    
    public static boolean validarNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return false;
        }
        return nombre.matches("^[a-zA-ZáéíóúñÑÁÉÍÓÚ\\s]+$");
    }
    
    public static boolean validarDpi(String dpi) {
        if (dpi == null || dpi.trim().isEmpty()) {
            return false;
        }
        // DPI de Guatemala tiene 13 digitos
        return dpi.matches("^\\d{13}$");
    }
    
    public static boolean validarTelefono(String telefono) {
        if (telefono == null || telefono.trim().isEmpty()) {
            return false;
        }
        return telefono.matches("^\\d{8,15}$");
    }
    
    public static boolean validarEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(regex);
    }
    
    public static boolean validarMonto(double monto) {
        return monto > 0;
    }
    
    public static boolean validarNumeroCasa(int numero) {
        return numero >= 1 && numero <= 30;
    }
    
    public static boolean validarMes(int mes) {
        return mes >= 1 && mes <= 12;
    }
    
    public static boolean validarAnio(int anio) {
        int anioActual = Year.now().getValue();
        return anio >= 2026 && anio <= anioActual + 1;
    }
    
    public static boolean validarCredenciales(String usuario, String password) {
        String usuarioValido = "iusr_vistaverde";
        String passwordValido = "R3sidencial2026%";
        return usuarioValido.equals(usuario) && passwordValido.equals(password);
    }
}
