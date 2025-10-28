
package org.example.view;

import org.example.model.Pelicula;

import javax.swing.*;
import java.awt.event.*;

    public class Detalle extends JDialog {
        private JPanel contentPane;
        private JButton buttonOK;
        private JButton buttonCancel;
        private JTextField tfTitulo;
        private JTextField tfAño;
        private JTextField tfDirector;
        private JTextField tfGenero;
        private JTextArea taDescripcion;
        private JLabel lblTitulo;
        private JLabel lblAño;
        private JLabel lblDirector;
        private JLabel lblGenero;
        private JLabel lblDescripcion;


        /**
         * Constructor para la ventana de Detalle.
         *
         * @param owner    La ventana padre (VentanaPrincipal)
         * @param pelicula La película de la que se mostrarán los detalles.
         */
        public Detalle(JFrame owner, Pelicula pelicula) {
            super(owner, "Detalle de la Película", true);
            setContentPane(contentPane);
            setModal(true);
            getRootPane().setDefaultButton(buttonOK);
            setSize(500, 500); // Tamaño similar a VentanaAñadir
            setLocationRelativeTo(owner);

            // --- Inicializar Labels ---
            lblAño.setText("Año:");
            lblDirector.setText("Director:");
            lblGenero.setText("Género:");
            lblTitulo.setText("Título:");
            lblDescripcion.setText("Descripción:");

            buttonOK.setText("Cerrar");
            buttonCancel.setVisible(false);

            //Cargar los datos de la película
            cargarDatos(pelicula);

            buttonOK.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    onOK();
                }
            });

            // call onCancel() when cross is clicked
            setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    onCancel();
                }
            });

            // call onCancel() on ESCAPE
            contentPane.registerKeyboardAction(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    onCancel();
                }
            }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        }

        /**
         * Rellena los campos del formulario con los datos de la película
         * y los hace no editables.
         */
        private void cargarDatos(Pelicula pelicula) {
            if (pelicula != null) {
                tfTitulo.setText(pelicula.getTitulo());
                tfAño.setText(String.valueOf(pelicula.getAnio()));
                tfDirector.setText(pelicula.getDirector());
                tfGenero.setText(pelicula.getGenero());
                taDescripcion.setText(pelicula.getDescripcion());

                // Hacemos que los campos no se puedan editar
                tfTitulo.setEditable(false);
                tfAño.setEditable(false);
                tfDirector.setEditable(false);
                tfGenero.setEditable(false);
                taDescripcion.setEditable(false);

                //Para JTextArea, movemos el cursor al inicio
                taDescripcion.setCaretPosition(0);
            }
        }

        private void onOK() {
            // Simplemente cierra el diálogo
            dispose();
        }

        private void onCancel() {
            // Simplemente cierra el diálogo
            dispose();
        }

    }
