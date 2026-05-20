package com.condominio.vistaverde.ui;

import com.condominio.vistaverde.model.Condominio;
import com.condominio.vistaverde.model.Casa;
import com.condominio.vistaverde.model.Pago;
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
import java.time.LocalDate;
import java.util.List;

public class EstadoCuentaUI extends JDialog {
    
    private JComboBox<Integer> cmbCasa;
    private JButton btnConsultar;
    private JButton btnCerrar;
    private JTextArea txtResultado;
    private Condominio condominio;
    
    public EstadoCuentaUI(JFrame parent) {
        super(parent, "Estado de Cuenta por Casa", true);
        cargarDatos();
        initComponents();
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
        setSize(700, 600);
        setLocationRelativeTo(getParent());
        setResizable(false);
        
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelPrincipal.setBackground(Color.WHITE);
        
        JPanel panelSeleccion = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panelSeleccion.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(155, 89, 182), 2),
            "Seleccionar Casa",
            javax.swing.border.TitledBorder.CENTER,
            javax.swing.border.TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 14),
            new Color(155, 89, 182)));
        panelSeleccion.setBackground(Color.WHITE);
        
        panelSeleccion.add(new JLabel("Numero de casa:"));
        cmbCasa = new JComboBox<>();
        for (int i = 1; i <= 30; i++) {
            cmbCasa.addItem(i);
        }
        cmbCasa.setPreferredSize(new java.awt.Dimension(80, 30));
        cmbCasa.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        
        btnConsultar = new JButton("CONSULTAR");
        btnConsultar.setBackground(new Color(155, 89, 182));
        btnConsultar.setForeground(Color.WHITE);
        btnConsultar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnConsultar.setFocusPainted(false);
        btnConsultar.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        
        panelSeleccion.add(cmbCasa);
        panelSeleccion.add(btnConsultar);
        
        txtResultado = new JTextArea();
        txtResultado.setEditable(false);
        txtResultado.setFont(new Font("Monospaced", Font.PLAIN, 12));
        txtResultado.setBackground(new Color(250, 250, 250));
        txtResultado.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        JScrollPane scrollResultado = new JScrollPane(txtResultado);
        scrollResultado.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(155, 89, 182), 1),
            "Informacion de la Casa",
            javax.swing.border.TitledBorder.CENTER,
            javax.swing.border.TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 12),
            new Color(155, 89, 182)));
        
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBotones.setBackground(Color.WHITE);
        btnCerrar = new JButton("CERRAR");
        btnCerrar.setBackground(new Color(200, 0, 0));
        btnCerrar.setForeground(Color.WHITE);
        btnCerrar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnCerrar.setFocusPainted(false);
        btnCerrar.setBorder(BorderFactory.createEmptyBorder(8, 25, 8, 25));
        panelBotones.add(btnCerrar);
        
        panelPrincipal.add(panelSeleccion, BorderLayout.NORTH);
        panelPrincipal.add(scrollResultado, BorderLayout.CENTER);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);
        
        add(panelPrincipal);
        
        btnConsultar.addActionListener(e -> consultar());
        btnCerrar.addActionListener(e -> dispose());
    }
    
    private void consultar() {
        int numeroCasa = (int) cmbCasa.getSelectedItem();
        Casa casa = condominio.getCasa(numeroCasa);
        int mesActual = DateUtils.getMesActual();
        int anioActual = DateUtils.getAnioActual();
        
        StringBuilder sb = new StringBuilder();
        sb.append("===========================================================\n");
        sb.append("              ESTADO DE CUENTA - CASA #").append(numeroCasa).append("\n");
        sb.append("===========================================================\n\n");
        
        // Mostrar todos los pagos historicos (si existen)
        List<Pago> todosLosPagos = casa.getPagos();
        double totalHistorico = 0;
        
        if (!todosLosPagos.isEmpty()) {
            sb.append("HISTORIAL DE PAGOS REGISTRADOS:\n");
            sb.append("-----------------------------------------------------------\n");
            for (Pago pago : todosLosPagos) {
                sb.append("   ").append(DateUtils.getNombreMes(pago.getMes()))
                  .append(" ").append(pago.getAnio())
                  .append(" - Q").append(String.format("%.2f", pago.getMonto())).append("\n");
                totalHistorico += pago.getMonto();
            }
            sb.append("-----------------------------------------------------------\n");
            sb.append("TOTAL PAGADO HISTORICO: Q").append(String.format("%.2f", totalHistorico)).append("\n\n");
        }
        
        if (!casa.tienePropietario()) {
            sb.append("===========================================================\n");
            sb.append("     CASA SIN PROPIETARIO REGISTRADO ACTUALMENTE\n");
            sb.append("===========================================================\n");
            sb.append("Los pagos historicos se muestran arriba.\n");
            sb.append("Puede registrar un nuevo propietario para esta casa.\n");
            sb.append("===========================================================\n");
            txtResultado.setText(sb.toString());
            return;
        }
        
        // Si hay propietario actual, mostrar su informacion
        LocalDate fechaIngreso = casa.getPropietario().getFechaIngreso();
        if (fechaIngreso == null) {
            fechaIngreso = LocalDate.of(2026, 1, 1);
        }
        
        int anioIngreso = fechaIngreso.getYear();
        int mesIngreso = fechaIngreso.getMonthValue();
        
        sb.append("-----------------------------------------------------------\n");
        sb.append("PROPIETARIO ACTUAL:\n");
        sb.append("NOMBRE: ").append(casa.getPropietario().getNombre()).append("\n");
        sb.append("DPI: ").append(casa.getPropietario().getDpi()).append("\n");
        sb.append("TELEFONO: ").append(casa.getPropietario().getTelefono()).append("\n");
        sb.append("CORREO: ").append(casa.getPropietario().getEmail()).append("\n");
        sb.append("FECHA INGRESO: ").append(DateUtils.formatearFecha(fechaIngreso)).append("\n\n");
        
        sb.append("-----------------------------------------------------------\n");
        sb.append("MESES PAGADOS (desde ingreso):\n");
        
        boolean tienePagos = false;
        for (int anio = anioIngreso; anio <= anioActual; anio++) {
            int mesInicio = (anio == anioIngreso) ? mesIngreso : 1;
            int mesFin = (anio == anioActual) ? mesActual : 12;
            for (int mes = mesInicio; mes <= mesFin; mes++) {
                if (casa.haPagado(mes, anio)) {
                    sb.append("   PAGADO: ").append(DateUtils.getNombreMes(mes)).append(" ").append(anio).append("\n");
                    tienePagos = true;
                }
            }
        }
        if (!tienePagos) {
            sb.append("   No hay pagos registrados desde su ingreso\n");
        }
        
        sb.append("\n-----------------------------------------------------------\n");
        sb.append("MESES PENDIENTES:\n");
        
        boolean tienePendientes = false;
        for (int anio = anioIngreso; anio <= anioActual; anio++) {
            int mesInicio = (anio == anioIngreso) ? mesIngreso : 1;
            int mesFin = (anio == anioActual) ? mesActual : 12;
            for (int mes = mesInicio; mes <= mesFin; mes++) {
                if (!casa.haPagado(mes, anio)) {
                    sb.append("   PENDIENTE: ").append(DateUtils.getNombreMes(mes)).append(" ").append(anio).append("\n");
                    tienePendientes = true;
                }
            }
        }
        if (!tienePendientes) {
            sb.append("   AL DIA - Todos los meses pagados\n");
        }
        
        sb.append("\n-----------------------------------------------------------\n");
        double totalPagado = 0;
        for (int anio = anioIngreso; anio <= anioActual; anio++) {
            int mesInicio = (anio == anioIngreso) ? mesIngreso : 1;
            int mesFin = (anio == anioActual) ? mesActual : 12;
            for (int mes = mesInicio; mes <= mesFin; mes++) {
                if (casa.haPagado(mes, anio)) {
                    totalPagado += condominio.getCuotaMensual();
                }
            }
        }
        sb.append("TOTAL PAGADO DESDE EL INGRESO: Q").append(String.format("%.2f", totalPagado)).append("\n");
        sb.append("CUOTA MENSUAL ACTUAL: Q").append(String.format("%.2f", condominio.getCuotaMensual())).append("\n");
        
        sb.append("\n-----------------------------------------------------------\n");
        if (casa.estaAlDia(mesActual, anioActual)) {
            sb.append("ESTADO GENERAL: AL DIA\n");
        } else {
            sb.append("ESTADO GENERAL: CON MORA\n");
        }
        sb.append("===========================================================\n");
        
        txtResultado.setText(sb.toString());
    }
}