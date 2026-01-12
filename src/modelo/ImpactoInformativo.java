
package modelo;

import modelo.EventoSeguridad;
import javax.swing.JOptionPane;

public class ImpactoInformativo implements EstrategiaImpacto {
    @Override
    public void ejecutarAnalisis(EventoSeguridad evento) {
        String mensaje = "El nivel de riesgo es " + evento.getNivelRiesgo() + 
                         ". TOME SUS PRECAUCIONES (Monitoreo estándar).";
        JOptionPane.showMessageDialog(null, mensaje, "Análisis Estándar", JOptionPane.INFORMATION_MESSAGE);
    }
    
}