package com.condominio.vistaverde.ui;

import com.condominio.vistaverde.model.Condominio;
import com.condominio.vistaverde.model.Casa;
import com.condominio.vistaverde.utils.DateUtils;
import com.condominio.vistaverde.logic.Calculador;
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

public class ReporteGeneralUI extends JDialog {
    
    private JTable tablaReporte;
    private DefaultTableModel model;
    private JLabel lblTotalRecaudado;
    private JLabel lblMetaEsperada;
    private JLabel lblDiferencia;
    private Condominio condominio;
    
    public ReporteGeneralUI(JFrame parent) {
        super(parent, "Reporte General de Cuotas", true);
        cargarDatos();
        initComponents();
        cargarReporte();
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
        setSize(1000, 600);
        setLocationRelativeTo(getParent());
        
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelPrincipal.setBackground(Color.WHITE);
        
        String[] columnas = {"N Casa", "Propietario", "Estado Mes Actual", "Total Pagado Anio"};
        model = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaReporte = new JTable(model);
        tablaReporte.setRowHeight(35);
        tablaReporte.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tablaReporte.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tablaReporte.getTableHeader().setBackground(new Color(0, 102, 204));
        tablaReporte.getTableHeader().setForeground(Color.WHITE);
        tablaReporte.setGridColor(new Color(220, 220, 220));
        
        tablaReporte.getColumnModel().getColumn(0).setPreferredWidth(60);
        tablaReporte.getColumnModel().getColumn(1).setPreferredWidth(250);
        tablaReporte.getColumnModel().getColumn(2).setPreferredWidth(150);
        tablaReporte.getColumnModel().getColumn(3).setPreferredWidth(150);
        
        tablaReporte.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    if (row % 2 == 0) {
                        c.setBackground(new Color(240, 248, 255));
                    } else {
                        c.setBackground(Color.WHITE);
                    }
                    if (column == 2 && value != null && value.toString().contains("Pendiente")) {
                        c.setForeground(Color.RED);
                    } else if (column == 2 && value != null && value.toString().contains("Pagado")) {
                        c.setForeground(new Color(0, 150, 0));
                    } else if (column == 2 && value != null && value.toString().contains("Sin propietario")) {
                        c.setForeground(new Color(200, 100, 0));
                    } else {
                        c.setForeground(Color.BLACK);
                    }
                }
                return c;
            }
        });
        
        JScrollPane scroll = new JScrollPane(tablaReporte);
        scroll.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(230, 126, 34), 2),
            "Listado de Casas",
            javax.swing.border.TitledBorder.CENTER,
            javax.swing.border.TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 14),
            new Color(230, 126, 34)));
        
        int mesActual = DateUtils.getMesActual();
        int anioActual = DateUtils.getAnioActual();
        double recaudado = condominio.getTotalRecaudadoMes(mesActual, anioActual);
        double esperado = condominio.getTotalEsperadoMes();
        double diferencia = Calculador.calcularDiferencia(recaudado, esperado);
        
        JPanel panelTotales = new JPanel(new GridLayout(1, 3, 15, 0));
        panelTotales.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(230, 126, 34), 1),
            "Resumen Financiero",
            javax.swing.border.TitledBorder.CENTER,
            javax.swing.border.TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 12),
            new Color(230, 126, 34)));
        panelTotales.setBackground(Color.WHITE);
        
        lblTotalRecaudado = new JLabel("Total recaudado mes actual: Q" + String.format("%.2f", recaudado));
        lblTotalRecaudado.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblTotalRecaudado.setHorizontalAlignment(JLabel.CENTER);
        
        lblMetaEsperada = new JLabel("Meta esperada: Q" + String.format("%.2f", esperado));
        lblMetaEsperada.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblMetaEsperada.setHorizontalAlignment(JLabel.CENTER);
        
        lblDiferencia = new JLabel("Diferencia: Q" + String.format("%.2f", diferencia));
        lblDiferencia.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblDiferencia.setHorizontalAlignment(JLabel.CENTER);
        
        if (diferencia < 0) {
            lblDiferencia.setForeground(Color.RED);
        } else {
            lblDiferencia.setForeground(new Color(0, 150, 0));
        }
        
        panelTotales.add(lblTotalRecaudado);
        panelTotales.add(lblMetaEsperada);
        panelTotales.add(lblDiferencia);
        
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBotones.setBackground(Color.WHITE);
        
        JButton btnCerrar = new JButton("CERRAR");
        btnCerrar.setBackground(new Color(200, 0, 0));
        btnCerrar.setForeground(Color.WHITE);
        btnCerrar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnCerrar.setFocusPainted(false);
        btnCerrar.setBorder(BorderFactory.createEmptyBorder(8, 25, 8, 25));
        panelBotones.add(btnCerrar);
        
        panelPrincipal.add(scroll, BorderLayout.CENTER);
        panelPrincipal.add(panelTotales, BorderLayout.NORTH);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);
        
        add(panelPrincipal);
        
        btnCerrar.addActionListener(e -> dispose());
    }
    
    private void cargarReporte() {
        model.setRowCount(0);
        int mesActual = DateUtils.getMesActual();
        int anioActual = DateUtils.getAnioActual();
        
        for (Casa casa : condominio.getCasas()) {
            String estado;
            if (!casa.tienePropietario()) {
                estado = "Sin propietario";
            } else if (casa.haPagado(mesActual, anioActual)) {
                estado = "Pagado";
            } else {
                estado = "Pendiente";
            }
            
            String nombrePropietario = casa.tienePropietario() ? casa.getPropietario().getNombre() : "---";
            double totalPagado = casa.getTotalPagadoAnio(anioActual);
            
            Object[] fila = {
                casa.getNumero(),
                nombrePropietario,
                estado,
                "Q" + String.format("%.2f", totalPagado)
            };
            model.addRow(fila);
        }
    }
}