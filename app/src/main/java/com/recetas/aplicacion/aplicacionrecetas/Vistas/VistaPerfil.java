package com.recetas.aplicacion.aplicacionrecetas.Vistas;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.recetas.aplicacion.aplicacionrecetas.App.AplicacionRecetas;
import com.recetas.aplicacion.aplicacionrecetas.App.ArchivosRecetas;
import com.recetas.aplicacion.aplicacionrecetas.BD.Ayudante;
import com.recetas.aplicacion.aplicacionrecetas.BD.Repositorios.RepositorioUsuarios;
import com.recetas.aplicacion.aplicacionrecetas.Dialogos.DialogoImagen;
import com.recetas.aplicacion.aplicacionrecetas.Dialogos.Interfaces.imagenDialogListener;
import com.recetas.aplicacion.aplicacionrecetas.Pojo.Usuario;
import com.recetas.aplicacion.aplicacionrecetas.R;

import java.io.File;
import java.lang.ref.WeakReference;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.recetas.aplicacion.aplicacionrecetas.App.AplicacionRecetas.REQUEST_IMAGE_CAPTURE;

/**
 * Created by anton on 09/05/2017.
 */

public class VistaPerfil extends AppCompatActivity implements imagenDialogListener {

    Usuario usuario;

    CircleImageView avatarIv;
    TextInputEditText nombreEt;
    TextInputEditText correoEt;
    TextInputEditText telefonoEt;
    TextInputEditText newPassEt;
    TextInputEditText currentPassEt;
    Button saveBt;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        if (savedInstanceState != null) {
            usuario = savedInstanceState.getParcelable("usuario");
        } else {
            Bundle b = getIntent().getExtras();
            if (b != null) {
                usuario = b.getParcelable("usuario");
            }
            else {
                SharedPreferences prefs =  getSharedPreferences(AplicacionRecetas.preferencias, Context.MODE_PRIVATE);

            }
        }
        inicializarVariables();
        asignarDatosUsuario();
        asignarEventoOnClickAvatar();
    }

    private void inicializarVariables() {
        avatarIv = (CircleImageView) findViewById(R.id.perfil_avatar);
        nombreEt = (TextInputEditText) findViewById(R.id.perfilName);
        correoEt = (TextInputEditText) findViewById(R.id.perfilMail);
        telefonoEt = (TextInputEditText) findViewById(R.id.profileTlf);
        currentPassEt = (TextInputEditText) findViewById(R.id.profileCurrentPass);
        newPassEt = (TextInputEditText) findViewById(R.id.profileNewPass);
        saveBt = (Button) findViewById(R.id.profileSaveChanges);
        saveBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarUsuario();
            }
        });
    }

    private void  asignarDatosUsuario() {
        nombreEt.setText(usuario.getNombre());
        correoEt.setText(usuario.getCorreo());
        telefonoEt.setText(usuario.getTelefono());
        setTitle(getResources().getString(R.string.titleProfile) + " " + usuario.getNombre());
        if (usuario.getAvatar() != null && !usuario.getAvatar().isEmpty()) {
            ArchivosRecetas.asignarImagenAImageViewPorRuta(avatarIv,usuario.getAvatar());
        }
        else {
            avatarIv.setImageDrawable(getResources().getDrawable(R.drawable.ic_menu_camera));
        }
    }

    private void asignarEventoOnClickAvatar() {
        avatarIv.setOnClickListener(new View.OnClickListener() {
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == AplicacionRecetas.REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            String ruta = Environment.getExternalStorageDirectory() + File.separator + ArchivosRecetas.IMAGE_DIRECTORY + File.separator + usuario.getNombre() +"Avatar.jpeg";
            ArchivosRecetas.crearImagen(ruta,imageBitmap,this);
            avatarIv.setImageBitmap(imageBitmap);
            usuario.setAvatar(ruta);
        }

        else if (requestCode == AplicacionRecetas.REQUEST_GALERY && resultCode == RESULT_OK) {
            String rutaAbsoluta = ArchivosRecetas.getRutaImagen(data.getData(), this);
            String nombreImagen = ArchivosRecetas.fechaActual() + rutaAbsoluta.substring(rutaAbsoluta.lastIndexOf("/") + 1, rutaAbsoluta.length());
            String rutaNuevaImagen = Environment.getExternalStorageDirectory() + File.separator + ArchivosRecetas.IMAGE_DIRECTORY + File.separator + nombreImagen;

            WeakReference<Bitmap> reference = new WeakReference<Bitmap>(BitmapFactory.decodeFile(rutaAbsoluta));
            ArchivosRecetas.crearImagen(rutaNuevaImagen,reference.get() , this); // le pasas una ruta,una imagen, y te crea un nuevo archivo en esa ruta con esa imagen
            avatarIv.setImageBitmap(reference.get());
            reference.clear();

            usuario.setAvatar(rutaNuevaImagen);
        }
    }

    private void guardarUsuario() {
        if (currentPassEt.getText().toString().trim().compareTo(usuario.getPass()) == 0) {
            String nuevoCorreo = correoEt.getText().toString().trim();


            if (!nuevoCorreo.isEmpty() ) {

                if (nuevoCorreo.compareTo(usuario.getCorreo()) != 0) {
                    RepositorioUsuarios userRepo = new RepositorioUsuarios(OpenHelperManager.getHelper(this, Ayudante.class));
                    Usuario usuarioPrueba = userRepo.leerUsuarioPorCorreo(nuevoCorreo);
                    if (usuarioPrueba == null) { // correo libre, puede guardar datos.
                        actualizarUsuario();
                    }
                    else {
                        Toast.makeText(this,R.string.mailUsed,Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    actualizarUsuario();
                }
            }
        }

        else {
            Toast.makeText(this,R.string.passRequired,Toast.LENGTH_LONG).show();
        }
    }

    private void actualizarUsuario() {

        RepositorioUsuarios userRepo = new RepositorioUsuarios(OpenHelperManager.getHelper(this, Ayudante.class));

        String nuevoCorreo = correoEt.getText().toString().trim();
        String nuevaPass = newPassEt.getText().toString().trim();
        String nuevoNombre =  nombreEt.getText().toString().trim();
        String nuevoTelefono =  telefonoEt.getText().toString().trim();

        usuario.setNombre(nuevoNombre);
        usuario.setCorreo(nuevoCorreo);
        usuario.setTelefono(nuevoTelefono);

        if (!nuevaPass.isEmpty())
            usuario.setPass(nuevaPass);

        userRepo.actualizarUsuario(usuario);
        finish();
    }
}
