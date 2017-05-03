package com.recetas.aplicacion.aplicacionrecetas.Pojo;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
/**
 * Created by anton on 01/05/2017.
 */
@DatabaseTable
public class Comentario {

    //COLUMNAS
    public static final String ID ="_id";
    public static final String RECETA ="receta";
    public static final String USUARIO ="usuario";
    public static final String TEXTO ="texto";

    @DatabaseField(generatedId = true, columnName = Comentario.ID)
    private int id;
    @DatabaseField(columnName = Comentario.RECETA,foreign = true)
    private Receta receta;
    @DatabaseField(columnName = Comentario.USUARIO,foreign = true)
    private Usuario usuario;
    @DatabaseField(columnName = Comentario.TEXTO)
    private String texto;

    public Comentario( Receta receta, Usuario usuario, String texto) {
        this.receta = receta;
        this.usuario = usuario;
        this.texto = texto;
    }

    public Comentario() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Receta getReceta() {
        return receta;
    }

    public void setReceta(Receta receta) {
        this.receta = receta;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
}
