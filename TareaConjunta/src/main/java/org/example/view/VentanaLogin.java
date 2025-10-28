package org.example.view;

import org.example.controller.AppController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaLogin extends JFrame {
    private JButton btnInicio;
    private JTextField tfEmail;
    private JPasswordField tfPassword;
    private JLabel lblEmail;
    private JLabel lblPassword;
    private JPanel panel1;

    public VentanaLogin() {
        setTitle("Inicio de Sesión - Mis Películas");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        setContentPane(panel1);



        lblEmail.setText("Email:");

        lblPassword.setText("Password:");


        btnInicio.setText("Inicio Sesión");


        btnInicio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = tfEmail.getText();
                String contrasena = new String(tfPassword.getPassword());

                // Llamamos al Controlador para que haga la validación
                boolean loginExitoso = AppController.getInstance().iniciarSesion(email, contrasena);

                if (loginExitoso) {
                    // Si el login es correcto, abrimos la ventana principal
                    new VentanaPrincipal().setVisible(true);

                    dispose();
                } else {
                    // Si el login falla, mostramos un mensaje de error
                    JOptionPane.showMessageDialog(null, "Email o contraseña incorrectos.", "Error de Login", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

    }
}