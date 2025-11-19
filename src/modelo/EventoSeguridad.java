
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

public class EventoSeguridad {
    //!!!!!!!!!!!
    protected String idEvento;
    protected String tipoAmenaza;
    protected int nivelRiesgo; //1: Bajo - 2:Medio - 3: Alto
    protected LocalDate fechaDeteccion;
    protected String descripcion;
    
    public EventoSeguridad(){
        idEvento = "N/A";
        tipoAmenaza = "N/A";
        nivelRiesgo = 0;
        fechaDeteccion = null;
        descripcion = "N/A";
    }
    
    public EventoSeguridad(String idEvento, String tipoAmenaza, int nivelRiesgo, LocalDate fechaDeteccion, String descripcion) {
        this.idEvento = idEvento ;
        this.tipoAmenaza = tipoAmenaza;
        this.nivelRiesgo = nivelRiesgo;
        this.fechaDeteccion = fechaDeteccion;
        this.descripcion = descripcion;
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
    
    public void analizarImpacto(){
        switch (nivelRiesgo){
            case 1->JOptionPane.showMessageDialog(null,"El nivel de riesgo es bajo. NO SE REQUIERE ACCION" , "Analisis de impacto", JOptionPane.INFORMATION_MESSAGE); 
            case 2->JOptionPane.showMessageDialog(null,"El nivel de riesgo es medio. TOME SUS PRECAUCIONES" , "Analisis de impacto", JOptionPane.INFORMATION_MESSAGE);  
            case 3->JOptionPane.showMessageDialog(null,"El nivel de riesgo es ALTO. ACCION INMEDIATA REQUERIDA" , "Analisis de impacto", JOptionPane.ERROR_MESSAGE); 
            default->JOptionPane.showMessageDialog(null,"No hay nivel de Riesgo: Sin Amenaza Detectada" , "Analisis de impacto", JOptionPane.INFORMATION_MESSAGE); 
        }
    }

   
    
    
    
    
}
