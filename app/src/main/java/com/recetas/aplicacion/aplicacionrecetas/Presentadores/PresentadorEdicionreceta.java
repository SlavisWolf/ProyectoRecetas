package com.recetas.aplicacion.aplicacionrecetas.Presentadores;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.recetas.aplicacion.aplicacionrecetas.BD.Ayudante;
import com.recetas.aplicacion.aplicacionrecetas.Modelos.ModeloEdicionReceta;
import com.recetas.aplicacion.aplicacionrecetas.Pojo.Receta;
import com.recetas.aplicacion.aplicacionrecetas.Vistas.VistaEdicionReceta;

/**
 * Created by anton on 21/05/2017.
 */

public class PresentadorEdicionreceta {

    private VistaEdicionReceta vista;
    private ModeloEdicionReceta modelo;

    public PresentadorEdicionreceta(VistaEdicionReceta vista) {
        this.vista = vista;
        Ayudante a = OpenHelperManager.getHelper(vista,Ayudante.class);
        modelo = new ModeloEdicionReceta(this,a);
    }

    public long numeroLikesReceta(Receta r) {
        return modelo.numeroLikesReceta(r);
    }

    public long numeroDislikesReceta(Receta r) {
        return modelo.numeroDislikesReceta(r);
    }
}
