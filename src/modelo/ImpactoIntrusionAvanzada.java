
package modelo;

import modelo.EventoSeguridad;
import modelo.EventoIntrusion;
import javax.swing.JOptionPane;

public class ImpactoIntrusionAvanzada implements EstrategiaImpacto {
    @Override
    public void ejecutarAnalisis(EventoSeguridad evento) {
        if (evento instanceof EventoIntrusion) {
            EventoIntrusion intrusion = (EventoIntrusion) evento;
            
            // CASO EXTREMO: Intrusión Nivel 3 con muchos intentos
            if (intrusion.getNivelRiesgo() == 3 && intrusion.getIntentosAcceso() > 10) {
                
                 JOptionPane.showMessageDialog(null, 
                    "¡AMENAZA CRÍTICA DETECTADA!\n" +
                    "Intentos: " + intrusion.getIntentosAcceso() + "\n" +
                    "SE REQUIERE BLOQUEO DE IP INMEDIATO.", 
                    "ALERTA DE INTRUSIÓN", 
                    JOptionPane.ERROR_MESSAGE); 
            } 
            // CASO MENOR: Intrusión detectada pero no tan grave (menor a 10 intentos o riesgo bajo)
            else {
                JOptionPane.showMessageDialog(null, 
                    "Actividad sospechosa detectada en " + intrusion.getPuntoAcceso() + ".\n" +
                    "Monitorear comportamiento.",
                    "Aviso de Intrusión",
                    JOptionPane.WARNING_MESSAGE); 
            }
        }
    }
}