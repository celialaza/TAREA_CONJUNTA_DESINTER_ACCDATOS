package org.example;


import org.example.view.VentanaLogin;

import javax.swing.*;

/**
 * Clase principal que inicia la aplicación.
 * Su única responsabilidad es crear y mostrar la primera ventana.
 */

public class Main {

            public static void main(String[] args) {
                // Se asegura de que la creación de la interfaz gráfica
                // se ejecute en el hilo de despacho de eventos de Swing (EDT).
                // Esta es la forma correcta y segura de iniciar una aplicación Swing.
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        // Crea una instancia de nuestra VentanaLogin
                        VentanaLogin ventanaLogin = new VentanaLogin();
                        // Y la hace visible
                        ventanaLogin.setVisible(true);
                    }
                });
            }
        }
