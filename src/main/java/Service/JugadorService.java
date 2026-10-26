/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import Dao.JugadorDAO;
import Model.Jugador;
import java.io.IOException;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author eliana
 */
public class JugadorService {

    private final JugadorDAO dao = new JugadorDAO();

    public List<Jugador> obtenerJugadores() throws IOException {
        return dao.display();
    }

    public void eliminarJugador(int index) throws Exception {
        if (index < 0) {
            throw new IllegalArgumentException("Debe seleccionar una fila válida.");
        }
        dao.delete(index);
    }

    public void actualizarJugador(int index, Jugador nuevo, Jugador actual) throws Exception {
        if (nuevo.getNombre() == null || nuevo.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        dao.update(index, nuevo);
    }

    public void crearJugador(Jugador jugador) throws Exception {
        if (jugador.getNombre() == null || jugador.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        dao.create(jugador);
    }

    public void cargarTabla(JTable jTable1) {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);

        try {
            List<Jugador> jugadores = obtenerJugadores();
            for (Jugador j : jugadores) {
                model.addRow(new Object[]{
                    j.getId(),
                    j.getNombre(),
                    j.getPosicion(),
                    j.getEdad(),
                    j.getEquipo()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al cargar los datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void processDelete(JTable jTable1) {
        int selectedRow = jTable1.getSelectedRow();

        // Validar si el usuario seleccionó una fila
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Por favor, seleccione un jugador de la tabla primero.", "Sin Selección", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(null, "¿Desea eliminar el jugador seleccionado?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                eliminarJugador(selectedRow);
                cargarTabla(jTable1);
                JOptionPane.showMessageDialog(null, "Jugador eliminado con éxito.");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error al eliminar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void processUpdate(JTable jTable1) {
        int selectedRow = jTable1.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Please select a player from the table first.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String idActual = jTable1.getValueAt(selectedRow, 0).toString();
        String nombreActual = jTable1.getValueAt(selectedRow, 1).toString();
        String posicionActual = jTable1.getValueAt(selectedRow, 2).toString();
        String edadActual = jTable1.getValueAt(selectedRow, 3).toString();
        String equipoActual = jTable1.getValueAt(selectedRow, 4).toString();

        // 3. Crear formulario gráfico con los 5 datos
        JTextField txtId = new JTextField(idActual);
        JTextField txtNombre = new JTextField(nombreActual);
        JTextField txtPosicion = new JTextField(posicionActual);
        JTextField txtEdad = new JTextField(edadActual);
        JTextField txtEquipo = new JTextField(equipoActual);

        JPanel panelForm = new JPanel(new java.awt.GridLayout(0, 1, 4, 4));
        panelForm.add(new JLabel("ID:"));
        panelForm.add(txtId);
        panelForm.add(new JLabel("Name:"));
        panelForm.add(txtNombre);
        panelForm.add(new JLabel("Position:"));
        panelForm.add(txtPosicion);
        panelForm.add(new JLabel("Age:"));
        panelForm.add(txtEdad);
        panelForm.add(new JLabel("Team:"));
        panelForm.add(txtEquipo);

        int result = JOptionPane.showConfirmDialog(null, panelForm, "Update Player #" + idActual, JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                // Construir los objetos Jugador con las 5 propiedades
                Jugador jugadorNuevo = new Jugador(
                        Integer.parseInt(txtId.getText().trim()),
                        txtNombre.getText().trim(),
                        txtPosicion.getText().trim(),
                        Integer.parseInt(txtEdad.getText().trim()),
                        txtEquipo.getText().trim()
                );

                Jugador jugadorActual = new Jugador(
                        Integer.parseInt(idActual),
                        nombreActual,
                        posicionActual,
                        Integer.parseInt(edadActual),
                        equipoActual
                );
                this.actualizarJugador(selectedRow, jugadorNuevo, jugadorActual);
                this.cargarTabla(jTable1);

                JOptionPane.showMessageDialog(null, "Player updated successfully.");

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error updating: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void processSearch(JTextField txtIndex, JTable jTable1) {
        String input = txtIndex.getText().trim();

        if (input.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter an index to search.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int index = Integer.parseInt(input) -1;
            List<Jugador> jugadores = this.obtenerJugadores();

            if (index < 0 || index >= jugadores.size()) {
                JOptionPane.showMessageDialog(null, "Player not found at index: " + index, "Not Found", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Jugador jugador = jugadores.get(index);

            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0);

            model.addRow(new Object[]{
                jugador.getId(),
                jugador.getNombre(),
                jugador.getPosicion(),
                jugador.getEdad(),
                jugador.getEquipo()
            });

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "The index must be a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error searching: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void processCreate(JTextField txtId, JTextField txtNombre, JTextField txtPosicion, JTextField txtEdad, JTextField txtEquipo) {
        String idStr = txtId.getText().trim();
        String nombre = txtNombre.getText().trim();
        String posicion = txtPosicion.getText().trim();
        String edadStr = txtEdad.getText().trim();
        String equipo = txtEquipo.getText().trim();

        if (idStr.isEmpty() || nombre.isEmpty() || posicion.isEmpty() || edadStr.isEmpty() || equipo.isEmpty()) {
            JOptionPane.showMessageDialog(null, "All fields are required.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int id = Integer.parseInt(idStr);
            int edad = Integer.parseInt(edadStr);

            Jugador nuevoJugador = new Jugador(id, nombre, posicion, edad, equipo);

            this.dao.create(nuevoJugador);

            JOptionPane.showMessageDialog(null, "Player created successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);

            txtId.setText("");
            txtNombre.setText("");
            txtPosicion.setText("");
            txtEdad.setText("");
            txtEquipo.setText("");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "ID and Age must be valid numbers.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error creating player: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void processExit() {
        int confirmacion = javax.swing.JOptionPane.showConfirmDialog(null, "Are you sure you want to leave?", "Confirm Departure", javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE);

        if (confirmacion == javax.swing.JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    public void refresh(JTable jTable1) {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0); // Vacía la tabla actual para no duplicar datos

        try {
            List<Jugador> jugadores = this.obtenerJugadores(); // Vuelve a leer la lista desde el JSON

            for (Jugador j : jugadores) {
                model.addRow(new Object[]{
                    j.getId(),
                    j.getNombre(),
                    j.getPosicion(),
                    j.getEdad(),
                    j.getEquipo()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error reloading data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
