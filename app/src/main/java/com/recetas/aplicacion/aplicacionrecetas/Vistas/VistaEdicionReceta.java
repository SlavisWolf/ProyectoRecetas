package com.recetas.aplicacion.aplicacionrecetas.Vistas;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.recetas.aplicacion.aplicacionrecetas.Adaptadores.AdaptadorComentarios;
import com.recetas.aplicacion.aplicacionrecetas.App.AplicacionRecetas;
import com.recetas.aplicacion.aplicacionrecetas.App.ArchivosRecetas;
import com.recetas.aplicacion.aplicacionrecetas.BD.Repositorios.RepositorioUsuarios;
import com.recetas.aplicacion.aplicacionrecetas.Dialogos.DialogoAceptarCancelar;
import com.recetas.aplicacion.aplicacionrecetas.Dialogos.DialogoImagen;
import com.recetas.aplicacion.aplicacionrecetas.Dialogos.DialogoNuevoComentario;
import com.recetas.aplicacion.aplicacionrecetas.Dialogos.Interfaces.AcceptOrCancelDialogListener;
import com.recetas.aplicacion.aplicacionrecetas.Dialogos.Interfaces.imagenDialogListener;
import com.recetas.aplicacion.aplicacionrecetas.Pojo.Comentario;
import com.recetas.aplicacion.aplicacionrecetas.Pojo.Receta;
import com.recetas.aplicacion.aplicacionrecetas.Pojo.Usuario;
import com.recetas.aplicacion.aplicacionrecetas.Presentadores.PresentadorEdicionreceta;
import com.recetas.aplicacion.aplicacionrecetas.R;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.Date;

import static com.recetas.aplicacion.aplicacionrecetas.App.AplicacionRecetas.REQUEST_IMAGE_CAPTURE;

/**
 * Created by anton on 21/05/2017.
 */

//CAMBIAR NOMBRE ARCHIVOS IMAGEN RECETAS

//asignar usuario a receta
public class VistaEdicionReceta extends AppCompatActivity implements imagenDialogListener,AcceptOrCancelDialogListener {


    private EditText tituloEt;
    private MultiAutoCompleteTextView descripcionEt;
    private ImageView imagenRecetaIv;
    private TextView contadorLikes;
    private TextView contadorDislikes;
    private Receta receta;
    private PresentadorEdicionreceta presentador;
    private Button guardarBt;
    private ImageView addComentario;

    // Comentarios

    private AdaptadorComentarios adaptador;
    private RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recetas_editing);

        inicializarVariables();
        presentador = new PresentadorEdicionreceta(this);
        if (savedInstanceState != null) {
            receta = savedInstanceState.getParcelable("receta");
        } else {
            Bundle b = getIntent().getExtras();
            if (b != null) {
                receta = b.getParcelable("receta");
            }
            else {
                receta = new Receta();
            }
        }

        receta.setComentarios(presentador.leerComentariosReceta(receta));
        receta.setUsuario(presentador.obtenerUsuarioActual() );

        if (receta.getId() != 0) // nueva receta
            asignarValoresReceta();
        else {
            System.out.println("ID RECETA" + receta.getId());
            if (receta.getId() < 1) {
                contadorLikes.setVisibility(View.GONE);
                contadorDislikes.setVisibility(View.GONE);
                rv.setVisibility(View.GONE);

                ImageView like = (ImageView) findViewById(R.id.LikeIv);
                like.setVisibility(View.GONE);
                ImageView dislike = (ImageView) findViewById(R.id.dislikeIv);
                dislike.setVisibility(View.GONE);

                TextView textoComentario = (TextView) findViewById(R.id.textoComentariosEditing);
                textoComentario.setVisibility(View.GONE);
                addComentario.setVisibility(View.GONE);
            }
        }


        asignarEventoOnClickImagen();
        inicializarRv();
    }
    private void inicializarVariables() {
        guardarBt = (Button) findViewById(R.id.guardarReceta);
        tituloEt = (EditText) findViewById(R.id.tituloRecetaEditText);
        descripcionEt = (MultiAutoCompleteTextView) findViewById(R.id.editTextDescriptionRecipe);
        imagenRecetaIv = (ImageView) findViewById(R.id.imagenRecetaEdicion);
        contadorLikes = (TextView) findViewById(R.id.likeCountLabel);
        contadorDislikes = (TextView) findViewById(R.id.dislikeCountLabel);

        guardarBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarNota();
                finish();
            }
        });


        addComentario = (ImageView) findViewById(R.id.nuevoComentario);
        addComentario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogoNuevoComentario dialogo = DialogoNuevoComentario.newInstance();
                dialogo.show(getSupportFragmentManager() ,"Dialogo comentario");
            }
        });

        rv = (RecyclerView) findViewById(R.id.listaComentarios);

    }

    private void asignarValoresReceta() {
        tituloEt.setText(receta.getTitulo());
        descripcionEt.setText(receta.getDescripcion());

        if (receta.getImagen() != null && !receta.getImagen().isEmpty()) {
            ArchivosRecetas.asignarImagenAImageViewPorRuta(imagenRecetaIv,receta.getImagen());
        }
        else {
            imagenRecetaIv.setImageDrawable(getResources().getDrawable(R.drawable.ic_menu_camera));
        }

        contadorLikes.setText(String.valueOf(presentador.numeroLikesReceta(receta) ) );
        contadorDislikes.setText(String.valueOf(presentador.numeroDislikesReceta(receta) ) );

        rv = (RecyclerView) findViewById(R.id.listaComentarios);
    }

    private void asignarEventoOnClickImagen() {
        imagenRecetaIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogoImagen dialogo = DialogoImagen.newInstance();
                dialogo.show(getSupportFragmentManager(), "Dialogo Imagen");
            }
        });
    }


    @Override
    public void imagenDialogGaleria() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED )
        {
            // pedimos los permisos
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    AplicacionRecetas.REQUEST_EXTERNAL_WRITE_PERMISSION);
        }
        else  { // si ya tenemos el permiso pedido
            abrirGaleria();
        }

    }

    @Override
    public void imagenDialogCamara() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                &
                ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                )
        {
            // pedimos los permisos
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                    AplicacionRecetas.REQUEST_CAMERA_PERMISSION);
        }
        else  { // si ya tenemos el permiso pedido
            abrirCamara();
        }
    }

    private void abrirCamara() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void abrirGaleria() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");//para que busque cualquier tipo de imagen
        startActivityForResult(intent.createChooser(intent, "Galería"),AplicacionRecetas.REQUEST_GALERY);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == AplicacionRecetas.REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            String ruta = Environment.getExternalStorageDirectory() + File.separator + ArchivosRecetas.IMAGE_DIRECTORY + File.separator + receta.getTitulo() +"Receta.jpeg";
            ArchivosRecetas.crearImagen(ruta,imageBitmap,this);
            imagenRecetaIv.setImageBitmap(imageBitmap);
            receta.setImagen(ruta);
        }

        else if (requestCode == AplicacionRecetas.REQUEST_GALERY && resultCode == RESULT_OK) {
            String rutaAbsoluta = ArchivosRecetas.getRutaImagen(data.getData(), this);
            String nombreImagen = ArchivosRecetas.fechaActual() + rutaAbsoluta.substring(rutaAbsoluta.lastIndexOf("/") + 1, rutaAbsoluta.length());
            String rutaNuevaImagen = Environment.getExternalStorageDirectory() + File.separator + ArchivosRecetas.IMAGE_DIRECTORY + File.separator + nombreImagen;

            WeakReference<Bitmap> reference = new WeakReference<Bitmap>(BitmapFactory.decodeFile(rutaAbsoluta));
            ArchivosRecetas.crearImagen(rutaNuevaImagen,reference.get() , this); // le pasas una ruta,una imagen, y te crea un nuevo archivo en esa ruta con esa imagen
            imagenRecetaIv.setImageBitmap(reference.get());
            reference.clear();

            receta.setImagen(rutaNuevaImagen);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)  {
            case AplicacionRecetas.REQUEST_CAMERA_PERMISSION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED & grantResults[1] == PackageManager.PERMISSION_DENIED) {
                    abrirCamara();
                }
                else {
                    System.out.println("Falta algún permiso");
                }
            }

            case AplicacionRecetas.REQUEST_EXTERNAL_WRITE_PERMISSION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    abrirGaleria();
                }
                else {
                    System.out.println("Falta algún permiso");
                }
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        guardarNota();
    }

    private void guardarNota() {

            String error = "";
            if (receta.getImagen() == null || receta.getImagen().isEmpty())
                error += getResources().getString(R.string.image) +" ";
            String titulo = tituloEt.getText().toString();
            if (titulo.isEmpty())
                error += getResources().getString(R.string.title) +" ";
            String descripcion = descripcionEt.getText().toString();
            if (descripcion.isEmpty() )
                error += getResources().getString(R.string.description) +" ";


            if (error.isEmpty() ) { // guardamos la nota
                receta.setTitulo(titulo);
                receta.setDescripcion(descripcion);
                //System.out.println("Prueba edicion:" + receta.getUsuario().getCorreo());
                if (receta.getId() != 0) { // actualizamos

                    presentador.actualizarReceta(receta);
                } else {
                    receta.setFechaPublicacion(new Date());
                    presentador.crearReceta(receta);
                }
            }
            else {
                Toast.makeText(this,error + " " +  getResources().getString(R.string.isRequired),Toast.LENGTH_LONG).show();
            }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        if (receta.getId() != 0)
            getMenuInflater().inflate(R.menu.edicion_receta, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {

            case R.id.menu_receta_borrar : {

                DialogoAceptarCancelar dialogo = DialogoAceptarCancelar.newInstance(getString(R.string.tituloBorrarReceta) , getString(R.string.textoBorrarReceta));
                dialogo.show(getSupportFragmentManager(), "Dialogo Borrar");
                return true;
            }

            default: {
                return false;
            }
        }
    }

    @Override
    public void onAcceptDialog(String texto) {

        if (texto == null) { // dialogo borrar receta
            presentador.borrarReceta(receta);
            finish();
        }
        else { // dialogo nuevo comentario
            Comentario c = new Comentario(receta, receta.getUsuario() , texto);
            adaptador.nuevoComentario(c);
            presentador.crearComentario(c);
            receta.setComentarios(presentador.leerComentariosReceta(receta) );
        }
    }

    @Override
    public void onCancelDialog() {

    }

    private void inicializarRv() {
        rv.setHasFixedSize(false); // true, la lista es estatica, false, los datos de la lista pueden variar.
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)); // forma en que se visualizan los elementos, en este caso en vertical.
        adaptador = new AdaptadorComentarios(receta.getComentarios());
        rv.setAdapter(adaptador);
    }
}
