package com.condominio.vistaverde.ui;

import com.condominio.vistaverde.model.Condominio;
import com.condominio.vistaverde.model.Casa;
import com.condominio.vistaverde.model.Propietario;
import com.condominio.vistaverde.utils.DialogUtils;
import com.condominio.vistaverde.utils.DateUtils;
import com.condominio.vistaverde.persistence.Persistencia;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.BorderFactory;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.Font;

public class EliminarPropietarioUI extends JDialog {
    
    private JComboBox<Integer> cmbCasa;
    private JButton btnCargar;
    private JButton btnEliminar;
    private JButton btnCerrar;
    private JTextArea txtInfo;
    
    private Condominio condominio;
    private Persistencia persistencia;
    private Casa casaSeleccionada;
    
    public EliminarPropietarioUI(JFrame parent) {
        super(parent, "Eliminar Propietario", true);
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
        setSize(500, 400);
        setLocationRelativeTo(getParent());
        setResizable(false);
        
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelPrincipal.setBackground(Color.WHITE);
        
        JPanel panelSeleccion = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panelSeleccion.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 0, 0), 2),
            "Seleccionar Casa",
            javax.swing.border.TitledBorder.CENTER,
            javax.swing.border.TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 14),
            new Color(200, 0, 0)));
        panelSeleccion.setBackground(Color.WHITE);
        
        panelSeleccion.add(new JLabel("Numero de casa:"));
        cmbCasa = new JComboBox<>();
        for (int i = 1; i <= 30; i++) {
            cmbCasa.addItem(i);
        }
        cmbCasa.setPreferredSize(new java.awt.Dimension(80, 30));
        
        btnCargar = new JButton("CARGAR");
        btnCargar.setBackground(new Color(200, 0, 0));
        btnCargar.setForeground(Color.WHITE);
        btnCargar.setFocusPainted(false);
        
        panelSeleccion.add(cmbCasa);
        panelSeleccion.add(btnCargar);
        
        txtInfo = new JTextArea();
        txtInfo.setEditable(false);
        txtInfo.setFont(new Font("Monospaced", Font.PLAIN, 12));
        txtInfo.setBackground(new Color(250, 250, 250));
        JScrollPane scrollInfo = new JScrollPane(txtInfo);
        scrollInfo.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 0, 0), 1),
            "Informacion del Propietario",
            javax.swing.border.TitledBorder.CENTER,
            javax.swing.border.TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 12),
            new Color(200, 0, 0)));
        
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelBotones.setBackground(Color.WHITE);
        
        btnEliminar = new JButton("ELIMINAR PROPIETARIO");
        btnEliminar.setBackground(new Color(200, 0, 0));
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnEliminar.setFocusPainted(false);
        btnEliminar.setEnabled(false);
        
        btnCerrar = new JButton("CERRAR");
        btnCerrar.setBackground(new Color(100, 100, 100));
        btnCerrar.setForeground(Color.WHITE);
        btnCerrar.setFocusPainted(false);
        
        panelBotones.add(btnEliminar);
        panelBotones.add(btnCerrar);
        
        panelPrincipal.add(panelSeleccion, BorderLayout.NORTH);
        panelPrincipal.add(scrollInfo, BorderLayout.CENTER);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);
        
        add(panelPrincipal);
        
        btnCargar.addActionListener(e -> cargarInformacion());
        btnEliminar.addActionListener(e -> eliminarPropietario());
        btnCerrar.addActionListener(e -> dispose());
    }
    
    private void cargarInformacion() {
        int numeroCasa = (int) cmbCasa.getSelectedItem();
        casaSeleccionada = condominio.getCasa(numeroCasa);
        
        StringBuilder sb = new StringBuilder();
        
        if (!casaSeleccionada.tienePropietario()) {
            sb.append("===========================================================\n");
            sb.append("        CASA SIN PROPIETARIO REGISTRADO\n");
            sb.append("===========================================================\n\n");
            sb.append("No hay propietario para eliminar en esta casa.\n");
            sb.append("Seleccione otra casa.\n");
            txtInfo.setText(sb.toString());
            btnEliminar.setEnabled(false);
            return;
        }
        
        Propietario prop = casaSeleccionada.getPropietario();
        
        int mesActual = DateUtils.getMesActual();
        int anioActual = DateUtils.getAnioActual();
        boolean estaSolvente = casaSeleccionada.estaAlDia(mesActual, anioActual);
        
        sb.append("===========================================================\n");
        sb.append("              INFORMACION DEL PROPIETARIO\n");
        sb.append("===========================================================\n\n");
        sb.append("CASA #").append(numeroCasa).append("\n");
        sb.append("NOMBRE: ").append(prop.getNombre()).append("\n");
        sb.append("DPI: ").append(prop.getDpi()).append("\n");
        sb.append("TELEFONO: ").append(prop.getTelefono()).append("\n");
        sb.append("CORREO: ").append(prop.getEmail()).append("\n");
        sb.append("FECHA INGRESO: ").append(DateUtils.formatearFecha(prop.getFechaIngreso())).append("\n\n");
        
        sb.append("-----------------------------------------------------------\n");
        
        if (estaSolvente) {
            sb.append("ESTADO: AL DIA (Sin deudas)\n\n");
            sb.append("La casa esta solvente. Puede eliminar al propietario.\n");
            sb.append("===========================================================\n");
            btnEliminar.setEnabled(true);
        } else {
            sb.append("ESTADO: CON MORA (Con deudas pendientes)\n\n");
            sb.append("NO se puede eliminar al propietario porque tiene pagos pendientes.\n");
            sb.append("Primero debe registrar todos los pagos faltantes.\n");
            sb.append("===========================================================\n");
            btnEliminar.setEnabled(false);
        }
        
        txtInfo.setText(sb.toString());
    }
    
    private void eliminarPropietario() {
        if (casaSeleccionada == null || !casaSeleccionada.tienePropietario()) {
            DialogUtils.mostrarError(this, "No hay propietario para eliminar");
            return;
        }
        
        int mesActual = DateUtils.getMesActual();
        int anioActual = DateUtils.getAnioActual();
        
        if (!casaSeleccionada.estaAlDia(mesActual, anioActual)) {
            DialogUtils.mostrarError(this, "No se puede eliminar al propietario porque tiene deudas pendientes.\n" +
                                     "Primero debe registrar todos los pagos faltantes.");
            return;
        }
        
        String nombrePropietario = casaSeleccionada.getPropietario().getNombre();
        int numeroCasa = casaSeleccionada.getNumero();
        
        boolean confirmar = DialogUtils.confirmar(this, 
            "¿Esta seguro de eliminar al propietario " + nombrePropietario + "?\n\n" +
            "Casa #" + numeroCasa + " quedara disponible para nuevo propietario.\n" +
            "Los pagos registrados quedaran en el historial.");
        
        if (confirmar) {
            casaSeleccionada.setPropietario(null);
            
            try {
                persistencia.guardar(condominio);
                DialogUtils.mostrarExito(this, "Propietario eliminado correctamente.\n" +
                                         "La casa #" + numeroCasa + " esta disponible.");
                cargarInformacion();
            } catch (Exception e) {
                DialogUtils.mostrarError(this, "Error al guardar: " + e.getMessage());
            }
        }
    }
}
