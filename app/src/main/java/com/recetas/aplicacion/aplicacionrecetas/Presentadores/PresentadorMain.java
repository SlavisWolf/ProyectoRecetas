package com.recetas.aplicacion.aplicacionrecetas.Presentadores;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.recetas.aplicacion.aplicacionrecetas.BD.Ayudante;
import com.recetas.aplicacion.aplicacionrecetas.Modelos.ModeloMain;
import com.recetas.aplicacion.aplicacionrecetas.Pojo.Receta;
import com.recetas.aplicacion.aplicacionrecetas.Pojo.Usuario;
import com.recetas.aplicacion.aplicacionrecetas.Vistas.VistaMain;

import java.util.List;

/**
 * Created by anton on 08/05/2017.
 */

public class PresentadorMain {

    private VistaMain vista;
    private ModeloMain modelo;

    public PresentadorMain(VistaMain vista) {
        this.vista = vista;
        Ayudante a = OpenHelperManager.getHelper(vista,Ayudante.class);
        modelo = new ModeloMain(this,a);
    }


    public List<Receta> getRecetasUsuario(int id) {
        return modelo.getRecetasDeUsuario(id);
    }

    public List<Receta> getRecetasOtrosUsuarios(int id) {        return modelo.getRecetasDeOtrosUsuarios(id);
    }

    public Usuario leerUsuarioPreferencias(int id) {
        return modelo.leerUsuarioId(id);
    }
}
