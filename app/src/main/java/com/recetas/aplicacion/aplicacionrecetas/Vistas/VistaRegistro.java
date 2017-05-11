package com.recetas.aplicacion.aplicacionrecetas.Vistas;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.recetas.aplicacion.aplicacionrecetas.App.AplicacionRecetas;
import com.recetas.aplicacion.aplicacionrecetas.BD.Ayudante;
import com.recetas.aplicacion.aplicacionrecetas.BD.Repositorios.RepositorioUsuarios;
import com.recetas.aplicacion.aplicacionrecetas.Pojo.Usuario;
import com.recetas.aplicacion.aplicacionrecetas.R;

import java.util.Date;

/**
 * Created by anton on 06/05/2017.
 */

public class VistaRegistro extends AppCompatActivity {

    private Button sendBt;
    private Button returnLogin;
    private TextInputEditText nombreEt;
    private TextInputEditText correoEt;
    private TextInputEditText tlfEt;
    private TextInputEditText passEt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        inicializarVariables();
    }

    private void inicializarVariables() {
        nombreEt = (TextInputEditText) findViewById(R.id.registerName);
        passEt = (TextInputEditText) findViewById(R.id.registerPass);
        tlfEt = (TextInputEditText) findViewById(R.id.registerTlf);
        correoEt = (TextInputEditText) findViewById(R.id.registerMail);


        sendBt = (Button) findViewById(R.id.registerSendBt);
        sendBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registro();
            }
        });

        returnLogin = (Button) findViewById(R.id.returnLoginBt);
        returnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irLogin();
            }
        });
    }

    private void registro() {
        String nombre = nombreEt.getText().toString().trim();
        String correo = correoEt.getText().toString().trim();
        String pass = passEt.getText().toString().trim();
        String telefono = tlfEt.getText().toString().trim();
        boolean error = false;
        String mensajeError = "";

        if (correo.isEmpty()) {
            error = true;
            mensajeError = getResources().getString(R.string.noMail);
        }

        if (pass.isEmpty()) {
            error = true;
            if (!mensajeError.isEmpty())
                mensajeError += "\n" + getResources().getString(R.string.noPass);
            else
                mensajeError  = getResources().getString(R.string.noPass);
        }

        if (error) {
            Toast.makeText(this,mensajeError,Toast.LENGTH_LONG).show();
        }
        else {
            Usuario usuario = new Usuario(nombre,telefono,correo,pass,new Date() ); // creamos el usuario
            RepositorioUsuarios repositorio = new RepositorioUsuarios(OpenHelperManager.getHelper(this, Ayudante.class));

            Usuario pruebaUsuario = repositorio.leerUsuarioPorCorreo(correo);
            if (pruebaUsuario != null)
                Toast.makeText(this,getResources().getString(R.string.mailExits),Toast.LENGTH_LONG).show();
            else {

                repositorio.crearUsuario(usuario);
                irLogin();
                finish();
            }
        }
    }

    private void irLogin() {
        finish();
    }
}
