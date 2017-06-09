package com.recetas.aplicacion.aplicacionrecetas.Adaptadores;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.recetas.aplicacion.aplicacionrecetas.App.AplicacionRecetas;
import com.recetas.aplicacion.aplicacionrecetas.App.ArchivosRecetas;
import com.recetas.aplicacion.aplicacionrecetas.BD.Repositorios.RepositorioRecetas;
import com.recetas.aplicacion.aplicacionrecetas.Pojo.Receta;
import com.recetas.aplicacion.aplicacionrecetas.Pojo.Usuario;
import com.recetas.aplicacion.aplicacionrecetas.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by anton on 19/05/2017.
 */

public class AdaptadorReceta extends RecyclerView.Adapter<AdaptadorReceta.ViewHolder> implements View.OnClickListener {


    private List<Receta> lista;
    private View.OnClickListener clickListener;
    private RepositorioRecetas bd;

    public AdaptadorReceta() {
            lista = new ArrayList<Receta>();
    }

    public AdaptadorReceta(List<Receta> lista , RepositorioRecetas bd) {
        this.bd = bd;
        if (lista == null)
            lista = new ArrayList<Receta>();
        this.lista = lista;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflador = LayoutInflater.from(parent.getContext());
        View vista = inflador.inflate(R.layout.item_recetas,parent,false);
        vista.setOnClickListener(this);
        return new AdaptadorReceta.ViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Receta r = lista.get(position);
        holder.getTituloReceta().setText(r.getTitulo());
        Usuario usuario = r.getUsuario();


        if (usuario.getId() == AplicacionRecetas.ID_CURRENT_USER) {
            holder.getAutorReceta().setText("Me");
        }
        else {
            if (usuario.getNombre() != null) {
                holder.getAutorReceta().setText(usuario.getNombre() );
            }
        }

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/YYYY");
        holder.getFechaReceta().setText(formatter.format(r.getFechaPublicacion()));

        if (r.getImagen() != null) {
            ArchivosRecetas.asignarImagenAImageViewPorRuta(holder.getFotoReceta() , r.getImagen() );
        }

        holder.getNumLikesReceta().setText(String.valueOf(bd.numeroLikesReceta(r) ) );
        holder.getNumDislikesReceta().setText(String.valueOf(bd.numeroDislikesReceta(r) ) );

    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    @Override
    public void onClick(View v) {
        if(clickListener != null)
            clickListener.onClick(v);
    }



    public  Receta getReceta(int position) {
        return lista.get(position);
    }
    public List<Receta> getLista() {
        return lista;
    }

    public void setLista(List<Receta> lista) {
        if (lista == null)
            lista = new ArrayList<Receta>();
        this.lista = lista;
    }

    public void setOnClickListener(View.OnClickListener clickListener) {
        this.clickListener = clickListener;
    }



    public class ViewHolder extends  RecyclerView.ViewHolder { //USADO PARA LAS NOTAS NORMALES

        private TextView    tituloReceta;
        private TextView    autorReceta;
        private TextView    fechaReceta;
        private ImageView   fotoReceta;
        private TextView    numLikesReceta;
        private TextView    numDislikesReceta;

        public ViewHolder(View itemView) { // aqui irian todos los elementos del layout
            super(itemView);
            tituloReceta = (TextView) itemView.findViewById(R.id.recetaTituloMain);
            autorReceta = (TextView) itemView.findViewById(R.id.recetaAutorMain);
            fechaReceta = (TextView) itemView.findViewById(R.id.recetaFechaMain);
            fotoReceta = (ImageView) itemView.findViewById(R.id.fotoRecetaMain);

            numLikesReceta = (TextView) itemView.findViewById(R.id.recetaNumLikesMain);
            numDislikesReceta = (TextView) itemView.findViewById(R.id.recetaNumDislikesMain);
        }

        public TextView getTituloReceta() {
            return tituloReceta;
        }

        public TextView getAutorReceta() {
            return autorReceta;
        }

        public ImageView getFotoReceta() {
            return fotoReceta;
        }

        public TextView getNumLikesReceta() {
            return numLikesReceta;
        }

        public TextView getNumDislikesReceta() {
            return numDislikesReceta;
        }

        public TextView getFechaReceta() {
            return fechaReceta;
        }
    }
}
