package com.recetas.aplicacion.aplicacionrecetas.Pojo;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
/**
 * Created by anton on 01/05/2017.
 */
@DatabaseTable
public class Valoracion {

    //COLUMNAS
    public static final String RECETA ="receta";
    public static final String USUARIO ="usuario";
    public static final String RICO ="rico";


    //Codigos rico

    public static final int ME_GUSTA = 2;
    public static final int NO_ME_GUSTA = 1;
    public static final int NADA = 0;

    // Con uniqueCombo conseguimos que  la combinacion de los 2 valores sea única, lo que vendria siendo una clave primaria
    @DatabaseField(columnName = Valoracion.RECETA,foreign = true ,uniqueCombo = true,canBeNull = false)
    private Receta receta;
    @DatabaseField(columnName = Valoracion.USUARIO,foreign = true, uniqueCombo =  true,canBeNull = false)
    private Usuario usuario;
    @DatabaseField(columnName = Valoracion.RICO)
    private int rico;


    public Valoracion(Receta receta, Usuario usuario, int rico) {
        this.receta = receta;
        this.usuario = usuario;
        this.rico = rico;
    }

    public Valoracion(Receta receta, Usuario usuario) {
        this(receta,usuario,0);
    }

    public Valoracion() {
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

    public int getRico() {
        return rico;
    }

    public void setRico(int rico) {
        this.rico = rico;
    }
}
