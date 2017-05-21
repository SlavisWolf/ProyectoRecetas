package com.recetas.aplicacion.aplicacionrecetas.Pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by anton on 02/05/2017.
 */
@DatabaseTable
public class Receta implements Parcelable {

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
   // @DatabaseField(columnName = Receta.INGREDIENTES,dataType = DataType.SERIALIZABLE)
   // private ArrayList<String> ingredientes;
    @DatabaseField(columnName = Receta.FECHA_PUBLICACION)
    private Date fechaPublicacion;
    @DatabaseField(columnName = Receta.IMAGEN)
    private String imagen;
    @DatabaseField(columnName = Receta.USUARIO,foreign = true)
    private Usuario usuario;


    private List<Comentario> comentarios;

    public Receta(String titulo, String descripcion, Date fechaPublicacion, String imagen, Usuario usuario) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        //this.ingredientes = ingredientes;
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

    /*public ArrayList<String> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(ArrayList<String> ingredientes) {
        this.ingredientes = ingredientes;
    }*/

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

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(titulo);
        dest.writeString(descripcion);
        dest.writeLong(fechaPublicacion.getTime() );
        dest.writeString(imagen);
        dest.writeParcelable(usuario,flags);
    }

    protected Receta(Parcel in) {
        id = in.readInt();
        titulo = in.readString();
        descripcion = in.readString();
        fechaPublicacion =  new Date(in.readLong());
        imagen = in.readString();
        usuario = in.readParcelable(Usuario.class.getClassLoader());
    }

    public static final Creator<Receta> CREATOR = new Creator<Receta>() {
        @Override
        public Receta createFromParcel(Parcel in) {
            return new Receta(in);
        }

        @Override
        public Receta[] newArray(int size) {
            return new Receta[size];
        }
    };
}
