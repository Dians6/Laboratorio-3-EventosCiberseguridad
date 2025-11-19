
package modelo;

import java.util.InputMismatchException;
import java.time.LocalTime;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

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
        //super();
        puntoAcceso = "N/A";
        intentosAcceso = 0;
        usuarioAfectado = "N/A";
    }
    
    @Override
    public void leerDatos() {
        super.leerDatos();
        puntoAcceso = validarCadena("Introduzca la direccion IP asignada: ","Error: IP invalido. Solo se permiten letras, números, espacios y ciertos signos de puntuación (1-250 caracteres).","^[a-zA-Z0-9áéíóúÁÉÍÓÚñÑüÜ\\s.',-]{1,250}$");
        intentosAcceso = validarEntero("Ingrese los intentos de acceso hacia el usuario (maximo 20): ", "Error: el numero debe ser del 1 al 20.",1,20);
        usuarioAfectado = validarCadena("Introduzca el nombre del usuario afectado: ","Error: Titulo invalido. Solo se permiten letras, números, espacios y ciertos signos de puntuación (1-250 caracteres).","^[a-zA-Z0-9áéíóúÁÉÍÓÚñÑüÜ\\s.',-]{1,250}$");
    }
    
    public void bloquearPuntoAcceso() {
        System.out.println("---BLOQUEANDO IP DEL USUARIO---");
        System.out.println("!!! IP BLOQUEADA !!!");
        
    }
    
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
    }
    
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
    
    @Override
    public void GenerarReporte(boolean eResumido){
         super.GenerarReporte(eResumido);
         System.out.println("Fecha de Deteccion: "+fechaDeteccion);
         System.out.println("Direccion IP o sistema desde donde se origino la intrusion: "+puntoAcceso);
         System.out.println("Numero de intentos de acceso no autorizados: "+intentosAcceso);
         System.out.println("---------------------------------------------------------");
    }

    @Override
    public void GenerarReporte(){
         super.GenerarReporte();
         System.out.println("Direccion IP o sistema desde donde se origino la intrusion: "+puntoAcceso);
         System.out.println("Numero de intentos de acceso no autorizados: "+intentosAcceso);

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
         System.out.println("Nivel de persistencia basado en intentos de acceso: "+nivelPersis);
         System.out.println("---------------------------------------------------------");

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
