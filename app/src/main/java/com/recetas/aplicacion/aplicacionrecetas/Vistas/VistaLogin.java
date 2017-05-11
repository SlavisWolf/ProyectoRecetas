package com.recetas.aplicacion.aplicacionrecetas.Vistas;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.recetas.aplicacion.aplicacionrecetas.App.AplicacionRecetas;
import com.recetas.aplicacion.aplicacionrecetas.App.LimpiadorErroresInputLayout;
import com.recetas.aplicacion.aplicacionrecetas.BD.Ayudante;
import com.recetas.aplicacion.aplicacionrecetas.BD.Repositorios.RepositorioUsuarios;
import com.recetas.aplicacion.aplicacionrecetas.Pojo.Usuario;
import com.recetas.aplicacion.aplicacionrecetas.R;

/**
 * Created by anton on 06/05/2017.
 */

public class VistaLogin extends AppCompatActivity {


    TextInputLayout userNameTiLa;
    TextInputLayout passNameTiLa;

    TextInputEditText userEt;
    TextInputEditText passEt;

    Button loginBt;
    Button registerButton;
    CheckBox rememberPassCb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        SharedPreferences prefs =  getSharedPreferences(AplicacionRecetas.preferencias, Context.MODE_PRIVATE);

        if (prefs.getInt("usuario",0) != 0 ) {
            Intent activityMain = new Intent( this , VistaMain.class);
            startActivity(activityMain);
            finish();
        }

        inicializarVariables();

    }


    private void inicializarVariables() {
            userNameTiLa = (TextInputLayout) findViewById(R.id.usernameInputLayout);
            passNameTiLa = (TextInputLayout) findViewById(R.id.passwordInputLayout);


            userEt = (TextInputEditText) findViewById(R.id.usernameEt);
            userEt.addTextChangedListener(new LimpiadorErroresInputLayout(userNameTiLa));


            passEt = (TextInputEditText) findViewById(R.id.passwordEt);
            passEt.addTextChangedListener(new LimpiadorErroresInputLayout(passNameTiLa));


            loginBt = (Button) findViewById(R.id.loginBt);
            loginBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    login();
                }
            });

            registerButton = (Button) findViewById(R.id.registerBt);
            registerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    irRegistro();
                }
            });

            rememberPassCb = (CheckBox) findViewById(R.id.rememberPass);
    }

    private void login() {

        boolean error = false;
        String correo = userEt.getText().toString().trim();
        String pass = passEt.getText().toString().trim();
        if (correo.isEmpty() ) {
            error = true;
            userNameTiLa.setError(getResources().getString(R.string.noMail) );
        }

        if (pass.isEmpty() ) {
            error = true;
            passNameTiLa.setError(getResources().getString(R.string.noPass) );
        }

        if (!error) {

            RepositorioUsuarios  userRepo = new RepositorioUsuarios(OpenHelperManager.getHelper(this, Ayudante.class));

            // si existe ese usuario nos logueamos y accedemos
            Usuario user = userRepo.leerUsuarioLogin(correo,pass);
            if (user != null) {

                // guardamos el correo en las preferencias

                if (rememberPassCb.isChecked()) {
                    guardarIdUsuarioPrefs(user);
                }

                Intent activityMain = new Intent(getApplicationContext(), VistaMain.class);
                Bundle b = new Bundle();
                b.putParcelable("usuario",user);
                activityMain.putExtras(b);
                startActivity(activityMain);
                finish();
            }
            else {
                userNameTiLa.setError(getResources().getString(R.string.userDontExist));
            }
        }
    }


    private void irRegistro() {
        Intent activityMain = new Intent(getApplicationContext(), VistaRegistro.class);
        startActivity(activityMain);
    }

    private void guardarIdUsuarioPrefs(Usuario user) {
        SharedPreferences prefs =  getSharedPreferences(AplicacionRecetas.preferencias, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("usuario", user.getId() );
        editor.commit();
    }
}
