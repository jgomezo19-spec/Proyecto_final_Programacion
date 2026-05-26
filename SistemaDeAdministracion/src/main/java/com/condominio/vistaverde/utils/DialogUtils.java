package com.condominio.vistaverde.utils;

import javax.swing.JOptionPane;
import java.awt.Component;

public class DialogUtils {
    
    public static void mostrarError(Component parent, String mensaje) {
        JOptionPane.showMessageDialog(parent, 
            "❌ " + mensaje, 
            "Error", 
            JOptionPane.ERROR_MESSAGE);
    }
    
    public static void mostrarExito(Component parent, String mensaje) {
        JOptionPane.showMessageDialog(parent, 
            "✅ " + mensaje, 
            "Éxito", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    public static void mostrarAdvertencia(Component parent, String mensaje) {
        JOptionPane.showMessageDialog(parent, 
            "⚠️ " + mensaje, 
            "Advertencia", 
            JOptionPane.WARNING_MESSAGE);
    }
    
    public static void mostrarInfo(Component parent, String mensaje) {
        JOptionPane.showMessageDialog(parent, 
            "ℹ️ " + mensaje, 
            "Información", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    public static boolean confirmar(Component parent, String mensaje) {
        int respuesta = JOptionPane.showConfirmDialog(parent, 
            mensaje, 
            "Confirmar", 
            JOptionPane.YES_NO_OPTION);
        return respuesta == JOptionPane.YES_OPTION;
    }
}
