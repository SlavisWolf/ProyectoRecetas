package com.recetas.aplicacion.aplicacionrecetas.BD.Repositorios;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.recetas.aplicacion.aplicacionrecetas.BD.Ayudante;
import com.recetas.aplicacion.aplicacionrecetas.Pojo.Usuario;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by anton on 03/05/2017.
 */

public class RepositorioUsuarios {

    private Dao<Usuario, Integer> usuarioDao;


    public RepositorioUsuarios(Ayudante ayudante) {
        try {
            this.usuarioDao = ayudante.getUsuarioDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Usuario> leerUsuarios() {
        try {
            return usuarioDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Usuario leerUsuarioId(int id) {
        try {
            return usuarioDao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Usuario leerUsuarioLogin(String correo,String pass) {

           QueryBuilder<Usuario, Integer> query  = usuarioDao.queryBuilder();
        try {
            query.where().eq(Usuario.CORREO, correo).and().eq(Usuario.PASS,pass);
            return query.queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Usuario leerUsuarioPorCorreo(String correo) {

        QueryBuilder<Usuario, Integer> query  = usuarioDao.queryBuilder();
        try {
            query.where().eq(Usuario.CORREO, correo);
            return query.queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void crearUsuario(Usuario user) {
        try {
            usuarioDao.create(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public  void actualizarUsuario(Usuario user) {
        try {
            usuarioDao.update(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
