package com.recetas.aplicacion.aplicacionrecetas.BD.Repositorios;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.recetas.aplicacion.aplicacionrecetas.BD.Ayudante;
import com.recetas.aplicacion.aplicacionrecetas.Pojo.Comentario;
import com.recetas.aplicacion.aplicacionrecetas.Pojo.Receta;
import com.recetas.aplicacion.aplicacionrecetas.Pojo.Usuario;
import com.recetas.aplicacion.aplicacionrecetas.Pojo.Valoracion;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by anton on 03/05/2017.
 */

public class RepositorioRecetas {

    private Dao<Receta, Integer> recetaDao;
    private Dao<Comentario, Integer> comentarioDao;
    private Dao<Valoracion, Integer> valoracionDao;

    public RepositorioRecetas(Ayudante ayudante) {
        try {
            this.recetaDao = ayudante.getRecetaDao();
            this.comentarioDao = ayudante.getComentarioDao();
            this.valoracionDao = ayudante.getValoracionDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Receta> leeRecetas() {
        try {
            QueryBuilder query  = recetaDao.queryBuilder();
            query.orderBy(Receta.FECHA_PUBLICACION,false); // descendente
            List<Receta> recetas = query.query();

            for (Receta r : recetas) { // le asignamos los comentarios a las recetas
                QueryBuilder queryComentarios = comentarioDao.queryBuilder();
                queryComentarios.where().eq(Comentario.RECETA,r);
                List<Comentario> comentarios = queryComentarios.query();
                r.setComentarios(comentarios);
            }
            return recetas;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Receta leerRecetaId(int id) {
        try {
            Receta resultado = recetaDao.queryForId(id);
            QueryBuilder queryComentarios = comentarioDao.queryBuilder();
            queryComentarios.where().eq(Comentario.RECETA,resultado);
            List<Comentario> comentarios = queryComentarios.query();
            resultado.setComentarios(comentarios);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void crearReceta(Receta rec) {
        try {
            recetaDao.create(rec);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public  void actualizarUsuario(Receta rec) {
        try {
            recetaDao.update(rec);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
