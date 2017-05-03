package com.recetas.aplicacion.aplicacionrecetas.Pojo;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by anton on 02/05/2017.
 */
@DatabaseTable
public class Receta {

    // Columnas
    public static final String ID ="_id";
    public static final String TITULO ="titulo";
    public static final String DESCRIPCION ="descripcion";
    public static final String INGREDIENTES ="ingredientes";
    public static final String FECHA_PUBLICACION ="fechaPublicacion";
    public static final String IMAGEN ="imagen";
    public static final String USUARIO ="usuario";

    @DatabaseField(generatedId = true, columnName = Receta.ID)
    private int id;
    @DatabaseField(columnName = Receta.TITULO)
    private String titulo;
    @DatabaseField(columnName = Receta.DESCRIPCION)
    private String descripcion;
    @DatabaseField(columnName = Receta.INGREDIENTES,dataType = DataType.SERIALIZABLE)
    private ArrayList<String> ingredientes;
    @DatabaseField(columnName = Receta.FECHA_PUBLICACION)
    private Date fechaPublicacion;
    @DatabaseField(columnName = Receta.IMAGEN)
    private String imagen;
    @DatabaseField(columnName = Receta.USUARIO,foreign = true)
    private Usuario usuario;


    public Receta(String titulo, String descripcion, ArrayList<String> ingredientes, Date fechaPublicacion, String imagen, Usuario usuario) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.ingredientes = ingredientes;
        this.fechaPublicacion = fechaPublicacion;
        this.imagen = imagen;
        this.usuario = usuario;
    }

    public Receta() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public ArrayList<String> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(ArrayList<String> ingredientes) {
        this.ingredientes = ingredientes;
    }

    public Date getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(Date fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
