package com.condominio.vistaverde.persistence;

import com.condominio.vistaverde.model.Condominio;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Persistencia {
    
    // Ruta que funciona en Windows, Mac y Linux
    private static final String ARCHIVO_DATOS = System.getProperty("user.home") + "/condominio.dat";
    
    public void guardar(Condominio condominio) throws IOException {
        File archivo = new File(ARCHIVO_DATOS);
        
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo))) {
            oos.writeObject(condominio);
        }
    }
    
    public Condominio cargar() throws IOException, ClassNotFoundException {
        File archivo = new File(ARCHIVO_DATOS);
        
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            return (Condominio) ois.readObject();
        }
    }
    
    public boolean existeArchivo() {
        File archivo = new File(ARCHIVO_DATOS);
        return archivo.exists();
    }
}