package org.example.model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Gestiona la carga de datos desde los archivos CSV.
 * Se encarga de leer los archivos y convertirlos en listas de objetos.
 */
public class GestorDatos {


        public List<Usuario> cargarUsuarios(String rutaArchivo) {
            List<Usuario> usuarios = new ArrayList<>();

            try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
                String linea;

                while ((linea = br.readLine()) != null) {
                    String[] datos = linea.split(",");

                    // Creamos un objeto Usuario con los datos leídos y lo añadimos a la lista
                    if (datos.length == 3) {
                        int id = Integer.parseInt(datos[0]);
                        String email = datos[1];
                        String password = datos[2];
                        usuarios.add(new Usuario(id, email, password));
                    }
                }
            } catch (IOException e) {
                System.err.println("Error al leer el archivo de usuarios: " + e.getMessage());
            }
            return usuarios;
        }

        public List<Pelicula> cargarPeliculas(String rutaArchivo) {
            List<Pelicula> peliculas = new ArrayList<>();
            try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
                String linea;
                br.readLine(); // Saltamos la cabecera del CSV

                while ((linea = br.readLine()) != null) {

                    String[] datos = linea.split(",", 8);

                    if (datos.length == 8) {
                        int id = Integer.parseInt(datos[0]);
                        String titulo = datos[1];
                        int anio = Integer.parseInt(datos[2]);
                        String director = datos[3];
                        String genero = datos[4];
                        String descripcion = datos[5];
                        String imagen = datos[6];
                        int idUsuario = Integer.parseInt(datos[7]);

                        peliculas.add(new Pelicula(id, titulo, anio, director, genero, descripcion, imagen, idUsuario));
                    }
                }
            } catch (IOException | NumberFormatException e) {
                System.err.println("Error al leer el archivo de películas: " + e.getMessage());
            }
            return peliculas;
        }
    /**
     * Guarda la lista completa de películas en el archivo CSV especificado.
     * Este método SOBRESCRIBE el archivo existente.
     * @param peliculas La lista de películas a guardar.
     * @param rutaArchivo La ruta del archivo "peliculas.csv".
     */
    public void guardarPeliculas(List<Pelicula> peliculas, String rutaArchivo) {
        String cabecera = "id,titulo,año,director,descripcion,genero,UrlImagen,id_usuario";

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaArchivo))) {
            bw.write(cabecera);
            bw.newLine();
            for (Pelicula pelicula : peliculas) {
                String linea = String.join(",",
                        String.valueOf(pelicula.getId()),
                        pelicula.getTitulo(),
                        String.valueOf(pelicula.getAnio()),
                        pelicula.getDirector(),
                        pelicula.getDescripcion(),
                        pelicula.getGenero(),
                        pelicula.getUrlImagen(),
                        String.valueOf(pelicula.getIdUsuario())
                );
                bw.write(linea);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error al guardar las películas en el archivo: " + e.getMessage());

        }
    }
    }

