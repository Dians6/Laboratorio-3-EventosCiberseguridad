
package modelo;

import modelo.EventoSeguridad;
import javax.swing.JOptionPane;

public class ImpactoCritico implements EstrategiaImpacto {
    @Override
    public void ejecutarAnalisis(EventoSeguridad evento) {
        // Aquí podrías agregar lógica extra, como "enviar alerta al CISO"
        String mensaje = "¡ALERTA! Riesgo ALTO (" + evento.getNivelRiesgo() + 
                         "). ACCION INMEDIATA REQUERIDA.";
        JOptionPane.showMessageDialog(null, mensaje, "ALERTA CRÍTICA", JOptionPane.ERROR_MESSAGE);
    }
}