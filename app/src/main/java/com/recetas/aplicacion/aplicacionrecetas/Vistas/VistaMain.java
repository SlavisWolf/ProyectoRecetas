package com.recetas.aplicacion.aplicacionrecetas.Vistas;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.graphics.BitmapFactory;

import android.os.Bundle;

import android.support.design.widget.FloatingActionButton;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import com.recetas.aplicacion.aplicacionrecetas.Adaptadores.AdaptadorReceta;
import com.recetas.aplicacion.aplicacionrecetas.App.AplicacionRecetas;

import com.recetas.aplicacion.aplicacionrecetas.BD.Ayudante;
import com.recetas.aplicacion.aplicacionrecetas.BD.Repositorios.RepositorioRecetas;
import com.recetas.aplicacion.aplicacionrecetas.BD.Repositorios.RepositorioUsuarios;
import com.recetas.aplicacion.aplicacionrecetas.Pojo.Receta;
import com.recetas.aplicacion.aplicacionrecetas.Pojo.Usuario;
import com.recetas.aplicacion.aplicacionrecetas.Presentadores.PresentadorMain;
import com.recetas.aplicacion.aplicacionrecetas.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class VistaMain extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final  int GALERY_ACTIVITY = 10;
    private Usuario usuario;
    private PresentadorMain presentador;
    private RecyclerView rv;
    private AdaptadorReceta adaptador;


    private static  int OWN_RECIPES = 100;
    private static  int OTHER_RECIPES = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_main);


        presentador = new PresentadorMain(this);

        rv = (RecyclerView) findViewById(R.id.recetasRecyclerView);
        rv.setHasFixedSize(false); // true, la lista es estatica, false, los datos de la lista pueden variar.
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)); // forma en que se visualizan los elementos, en este caso en vertical.


        if (savedInstanceState != null) {
            usuario = savedInstanceState.getParcelable("usuario");
        } else {
            Bundle b = getIntent().getExtras();
            if (b != null) {
                usuario = b.getParcelable("usuario");
            }
            else if (AplicacionRecetas.ID_CURRENT_USER != 0){
                usuario = leerUsuarioPreferencias(AplicacionRecetas.ID_CURRENT_USER);
            }

            else {
                SharedPreferences prefs =  getSharedPreferences(AplicacionRecetas.preferencias, Context.MODE_PRIVATE);
                usuario = leerUsuarioPreferencias(prefs.getInt("usuario",0) );
            }
        }

        AplicacionRecetas.ID_CURRENT_USER = usuario.getId();

        Ayudante a = OpenHelperManager.getHelper(this,Ayudante.class);
        RepositorioRecetas  bd = new RepositorioRecetas(a);

        adaptador = new AdaptadorReceta(obtenerListaRecycler() , bd);
        adaptador.setOnClickListener(new EdicionRecetaListener() );

        rv.setAdapter(adaptador);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                botonNuevo();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        iniciarValoresNavHeader();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.vista_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_own_recipes) {
            AplicacionRecetas.CURRENT_RECIPES = VistaMain.OWN_RECIPES;
            cambiarListaAdaptador();
        } else if (id == R.id.nav_other_recipes) {
            AplicacionRecetas.CURRENT_RECIPES = VistaMain.OTHER_RECIPES;
            cambiarListaAdaptador();
        }
        else if (id == R.id.nav_profile) {
            irPerfil();
        } else if (id == R.id.nav_sign_out) {
            desconectarUsuario();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void desconectarUsuario() {
        SharedPreferences prefs =  getSharedPreferences(AplicacionRecetas.preferencias, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("usuario", 0 ) ;
        editor.commit();

        Intent activityLogin = new Intent( this , VistaLogin.class);
        startActivity(activityLogin);
        finish();
    }

    private void  irPerfil() {
        Intent activityMain = new Intent(getApplicationContext(), VistaPerfil.class);
        Bundle b = new Bundle();
        b.putParcelable("usuario",usuario);
        activityMain.putExtras(b);
        startActivity(activityMain);
    }

    private Usuario  leerUsuarioPreferencias(int id) {
        return presentador.leerUsuarioPreferencias(id);
    }


    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("Codigo : " + AplicacionRecetas.CURRENT_RECIPES);
        RepositorioUsuarios userRepo = new RepositorioUsuarios(OpenHelperManager.getHelper(this, Ayudante.class));

        usuario =  presentador.leerUsuarioPreferencias(AplicacionRecetas.ID_CURRENT_USER);
        iniciarValoresNavHeader();

        cambiarListaAdaptador();
    }

    private  void  iniciarValoresNavHeader() {
        NavigationView nav = (NavigationView) findViewById(R.id.nav_view); // los ids no estan en quip, sino dentro del header del navigation.
        View header = nav.getHeaderView(0); // 0 es porque es el primero, si estuviera el 2º en el layout seria el 1
        TextView nombreTv = (TextView) header.findViewById(R.id.nav_usuario);
        nombreTv.setText(usuario.getNombre());
        TextView correoTv = (TextView) header.findViewById(R.id.nav_correo);
        correoTv.setText(usuario.getCorreo());
        CircleImageView avatarIv = (CircleImageView) header.findViewById(R.id.nav_avatar);
        if (usuario.getAvatar() != null && !usuario.getAvatar().isEmpty()) {
            avatarIv.setImageBitmap(BitmapFactory.decodeFile(usuario.getAvatar()));
        } else {
            avatarIv.setImageDrawable(getResources().getDrawable(R.drawable.ic_no_avatar));
        }
    }


    private void botonNuevo() {
        Intent activity = new Intent(getApplicationContext(), VistaEdicionReceta.class);
        startActivity(activity);
    }



    private List<Receta> obtenerListaRecycler() {
        NavigationView nav = (NavigationView) findViewById(R.id.nav_view); // los ids no estan en quip, sino dentro del header del navigation.
        Menu menu =nav.getMenu();
        if (AplicacionRecetas.CURRENT_RECIPES == VistaMain.OWN_RECIPES) {
            return presentador.getRecetasUsuario(AplicacionRecetas.ID_CURRENT_USER);
        }
        else {
            return presentador.getRecetasOtrosUsuarios(AplicacionRecetas.ID_CURRENT_USER);
        }
    }

    private void cambiarListaAdaptador () {
        adaptador.setLista(obtenerListaRecycler());

        if (AplicacionRecetas.CURRENT_RECIPES == VistaMain.OWN_RECIPES) {
            adaptador.setOnClickListener(new EdicionRecetaListener());
        }
        else
            adaptador.setOnClickListener(new VistaRecetaListener());
        adaptador.notifyDataSetChanged();

    }




    public class EdicionRecetaListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            int pos = rv.getChildAdapterPosition(v);
            Intent activity = new Intent(getApplicationContext(), VistaEdicionReceta.class);
            Bundle b = new Bundle();
            b.putParcelable("receta",adaptador.getReceta(pos) );
            activity.putExtras(b);
            startActivity(activity);
        }
    }

    public class VistaRecetaListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            int pos = rv.getChildAdapterPosition(v);
            Intent activity = new Intent(getApplicationContext(), VistaReceta.class);
            Bundle b = new Bundle();
            b.putParcelable("receta",adaptador.getReceta(pos) );
            activity.putExtras(b);
            startActivity(activity);
        }
    }
}

