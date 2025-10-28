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
    private JButton btnVerDetalle;
    private DefaultTableModel modeloTabla;


    public VentanaPrincipal() {
        setContentPane(panel1);
        setTitle("Mis películas" + AppContext.getInstance().getUsuarioActual().getEmail());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setVisible(true);

        setLocationRelativeTo(null);


        btnAñadir.setText("Añadir");
        btnEliminar.setText("Eliminar");
        btnCerrar.setText("Cerrar Sesión");

        //Creación de la tabla
        String[] columnas = {"ID", "Título", "Año", "Director", "Género"};
        modeloTabla = new DefaultTableModel(columnas, 0); // 0 filas iniciales
        table1.setModel(modeloTabla);

        cargarPeliculas();

        btnCerrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AppController.getInstance().cerrarSesion();
                new VentanaLogin().setVisible(true);
                dispose();
            }
        });

        btnAñadir.addActionListener(e -> {
                    //Creamos y mostramos la ventana de detalle/añadir
                    VentanaAñadir dialogoAnadir = new VentanaAñadir(VentanaPrincipal.this);
                    dialogoAnadir.setVisible(true);

                    // Después de que la ventana se cierre, recargamos la tabla
                    cargarPeliculas();
                }
        );
        btnEliminar.addActionListener(e -> {
            //Obtenemos el índice de la fila seleccionada
            int filaSeleccionada = table1.getSelectedRow();

            //Verificamos si se seleccionó alguna fila
            if (filaSeleccionada == -1) {
                JOptionPane.showMessageDialog(this, "Por favor, selecciona una película para eliminar.", "Ninguna película seleccionada", JOptionPane.WARNING_MESSAGE);
                return; // Salimos del método si no hay nada seleccionado
            }

            // Pedimos confirmación
            int confirmacion = JOptionPane.showConfirmDialog(this, "¿Estás seguro de que quieres eliminar esta película?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

            if (confirmacion == JOptionPane.YES_OPTION) {
                //Obtenemos el ID de la película de la columna 0 de la fila seleccionada
                int idPelicula = (int) modeloTabla.getValueAt(filaSeleccionada, 0);

                //Llamamos al controlador
                AppController.getInstance().eliminarPelicula(idPelicula);

                //Recargamos la tabla para que se vea el cambio
                cargarPeliculas();
            }
        });

        btnVerDetalle.setText("Ver Detalle");

        btnVerDetalle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtenemos el índice de la fila seleccionada
                int filaSeleccionada = table1.getSelectedRow();

                // Verificamos si se seleccionó alguna fila
                if (filaSeleccionada == -1) {
                    JOptionPane.showMessageDialog(VentanaPrincipal.this,
                            "Por favor, selecciona una película para ver el detalle.",
                            "Ninguna película seleccionada",
                            JOptionPane.WARNING_MESSAGE);
                    return; // Salimos del método si no hay nada seleccionado
                }

                // Obtenemos el ID de la película (columna 0)
                int idPelicula = (int) modeloTabla.getValueAt(filaSeleccionada, 0);

                // Pedimos al controlador la película completa
                Pelicula peliculaCompleta = AppController.getInstance().getPeliculaById(idPelicula);

                if (peliculaCompleta != null) {
                    // Creamos y mostramos la ventana de detalle
                    Detalle ventanaDetalle = new Detalle(VentanaPrincipal.this, peliculaCompleta);
                    ventanaDetalle.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(VentanaPrincipal.this,
                            "No se pudo encontrar la película seleccionada.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });


    }
        private void cargarPeliculas () {
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