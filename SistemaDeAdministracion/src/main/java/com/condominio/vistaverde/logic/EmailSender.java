package com.condominio.vistaverde.logic;

import com.condominio.vistaverde.model.Propietario;
import com.condominio.vistaverde.utils.DateUtils;
import java.time.LocalDate;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender {
  
    private static final String SMTP_HOST = "smtp.gmail.com";
    private static final String SMTP_PORT = "587";
    private static final String EMAIL_REMITENTE = "vistaverdecondominio521@gmail.com";
    private static final String PASSWORD_REMITENTE = "xfyw nwsu wknj vmkh";
    
    private static boolean emailHabilitado = true;
    
    public static void setEmailHabilitado(boolean habilitado) {
        emailHabilitado = habilitado;
    }
    
    public static boolean isEmailHabilitado() {
        return emailHabilitado;
    }
    
    public static boolean enviarComprobantePago(Propietario propietario, int numeroCasa, 
                                                 int mes, int anio, double monto) {
        if (!emailHabilitado || propietario == null || propietario.getEmail() == null || propietario.getEmail().isEmpty()) {
            System.out.println("Correo no enviado: email no configurado o propietario sin email");
            return false;
        }
        
        String destinatario = propietario.getEmail();
        String asunto = "Comprobante de Pago - Condominio Vista Verde";
        String cuerpo = generarCuerpoComprobante(propietario, numeroCasa, mes, anio, monto);
        
        return enviarCorreo(destinatario, asunto, cuerpo);
    }
    
    public static boolean enviarRecordatorio(Propietario propietario, int numeroCasa, 
                                              int mes, int anio, double monto) {
        if (!emailHabilitado || propietario == null || propietario.getEmail() == null || propietario.getEmail().isEmpty()) {
            System.out.println("Recordatorio no enviado: email no configurado");
            return false;
        }
        
        String destinatario = propietario.getEmail();
        String asunto = "Recordatorio de Pago Pendiente - Condominio Vista Verde";
        String cuerpo = "Estimado(a) " + propietario.getNombre() + ",\n\n" +
                        "Le recordamos que su pago de cuota de mantenimiento del mes de " +
                        DateUtils.getNombreMes(mes) + " " + anio + 
                        " aun no ha sido registrado.\n\n" +
                        "Monto a pagar: Q" + String.format("%.2f", monto) + "\n\n" +
                        "Por favor, realice su pago a la brevedad.\n\n" +
                        "Atentamente,\n" +
                        "Administracion - Condominio Vista Verde\n" +
                        "Fecha: " + DateUtils.formatearFecha(LocalDate.now());
        
        return enviarCorreo(destinatario, asunto, cuerpo);
    }
    
    private static String generarCuerpoComprobante(Propietario propietario, int numeroCasa, 
                                                    int mes, int anio, double monto) {
        return "==========================================\n" +
               "      COMPROBANTE DE PAGO\n" +
               "==========================================\n\n" +
               "Estimado(a) " + propietario.getNombre() + ",\n\n" +
               "Se ha registrado su pago de cuota de mantenimiento:\n\n" +
               "DETALLE DEL PAGO:\n" +
               "   Casa: #" + numeroCasa + "\n" +
               "   Mes: " + DateUtils.getNombreMes(mes) + " " + anio + "\n" +
               "   Monto: Q" + String.format("%.2f", monto) + "\n" +
               "   Fecha de registro: " + DateUtils.formatearFecha(LocalDate.now()) + "\n\n" +
               "Gracias por su puntualidad.\n\n" +
               "Atentamente,\n" +
               "Administracion - Condominio Vista Verde\n" +
               "==========================================";
    }
    
    private static boolean enviarCorreo(String destinatario, String asunto, String cuerpo) {
        try {
            Properties props = new Properties();
            props.put("mail.smtp.host", SMTP_HOST);
            props.put("mail.smtp.port", SMTP_PORT);
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.ssl.protocols", "TLSv1.2");
            
            Authenticator auth = new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(EMAIL_REMITENTE, PASSWORD_REMITENTE);
                }
            };
            
            Session session = Session.getInstance(props, auth);
            session.setDebug(false);
            
            Message mensaje = new MimeMessage(session);
            mensaje.setFrom(new InternetAddress(EMAIL_REMITENTE));
            mensaje.setRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
            mensaje.setSubject(asunto);
            mensaje.setText(cuerpo);
            
            Transport.send(mensaje);
            System.out.println("Correo enviado a: " + destinatario);
            return true;
            
        } catch (MessagingException e) {
            System.out.println("Error al enviar correo: " + e.getMessage());
            return false;
        }
    }
}
