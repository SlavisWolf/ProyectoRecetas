package com.recetas.aplicacion.aplicacionrecetas.Modelos;

import com.recetas.aplicacion.aplicacionrecetas.BD.Ayudante;
import com.recetas.aplicacion.aplicacionrecetas.BD.Repositorios.RepositorioRecetas;
import com.recetas.aplicacion.aplicacionrecetas.BD.Repositorios.RepositorioUsuarios;
import com.recetas.aplicacion.aplicacionrecetas.Pojo.Receta;
import com.recetas.aplicacion.aplicacionrecetas.Presentadores.PresentadorEdicionreceta;

/**
 * Created by anton on 21/05/2017.
 */

public class ModeloEdicionReceta {

    private PresentadorEdicionreceta presentador;
    private RepositorioUsuarios repositorioUsuarios;
    private RepositorioRecetas repositorioRecetas;

    public ModeloEdicionReceta(PresentadorEdicionreceta presentador, Ayudante a) {
        this.presentador = presentador;
        repositorioUsuarios = new RepositorioUsuarios(a);
        repositorioRecetas = new RepositorioRecetas(a);
    }

    public long numeroLikesReceta(Receta r) {
        return  repositorioRecetas.numeroLikesReceta(r);
    }

    public long numeroDislikesReceta(Receta r) {
        return  repositorioRecetas.numeroDislikesReceta(r);
    }
}
