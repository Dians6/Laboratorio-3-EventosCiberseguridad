
package modelo;

import java.util.InputMismatchException;
import java.time.LocalTime;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import modelo.EventoIntrusion;
import modelo.EventoSeguridad;
import modelo.ImpactoCritico;
import modelo.ImpactoInformativo;
import modelo.ImpactoIntrusionAvanzada;


public class EventoSeguridad {
    //!!!!!!!!!!!
    protected String idEvento;
    protected String tipoAmenaza;
    protected int nivelRiesgo; //1: Bajo - 2:Medio - 3: Alto
    protected LocalDate fechaDeteccion;
    protected String descripcion;
    protected transient EstrategiaImpacto estrategia;
    
    public EventoSeguridad(){
        idEvento = "N/A";
        tipoAmenaza = "N/A";
        nivelRiesgo = 0;
        fechaDeteccion = null;
        descripcion = "N/A";
        this.estrategia = new ImpactoInformativo();
    }
    
    public EventoSeguridad(String idEvento, String tipoAmenaza, int nivelRiesgo, LocalDate fechaDeteccion, String descripcion) {
        this.idEvento = idEvento ;
        this.tipoAmenaza = tipoAmenaza;
        this.nivelRiesgo = nivelRiesgo;
        this.fechaDeteccion = fechaDeteccion;
        this.descripcion = descripcion;
        this.definirEstrategiaAutomatica();
    }
    
    public void definirEstrategiaAutomatica() {
        // Si el riesgo es 3 o más, es crítico. Si no, es informativo.
        if (this.nivelRiesgo >= 3) {
            this.estrategia = new ImpactoCritico();
        } else {
            this.estrategia = new ImpactoInformativo();
        }
    }
    public String getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(String idEvento) {
        this.idEvento = idEvento;
    }

    public String getTipoAmenaza() {
        return tipoAmenaza;
    }

    public void setTipoAmenaza(String tipoAmenaza) {
        this.tipoAmenaza = tipoAmenaza;
    }

    public int getNivelRiesgo() {
        return nivelRiesgo;
    }

    public void setNivelRiesgo(int nivelRiesgo) {
        this.nivelRiesgo = nivelRiesgo;
    }

    public LocalDate getFechaDeteccion() {
        return fechaDeteccion;
    }

    public void setFechaDeteccion(LocalDate fechaDeteccion) {
        this.fechaDeteccion = fechaDeteccion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    
    public void GenerarReporteBasico(JLabel ID, JLabel amenaza, JLabel descripcionJ){
        ID.setText(idEvento);
        amenaza.setText(tipoAmenaza);
        descripcionJ.setText(descripcion);
    }
    
    public void GenerarReporteDetallado(JLabel ID, JLabel amenaza, JLabel nivel, JLabel evalRiesgo,JLabel fecha, JLabel descripcionJ){
        ID.setText(idEvento);
        amenaza.setText(tipoAmenaza);
        nivel.setText(String.valueOf(nivelRiesgo));
        switch (nivelRiesgo){
            case 1->evalRiesgo.setText("Amenaza Leve");
            case 2->evalRiesgo.setText("Amenaza Moderada");
            case 3->evalRiesgo.setText("Amenaza Critica");
            default->evalRiesgo.setText("Sin amenaza detectada");
        }
        fecha.setText(fechaDeteccion.toString());
        descripcionJ.setText(descripcion);
    }
    // Permitir cambiar la estrategia manualmente si es necesario (Setter)
    public void setEstrategiaImpacto(EstrategiaImpacto nuevaEstrategia) {
        this.estrategia = nuevaEstrategia;
    }

    // Metodo cambiado por la implementacion del Strategy
    public void analizarImpacto() {
        // Ya no hay switch ni JOptionPane aquí. Delegamos.
        if (estrategia != null) {
            estrategia.ejecutarAnalisis(this);
        }
    }
    
}
