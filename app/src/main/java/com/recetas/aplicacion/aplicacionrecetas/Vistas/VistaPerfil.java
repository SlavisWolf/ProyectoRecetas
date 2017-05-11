package com.recetas.aplicacion.aplicacionrecetas.Vistas;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.recetas.aplicacion.aplicacionrecetas.App.AplicacionRecetas;
import com.recetas.aplicacion.aplicacionrecetas.Dialogos.DialogoImagen;
import com.recetas.aplicacion.aplicacionrecetas.Dialogos.Interfaces.imagenDialogListener;
import com.recetas.aplicacion.aplicacionrecetas.Pojo.Usuario;
import com.recetas.aplicacion.aplicacionrecetas.R;

import de.hdodenhof.circleimageview.CircleImageView;

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

    }

    private void  asignarDatosUsuario() {
        nombreEt.setText(usuario.getNombre());
        correoEt.setText(usuario.getCorreo());
        telefonoEt.setText(usuario.getTelefono());
        setTitle(getResources().getString(R.string.titleProfile) + " " + usuario.getNombre());
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

    }

    @Override
    public void imagenDialogCamara() {

    }
}
