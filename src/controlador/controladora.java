/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.awt.event.KeyEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author adrif
 */
public class controladora {
    //!!!!!!!!!!!
    public void iniciaVentana(JFrame ventana, String ruta) {   
       ventana.setResizable(false);//impide que la ventana se maximize
       ventana.setLocationRelativeTo(null);//centra la ventana
       ventana.setIconImage(new ImageIcon(ruta).getImage());//coloca un icono en
                                           //la ventana reemplazando la taza de java
                                          // que aparece por defecto
    }
    public void validarLetras (java.awt.event.KeyEvent evt){
        int k=(int)evt.getKeyChar();
            if ((k < 97 || k > 122) && (k<65 || k>90) && k!=20 && k!=8 && k!=32 && k!=127){
                evt.setKeyChar((char)KeyEvent.VK_CLEAR);
                JOptionPane.showMessageDialog(null,"Sólo debe ingresar letras","Error Datos",JOptionPane.ERROR_MESSAGE);
            }
        }
    public boolean esVacio (JTextField campo, String mensaje){
            if (campo.getText().isEmpty()|| "Ingresa tu primer nombre".equals(campo.getText())|| campo.getText().equals("Ingresa tu primer apellido")||campo.getText().equals("tucorreo@gmail.com")|| campo.getText().equals("********"))
               { JOptionPane.showMessageDialog(null, mensaje, "Error falta un dato", JOptionPane.ERROR_MESSAGE); 
                 return true; 
               }
            else
                 return false;
        }  

    public boolean validarEntero (String numero, String mensaje){
        try{
            int numero1 = Integer.parseInt(numero);
            return true; 
        }catch(NumberFormatException ex){
            JOptionPane.showMessageDialog(null,mensaje,"Debe indicar un numero entero", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    protected LocalDate validarFecha(JTextField fecha) {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);
        
        LocalDate fechaValida = null;
        while (fechaValida == null) {
            try {
                String fechaentrada = fecha.getText();
                LocalDate fechaFinal = LocalDate.parse(fechaentrada, dtf);
                LocalDate hoy = LocalDate.now();
            if (fechaFinal.isBefore(hoy) || fechaFinal.isEqual(hoy)) {
                fechaValida = fechaFinal;
            } else {
                JOptionPane.showMessageDialog(null,"La fecha debe ser hasta el dia de hoy.","Error al cargar fecha.", JOptionPane.ERROR_MESSAGE);
            }
            } catch (DateTimeParseException e) {
                JOptionPane.showMessageDialog(null,"El formato de la fecha es incorrecto, recuerde que es 'dd/MM/aaaa'.","Error al cargar fecha.", JOptionPane.ERROR_MESSAGE);
            }
        }
        return fechaValida;
    }
}
