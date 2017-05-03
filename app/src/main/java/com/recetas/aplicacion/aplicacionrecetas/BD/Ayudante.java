package com.recetas.aplicacion.aplicacionrecetas.BD;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.recetas.aplicacion.aplicacionrecetas.Pojo.Comentario;
import com.recetas.aplicacion.aplicacionrecetas.Pojo.Receta;
import com.recetas.aplicacion.aplicacionrecetas.Pojo.Usuario;
import com.recetas.aplicacion.aplicacionrecetas.Pojo.Valoracion;

/**
 * Created by anton on 01/05/2017.
 */

public class Ayudante extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "recetas_app_ormlite.db";
    private static final int DATABASE_VERSION = 1;

    private Dao<Usuario, Integer> usuarioDao;
    private Dao<Receta, Integer> RecetaDao;

    private Dao<Comentario, Integer> comentarioDao;
    private Dao<Valoracion, Integer> valoracionDao;

    public Ayudante(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Usuario.class);
            TableUtils.createTable(connectionSource, Receta.class);
            TableUtils.createTable(connectionSource, Comentario.class);
            TableUtils.createTable(connectionSource, Valoracion.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {

    }

    @Override
    public void close() {
        super.close();
        usuarioDao = null;
    }

    //DAOS

    public Dao<Usuario, Integer> getUsuarioDao() throws SQLException {
        if (usuarioDao == null) {
            usuarioDao = getDao(Usuario.class);
        }
        return usuarioDao;
    }


    public Dao<Receta, Integer> getRecetaDao() throws SQLException {
        if (RecetaDao == null) {
            RecetaDao = getDao(Receta.class);
        }
        return RecetaDao;
    }

    public Dao<Comentario, Integer> getComentarioDao() throws SQLException {
        if (comentarioDao == null) {
            comentarioDao = getDao(Comentario.class);
        }
        return comentarioDao;
    }

    public Dao<Valoracion, Integer> getValoracionDao() throws SQLException {
        if (valoracionDao == null) {
            valoracionDao = getDao(Valoracion.class);
        }
        return valoracionDao;
    }
}
