package org.example;


import org.example.view.VentanaLogin;

import javax.swing.*;


public class Main {

            public static void main(String[] args) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        VentanaLogin ventanaLogin = new VentanaLogin();
                        ventanaLogin.setVisible(true);
                    }
                });
            }
        }
