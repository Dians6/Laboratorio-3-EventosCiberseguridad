
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

public class EventoIntrusion extends EventoSeguridad{
    private String puntoAcceso;
    private int intentosAcceso;
    private String usuarioAfectado;
    
    Scanner entrada = new Scanner(System.in);

    
    public EventoIntrusion(String idEvento, String tipoAmenaza, int nivelRiesgo, LocalDate fechaDeteccion, String descripcion, String puntoAcceso, int intentosAcceso, String usuarioAfectado) {
        super(idEvento, tipoAmenaza, nivelRiesgo, fechaDeteccion, descripcion);
        this.puntoAcceso = puntoAcceso;
        this.intentosAcceso = intentosAcceso;
        this.usuarioAfectado = usuarioAfectado; 
    }
    
    public EventoIntrusion() {
        super();
        puntoAcceso = "N/A";
        intentosAcceso = 0;
        usuarioAfectado = "N/A";
    }
    
    public void bloquearPuntoAcceso() {
        int confirmar = JOptionPane.showConfirmDialog(null, "¿Deseas bloquear la IP del usuario?","Confirmacion",JOptionPane.YES_NO_CANCEL_OPTION);
        boolean bloqueado;
        if (confirmar == JOptionPane.YES_OPTION){
            bloqueado = true;
            JOptionPane.showMessageDialog(null,"IP BLOQUEADA" , "Bloquear punto de acceso concedido", JOptionPane.INFORMATION_MESSAGE); 
        }
        else if (confirmar == JOptionPane.NO_OPTION || confirmar == JOptionPane.CANCEL_OPTION || confirmar == JOptionPane.CLOSED_OPTION) {
            bloqueado = false;
        }
    }
    
    /*
    public void aislarSistema() {
        System.out.println("---PROTOCOLO DE AISLAMIENTO DEL SISTEMA---");
        System.out.println("Se ha detectado una amenaza en su sistema, desea aislarlo?");
        int decision = validarEntero("(1: Si, 2: No)","Debe ingresar un numero entre 1 y 2",1,2);
        if (decision == 1) {
            System.out.println("---AISLANDO SISTEMA...---");
            System.out.println("!!! AISLADO SATISFACTORIAMENTE !!!");
        } else {
            System.out.println("!!! ACCION CANCELADA !!!");
        }
    }*/
    /*
    public void notificarAdministrador() {
        System.out.println("---ANALIZANDO ALTERACIONES EN LA CUENTA DEL USUARIO...---");
        System.out.println("NIVEL DE RIESGO HACIA EL USUARIO:"+ nivelRiesgo);
        System.out.println("NUMERO DE INTENTOS DE ACCESO HACIA EL USUARIO:"+ intentosAcceso);
        
        if (nivelRiesgo == 3 && intentosAcceso >= 15) {
            aislarSistema();
        }
        else {
            System.out.println("No hay riesgo para aislar el sistema.");
        }
    }
    */
    public void GenerarReporteBasico(JLabel ID, JLabel amenaza, JLabel descripcionJ, JLabel fecha, JLabel intentos, JLabel ptoAcceso){
        ID.setText(idEvento);
        amenaza.setText(tipoAmenaza);
        descripcionJ.setText(descripcion);
        fecha.setText(fechaDeteccion.toString());
        ptoAcceso.setText(puntoAcceso);
        intentos.setText(String.valueOf(intentosAcceso));
        
    }

    public void GenerarReporteDetallado(JLabel ID, JLabel amenaza, JLabel descripcionJ, JLabel fecha, JLabel intentos, JLabel ptoAcceso, JLabel nivel, JLabel persistencia){
        ID.setText(idEvento);
        amenaza.setText(tipoAmenaza);
        switch (nivelRiesgo){
            case 1->nivel.setText("Amenaza Leve");
            case 2->nivel.setText("Amenaza Moderada");
            case 3->nivel.setText("Amenaza Critica");
            default->nivel.setText("Sin amenaza detectada");
        }
        fecha.setText(fechaDeteccion.toString());
        descripcionJ.setText(descripcion);
        ptoAcceso.setText(puntoAcceso);
        intentos.setText(String.valueOf(intentosAcceso));

        String nivelPersis = "";
        if ((intentosAcceso >= 1) && (intentosAcceso <= 5)){
            nivelPersis = "Exploracion";
        }
        if ((intentosAcceso >= 6) && (intentosAcceso <= 15)){
            nivelPersis = "Ataque Coordinado";
        }
        if (intentosAcceso > 15){
            nivelPersis = "Amenaza Persistente Avanzada";
        }
        persistencia.setText(nivelPersis);

    }
   
    @Override
    public void analizarImpacto(){
        super.analizarImpacto();
        //si se superan los 10 intentos y el nivel de riesgo es alto alerta critica
        if ((nivelRiesgo == 3) && (intentosAcceso >= 10)){
            System.out.println("ALERTA CRITICA: Posible compromiso de sistema");
        }
    }
   
    
}
