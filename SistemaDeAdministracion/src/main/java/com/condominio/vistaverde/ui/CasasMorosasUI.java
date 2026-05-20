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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Component;
import java.util.List;

public class CasasMorosasUI extends JDialog {
    
    private JTable tablaMorosas;
    private DefaultTableModel model;
    private JLabel lblTotalMorosas;
    private JLabel lblMontoPendiente;
    private Condominio condominio;
    
    public CasasMorosasUI(JFrame parent) {
        super(parent, "Casas Morosas", true);
        cargarDatos();
        initComponents();
        cargarMorosas();
    }
    
    private void cargarDatos() {
        Persistencia persistencia = new Persistencia();
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
        setSize(950, 550);
        setLocationRelativeTo(getParent());
        
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelPrincipal.setBackground(Color.WHITE);
        
        String[] columnas = {"N Casa", "Propietario", "Telefono", "Correo Electronico"};
        model = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaMorosas = new JTable(model);
        tablaMorosas.setRowHeight(35);
        tablaMorosas.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tablaMorosas.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tablaMorosas.getTableHeader().setBackground(new Color(200, 50, 50));
        tablaMorosas.getTableHeader().setForeground(Color.WHITE);
        tablaMorosas.setGridColor(new Color(220, 220, 220));
        
        tablaMorosas.getColumnModel().getColumn(0).setPreferredWidth(60);
        tablaMorosas.getColumnModel().getColumn(1).setPreferredWidth(220);
        tablaMorosas.getColumnModel().getColumn(2).setPreferredWidth(120);
        tablaMorosas.getColumnModel().getColumn(3).setPreferredWidth(280);
        
        tablaMorosas.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    if (row % 2 == 0) {
                        c.setBackground(new Color(255, 240, 240));
                    } else {
                        c.setBackground(new Color(255, 220, 220));
                    }
                    c.setForeground(Color.BLACK);
                }
                return c;
            }
        });
        
        JScrollPane scroll = new JScrollPane(tablaMorosas);
        scroll.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 50, 50), 2),
            "Lista de Casas con Pago Pendiente - " + 
            DateUtils.getNombreMes(DateUtils.getMesActual()) + " " + DateUtils.getAnioActual(),
            javax.swing.border.TitledBorder.CENTER,
            javax.swing.border.TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 14),
            new Color(200, 50, 50)));
        
        JPanel panelTotales = new JPanel(new GridLayout(1, 2, 15, 0));
        panelTotales.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 50, 50), 1),
            "Resumen de Morosidad",
            javax.swing.border.TitledBorder.CENTER,
            javax.swing.border.TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 12),
            new Color(200, 50, 50)));
        panelTotales.setBackground(Color.WHITE);
        
        lblTotalMorosas = new JLabel("Total de casas morosas: 0");
        lblTotalMorosas.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTotalMorosas.setHorizontalAlignment(JLabel.CENTER);
        
        lblMontoPendiente = new JLabel("Monto total pendiente: Q0.00");
        lblMontoPendiente.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblMontoPendiente.setHorizontalAlignment(JLabel.CENTER);
        
        panelTotales.add(lblTotalMorosas);
        panelTotales.add(lblMontoPendiente);
        
        JPanel panelRecomendacion = new JPanel();
        panelRecomendacion.setBackground(new Color(255, 255, 200));
        panelRecomendacion.setBorder(BorderFactory.createLineBorder(new Color(200, 150, 0), 1));
        JLabel lblRecomendacion = new JLabel("RECOMENDACION: Contactar a estas casas para recordar el pago");
        lblRecomendacion.setFont(new Font("Segoe UI", Font.BOLD, 13));
        panelRecomendacion.add(lblRecomendacion);
        
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelBotones.setBackground(Color.WHITE);
        
        JButton btnEnviarCorreo = new JButton("ENVIAR RECORDATORIO");
        btnEnviarCorreo.setBackground(new Color(0, 102, 204));
        btnEnviarCorreo.setForeground(Color.WHITE);
        btnEnviarCorreo.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnEnviarCorreo.setFocusPainted(false);
        btnEnviarCorreo.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        
        JButton btnCerrar = new JButton("CERRAR");
        btnCerrar.setBackground(new Color(200, 0, 0));
        btnCerrar.setForeground(Color.WHITE);
        btnCerrar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnCerrar.setFocusPainted(false);
        btnCerrar.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        
        panelBotones.add(btnEnviarCorreo);
        panelBotones.add(btnCerrar);
        
        JPanel panelSur = new JPanel(new BorderLayout(10, 10));
        panelSur.add(panelTotales, BorderLayout.NORTH);
        panelSur.add(panelRecomendacion, BorderLayout.CENTER);
        panelSur.add(panelBotones, BorderLayout.SOUTH);
        
        panelPrincipal.add(scroll, BorderLayout.CENTER);
        panelPrincipal.add(panelSur, BorderLayout.SOUTH);
        
        add(panelPrincipal);
        
        btnEnviarCorreo.addActionListener(e -> enviarRecordatorios());
        btnCerrar.addActionListener(e -> dispose());
    }
    
    private void cargarMorosas() {
        model.setRowCount(0);
        int mesActual = DateUtils.getMesActual();
        int anioActual = DateUtils.getAnioActual();
        List<Casa> casasMorosas = condominio.getCasasMorosas(mesActual, anioActual);
        
        double montoPendienteTotal = casasMorosas.size() * condominio.getCuotaMensual();
        
        for (Casa casa : casasMorosas) {
            Object[] fila = {
                casa.getNumero(),
                casa.getPropietario().getNombre(),
                casa.getPropietario().getTelefono(),
                casa.getPropietario().getEmail()
            };
            model.addRow(fila);
        }
        
        lblTotalMorosas.setText("Total de casas morosas: " + casasMorosas.size() + " de 30");
        lblMontoPendiente.setText("Monto total pendiente: Q" + String.format("%.2f", montoPendienteTotal));
        
        if (casasMorosas.size() > 10) {
            lblTotalMorosas.setForeground(Color.RED);
            lblMontoPendiente.setForeground(Color.RED);
        } else if (casasMorosas.size() > 5) {
            lblTotalMorosas.setForeground(new Color(255, 140, 0));
            lblMontoPendiente.setForeground(new Color(255, 140, 0));
        } else if (casasMorosas.size() > 0) {
            lblTotalMorosas.setForeground(new Color(255, 140, 0));
            lblMontoPendiente.setForeground(new Color(255, 140, 0));
        } else {
            lblTotalMorosas.setForeground(new Color(0, 150, 0));
            lblMontoPendiente.setForeground(new Color(0, 150, 0));
        }
    }
    
    private void enviarRecordatorios() {
        int morosas = model.getRowCount();
        if (morosas == 0) {
            DialogUtils.mostrarInfo(this, "No hay casas morosas para enviar recordatorios.");
            return;
        }
        
        if (DialogUtils.confirmar(this, "Desea enviar correo recordatorio a las " + morosas + " casas morosas?")) {
            int enviados = 0;
            int fallidos = 0;
            
            for (int i = 0; i < model.getRowCount(); i++) {
                int numeroCasa = (int) model.getValueAt(i, 0);
                Casa casa = condominio.getCasa(numeroCasa);
                
                if (casa.tienePropietario()) {
                    boolean enviado = EmailSender.enviarRecordatorio(
                        casa.getPropietario(), 
                        numeroCasa, 
                        DateUtils.getMesActual(), 
                        DateUtils.getAnioActual(), 
                        condominio.getCuotaMensual()
                    );
                    if (enviado) {
                        enviados++;
                    } else {
                        fallidos++;
                    }
                }
            }
            
            DialogUtils.mostrarExito(this, "Correos enviados: " + enviados + "\nFallidos: " + fallidos);
        }
    }
}