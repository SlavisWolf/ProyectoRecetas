package com.recetas.aplicacion.aplicacionrecetas.Vistas;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.recetas.aplicacion.aplicacionrecetas.Pojo.Receta;
import com.recetas.aplicacion.aplicacionrecetas.Pojo.Usuario;
import com.recetas.aplicacion.aplicacionrecetas.Pojo.Valoracion;
import com.recetas.aplicacion.aplicacionrecetas.Presentadores.PresentadorReceta;
import com.recetas.aplicacion.aplicacionrecetas.R;

public class VistaReceta extends AppCompatActivity implements View.OnClickListener {

    private ImageView imagenRecetaIv;
    private TextView tituloTv;
    private TextView descripcionTv;
    private ImageView likeIv;
    private ImageView dislikeIv;

    private Receta receta;
    private Valoracion valoracion;
    private Usuario usuario;

    PresentadorReceta presentador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receta);


        inicializarVariables();
        presentador = new PresentadorReceta(this);

        if (savedInstanceState != null) {
            receta = savedInstanceState.getParcelable("receta");
        } else {
            Bundle b = getIntent().getExtras();
            if (b != null) {
                receta = b.getParcelable("receta");
            }
        }
        usuario = presentador.obtenerUsuarioActual();

        valoracion = presentador.obtenerValoracionRecetaUsuarioActual(receta , usuario);
        if (valoracion == null) {
            valoracion = new Valoracion(receta , usuario);
            presentador.crearValoracion(valoracion);
        }

        asignarValoresReceta();

    }

    private void inicializarVariables() {
        imagenRecetaIv = (ImageView) findViewById(R.id.recetaImagenNoEdicion);
        tituloTv = (TextView) findViewById(R.id.tituloRecetaTextView);
        descripcionTv = (TextView) findViewById(R.id.descripcionRecetaTextView);

        likeIv = (ImageView) findViewById(R.id.recetaLike);
        dislikeIv = (ImageView) findViewById(R.id.recetaDislike);
    }

    private  void asignarValoresReceta() {
        tituloTv.setText(receta.getTitulo());
        descripcionTv.setText(receta.getDescripcion());

        Bitmap bmp = BitmapFactory.decodeFile(receta.getImagen());

        if (bmp != null) {
            imagenRecetaIv.setImageBitmap(bmp);
        }

        asignarValoracion();
    }

    private  void asignarValoracion() {
        switch (valoracion.getRico()) {

            case Valoracion.NO_ME_GUSTA : {
                likeIv.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_like_black));
                dislikeIv.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_dislike_red));
                break;
            }

            case Valoracion.ME_GUSTA : {
                likeIv.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_like_green));
                dislikeIv.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_dislike_black));
                break;
            }
            default: {
                likeIv.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_like_black));
                dislikeIv.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_dislike_black));
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.recetaLike: {
                if (valoracion.getRico() == Valoracion.ME_GUSTA)
                    valoracion.setRico(Valoracion.NADA);
                else
                    valoracion.setRico(Valoracion.ME_GUSTA);
                asignarValoracion();
                break;
            }

            case R.id.recetaDislike: {
                if (valoracion.getRico() == Valoracion.NO_ME_GUSTA)
                    valoracion.setRico(Valoracion.NADA);
                else
                    valoracion.setRico(Valoracion.NO_ME_GUSTA);
                asignarValoracion();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        presentador.actualizarValoracion(valoracion);
    }
}
