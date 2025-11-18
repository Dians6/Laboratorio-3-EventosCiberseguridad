/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 *
 * @author adrif
 */
public class controladora {
    
    public void iniciaVentana(JFrame ventana, String ruta) {   
       ventana.setResizable(false);//impide que la ventana se maximize
       ventana.setLocationRelativeTo(null);//centra la ventana
       ventana.setIconImage(new ImageIcon(ruta).getImage());//coloca un icono en
                                           //la ventana reemplazando la taza de java
                                          // que aparece por defecto
    }
    
}
