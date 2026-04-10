package org.example.registro;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ArchivoRegistroCosto implements RegistroDeCosto {
    private String rutaArchivo;

    public ArchivoRegistroCosto(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }

    @Override
    public void registrarCosto(double monto) {
        String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String linea = String.format("%s || %f", fecha, monto);

        try (PrintWriter out = new PrintWriter(new FileWriter(rutaArchivo, true))) {
            out.println(linea);
        } catch (IOException e) {
            System.err.println("Error al registrar el costo: " + e.getMessage());
        }

    }

}
