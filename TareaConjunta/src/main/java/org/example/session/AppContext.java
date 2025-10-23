package org.example.session;


import org.example.model.Usuario;

/**
 * Gestiona el contexto o estado de la sesión de la aplicación.
 * Utiliza el patrón Singleton para garantizar una única instancia.
 * Su propósito es mantener al usuario que ha iniciado sesión.
 */

public class AppContext {

        //La única instancia de la clase (Singleton)
        private static AppContext instance;

        // El estado que queremos compartir: el usuario logueado
        private Usuario usuarioActual;


        // Constructor privado para evitar que se creen nuevas instancias desde fuera
        private AppContext() {

        }

        /**
         * Método estático para obtener la única instancia de la clase.
         * Si no existe, la crea. Si ya existe, devuelve la existente.
         * @return La instancia única de AppContext.
         */
        public static AppContext getInstance() {
            if (instance == null) {
                instance = new AppContext();
            }
            return instance;
        }


        /**
         * Guarda el usuario que ha iniciado sesión.
         * @param usuario El usuario que se ha logueado.
         */
        public void setUsuarioActual(Usuario usuario) {
            this.usuarioActual = usuario;
        }

        /**
         * Obtiene el usuario que está actualmente en la sesión.
         * @return El usuario logueado, o null si no hay nadie.
         */
        public Usuario getUsuarioActual() {
            return this.usuarioActual;
        }

        /**
         * Cierra la sesión, eliminando al usuario actual.
         */
        public void cerrarSesion() {
            this.usuarioActual = null;

        }




        }


