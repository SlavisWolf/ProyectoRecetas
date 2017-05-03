package com.recetas.aplicacion.aplicacionrecetas.Pojo;

import java.util.Date;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
/**
 * Created by anton on 01/05/2017.
 */
@DatabaseTable
public class Usuario {

    //COLUMNAS
    public static final String ID ="_id";
    public static final String NOMBRE ="nombre";
    public static final String TELEFONO ="telefono";
    public static final String CORREO ="correo";
    public static final String FECHA_REGISTRO ="fechaRegistro";
    public static final String AVATAR ="avatar";


    @DatabaseField(generatedId = true, columnName = Usuario.ID)
    private int id;
    @DatabaseField(columnName = Usuario.NOMBRE)
    private String nombre;
    @DatabaseField(columnName = Usuario.TELEFONO)
    private String telefono;
    @DatabaseField(columnName = Usuario.CORREO)
    private String correo;
    @DatabaseField(columnName = Usuario.FECHA_REGISTRO)
    private Date fechaRegistro;
    @DatabaseField(columnName = Usuario.AVATAR)
    private String avatar;


    public Usuario() {
    }

    public Usuario(String nombre, String telefono, String correo, Date fechaRegistro, String avatar) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.correo = correo;
        this.fechaRegistro = fechaRegistro;
        this.avatar = avatar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
