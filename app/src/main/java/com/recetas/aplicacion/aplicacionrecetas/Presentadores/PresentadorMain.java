package com.recetas.aplicacion.aplicacionrecetas.Presentadores;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.recetas.aplicacion.aplicacionrecetas.BD.Ayudante;
import com.recetas.aplicacion.aplicacionrecetas.Modelos.ModeloMain;
import com.recetas.aplicacion.aplicacionrecetas.Pojo.Usuario;
import com.recetas.aplicacion.aplicacionrecetas.Vistas.VistaMain;

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


    public Usuario leerUsuarioPreferencias(int id) {
        return modelo.leerUsuarioId(id);
    }
}
