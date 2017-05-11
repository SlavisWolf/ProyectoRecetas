package com.recetas.aplicacion.aplicacionrecetas.Modelos;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.recetas.aplicacion.aplicacionrecetas.BD.Ayudante;
import com.recetas.aplicacion.aplicacionrecetas.BD.Repositorios.RepositorioRecetas;
import com.recetas.aplicacion.aplicacionrecetas.BD.Repositorios.RepositorioUsuarios;
import com.recetas.aplicacion.aplicacionrecetas.Pojo.Usuario;
import com.recetas.aplicacion.aplicacionrecetas.Presentadores.PresentadorMain;
import com.recetas.aplicacion.aplicacionrecetas.Vistas.VistaMain;

/**
 * Created by anton on 08/05/2017.
 */

public class ModeloMain {

    private PresentadorMain presentador;
    private RepositorioUsuarios repositorioUsuarios;
    private RepositorioRecetas repositorioRecetas;

    public ModeloMain(PresentadorMain presentador, Ayudante a) {
        this.presentador = presentador;
        repositorioUsuarios = new RepositorioUsuarios(a);
        repositorioRecetas = new RepositorioRecetas(a);
    }

    public Usuario leerUsuarioId(int id) {
       return repositorioUsuarios.leerUsuarioId(id);
    }
}
