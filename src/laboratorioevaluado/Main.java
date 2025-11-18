/*
Michelangeli
Feng
Ferrera
*/
package laboratorioevaluado;

import java.util.InputMismatchException;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);
        
        int opc = 0;
        
        do{
            try{
                System.out.println("----------------------EVENTOS CIBERSEGURIDAD--------------------------");
                System.out.println("-- Elige el tipo de evento que quiere registrar y manejar. --");
                System.out.println("\t 1. Evento Seguridad (NORMAL).");
                System.out.println("\t 2. Evento de Intrusion.");
                System.out.println("\t 3. Salir.");
                System.out.println("----------------------------------------------------------------------");
                System.out.print("Introduzca una opcion: ");
                opc = entrada.nextInt();
                entrada.nextLine();
            
            switch (opc){
                case 1-> {
                    EventoSeguridad evento = null;
                    EventoSeguridad evento1 = new EventoSeguridad();
                    EventoSeguridad evento2 = new EventoSeguridad();
                    int cont = 1;
                    int opcion = 0;
                    do{
                        try {
                            System.out.println("----------------------EVENTOS SEGURIDAD--------------------------");
                            System.out.println("\n\t1. Registrar un nuevo evento.");
                            System.out.println("\t2. Generar reportes de eventos.");
                            System.out.println("\t3. Consultar informacion.");
                            System.out.println("\t4. Analisis de impacto.");
                            System.out.println("\t5. Salir\n");
                            System.out.println("----------------------------------------------------------------------");
                            System.out.print("Introduzca una opcion: ");
                            opcion = entrada.nextInt();
                            entrada.nextLine();

                            switch (opcion) {
                                case 1 ->{
                                    System.out.println("------ REGISTRAR EVENTOS ------");
                                    if (cont==1){
                                        evento = evento1;
                                        evento.leerDatos();
                                        cont+=1;
                                    } else {
                                        evento = evento2;
                                        evento.leerDatos();

                                    }

                                }
                                case 2 ->{
                                    System.out.println("------ REPORTES DE EVENTOS ------\n");
                                    int op = evento.validarEntero("Indique como quiere que sea el reporte (1:Basico - 2:Detallado): ", "Error: Debe ingresar 1 o 2", 1, 2);

                                    if (op==1){
                                        evento1.GenerarReporte(true);
                                        if (cont!=1){
                                            evento2.GenerarReporte(true);
                                        }
                                    }
                                    else {
                                        evento1.GenerarReporte();
                                        if (cont!=1){
                                            evento2.GenerarReporte();
                                        }
                                    }

                                }
                                case 3 -> {
                                    boolean bool=false;
                                    System.out.println("------ CONSULTAR INFORMACION -------");
                                    System.out.println("EVENTO #1 - ID "+ evento1.getIdEvento());
                                    if (cont!=1){
                                        System.out.println("EVENTO #2 - ID "+ evento2.getIdEvento());
                                    }
                                    String id = "";

                                    while(bool==false){
                                        System.out.print("\nIndique la ID del evento que quiere consultar: ");
                                        id = entrada.nextLine();

                                        if (id.equals(evento1.getIdEvento())){
                                            evento1.mostrarInformacion();
                                            bool = true;

                                        } else if( id.equals(evento2.getIdEvento())) {
                                            evento2.mostrarInformacion();
                                            bool = true;

                                        } else {
                                            System.out.println("El ID introducido no coincide con ninguno de los registrados");

                                        }
                                    }


                                }
                                case 4 ->{
                                    System.out.println("------ ANALIZAR IMPACTO -------");
                                    evento.analizarImpacto();
                                    System.out.println("-------------------------------");
                                }
                                case 5 ->{
                                    System.out.println("Hasta luego!");
                                }
                                default -> {System.out.println("Opcion Invalida. Debes indicar un numero entre 1 y 5.");}

                            }
                        } catch (InputMismatchException e) {
                            System.out.println("Error: Debes indicar un numero entre 1 y 5.");
                            entrada.nextLine();
                        }
                    }while (opcion!=5);  
                }
                case 2->{
                    EventoIntrusion evento3 = new EventoIntrusion();
                    EventoIntrusion evento4 = new EventoIntrusion();
                    EventoIntrusion eventoI = null;
                    
                    int cont = 1;
                    int opcion = 0;
                    
                    do {
                        try {
                            System.out.println("----------------------EVENTOS DE INTRUSION--------------------------");
                            System.out.println("\n\t1. Registrar un nuevo evento.");
                            System.out.println("\t2. Generar reportes de eventos.");
                            System.out.println("\t3. Analisis de impacto.");
                            System.out.println("\t4. Bloquear punto de acceso.");
                            System.out.println("\t5. Analisis de criterios criticos.");
                            System.out.println("\t6. Salir\n");
                            System.out.println("----------------------------------------------------------------------");
                            System.out.print("Introduzca una opcion: ");
                            opcion = entrada.nextInt();
                            entrada.nextLine();
                            switch(opcion){
                                case 1-> {
                                    System.out.println("------ REGISTRAR EVENTOS ------");
                                    if (cont==1){
                                        eventoI = evento3;
                                        eventoI.leerDatos();
                                        cont+=1;
                                    } else {
                                        eventoI = evento4;
                                        eventoI.leerDatos();

                                    }
                                }
                                case 2->{
                                    System.out.println("------ REPORTES DE EVENTOS ------\n");
                                    int op = eventoI.validarEntero("Indique como quiere que sea el reporte (1:Basico - 2:Detallado): ", "Error: Debe ingresar 1 o 2", 1, 2);

                                    if (op==1){
                                        evento3.GenerarReporte(true);
                                        if (cont!=1){
                                            evento4.GenerarReporte(true);
                                        }
                                    }
                                    else {
                                        evento3.GenerarReporte();
                                        if (cont!=1){
                                            evento4.GenerarReporte();
                                        }
                                    }
                                }
                                case 3->{
                                    System.out.println("------ ANALIZAR IMPACTO -------");
                                    eventoI.analizarImpacto();
                                    System.out.println("-------------------------------");
                                }
                                case 4->{
                                    eventoI.bloquearPuntoAcceso();
                                }
                                case 5->{
                                    eventoI.notificarAdministrador();
                                }
                                case 6->{
                                    System.out.println("Hasta luego!");

                                }
                                default -> System.out.println("Opcion invalida");
                        }
                    
                        }catch (InputMismatchException e) {
                            System.out.println("Error: Debes indicar un numero entre 1 y 5.");
                            entrada.nextLine();
                            }
                    }while(opcion!=6);
                
                }    
                
                case 3->{
                    System.out.println("Hasta luego!");
                }
                default -> System.out.println("Opcion Invalida. Introduzca un numero entre 1 y 3");
            }
        }catch (InputMismatchException e) {
            System.out.println("Error: Debes indicar un numero entre 1 y 3.");
            entrada.nextLine();
        }
        }while (opc!=3);
         
    }
    
}
