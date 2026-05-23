package com.condominio.vistaverde.ui;

import com.condominio.vistaverde.model.Condominio;
import com.condominio.vistaverde.model.Casa;
import com.condominio.vistaverde.logic.EmailSender;
import com.condominio.vistaverde.utils.DialogUtils;
import com.condominio.vistaverde.utils.DateUtils;
import com.condominio.vistaverde.persistence.Persistencia;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.Font;
import java.time.LocalDate;

public class RegistroPagoUI extends JDialog {
    
    private JComboBox<Integer> cmbCasa;
    private JComboBox<String> cmbMes;
    private JComboBox<Integer> cmbAnio;
    private JTextField txtMonto;
    private JButton btnRegistrar;
    private JButton btnCancelar;
    private Condominio condominio;
    private Persistencia persistencia;
    
    public RegistroPagoUI(JFrame parent) {
        super(parent, "Registrar Pago", true);
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
        setSize(550, 450);
        setLocationRelativeTo(getParent());
        setResizable(false);
        
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelPrincipal.setBackground(Color.WHITE);
        
        JPanel panelFormulario = new JPanel(new GridLayout(5, 2, 15, 20));
        panelFormulario.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(46, 204, 113), 2),
            "Datos del Pago",
            javax.swing.border.TitledBorder.CENTER,
            javax.swing.border.TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 14),
            new Color(46, 204, 113)));
        panelFormulario.setBackground(Color.WHITE);
        
        JLabel lblCasa = new JLabel("Seleccionar casa:");
        lblCasa.setFont(new Font("Segoe UI", Font.BOLD, 13));
        cmbCasa = new JComboBox<>();
        for (int i = 1; i <= 30; i++) {
            cmbCasa.addItem(i);
        }
        cmbCasa.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        
        JLabel lblMes = new JLabel("Mes:");
        lblMes.setFont(new Font("Segoe UI", Font.BOLD, 13));
        cmbMes = new JComboBox<>();
        String[] meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
                          "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
        for (String mes : meses) {
            cmbMes.addItem(mes);
        }
        cmbMes.setSelectedIndex(DateUtils.getMesActual() - 1);
        cmbMes.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        
        JLabel lblAnio = new JLabel("Año:");
        lblAnio.setFont(new Font("Segoe UI", Font.BOLD, 13));
        cmbAnio = new JComboBox<>();
        int anioActual = DateUtils.getAnioActual();
        for (int i = anioActual; i <= anioActual + 1; i++) {
            cmbAnio.addItem(i);
        }
        cmbAnio.setSelectedItem(anioActual);
        cmbAnio.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        
        JLabel lblMonto = new JLabel("Monto (Q):");
        lblMonto.setFont(new Font("Segoe UI", Font.BOLD, 13));
        txtMonto = new JTextField();
        txtMonto.setText(String.format("%.2f", condominio.getCuotaMensual()));
        txtMonto.setEditable(false);
        txtMonto.setBackground(new Color(240, 240, 240));
        txtMonto.setFont(new Font("Segoe UI", Font.BOLD, 14));
        txtMonto.setForeground(new Color(0, 150, 0));
        txtMonto.setHorizontalAlignment(JTextField.CENTER);
        
        panelFormulario.add(lblCasa);
        panelFormulario.add(cmbCasa);
        panelFormulario.add(lblMes);
        panelFormulario.add(cmbMes);
        panelFormulario.add(lblAnio);
        panelFormulario.add(cmbAnio);
        panelFormulario.add(lblMonto);
        panelFormulario.add(txtMonto);
        panelFormulario.add(new JLabel());
        panelFormulario.add(new JLabel());
        
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelBotones.setBackground(Color.WHITE);
        
        btnRegistrar = new JButton("REGISTRAR PAGO");
        btnRegistrar.setBackground(new Color(46, 204, 113));
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
        cmbCasa.addActionListener(e -> actualizarInfoCasa());
    }
    
    private void actualizarInfoCasa() {
        int numeroCasa = (int) cmbCasa.getSelectedItem();
        Casa casa = condominio.getCasa(numeroCasa);
        
        if (!casa.tienePropietario()) {
            setTitle("Registrar Pago - Casa #" + numeroCasa + " (SIN PROPIETARIO)");
            btnRegistrar.setEnabled(false);
        } else {
            setTitle("Registrar Pago - Casa #" + numeroCasa + " - " + casa.getPropietario().getNombre());
            btnRegistrar.setEnabled(true);
        }
    }
    
    private void registrar() {
        try {
            int numeroCasa = (int) cmbCasa.getSelectedItem();
            int mes = cmbMes.getSelectedIndex() + 1;
            int anio = (int) cmbAnio.getSelectedItem();
            
            Casa casa = condominio.getCasa(numeroCasa);
            
            if (!casa.tienePropietario()) {
                DialogUtils.mostrarError(this, "La casa #" + numeroCasa + " no tiene propietario registrado");
                return;
            }
            
            if (casa.haPagado(mes, anio)) {
                DialogUtils.mostrarError(this, "La casa #" + numeroCasa + " ya pago el mes de " + 
                                         cmbMes.getSelectedItem() + " " + anio);
                return;
            }
            
           
            LocalDate fechaIngreso = casa.getPropietario().getFechaIngreso();
            if (fechaIngreso == null) {
                fechaIngreso = LocalDate.of(2026, 1, 1);
                casa.getPropietario().setFechaIngreso(fechaIngreso);
            }
            
            int anioIngreso = fechaIngreso.getYear();
            int mesIngreso = fechaIngreso.getMonthValue();
            
            if (anio < anioIngreso || (anio == anioIngreso && mes < mesIngreso)) {
                DialogUtils.mostrarError(this, "No se pueden registrar pagos de meses anteriores a la fecha de ingreso del propietario (" +
                                         DateUtils.getNombreMes(mesIngreso) + " " + anioIngreso + ")");
                return;
            }
            
           
            int ultimoMesPagado = 0;
            int ultimoAnioPagado = anioIngreso;
            
            for (int a = anioIngreso; a <= anio; a++) {
                int mesInicio = (a == anioIngreso) ? mesIngreso : 1;
                int mesFin = (a == anio) ? mes : 12;
                
                for (int m = mesInicio; m <= mesFin; m++) {
                    if (casa.haPagado(m, a)) {
                        ultimoMesPagado = m;
                        ultimoAnioPagado = a;
                    }
                }
            }
            
            boolean esSiguienteMes;
            
            if (ultimoMesPagado == 0) {
                esSiguienteMes = (anio == anioIngreso && mes == mesIngreso);
                if (!esSiguienteMes) {
                    DialogUtils.mostrarError(this, "Debe pagar primero el mes de " +
                                             DateUtils.getNombreMes(mesIngreso) + " " + anioIngreso +
                                             " (fecha de ingreso del propietario)");
                    return;
                }
            } else {
                if (anio == ultimoAnioPagado && mes == ultimoMesPagado + 1) {
                    esSiguienteMes = true;
                } else if (anio == ultimoAnioPagado + 1 && ultimoMesPagado == 12 && mes == 1) {
                    esSiguienteMes = true;
                } else {
                    esSiguienteMes = false;
                }
                
                if (!esSiguienteMes) {
                    String ultimoPagadoStr = DateUtils.getNombreMes(ultimoMesPagado) + " " + ultimoAnioPagado;
                    DialogUtils.mostrarError(this, "Los pagos deben ser consecutivos.\n" +
                                             "Ultimo mes pagado: " + ultimoPagadoStr + "\n" +
                                             "Debe pagar el mes siguiente antes de saltar a " + 
                                             cmbMes.getSelectedItem() + " " + anio);
                    return;
                }
            }
            
            
            boolean registrado = condominio.registrarPago(numeroCasa, mes, anio);
            
            if (registrado) {
                persistencia.guardar(condominio);
                DialogUtils.mostrarExito(this, "Pago registrado correctamente");
                
                boolean correoEnviado = EmailSender.enviarComprobantePago(
                    casa.getPropietario(), numeroCasa, mes, anio, condominio.getCuotaMensual());
                
                if (correoEnviado) {
                    DialogUtils.mostrarInfo(this, "Se ha enviado un comprobante al correo del propietario");
                } else {
                    System.out.println("No se pudo enviar el correo de comprobante");
                }
                
                dispose();
            } else {
                DialogUtils.mostrarError(this, "Error al registrar el pago");
            }
            
        } catch (Exception ex) {
            DialogUtils.mostrarError(this, "Error: " + ex.getMessage());
        }
    }
}
