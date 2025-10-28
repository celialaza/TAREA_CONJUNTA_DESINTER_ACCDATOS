package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Pelicula {
    private int id;
    private String titulo;
    private int anio;
    private String director;
    private String descripcion;
    private String genero;
    private String urlImagen;
    private int idUsuario;


}
