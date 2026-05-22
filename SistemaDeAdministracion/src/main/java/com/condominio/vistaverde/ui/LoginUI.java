package com.condominio.vistaverde.ui;

import com.condominio.vistaverde.logic.Validador;
import com.condominio.vistaverde.utils.DialogUtils;
import com.condominio.vistaverde.utils.UIManagerConfig;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Component;

public class LoginUI extends JFrame {
    
    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JButton btnSalir;
    private JCheckBox chkMostrarPassword;
    private int intentosFallidos = 0;
    private static final int MAX_INTENTOS = 3;
    
    public LoginUI() {
        UIManagerConfig.setupUI();
        initComponents();
    }
    
    private void initComponents() {
        setTitle("Condominio Vista Verde - Login");
        setSize(450, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(Color.WHITE);
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(30, 40, 40, 40));
        
        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(Color.WHITE);
        panelTitulo.setLayout(new BoxLayout(panelTitulo, BoxLayout.Y_AXIS));
        
        JLabel lblTitulo = new JLabel("CONDOMINIO VISTA VERDE", JLabel.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setForeground(new Color(0, 102, 204));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lblSubtitulo = new JLabel("Sistema de Gestion de Cuotas", JLabel.CENTER);
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSubtitulo.setForeground(Color.GRAY);
        lblSubtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        panelTitulo.add(lblTitulo);
        panelTitulo.add(Box.createRigidArea(new Dimension(0, 10)));
        panelTitulo.add(lblSubtitulo);
        
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 2;
        
        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setFont(new Font("Segoe UI", Font.BOLD, 13));
        gbc.gridy = 0;
        panelFormulario.add(lblUsuario, gbc);
        
        txtUsuario = new JTextField();
        txtUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtUsuario.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        gbc.gridy = 1;
        panelFormulario.add(txtUsuario, gbc);
        
        JLabel lblPassword = new JLabel("Contrasena:");
        lblPassword.setFont(new Font("Segoe UI", Font.BOLD, 13));
        gbc.gridy = 2;
        panelFormulario.add(lblPassword, gbc);
        
        txtPassword = new JPasswordField();
        txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtPassword.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        gbc.gridy = 3;
        panelFormulario.add(txtPassword, gbc);
        
        chkMostrarPassword = new JCheckBox("Mostrar contrasena");
        chkMostrarPassword.setBackground(Color.WHITE);
        chkMostrarPassword.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        gbc.gridy = 4;
        panelFormulario.add(chkMostrarPassword, gbc);
        
        JPanel panelBotones = new JPanel(new GridLayout(1, 2, 15, 0));
        panelBotones.setBackground(Color.WHITE);
        
        btnLogin = new JButton("INICIAR SESION");
        btnLogin.setBackground(new Color(0, 102, 204));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnLogin.setFocusPainted(false);
        btnLogin.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
        
        btnSalir = new JButton("SALIR");
        btnSalir.setBackground(new Color(200, 0, 0));
        btnSalir.setForeground(Color.WHITE);
        btnSalir.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnSalir.setFocusPainted(false);
        btnSalir.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
        
        panelBotones.add(btnLogin);
        panelBotones.add(btnSalir);
        
        gbc.gridy = 5;
        panelFormulario.add(panelBotones, gbc);
        
        JPanel panelFooter = new JPanel();
        panelFooter.setBackground(Color.WHITE);
        JLabel lblCredits = new JLabel("2025 Condominio Vista Verde - Todos los derechos reservados");
        lblCredits.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        lblCredits.setForeground(Color.GRAY);
        panelFooter.add(lblCredits);
        
        panelPrincipal.add(panelTitulo, BorderLayout.NORTH);
        panelPrincipal.add(panelFormulario, BorderLayout.CENTER);
        panelPrincipal.add(panelFooter, BorderLayout.SOUTH);
        
        add(panelPrincipal);
        
        chkMostrarPassword.addActionListener(e -> {
            if (chkMostrarPassword.isSelected()) {
                txtPassword.setEchoChar((char) 0);
            } else {
                txtPassword.setEchoChar('•');
            }
        });
        
        btnLogin.addActionListener(e -> login());
        btnSalir.addActionListener(e -> System.exit(0));
        getRootPane().setDefaultButton(btnLogin);
    }
    
    private void login() {
        String usuario = txtUsuario.getText().trim();
        String password = new String(txtPassword.getPassword());
        
        if (usuario.isEmpty() || password.isEmpty()) {
            DialogUtils.mostrarAdvertencia(this, "Ingrese usuario y contrasena");
            return;
        }
        
        if (Validador.validarCredenciales(usuario, password)) {
            intentosFallidos = 0;
            DialogUtils.mostrarExito(this, "Bienvenido al sistema");
            abrirVentanaPrincipal();
            dispose();
        } else {
            intentosFallidos++;
            int intentosRestantes = MAX_INTENTOS - intentosFallidos;
            
            if (intentosFallidos >= MAX_INTENTOS) {
                DialogUtils.mostrarError(this, "Ha superado el numero maximo de intentos (" + MAX_INTENTOS + ").\nEl programa se cerrara.");
                System.exit(0);
            } else {
                DialogUtils.mostrarError(this, "Usuario o contrasena incorrectos.\nLe quedan " + intentosRestantes + " intento(s).");
                txtUsuario.setText("");
                txtPassword.setText("");
                txtUsuario.requestFocus();
            }
        }
    }
    
    private void abrirVentanaPrincipal() {
        InicioUI inicio = new InicioUI();
        inicio.setVisible(true);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginUI().setVisible(true);
        });
    }
}
