package org.example.view;

import org.example.controller.AppController;

import javax.swing.*;
import java.awt.event.*;

public class VentanaAñadir extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField tfTitulo;
    private JTextField tfAño;
    private JTextField tfDirector;
    private JTextArea taDescripcion;
    private JTextField tfGenero;
    private JLabel lblTitulo;
    private JLabel lblAño;
    private JLabel lblDirector;
    private JLabel lblDescripcion;
    private JLabel lblGenero;

    public VentanaAñadir(JFrame owner) {
        super(owner, "Añadir Nueva Película", true);
        setSize(400, 400);
        setLocationRelativeTo(owner);
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        lblAño.setText("Año");
        lblDirector.setText("Director");
        lblGenero.setText("Genero");
        lblTitulo.setText("Titulo");
        lblDescripcion.setText("Descripcion");

        buttonOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
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

    private void onOK() {
        if (tfTitulo.getText().isEmpty() || tfAño.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Título y Año son campos obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            String titulo = tfTitulo.getText();
            int año = Integer.parseInt(tfAño.getText());
            String director = tfDirector.getText();
            String genero = tfGenero.getText();
            String descripcion = taDescripcion.getText();

            // Llamamos al controlador para que haga la magia
            AppController.getInstance().añadirPelicula(titulo, año, director, genero, descripcion);

            // Avisamos al usuario y cerramos
            JOptionPane.showMessageDialog(this, "Película guardada con éxito.");
            dispose();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El año debe ser un número válido.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        }catch (Exception ex) { // <-- ¡ESTE ES EL CAMBIO IMPORTANTE!
            // Capturamos cualquier otro error que pueda ocurrir
            JOptionPane.showMessageDialog(this, "Ocurrió un error inesperado al guardar:\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            // Imprimimos el error completo en la consola para tener más detalles
            ex.printStackTrace();
        }
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

}
