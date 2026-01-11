/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.time.LocalDate;
import java.util.ArrayList;
import modelo.*;
/**
 *
 * @author adrif
 */
public class GestorDatos {
    private static final String ARCHIVO = "eventos_data.json";

    //para estructurar el JSON
    private static class ContenedorDatos {
        ArrayList<EventoSeguridad> simples = new ArrayList<>();
        ArrayList<EventoIntrusion> intrusiones = new ArrayList<>();
    }

    public static void guardarDatos(ArrayList<EventoSeguridad> listaCompleta) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .setPrettyPrinting()
                .create();

        // para no confundirnos separamos en dos listas por los dos tipos de eventos
        ContenedorDatos contenedor = new ContenedorDatos();
        for (EventoSeguridad ev : listaCompleta) {
            if (ev instanceof EventoIntrusion) {
                contenedor.intrusiones.add((EventoIntrusion) ev);
            } else {
                contenedor.simples.add(ev);
            }
        }
        try (Writer writer = new FileWriter(ARCHIVO)) {
            gson.toJson(contenedor, writer);
            System.out.println("Datos guardados correctamente en " + ARCHIVO);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<EventoSeguridad> cargarDatos() {
        ArrayList<EventoSeguridad> listaRecuperada = new ArrayList<>();
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();

        try (Reader reader = new FileReader(ARCHIVO)) {
            ContenedorDatos contenedor = gson.fromJson(reader, ContenedorDatos.class);
            if (contenedor.simples != null) { //unimos las dos listas en una como esta en el controlador
                listaRecuperada.addAll(contenedor.simples);
            }
            if (contenedor.intrusiones != null) {
                listaRecuperada.addAll(contenedor.intrusiones);
            }
            
        } catch (IOException e) {
            System.out.println("No se encontró archivo de datos previo o error al leer.");
        }
        
        return listaRecuperada;
    }
    
}
