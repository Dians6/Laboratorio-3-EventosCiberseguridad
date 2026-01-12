package controlador;



import java.awt.event.KeyEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import modelo.*;

public class Controladora {
    private ArrayList<EventoSeguridad> listaEventos;
    private EventoSeguridad eventoActual;
    public Controladora() {
        this.listaEventos = GestorDatos.cargarDatos(); 
        
        if (this.listaEventos == null) {
            this.listaEventos = new ArrayList<>();
        }
        this.eventoActual = null;
    }
    // =================================================================
    // 1. UTILIDADES Y CONFIGURACIÓN
    // =================================================================
    public void iniciaVentana(JFrame ventana, String ruta) {   
        ventana.setResizable(false);
        ventana.setLocationRelativeTo(null);
        if (ruta != null && !ruta.isEmpty()) {
            ventana.setIconImage(new ImageIcon(ruta).getImage());
        }
    }

    public void validarLetras (java.awt.event.KeyEvent evt){
        int k=(int)evt.getKeyChar();
        if ((k < 97 || k > 122) && (k<65 || k>90) && k!=20 && k!=8 && k!=32 && k!=127){
            evt.setKeyChar((char)KeyEvent.VK_CLEAR);
            JOptionPane.showMessageDialog(null,"Sólo debe ingresar letras","Error Datos",JOptionPane.ERROR_MESSAGE);
        }

    }

    public boolean esVacio (JTextField campo, String mensaje){
        if (campo.getText().trim().isEmpty()){ 
            JOptionPane.showMessageDialog(null, mensaje, "Campo Vacío", JOptionPane.ERROR_MESSAGE); 
            return true;
        }
        return false;
    }  

    public boolean validarEntero (String numero, String mensaje){
        try{
            Integer.parseInt(numero);
            return true; 
        }catch(NumberFormatException ex){
            JOptionPane.showMessageDialog(null,mensaje,"Debe indicar un número entero", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    

    protected LocalDate validarFecha(JTextField fecha) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);
        try {
            LocalDate fechaFinal = LocalDate.parse(fecha.getText(), dtf);
            LocalDate hoy = LocalDate.now();
            if (fechaFinal.isAfter(hoy)) {
                JOptionPane.showMessageDialog(null,"La fecha no puede ser futura.","Error fecha", JOptionPane.ERROR_MESSAGE);
                return null;
            }
            return fechaFinal;
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(null,"Formato incorrecto (dd/MM/aaaa).","Error fecha", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public boolean existeID(String id) {
        for (EventoSeguridad evento : listaEventos) {
            if (evento.getIdEvento().equalsIgnoreCase(id)) {
                return true; 
            }
        }
        return false;
    }

    // =================================================================
    // 2. NAVEGACIÓN CON JTABBEDPANE (LA SOLUCIÓN ESTABLE)
    // =================================================================

    // Método base para cambiar pestañas
    private void cambiarPestana(JTabbedPane pestañas, JPanel panelDestino) {
        pestañas.setSelectedComponent(panelDestino);
    }

    // Navegación general (Botones Siguiente, Volver, Escudo, Hacker)
    public void cambiarPantalla(JTabbedPane pestañas, JPanel panelDestino) {
         cambiarPestana(pestañas, panelDestino);
    }

    // Ir a REGISTRAR
    public void irARegistrar(JTabbedPane pestañas, JPanel panelDestino) {
        cambiarPestana(pestañas, panelDestino);
    }

    // Ir a REPORTES (Valida lista vacía)
    public void irAReportes(JTabbedPane pestañas, JPanel panelDestino, JList<String> listaReportes) {
        if (listaEventos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay eventos registrados.", "Lista Vacía", JOptionPane.WARNING_MESSAGE);
            return;
        }
        cargarListaParaSeleccion(listaReportes);
        cambiarPestana(pestañas, panelDestino);
    }

    // Ir a CONSULTAR (Valida lista vacía)
    public void irAConsultarInformacion(JTabbedPane pestañas, JPanel panelDestino, JList<String> listaConsulta) {
        if (listaEventos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay información para consultar.", "Lista Vacía", JOptionPane.WARNING_MESSAGE);
            return;
        }

        cargarListaParaSeleccion(listaConsulta);

        cambiarPestana(pestañas, panelDestino);

    }



    // Ir a CAMBIAR EVENTO ACTUAL (Valida lista vacía)

    public void irACambiarEventoActual(JTabbedPane pestañas, JPanel panelDestino, JList<String> listaSeleccion) {

        if (listaEventos.isEmpty()) {

            JOptionPane.showMessageDialog(null, "No hay eventos para seleccionar.", "Lista Vacía", JOptionPane.WARNING_MESSAGE);

            return;

        }

        cargarListaParaSeleccion(listaSeleccion);

        cambiarPestana(pestañas, panelDestino);

    }



    // Navegación específica de Intrusión (Paso 1 -> Paso 2 con validación)

    public void validarYPasarPaso2(JTabbedPane pestañas, JPanel panelPaso2, JTextField txtID, JTextField txtTipo, JTextField txtFecha, JComboBox cmbNivel, JTextField txtDesc) { 

        if (esVacio(txtID, "Debe indicar el ID.")) return;

        

        if (existeID(txtID.getText())) {

            JOptionPane.showMessageDialog(null, "El ID '" + txtID.getText() + "' ya existe.", "Duplicado", JOptionPane.ERROR_MESSAGE);

            return;

        }

        if (esVacio(txtDesc, "Debe indicar una descripción.")) return;

        if (esVacio(txtTipo, "Debe indicar el Tipo de Amenaza.")) return;

        if (validarFecha(txtFecha) == null) return;

        

        cambiarPestana(pestañas, panelPaso2);

    }



    public void salirDelSistema() {

        if (JOptionPane.showConfirmDialog(null, "¿Esta seguro que desea salir?", "Salir", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

            System.exit(0);

        }

    }



    // =================================================================

    // 3. REGISTRO DE EVENTOS

    // =================================================================



    public void registrarEventoSimple(JTextField txtID, JTextField txtTipo, JComboBox<String> cmbNivel, JTextField txtFecha, JTextField txtDesc, JLabel lblIDActualUI) {
        if (esVacio(txtID, "Debe indicar el ID.")) return;
        if (existeID(txtID.getText())) {
            JOptionPane.showMessageDialog(null, "El ID ya existe.", "Duplicado", JOptionPane.ERROR_MESSAGE); return;
        }
        if (esVacio(txtTipo, "Falta Tipo.")) return;
        if (esVacio(txtDesc, "Falta Descripción.")) return;
        LocalDate fechaValida = validarFecha(txtFecha);
        if (fechaValida == null) return;
        try {
            EventoSeguridad nuevo = new EventoSeguridad(
                txtID.getText(), txtTipo.getText(), 
                Integer.parseInt(cmbNivel.getSelectedItem().toString()), 
                fechaValida, txtDesc.getText()
            );

            listaEventos.add(nuevo);
            this.eventoActual = nuevo;
            lblIDActualUI.setText(nuevo.getIdEvento());
            GestorDatos.guardarDatos(listaEventos);
            JOptionPane.showMessageDialog(null, "Evento Simple Registrado");
            txtID.setText(""); txtTipo.setText(""); txtDesc.setText(""); txtFecha.setText("");
            cmbNivel.setSelectedIndex(0);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    

    public void registrarIntrusionFinal(
            JTextField txtID, JTextField txtTipo, JTextField txtFecha, JComboBox cmbNivel, JTextField txtDesc, // Panel 1
            JTextField txtPunto, JTextField txtIntentos, JTextField txtUsuario, // Panel 2
            JLabel lblIDActualUI
    ) {

        if (esVacio(txtPunto, "Falta punto de acceso.")) return;

        if (esVacio(txtIntentos, "Faltan intentos.")) return;

        if (esVacio(txtUsuario, "Falta usuario.")) return;

        if (!validarEntero(txtIntentos.getText(), "Intentos debe ser numérico.")) return;

        LocalDate fechaValida = validarFecha(txtFecha);
        try {
            EventoIntrusion intrusion = new EventoIntrusion(
                txtID.getText(), txtTipo.getText(),
                Integer.parseInt(cmbNivel.getSelectedItem().toString()),
                fechaValida, txtDesc.getText(),
                txtPunto.getText(), Integer.parseInt(txtIntentos.getText()), txtUsuario.getText()
            );
            listaEventos.add(intrusion);
            this.eventoActual = intrusion;
            lblIDActualUI.setText(intrusion.getIdEvento());
            GestorDatos.guardarDatos(listaEventos);
            JOptionPane.showMessageDialog(null, "¡Intrusión registrada!", "Éxito", JOptionPane.INFORMATION_MESSAGE);

            

            txtID.setText(""); txtTipo.setText(""); txtFecha.setText("");

            txtDesc.setText(""); txtPunto.setText(""); txtIntentos.setText(""); txtUsuario.setText("");



        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());

        }

    }



    // =================================================================

    // 4. LISTAS Y REPORTES

    // =================================================================



    public void cargarListaParaSeleccion(JList<String> jListSeleccion) {

        DefaultListModel<String> modelo = new DefaultListModel<>();

        int index = 0;

        if (listaEventos.isEmpty()) {

            modelo.addElement("No hay eventos registrados.");

            jListSeleccion.setEnabled(false);

        } else {

            jListSeleccion.setEnabled(true);

            for (EventoSeguridad ev : listaEventos) {

                String tipo = (ev instanceof EventoIntrusion) ? "[INTRUSIÓN]" : "[SEGURIDAD]";

                modelo.addElement(index + " | " + tipo + " | ID: " + ev.getIdEvento() + " | " + ev.getTipoAmenaza());

                index++;

            }

        }

        jListSeleccion.setModel(modelo);

    }



    public void confirmarEventoActual(JList<String> jListSeleccion, JLabel lblIDEventoActual) {

        if (listaEventos.isEmpty()) return;

        

        int index = jListSeleccion.getSelectedIndex();

        if (index != -1) {

            this.eventoActual = listaEventos.get(index);

            lblIDEventoActual.setText(this.eventoActual.getIdEvento());

            JOptionPane.showMessageDialog(null, "Evento ID: " + this.eventoActual.getIdEvento() + " seleccionado como Actual.");

        } else {

            JOptionPane.showMessageDialog(null, "Seleccione un evento primero.");

        }

    }

    

    public void gestionarReporteBasico(JTabbedPane pestañas, JList<String> lista, JPanel panelIntrusion, JPanel panelSimple,

            JLabel lblID_I, JLabel lblAm_I, JLabel lblDesc_I, JLabel lblFec_I, JLabel lblInt_I, JLabel lblPto_I,

            JLabel lblID_S, JLabel lblAm_S, JLabel lblDesc_S

    ) {

        int index = lista.getSelectedIndex();

        if (index == -1) {

            JOptionPane.showMessageDialog(null, "Por favor, seleccione un evento de la lista.");

            return;

        }

        EventoSeguridad ev = listaEventos.get(index);

        if (ev instanceof EventoIntrusion) {

            ((EventoIntrusion) ev).GenerarReporteBasico(lblID_I, lblAm_I, lblDesc_I, lblFec_I, lblInt_I, lblPto_I);

            cambiarPestana(pestañas, panelIntrusion);

        } else {

            ev.GenerarReporteBasico(lblID_S, lblAm_S, lblDesc_S);

            cambiarPestana(pestañas, panelSimple);

        }

    }



    public void gestionarReporteDetallado(JTabbedPane pestañas, JList<String> lista, JPanel panelIntrusion, JPanel panelSimple,

            JLabel d3ID, JLabel d3Am, JLabel d3De, JLabel d3Fe, JLabel d3In, JLabel d3Pto, JLabel d3Niv, JLabel d3Ev, JLabel d3Per,

            JLabel d2ID, JLabel d2Am, JLabel d2Niv, JLabel d2Ev, JLabel d2Fe, JLabel d2De

    ) {

        int index = lista.getSelectedIndex();

        if (index == -1) {

            JOptionPane.showMessageDialog(null, "Por favor, seleccione un evento de la lista.");

            return;

        }

        EventoSeguridad ev = listaEventos.get(index);

        if (ev instanceof EventoIntrusion) {

            ((EventoIntrusion) ev).GenerarReporteDetallado(d3ID, d3Am, d3De, d3Fe, d3In, d3Pto, d3Niv, d3Ev, d3Per);

            cambiarPestana(pestañas, panelIntrusion);

        } else {

            ev.GenerarReporteDetallado(d2ID, d2Am, d2Niv, d2Ev, d2Fe, d2De);

            cambiarPestana(pestañas, panelSimple);

        }

    }



    // =================================================================

    // 5. ACCIONES EN CONSULTAR INFORMACIÓN

    // =================================================================

    

    public void gestionarAnalizarImpacto(JList<String> jListConsulta) {

        int index = jListConsulta.getSelectedIndex();

        if (index == -1) {

            JOptionPane.showMessageDialog(null, "Seleccione un evento.", "Atencion", JOptionPane.WARNING_MESSAGE); return;

        }

        listaEventos.get(index).analizarImpacto(); 

    }



    public void gestionarBloquearIP(JList<String> jListConsulta) {

        int index = jListConsulta.getSelectedIndex();

        if (index == -1) {

            JOptionPane.showMessageDialog(null, "Seleccione un evento.", "Atencion", JOptionPane.WARNING_MESSAGE); return;

        }

        EventoSeguridad ev = listaEventos.get(index);

        if (ev instanceof EventoIntrusion) {

            ((EventoIntrusion) ev).bloquearPuntoAcceso(); 

        } else {

            JOptionPane.showMessageDialog(null, "Bloquear IP solo aplica a Intrusiones.", "Error", JOptionPane.ERROR_MESSAGE);

        }

    }



    public void gestionarAislarSistema(JList<String> jListConsulta) {

        int index = jListConsulta.getSelectedIndex();

        if (index == -1) {

            JOptionPane.showMessageDialog(null, "Seleccione un evento.", "Atencion", JOptionPane.WARNING_MESSAGE); return;

        }

        EventoSeguridad ev = listaEventos.get(index);

        if (ev instanceof EventoIntrusion) {

            if(JOptionPane.showConfirmDialog(null, "¿Aislar sistema del evento " + ev.getIdEvento() + "?", "Confirmar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){

                 JOptionPane.showMessageDialog(null, "SISTEMA AISLADO.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

            }

        } else {

            JOptionPane.showMessageDialog(null, "Aislar Sistema solo aplica a Intrusiones.", "Info", JOptionPane.INFORMATION_MESSAGE);

        }

    }

    

    // Tabla (Si la llegas a usar, pero ahora usamos Listas)

    public void llenarTablaConsulta(JTable tabla) {

         String[] columnas = {"ID", "Tipo", "Nivel", "Fecha", "Clase"};

         DefaultTableModel dtm = new DefaultTableModel(columnas, 0) {

             @Override public boolean isCellEditable(int row, int column) { return false; }

         };

         for(EventoSeguridad ev : listaEventos) {

             String clase = (ev instanceof EventoIntrusion) ? "Intrusión" : "Seguridad";

             Object[] fila = {ev.getIdEvento(), ev.getTipoAmenaza(), ev.getNivelRiesgo(), ev.getFechaDeteccion(), clase};

             dtm.addRow(fila);

         }

         tabla.setModel(dtm);

    }

}


