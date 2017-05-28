package com.recetas.aplicacion.aplicacionrecetas.Pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
/**
 * Created by anton on 01/05/2017.
 */
@DatabaseTable
public class Usuario implements Parcelable{

    //COLUMNAS
    public static final String ID ="_id";
    public static final String NOMBRE ="nombre";
    public static final String TELEFONO ="telefono";
    public static final String CORREO ="correo";
    public static final String FECHA_REGISTRO ="fechaRegistro";
    public static final String AVATAR ="avatar";
    public static final String PASS ="pass";


    @DatabaseField(generatedId = true, columnName = Usuario.ID)
    private int id;
    @DatabaseField(columnName = Usuario.NOMBRE)
    private String nombre;
    @DatabaseField(columnName = Usuario.TELEFONO)
    private String telefono;
    @DatabaseField(columnName = Usuario.CORREO, unique = true,canBeNull = false)
    private String correo; // login
    @DatabaseField(columnName = Usuario.PASS)
    private String pass; // login
    @DatabaseField(columnName = Usuario.FECHA_REGISTRO)
    private Date fechaRegistro;
    @DatabaseField(columnName = Usuario.AVATAR)
    private String avatar;


    public Usuario() {
    }

    public Usuario(String nombre, String telefono, String correo, String pass, Date fechaRegistro, String avatar) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.correo = correo;
        this.pass = pass;
        this.fechaRegistro = fechaRegistro;
        this.avatar = avatar;
    }

    public Usuario(String nombre, String telefono, String correo, String pass, Date fechaRegistro) {
        this(nombre,telefono,correo,pass,fechaRegistro,"");
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

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(nombre);
        dest.writeString(telefono);
        dest.writeString(correo);
        dest.writeString(pass);
        System.out.println(fechaRegistro);
        dest.writeLong(fechaRegistro.getTime() );
        dest.writeString(avatar);
    }

    protected Usuario(Parcel in) {
        id = in.readInt();
        nombre = in.readString();
        telefono = in.readString();
        correo = in.readString();
        pass = in.readString();
        avatar = in.readString();
    }

    public static final Creator<Usuario> CREATOR = new Creator<Usuario>() {
        @Override
        public Usuario createFromParcel(Parcel in) {
            return new Usuario(in);
        }

        @Override
        public Usuario[] newArray(int size) {
            return new Usuario[size];
        }
    };

}
