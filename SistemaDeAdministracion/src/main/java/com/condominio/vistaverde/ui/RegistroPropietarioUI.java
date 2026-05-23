package com.condominio.vistaverde.ui;

import com.condominio.vistaverde.model.Condominio;
import com.condominio.vistaverde.model.Propietario;
import com.condominio.vistaverde.logic.Validador;
import com.condominio.vistaverde.utils.DialogUtils;
import com.condominio.vistaverde.persistence.Persistencia;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.Font;

public class RegistroPropietarioUI extends JDialog {
    
    private JTextField txtNombre;
    private JTextField txtDpi;
    private JComboBox<Integer> cmbCasa;
    private JTextField txtTelefono;
    private JTextField txtEmail;
    private JButton btnRegistrar;
    private JButton btnCancelar;
    private Condominio condominio;
    private Persistencia persistencia;
    
    public RegistroPropietarioUI(JFrame parent) {
        super(parent, "Registrar Propietario", true);
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
        setSize(600, 500);
        setLocationRelativeTo(getParent());
        setResizable(false);
        
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelPrincipal.setBackground(Color.WHITE);
        
        JPanel panelFormulario = new JPanel(new GridLayout(6, 2, 15, 20));
        panelFormulario.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(0, 102, 204), 2),
            "Datos del Propietario",
            javax.swing.border.TitledBorder.CENTER,
            javax.swing.border.TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 14),
            new Color(0, 102, 204)));
        panelFormulario.setBackground(Color.WHITE);
        
        JLabel lblNombre = new JLabel("Nombre completo:");
        lblNombre.setFont(new Font("Segoe UI", Font.BOLD, 13));
        txtNombre = new JTextField();
        txtNombre.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        
        JLabel lblDpi = new JLabel("DPI (13 digitos):");
        lblDpi.setFont(new Font("Segoe UI", Font.BOLD, 13));
        txtDpi = new JTextField();
        txtDpi.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        
        JLabel lblCasa = new JLabel("Numero de casa:");
        lblCasa.setFont(new Font("Segoe UI", Font.BOLD, 13));
        cmbCasa = new JComboBox<>();
        for (int i = 1; i <= 30; i++) {
            cmbCasa.addItem(i);
        }
        cmbCasa.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        
        JLabel lblTelefono = new JLabel("Telefono:");
        lblTelefono.setFont(new Font("Segoe UI", Font.BOLD, 13));
        txtTelefono = new JTextField();
        txtTelefono.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        
        JLabel lblEmail = new JLabel("Correo electronico:");
        lblEmail.setFont(new Font("Segoe UI", Font.BOLD, 13));
        txtEmail = new JTextField();
        txtEmail.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        
        panelFormulario.add(lblNombre);
        panelFormulario.add(txtNombre);
        panelFormulario.add(lblDpi);
        panelFormulario.add(txtDpi);
        panelFormulario.add(lblCasa);
        panelFormulario.add(cmbCasa);
        panelFormulario.add(lblTelefono);
        panelFormulario.add(txtTelefono);
        panelFormulario.add(lblEmail);
        panelFormulario.add(txtEmail);
        panelFormulario.add(new JLabel());
        panelFormulario.add(new JLabel());
        
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelBotones.setBackground(Color.WHITE);
        
        btnRegistrar = new JButton("REGISTRAR");
        btnRegistrar.setBackground(new Color(0, 102, 204));
        btnRegistrar.setForeground(Color.WHITE);
        btnRegistrar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnRegistrar.setFocusPainted(false);
        btnRegistrar.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        
        btnCancelar = new JButton("CANCELAR");
        btnCancelar.setBackground(new Color(200, 0, 0));
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnCancelar.setFocusPainted(false);
        btnCancelar.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        
        panelBotones.add(btnRegistrar);
        panelBotones.add(btnCancelar);
        
        panelPrincipal.add(panelFormulario, BorderLayout.CENTER);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);
        
        add(panelPrincipal);
        
        btnRegistrar.addActionListener(e -> registrar());
        btnCancelar.addActionListener(e -> dispose());
    }
    
    private void registrar() {
        String nombre = txtNombre.getText().trim();
        String dpi = txtDpi.getText().trim();
        int numeroCasa = (int) cmbCasa.getSelectedItem();
        String telefono = txtTelefono.getText().trim();
        String email = txtEmail.getText().trim();
        
        if (nombre.isEmpty()) {
            DialogUtils.mostrarAdvertencia(this, "Ingrese el nombre del propietario");
            return;
        }
        
        if (!Validador.validarNombre(nombre)) {
            DialogUtils.mostrarError(this, "El nombre solo puede contener letras y espacios");
            return;
        }
        
        if (dpi.isEmpty()) {
            DialogUtils.mostrarAdvertencia(this, "Ingrese el DPI del propietario");
            return;
        }
        
        if (!Validador.validarDpi(dpi)) {
            DialogUtils.mostrarError(this, "DPI invalido. Debe tener 13 digitos (ejemplo: 1234567890101)");
            return;
        }
        
        if (condominio.dpiExiste(dpi, numeroCasa)) {
            DialogUtils.mostrarError(this, "Este DPI ya esta registrado en otra casa. Una persona solo puede tener una casa.");
            return;
        }
        
        if (telefono.isEmpty()) {
            DialogUtils.mostrarAdvertencia(this, "Ingrese el numero de telefono");
            return;
        }
        
        if (!Validador.validarTelefono(telefono)) {
            DialogUtils.mostrarError(this, "El telefono debe contener solo numeros (8-15 digitos)");
            return;
        }
        
        if (email.isEmpty()) {
            DialogUtils.mostrarAdvertencia(this, "Ingrese el correo electronico");
            return;
        }
        
        if (!Validador.validarEmail(email)) {
            DialogUtils.mostrarError(this, "Correo electronico no valido (ejemplo: nombre@dominio.com)");
            return;
        }
        
        if (condominio.getCasa(numeroCasa).tienePropietario()) {
            DialogUtils.mostrarError(this, "La casa #" + numeroCasa + " ya tiene un propietario registrado");
            return;
        }
        
        if (condominio.emailExiste(email, numeroCasa)) {
            DialogUtils.mostrarError(this, "Este correo ya esta registrado en otra casa");
            return;
        }
        
        Propietario propietario = new Propietario(nombre, dpi, telefono, email, numeroCasa);
        condominio.registrarPropietario(numeroCasa, propietario);
        
        try {
            persistencia.guardar(condominio);
            DialogUtils.mostrarExito(this, "Propietario registrado correctamente");
            limpiarCampos();
        } catch (Exception e) {
            DialogUtils.mostrarError(this, "Error al guardar los datos: " + e.getMessage());
        }
    }
    
    private void limpiarCampos() {
        txtNombre.setText("");
        txtDpi.setText("");
        cmbCasa.setSelectedIndex(0);
        txtTelefono.setText("");
        txtEmail.setText("");
        txtNombre.requestFocus();
    }
}
