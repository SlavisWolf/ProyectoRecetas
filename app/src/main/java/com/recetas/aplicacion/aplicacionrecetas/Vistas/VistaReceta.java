package com.recetas.aplicacion.aplicacionrecetas.Vistas;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.recetas.aplicacion.aplicacionrecetas.Adaptadores.AdaptadorComentarios;
import com.recetas.aplicacion.aplicacionrecetas.BD.Ayudante;
import com.recetas.aplicacion.aplicacionrecetas.BD.Repositorios.RepositorioRecetas;
import com.recetas.aplicacion.aplicacionrecetas.Dialogos.DialogoNuevoComentario;
import com.recetas.aplicacion.aplicacionrecetas.Dialogos.Interfaces.AcceptOrCancelDialogListener;
import com.recetas.aplicacion.aplicacionrecetas.Pojo.Comentario;
import com.recetas.aplicacion.aplicacionrecetas.Pojo.Receta;
import com.recetas.aplicacion.aplicacionrecetas.Pojo.Usuario;
import com.recetas.aplicacion.aplicacionrecetas.Pojo.Valoracion;
import com.recetas.aplicacion.aplicacionrecetas.Presentadores.PresentadorReceta;
import com.recetas.aplicacion.aplicacionrecetas.R;

import java.text.SimpleDateFormat;
import java.util.List;

public class VistaReceta extends AppCompatActivity implements View.OnClickListener, AcceptOrCancelDialogListener {

    private ImageView imagenRecetaIv;
    private TextView tituloTv;
    private TextView descripcionTv;
    private TextView autorTv;
    private TextView fechaTv;
    private ImageView likeIv;
    private ImageView dislikeIv;

    private ImageView nuevoComentarioIv;

    private Receta receta;
    private Valoracion valoracion;
    private Usuario usuario;

    private AdaptadorComentarios adaptador;
    private RecyclerView rv;

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

        receta.setComentarios(leerComentariosReceta() );

        if (receta.getComentarios().isEmpty()) {
            System.out.println("COMENTARIOS VACIOS");
        }
        usuario = presentador.obtenerUsuarioActual();

        valoracion = presentador.obtenerValoracionRecetaUsuarioActual(receta , usuario);
        System.out.println("usuario:" + usuario.getCorreo() );
        System.out.println("receta:" + receta.getTitulo());
        if (valoracion == null) {

            valoracion = new Valoracion(receta , usuario);
            presentador.crearValoracion(valoracion);
        }

        asignarValoresReceta();
        inicializarRv();

    }

    private void inicializarVariables() {
        rv = (RecyclerView) findViewById(R.id.listaComentarios);
        imagenRecetaIv = (ImageView) findViewById(R.id.recetaImagenNoEdicion);
        fechaTv = (TextView) findViewById(R.id.fechaRecetaTextView);
        tituloTv = (TextView) findViewById(R.id.tituloRecetaTextView);
        descripcionTv = (TextView) findViewById(R.id.descripcionRecetaTextView);
        autorTv = (TextView) findViewById(R.id.autorRecetaTextView);
        nuevoComentarioIv = (ImageView) findViewById(R.id.nuevoComentario);

        likeIv = (ImageView) findViewById(R.id.recetaLike);
        dislikeIv = (ImageView) findViewById(R.id.recetaDislike);
    }

    private  void asignarValoresReceta() {
        tituloTv.setText(receta.getTitulo());
        descripcionTv.setText(receta.getDescripcion());
        autorTv.setText(receta.getUsuario().getNombre());
        Bitmap bmp = BitmapFactory.decodeFile(receta.getImagen());

        if (bmp != null) {
            imagenRecetaIv.setImageBitmap(bmp);
        }


        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/YYYY");
        fechaTv.setText(formatter.format(receta.getFechaPublicacion()));
        nuevoComentarioIv.setOnClickListener(this);
        likeIv.setOnClickListener(this);
        dislikeIv.setOnClickListener(this);
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
                break;
            }
            case R.id.nuevoComentario: {
                DialogoNuevoComentario dialogo = DialogoNuevoComentario.newInstance();
                dialogo.show(getSupportFragmentManager() ,"Dialogo comentario");
                break;
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        presentador.actualizarValoracion(valoracion);
    }

    private void inicializarRv() {
        rv.setHasFixedSize(false); // true, la lista es estatica, false, los datos de la lista pueden variar.
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)); // forma en que se visualizan los elementos, en este caso en vertical.
        adaptador = new AdaptadorComentarios(receta.getComentarios());
        rv.setAdapter(adaptador);
    }

    @Override
    public void onAcceptDialog(String texto) {
        if (!texto.isEmpty()) {
            Comentario c = new Comentario(receta, usuario, texto);
            adaptador.nuevoComentario(c);

            Ayudante a = OpenHelperManager.getHelper(this,Ayudante.class);
            RepositorioRecetas bd = new RepositorioRecetas(a);
            bd.crearComentario(c);

            receta.setComentarios(bd.leerComentariosReceta(receta) );
        }
    }

    @Override
    public void onCancelDialog() {

    }

    private List<Comentario> leerComentariosReceta() {
        Ayudante a = OpenHelperManager.getHelper(this,Ayudante.class);
        RepositorioRecetas bd = new RepositorioRecetas(a);
        return bd.leerComentariosReceta(receta);
    }
}
