package com.condominio.vistaverde.ui;

import com.condominio.vistaverde.utils.DateUtils;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Font;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class InicioUI extends JFrame {
    
    private JButton btnRegistroPropietario;
    private JButton btnRegistroPago;
    private JButton btnConfiguracionCuota;
    private JButton btnEstadoCuenta;
    private JButton btnReporteGeneral;
    private JButton btnCasasMorosas;
    private JButton btnEliminarPropietario;
    private JButton btnCerrarSesion;
    private JLabel lblBienvenida;
    private JLabel lblFecha;
    private JLabel lblHora;
    
    public InicioUI() {
        initComponents();
        iniciarReloj();
    }
    
    private void initComponents() {
        setTitle("Condominio Vista Verde - Sistema de Gestion");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelPrincipal.setBackground(Color.WHITE);
        
        JPanel panelHeader = new JPanel(new BorderLayout());
        panelHeader.setBackground(new Color(0, 102, 204));
        panelHeader.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel lblTitulo = new JLabel("CONDOMINIO VISTA VERDE", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitulo.setForeground(Color.WHITE);
        panelHeader.add(lblTitulo, BorderLayout.CENTER);
        
        JPanel panelInfo = new JPanel(new GridLayout(1, 3, 10, 0));
        panelInfo.setBackground(new Color(0, 102, 204));
        
        lblBienvenida = new JLabel("Bienvenido, Administrador");
        lblBienvenida.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblBienvenida.setForeground(Color.WHITE);
        
        lblFecha = new JLabel(DateUtils.getNombreMes(DateUtils.getMesActual()) + " " + DateUtils.getAnioActual());
        lblFecha.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblFecha.setForeground(Color.WHITE);
        lblFecha.setHorizontalAlignment(SwingConstants.CENTER);
        
        lblHora = new JLabel("");
        lblHora.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblHora.setForeground(Color.WHITE);
        lblHora.setHorizontalAlignment(SwingConstants.RIGHT);
        
        panelInfo.add(lblBienvenida);
        panelInfo.add(lblFecha);
        panelInfo.add(lblHora);
        panelHeader.add(panelInfo, BorderLayout.SOUTH);
        
        JPanel panelMenu = new JPanel(new GridLayout(2, 4, 25, 25));
        panelMenu.setBackground(Color.WHITE);
        panelMenu.setBorder(BorderFactory.createEmptyBorder(40, 50, 40, 50));
        
        btnRegistroPropietario = crearBoton("Registrar Propietario", new Color(52, 152, 219));
        btnRegistroPago = crearBoton("Registrar Pago", new Color(46, 204, 113));
        btnConfiguracionCuota = crearBoton("Configurar Cuota", new Color(241, 196, 15));
        btnEstadoCuenta = crearBoton("Estado de Cuenta", new Color(155, 89, 182));
        btnReporteGeneral = crearBoton("Reporte General", new Color(230, 126, 34));
        btnCasasMorosas = crearBoton("Casas Morosas", new Color(231, 76, 60));
        btnEliminarPropietario = crearBoton("Eliminar Propietario", new Color(200, 0, 0));
        
        panelMenu.add(btnRegistroPropietario);
        panelMenu.add(btnRegistroPago);
        panelMenu.add(btnConfiguracionCuota);
        panelMenu.add(btnEstadoCuenta);
        panelMenu.add(btnReporteGeneral);
        panelMenu.add(btnCasasMorosas);
        panelMenu.add(btnEliminarPropietario);
        
        JPanel panelFooter = new JPanel();
        panelFooter.setBackground(Color.WHITE);
        
        btnCerrarSesion = new JButton("Cerrar Sesion");
        btnCerrarSesion.setBackground(new Color(200, 0, 0));
        btnCerrarSesion.setForeground(Color.WHITE);
        btnCerrarSesion.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnCerrarSesion.setFocusPainted(false);
        btnCerrarSesion.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        panelFooter.add(btnCerrarSesion);
        
        panelPrincipal.add(panelHeader, BorderLayout.NORTH);
        panelPrincipal.add(panelMenu, BorderLayout.CENTER);
        panelPrincipal.add(panelFooter, BorderLayout.SOUTH);
        
        add(panelPrincipal);
        
        btnRegistroPropietario.addActionListener(e -> abrirRegistroPropietario());
        btnRegistroPago.addActionListener(e -> abrirRegistroPago());
        btnConfiguracionCuota.addActionListener(e -> abrirConfiguracionCuota());
        btnEstadoCuenta.addActionListener(e -> abrirEstadoCuenta());
        btnReporteGeneral.addActionListener(e -> abrirReporteGeneral());
        btnCasasMorosas.addActionListener(e -> abrirCasasMorosas());
        btnEliminarPropietario.addActionListener(e -> abrirEliminarPropietario());
        btnCerrarSesion.addActionListener(e -> cerrarSesion());
    }
    
    private JButton crearBoton(String texto, Color color) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        boton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        return boton;
    }
    
    private void iniciarReloj() {
        Thread reloj = new Thread(() -> {
            while (true) {
                LocalDateTime ahora = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                lblHora.setText(ahora.format(formatter));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    break;
                }
            }
        });
        reloj.start();
    }
    
    private void abrirRegistroPropietario() {
        RegistroPropietarioUI dialog = new RegistroPropietarioUI(this);
        dialog.setVisible(true);
    }
    
    private void abrirRegistroPago() {
        RegistroPagoUI dialog = new RegistroPagoUI(this);
        dialog.setVisible(true);
    }
    
    private void abrirConfiguracionCuota() {
        ConfiguracionCuotaUI dialog = new ConfiguracionCuotaUI(this);
        dialog.setVisible(true);
    }
    
    private void abrirEstadoCuenta() {
        EstadoCuentaUI dialog = new EstadoCuentaUI(this);
        dialog.setVisible(true);
    }
    
    private void abrirReporteGeneral() {
        ReporteGeneralUI dialog = new ReporteGeneralUI(this);
        dialog.setVisible(true);
    }
    
    private void abrirCasasMorosas() {
        CasasMorosasUI dialog = new CasasMorosasUI(this);
        dialog.setVisible(true);
    }
    
    private void abrirEliminarPropietario() {
        EliminarPropietarioUI dialog = new EliminarPropietarioUI(this);
        dialog.setVisible(true);
    }
    
    private void cerrarSesion() {
        int confirm = javax.swing.JOptionPane.showConfirmDialog(this, 
            "Esta seguro que desea cerrar sesion?", 
            "Cerrar Sesion", 
            javax.swing.JOptionPane.YES_NO_OPTION);
        if (confirm == javax.swing.JOptionPane.YES_OPTION) {
            dispose();
            new LoginUI().setVisible(true);
        }
    }
}
