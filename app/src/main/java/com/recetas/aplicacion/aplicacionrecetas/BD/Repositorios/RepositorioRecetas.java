package com.recetas.aplicacion.aplicacionrecetas.BD.Repositorios;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.recetas.aplicacion.aplicacionrecetas.BD.Ayudante;
import com.recetas.aplicacion.aplicacionrecetas.Pojo.Comentario;
import com.recetas.aplicacion.aplicacionrecetas.Pojo.Receta;
import com.recetas.aplicacion.aplicacionrecetas.Pojo.Usuario;
import com.recetas.aplicacion.aplicacionrecetas.Pojo.Valoracion;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by anton on 03/05/2017.
 */

public class RepositorioRecetas {

    private Dao<Receta, Integer> recetaDao;
    private Dao<Comentario, Integer> comentarioDao;
    private Dao<Valoracion, Integer> valoracionDao;
    private Dao<Usuario, Integer> usuarioDao;

    public RepositorioRecetas(Ayudante ayudante) {
        try {
            this.recetaDao = ayudante.getRecetaDao();
            this.comentarioDao = ayudante.getComentarioDao();
            this.valoracionDao = ayudante.getValoracionDao();
            this.usuarioDao = ayudante.getUsuarioDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Receta> leerRecetas() {
        try {
            QueryBuilder query  = recetaDao.queryBuilder();
            query.orderBy(Receta.FECHA_PUBLICACION,false); // descendente
            List<Receta> recetas = query.query();

            for (Receta r : recetas) { // le asignamos los comentarios a las recetas
                QueryBuilder queryComentarios = comentarioDao.queryBuilder();
                queryComentarios.where().eq(Comentario.RECETA,r);
                List<Comentario> comentarios = queryComentarios.query();
                r.setComentarios(comentarios);
                r.setUsuario(usuarioDao.queryForId(r.getUsuario().getId()));
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
            resultado.setUsuario(usuarioDao.queryForId(resultado.getUsuario().getId()));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Receta> leerRecetasUsuario(Usuario u) {
        try {
            QueryBuilder query  = recetaDao.queryBuilder();
            query.where().eq(Receta.USUARIO,u);
            query.orderBy(Receta.FECHA_PUBLICACION,false); // descendente
            List<Receta> recetas = query.query();

            for (Receta r : recetas) { // le asignamos los comentarios a las recetas
                r.setUsuario(usuarioDao.queryForId(r.getUsuario().getId()));
            }
            return recetas;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Receta> leerRecetasDistintasUsuario(Usuario u) {
        try {
            QueryBuilder query  = recetaDao.queryBuilder();
            query.where().not().eq(Receta.USUARIO,u);
            query.orderBy(Receta.FECHA_PUBLICACION,false); // descendente
            List<Receta> recetas = query.query();

            for (Receta r : recetas) { // le asignamos los comentarios a las recetas
                r.setUsuario(usuarioDao.queryForId(r.getUsuario().getId()));
            }
            return recetas;
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

    public  void actualizarReceta(Receta rec) {
        try {
            recetaDao.update(rec);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public long numeroLikesReceta(Receta receta) {
        QueryBuilder queryValoracion = valoracionDao.queryBuilder();
        try {
            return queryValoracion.where().eq(Valoracion.RECETA,receta).and().eq(Valoracion.RICO,Valoracion.ME_GUSTA).countOf();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public long numeroDislikesReceta(Receta receta) {
        QueryBuilder queryValoracion = valoracionDao.queryBuilder();
        try {
            return queryValoracion.where().eq(Valoracion.RECETA,receta).and().eq(Valoracion.RICO,Valoracion.NO_ME_GUSTA).countOf();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public Valoracion obtenerValoracionRecetaConUsuario(Receta r , Usuario u) {
        QueryBuilder query  = valoracionDao.queryBuilder();
        try {
            return (Valoracion) query.where().eq(Valoracion.RECETA , r).and().eq(Valoracion.USUARIO , u).queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void crearValoracion(Valoracion v) {
        try {
            valoracionDao.create(v);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actualizarValoracion(Valoracion v) {
        try {
            valoracionDao.update(v);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void crearComentario(Comentario c) {
        try {
            comentarioDao.create(c);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Comentario> leerComentariosReceta(Receta r) {

        try {
            QueryBuilder query  = comentarioDao.queryBuilder();
            query.where().eq(Comentario.RECETA,r);
            List<Comentario> comentarios = query.query();

            for (Comentario c : comentarios ) {
                c.setUsuario(usuarioDao.queryForId(c.getUsuario().getId() ) );
            }

            return comentarios;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<Comentario>();
        }
    }

    public void borrarReceta(Receta rec) {
        try {
            recetaDao.delete(rec);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
