package com.recetas.aplicacion.aplicacionrecetas.Vistas;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.recetas.aplicacion.aplicacionrecetas.R;

/**
 * Created by anton on 28/05/2017.
 */

public class VistaReceta extends AppCompatActivity {

    private ImageView imagenRecetaIv;
    private TextView tituloTv;
    private TextView descripcionTv;
    private ImageView likeIv;
    private ImageView dislikeIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receta);
    }


    private void inicializarVariables() {

        imagenRecetaIv = (ImageView) findViewById(R.id.recetaImagenNoEdicion);
        tituloTv = (TextView) findViewById(R.id.tituloRecetaTextView);
        descripcionTv = (TextView) findViewById(R.id.descripcionRecetaTextView);
        likeIv = (ImageView) findViewById(R.id.recetaLike);
        dislikeIv = (ImageView) findViewById(R.id.recetaDislike);


    }


}
