package com.recetas.aplicacion.aplicacionrecetas.Presentadores;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.recetas.aplicacion.aplicacionrecetas.BD.Ayudante;
import com.recetas.aplicacion.aplicacionrecetas.Modelos.ModeloReceta;
import com.recetas.aplicacion.aplicacionrecetas.Pojo.Receta;
import com.recetas.aplicacion.aplicacionrecetas.Pojo.Usuario;
import com.recetas.aplicacion.aplicacionrecetas.Pojo.Valoracion;
import com.recetas.aplicacion.aplicacionrecetas.Vistas.VistaReceta;

/**
 * Created by anton on 01/06/2017.
 */

public class PresentadorReceta {

    private VistaReceta vista;
    private ModeloReceta modelo;

    public PresentadorReceta(VistaReceta vista) {
        this.vista = vista;
        Ayudante a = OpenHelperManager.getHelper(vista,Ayudante.class);
        modelo = new ModeloReceta(this,a);
    }

    public Usuario obtenerUsuarioActual() {
        return modelo.obtenerUsuarioActual();
    }


    public Valoracion obtenerValoracionRecetaUsuarioActual(Receta r , Usuario u) {
        return modelo.obtenerValoracionRecetaUsuarioActual(r , u);
    }

    public void crearValoracion(Valoracion v) {
        modelo.crearValoracion(v);
    }

    public void actualizarValoracion(Valoracion v) {
        modelo.actualizarValoracion(v);
    }
}

