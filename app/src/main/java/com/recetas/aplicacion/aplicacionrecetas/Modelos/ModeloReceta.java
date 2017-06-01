package com.recetas.aplicacion.aplicacionrecetas.Modelos;

import com.recetas.aplicacion.aplicacionrecetas.App.AplicacionRecetas;
import com.recetas.aplicacion.aplicacionrecetas.BD.Ayudante;
import com.recetas.aplicacion.aplicacionrecetas.BD.Repositorios.RepositorioRecetas;
import com.recetas.aplicacion.aplicacionrecetas.BD.Repositorios.RepositorioUsuarios;
import com.recetas.aplicacion.aplicacionrecetas.Pojo.Receta;
import com.recetas.aplicacion.aplicacionrecetas.Pojo.Usuario;
import com.recetas.aplicacion.aplicacionrecetas.Pojo.Valoracion;
import com.recetas.aplicacion.aplicacionrecetas.Presentadores.PresentadorReceta;

/**
 * Created by anton on 01/06/2017.
 */

public class ModeloReceta {

    private PresentadorReceta presentador;
    private RepositorioUsuarios repositorioUsuarios;
    private RepositorioRecetas repositorioRecetas;

    public ModeloReceta(PresentadorReceta presentador, Ayudante a) {
        this.presentador = presentador;
        repositorioUsuarios = new RepositorioUsuarios(a);
        repositorioRecetas = new RepositorioRecetas(a);
    }

    public Usuario obtenerUsuarioActual()  {
        return repositorioUsuarios.leerUsuarioId(AplicacionRecetas.ID_CURRENT_USER);
    }

    public Valoracion obtenerValoracionRecetaUsuarioActual(Receta r , Usuario u) {
        return repositorioRecetas.obtenerValoracionRecetaConUsuario(r , u);
    }

    public void crearValoracion(Valoracion v) {
        repositorioRecetas.crearValoracion(v);
    }

    public void actualizarValoracion(Valoracion v) {
        repositorioRecetas.actualizarValoracion(v);
    }
}
