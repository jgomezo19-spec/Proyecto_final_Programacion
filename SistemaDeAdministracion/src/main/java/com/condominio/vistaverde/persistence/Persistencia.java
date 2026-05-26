package com.condominio.vistaverde.persistence;

import com.condominio.vistaverde.model.Condominio;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Persistencia {
    
    private static final String ARCHIVO_DATOS = "condominio.dat";
    
    public void guardar(Condominio condominio) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO_DATOS))) {
            oos.writeObject(condominio);
        }
    }
    
    public Condominio cargar() throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARCHIVO_DATOS))) {
            return (Condominio) ois.readObject();
        }
    }
    
    public boolean existeArchivo() {
        File archivo = new File(ARCHIVO_DATOS);
        return archivo.exists();
    }
}
