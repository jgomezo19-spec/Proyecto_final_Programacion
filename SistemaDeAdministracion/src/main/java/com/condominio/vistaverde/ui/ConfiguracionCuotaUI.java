package com.condominio.vistaverde.ui;

import com.condominio.vistaverde.model.Condominio;
import com.condominio.vistaverde.logic.Validador;
import com.condominio.vistaverde.utils.DialogUtils;
import com.condominio.vistaverde.utils.DateUtils;
import com.condominio.vistaverde.persistence.Persistencia;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.time.LocalDate;

public class ConfiguracionCuotaUI extends JDialog {
    
    private JTextField txtCuotaActual;
    private JTextField txtNuevaCuota;
    private JTextField txtUltimoCambio;
    private JButton btnGuardar;
    private JButton btnCancelar;
    private Condominio condominio;
    private Persistencia persistencia;
    
    public ConfiguracionCuotaUI(JFrame parent) {
        super(parent, "Configurar Cuota de Mantenimiento", true);
        cargarDatos();
        initComponents();
    }
    
    private void cargarDatos() {
        persistencia = new Persistencia();
        if (persistencia.existeArchivo()) {
            try {
                condominio = persistencia.cargar();
            } catch (Exception e) {
                condominio = new Condominio("Vista Verde");
            }
        } else {
            condominio = new Condominio("Vista Verde");
        }
    }
    
    private void initComponents() {
        setSize(550, 400);
        setLocationRelativeTo(getParent());
        setResizable(false);
        
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelPrincipal.setBackground(Color.WHITE);
        
        JPanel panelFormulario = new JPanel(new GridLayout(4, 2, 15, 20));
        panelFormulario.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(241, 196, 15), 2),
            "Configuracion de Cuota",
            javax.swing.border.TitledBorder.CENTER,
            javax.swing.border.TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 14),
            new Color(241, 196, 15)));
        panelFormulario.setBackground(Color.WHITE);
        
        JLabel lblCuotaActual = new JLabel("Cuota actual (Q):");
        lblCuotaActual.setFont(new Font("Segoe UI", Font.BOLD, 13));
        txtCuotaActual = new JTextField();
        txtCuotaActual.setText(String.format("%.2f", condominio.getCuotaMensual()));
        txtCuotaActual.setEditable(false);
        txtCuotaActual.setBackground(new Color(240, 240, 240));
        txtCuotaActual.setFont(new Font("Segoe UI", Font.BOLD, 14));
        txtCuotaActual.setForeground(new Color(0, 102, 204));
        txtCuotaActual.setHorizontalAlignment(JTextField.CENTER);
        txtCuotaActual.setPreferredSize(new Dimension(150, 30));
        
        JLabel lblNuevaCuota = new JLabel("Nueva cuota (Q):");
        lblNuevaCuota.setFont(new Font("Segoe UI", Font.BOLD, 13));
        txtNuevaCuota = new JTextField();
        txtNuevaCuota.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtNuevaCuota.setHorizontalAlignment(JTextField.CENTER);
        txtNuevaCuota.setPreferredSize(new Dimension(150, 30));
        
        JLabel lblUltimoCambio = new JLabel("Ultimo cambio:");
        lblUltimoCambio.setFont(new Font("Segoe UI", Font.BOLD, 13));
        txtUltimoCambio = new JTextField();
        if (condominio.getFechaUltimoCambioCuota() != null) {
            txtUltimoCambio.setText(DateUtils.formatearFecha(condominio.getFechaUltimoCambioCuota()));
        } else {
            txtUltimoCambio.setText("Nunca");
        }
        txtUltimoCambio.setEditable(false);
        txtUltimoCambio.setBackground(new Color(240, 240, 240));
        txtUltimoCambio.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtUltimoCambio.setHorizontalAlignment(JTextField.CENTER);
        
        panelFormulario.add(lblCuotaActual);
        panelFormulario.add(txtCuotaActual);
        panelFormulario.add(lblNuevaCuota);
        panelFormulario.add(txtNuevaCuota);
        panelFormulario.add(lblUltimoCambio);
        panelFormulario.add(txtUltimoCambio);
        panelFormulario.add(new JLabel());
        panelFormulario.add(new JLabel());
        
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelBotones.setBackground(Color.WHITE);
        
        btnGuardar = new JButton("GUARDAR CAMBIOS");
        btnGuardar.setBackground(new Color(241, 196, 15));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnGuardar.setFocusPainted(false);
        btnGuardar.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        
        btnCancelar = new JButton("CANCELAR");
        btnCancelar.setBackground(new Color(200, 0, 0));
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnCancelar.setFocusPainted(false);
        btnCancelar.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);
        
        JPanel panelInfo = new JPanel();
        panelInfo.setBackground(new Color(255, 255, 200));
        panelInfo.setBorder(BorderFactory.createLineBorder(new Color(241, 196, 15), 1));
        JLabel lblInfo = new JLabel("Los cambios aplican al mes siguiente (no afecta el mes actual)");
        lblInfo.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        lblInfo.setForeground(new Color(200, 100, 0));
        panelInfo.add(lblInfo);
        
        panelPrincipal.add(panelFormulario, BorderLayout.CENTER);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);
        panelPrincipal.add(panelInfo, BorderLayout.NORTH);
        
        add(panelPrincipal);
        
        btnGuardar.addActionListener(e -> guardar());
        btnCancelar.addActionListener(e -> dispose());
    }
    
    private void guardar() {
        String nuevaCuotaStr = txtNuevaCuota.getText().trim();
        
        if (nuevaCuotaStr.isEmpty()) {
            DialogUtils.mostrarAdvertencia(this, "Ingrese el nuevo monto de la cuota");
            return;
        }
        
        double nuevaCuota;
        try {
            nuevaCuota = Double.parseDouble(nuevaCuotaStr);
        } catch (NumberFormatException e) {
            DialogUtils.mostrarError(this, "Ingrese un valor numerico valido");
            return;
        }
        
        if (!Validador.validarMonto(nuevaCuota)) {
            DialogUtils.mostrarError(this, "El monto debe ser mayor a cero");
            return;
        }

        
        LocalDate ahora = LocalDate.now();
        LocalDate ultimoCambio = condominio.getFechaUltimoCambioCuota();
        
        if (ultimoCambio != null && 
            ultimoCambio.getYear() == ahora.getYear() &&
            ultimoCambio.getMonthValue() == ahora.getMonthValue()) {
            DialogUtils.mostrarError(this, "Solo se puede cambiar la cuota una vez por mes.\n" +
                                     "Ya se realizo un cambio este mes (fecha: " + 
                                     DateUtils.formatearFecha(ultimoCambio) + ")");
            return;
        }
        
        boolean cambiado = condominio.setCuotaMensual(nuevaCuota);
        
        if (cambiado) {
            try {
                persistencia.guardar(condominio);
                DialogUtils.mostrarExito(this, "Cuota actualizada correctamente a Q" + 
                                         String.format("%.2f", nuevaCuota) +
                                         "\nEl cambio aplicara a partir del mes siguiente.");
                txtCuotaActual.setText(String.format("%.2f", nuevaCuota));
                txtUltimoCambio.setText(DateUtils.formatearFecha(ahora));
                txtNuevaCuota.setText("");
                dispose();
            } catch (Exception e) {
                DialogUtils.mostrarError(this, "Error al guardar los cambios: " + e.getMessage());
            }
        } else {
            DialogUtils.mostrarError(this, "Error al cambiar la cuota");
        }
    }
}
