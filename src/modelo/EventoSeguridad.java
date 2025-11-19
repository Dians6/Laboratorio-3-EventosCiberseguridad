
package modelo;

import java.util.InputMismatchException;
import java.time.LocalTime;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

public class EventoSeguridad {
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
    
    
    Scanner entrada = new Scanner(System.in);
    
    public int validarEntero(String mensaje, String error, int limiteInf, int limiteSup){
       int valor = 0;
       boolean continuar = true;
        do {
            try {
                System.out.print(mensaje);
                valor = entrada.nextInt();
                if (valor < limiteInf || valor > limiteSup) {
                    System.out.println(error);
                } else {
                    continuar = false;
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Debes indicar un numero entero positivo.");
                entrada.nextLine(); // Limpia el buffer del Scanner para evitar un bucle infinito
            }
        } while (continuar);
        entrada.nextLine(); // Limpia el buffer después de la lectura exitosa del número
        return valor;
    }

    protected String validarCadena(String mensaje, String error, String regex){
        String valor;
        boolean continuar = true;
        do {
            System.out.print(mensaje);
            valor = entrada.nextLine();
            if (valor.matches(regex)) {
                continuar = false;
            } else {
                System.out.println(error);
            }
        } while (continuar);
        return valor;
    }
    
    protected LocalDate validarFecha(String mensaje, String mensajeError) {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);
        
        LocalDate fechaValida = null;
        while (fechaValida == null) {
            try {
                System.out.print(mensaje);
                String fechaentrada = entrada.nextLine().trim();
                LocalDate fecha = LocalDate.parse(fechaentrada, dtf);
                LocalDate hoy = LocalDate.now();
            if (fecha.isBefore(hoy) || fecha.isEqual(hoy)) {
                fechaValida = fecha;
            } else {
                System.out.println("Error: La fecha debe ser hasta el dia de hoy. La fecha no debe ser futura.");
            }
            } catch (DateTimeParseException e) {
                System.out.println("Error: " + mensajeError);
            }
        }
        return fechaValida;
    }


    public void leerDatos(){
        idEvento = validarCadena("Ingrese el ID del evento: ","Error: Titulo invalido. Solo se permiten letras, números, espacios y ciertos signos de puntuación (1-250 caracteres).","^[a-zA-Z0-9áéíóúÁÉÍÓÚñÑüÜ\\s.',-]{1,250}$");
        tipoAmenaza = validarCadena("Ingrese el tipo de amenaza: ","Error: Titulo invalido. Solo se permiten letras, números, espacios y ciertos signos de puntuación (1-250 caracteres).","^[a-zA-Z0-9áéíóúÁÉÍÓÚñÑüÜ\\s.',-]{1,250}$");
        nivelRiesgo = validarEntero("Ingrese un numero del 1 al 3 segun el nivel de riesgo de la amenaza (1 = Bajo; 2 = Medio; 3 = Alto): ", "Error: el numero debe ser del 1 al 3.",1,3);
        fechaDeteccion = validarFecha("Ingrese la fecha en que se detecto el evento (dd/MM/yyyy): ","Error: Formato incorrecto o fecha inexistente. Asegurate de usar dd/MM/yyyy");
        descripcion = validarCadena("Ingrese la descripcion del incidente: ","Error: Titulo invalido. Solo se permiten letras, números, espacios y ciertos signos de puntuación (1-250 caracteres).","^[a-zA-Z0-9áéíóúÁÉÍÓÚñÑüÜ\\s.',-]{1,250}$");
        }
    
    public void GenerarReporte(boolean esResumido){
        System.out.println("--GENERANDO REPORTE BASICO--");
        System.out.println("identificador: "+idEvento);
        System.out.println("Tipo de Amenaza: "+tipoAmenaza);
        System.out.println("Descripcion: "+descripcion);
    }
    
    public void GenerarReporte(){
        System.out.println("--GENERANDO REPORTE DETALLADO--");
        System.out.println("identificador: "+idEvento);
        System.out.println("Tipo de Amenaza: "+tipoAmenaza);
        
        switch (nivelRiesgo){
            case 1->System.out.println("Nivel de Riesgo: Amenaza Leve"); 
            case 2->System.out.println("Nivel de Riesgo: Amenaza Moderada");   
            case 3->System.out.println("Nivel de Riesgo: Amenaza Critica");
            default->System.out.println("Nivel de Riesgo: Sin Amenaza Detectada");
        }
        System.out.println("Descripcion: "+descripcion);
        System.out.println("Fecha de Deteccion: "+fechaDeteccion);
    }
    
    public void analizarImpacto(){
        switch (nivelRiesgo){
            case 1->System.out.println("El nivel de riesgo es bajo. NO SE REQUIERE ACCION"); 
            case 2->System.out.println("El nivel de riesgo es medio. TOME SUS PRECAUCIONES");   
            case 3->System.out.println("El nivel de riesgo es alto. ACCION INMEDIATA REQUERIDA");
            default->System.out.println("No hay nivel de Riesgo: Sin Amenaza Detectada");
        }
    }

    public void mostrarInformacion(){
        System.out.println("ID del evento: " + idEvento);
        System.out.println("Tipo de Amenaza: " + tipoAmenaza);
        System.out.print("Nivel de riesgo: ");
        switch (nivelRiesgo) {
            case 1 -> System.out.println("Bajo");
            case 2 -> System.out.println("Medio");
            case 3 -> System.out.println("Alto");
            default -> System.out.println("Nivel de riesgo desconocido (" + nivelRiesgo + ")");
            }
        System.out.println("Fecha de deteccion: " + fechaDeteccion);
        System.out.println("Descripcion: " + descripcion);
    }

    
    
    
    
}
