package org.example.view;

import org.example.controller.AppController;
import org.example.model.Pelicula;
import org.example.session.AppContext;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class VentanaPrincipal extends JFrame {
    private JPanel panel1;
    private JTable table1;
    private JButton btnEliminar;
    private JButton btnCerrar;
    private JButton btnAñadir;
    private DefaultTableModel modeloTabla;




    public  VentanaPrincipal() {
        setContentPane(panel1);
        setTitle("Mis películas" + AppContext.getInstance().getUsuarioActual().getEmail());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setVisible(true);

        setLocationRelativeTo(null);


        btnAñadir.setText("Añadir");
        btnEliminar.setText("Eliminar");
        btnCerrar.setText("Cerrar Sesión");

        // 2. Creación de la tabla
        String[] columnas = {"ID", "Título", "Año", "Director", "Descripción"};
        modeloTabla = new DefaultTableModel(columnas, 0); // 0 filas iniciales
        table1.setModel(modeloTabla);

        cargarPeliculas();

        btnCerrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AppController.getInstance().cerrarSesion();
                new VentanaLogin().setVisible(true);
                dispose(); // Cierra la ventana actual
            }
        });
        // Lógica para los otros botones (la añadiremos más adelante)
        btnAñadir.addActionListener(e ->{
                    // 1. Creamos y mostramos la ventana de detalle/añadir
                    VentanaAñadir dialogoAnadir = new VentanaAñadir(VentanaPrincipal.this);
                    dialogoAnadir.setVisible(true);

                    // 2. Después de que la ventana se cierre, recargamos la tabla
                    // El código se detiene aquí hasta que se cierra el JDialog modal
                    cargarPeliculas();
                }
                );
        btnEliminar.addActionListener(e -> {
            // 1. Obtenemos el índice de la fila seleccionada
            int filaSeleccionada = table1.getSelectedRow();

            // 2. Verificamos si se seleccionó alguna fila
            if (filaSeleccionada == -1) {
                JOptionPane.showMessageDialog(this, "Por favor, selecciona una película para eliminar.", "Ninguna película seleccionada", JOptionPane.WARNING_MESSAGE);
                return; // Salimos del método si no hay nada seleccionado
            }

            // 3. Pedimos confirmación
            int confirmacion = JOptionPane.showConfirmDialog(this, "¿Estás seguro de que quieres eliminar esta película?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

            if (confirmacion == JOptionPane.YES_OPTION) {
                // 4. Obtenemos el ID de la película de la columna 0 de la fila seleccionada
                int idPelicula = (int) modeloTabla.getValueAt(filaSeleccionada, 0);

                // 5. Llamamos al controlador
                AppController.getInstance().eliminarPelicula(idPelicula);

                // 6. Recargamos la tabla para que se vea el cambio
                cargarPeliculas();
            }
        });

    }
        private void cargarPeliculas() {
            // Limpiamos la tabla por si tenía datos de antes
            modeloTabla.setRowCount(0);

            List<Pelicula> peliculas = AppController.getInstance().getPeliculasUsuarioActual();
            for (Pelicula p : peliculas) {
                Object[] fila = {
                        p.getId(),
                        p.getTitulo(),
                        p.getAnio(),
                        p.getDirector(),
                        p.getGenero()
                };
                modeloTabla.addRow(fila);
            }

    }
}
