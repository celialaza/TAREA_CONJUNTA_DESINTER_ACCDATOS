package org.example.controller;


import org.example.model.GestorDatos;
import org.example.model.Pelicula;
import org.example.model.Usuario;
import org.example.session.AppContext;

import java.util.ArrayList;
import java.util.List;


/**
 * Controlador principal de la aplicación.
 * Orquesta la lógica de negocio y la interacción entre el modelo y la vista.
 * Utiliza un patrón Singleton para ser accesible desde cualquier parte.
 */
public class AppController {

    private static AppController instance;
    private final GestorDatos gestorDatos;
    private final List<Usuario> usuarios;
    private final List<Pelicula> peliculas;

    // variable para la ruta del archivo peliculas.csv
    private final String RUTA_PELICULAS_CSV = "peliculas.csv";

    /**
     * Constructor privado. Se encarga de inicializar el gestor de datos
     * y cargar toda la información de los CSV en memoria.
     */
    private AppController() {
        this.gestorDatos = new GestorDatos();
        // Asegúrate de poner las rutas correctas a tus archivos CSV
        this.usuarios = gestorDatos.cargarUsuarios("usuarios.csv");
        this.peliculas = gestorDatos.cargarPeliculas(RUTA_PELICULAS_CSV);
    }

    /**
     * Devuelve la instancia única del controlador (Singleton).
     */
    public static AppController getInstance() {
        if (instance == null) {
            instance = new AppController();
        }
        return instance;
    }

    /**
     * Valida las credenciales de un usuario.
     *
     * @param email      El email introducido por el usuario.
     * @param password La contraseña introducida.
     * @return true si las credenciales son correctas, false en caso contrario.
     */
    public boolean iniciarSesion(String email, String password) {
        for (Usuario usuario : usuarios) {
            if (usuario.getEmail().equals(email) && usuario.getPassword().equals(password)) {
                // Si las credenciales son correctas, guardamos el usuario en la sesión.
                AppContext.getInstance().setUsuarioActual(usuario);
                return true;
            }
        }
        return false; // No se encontró el usuario o la contraseña es incorrecta
    }

    /**
     * Cierra la sesión del usuario actual.
     */
    public void cerrarSesion() {
        AppContext.getInstance().cerrarSesion();
    }

    /**
     * Obtiene la lista de películas que pertenecen al usuario que ha iniciado sesión.
     *
     * @return Una lista de objetos Pelicula. Si no hay usuario, devuelve una lista vacía.
     */
    public List<Pelicula> getPeliculasUsuarioActual() {
        Usuario usuarioActual = AppContext.getInstance().getUsuarioActual();
        if (usuarioActual == null) {
            return new ArrayList<>(); // Devuelve lista vacía si nadie ha iniciado sesión
        }

        //Creamos una nueva lista vacía para almacenar los resultados
        List<Pelicula> peliculasDelUsuario = new ArrayList<>();

        //Iteramos sobre la lista completa de películas
        for (Pelicula pelicula : peliculas) {

            //Aplicamos la condición del filtro
            if (pelicula.getIdUsuario() == usuarioActual.getId()) {
                peliculasDelUsuario.add(pelicula);
            }
        }
        return peliculasDelUsuario;

    }
    /**
     * Añade una nueva película a la colección del usuario actual.
     * Por ahora, los cambios solo se guardan en memoria.
     */
    public void añadirPelicula(String titulo, int año, String director, String descripcion, String genero) {

        int maxId = 0;

        // Iteramos sobre la lista para encontrar el ID más alto
        for (Pelicula p : this.peliculas) {
            if (p.getId() > maxId) {
                maxId = p.getId();
            }
        }
        int nuevoId = maxId + 1;

        int idUsuario = AppContext.getInstance().getUsuarioActual().getId();
        Pelicula nuevaPelicula = new Pelicula(nuevoId, titulo, año, director, descripcion, genero, "url_imagen_default", idUsuario);
        this.peliculas.add(nuevaPelicula);

        //Llamada para guardar la película en el archivo CSV
        gestorDatos.guardarPeliculas(this.peliculas, RUTA_PELICULAS_CSV);
    }


    /**
     * Elimina una película de la lista basándose en su ID.
     * @param idPelicula El ID de la película a eliminar.
     */
    public void eliminarPelicula(int idPelicula) {
        peliculas.removeIf(pelicula -> pelicula.getId() == idPelicula);
        gestorDatos.guardarPeliculas(this.peliculas, RUTA_PELICULAS_CSV);

    }
}