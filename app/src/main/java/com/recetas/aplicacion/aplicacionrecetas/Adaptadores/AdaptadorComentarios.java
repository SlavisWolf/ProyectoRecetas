package com.recetas.aplicacion.aplicacionrecetas.Adaptadores;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.recetas.aplicacion.aplicacionrecetas.App.AplicacionRecetas;
import com.recetas.aplicacion.aplicacionrecetas.Pojo.Comentario;
import com.recetas.aplicacion.aplicacionrecetas.Pojo.Receta;
import com.recetas.aplicacion.aplicacionrecetas.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anton on 08/06/2017.
 */

public class AdaptadorComentarios extends RecyclerView.Adapter<AdaptadorComentarios.ViewHolder> {


    private List<Comentario> lista;


    public AdaptadorComentarios() {
        lista = new ArrayList<Comentario>();
    }

    public AdaptadorComentarios(List<Comentario> lista) {
        if (lista == null)
            lista = new ArrayList<Comentario>();
        this.lista = lista;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflador = LayoutInflater.from(parent.getContext());
        View vista = inflador.inflate(R.layout.item_comentario,parent,false);
        return new AdaptadorComentarios.ViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
            Comentario comentario = lista.get(position);
            holder.getTextoComentario().setText(comentario.getTexto() );

            if (comentario.getUsuario().getId()  == AplicacionRecetas.ID_CURRENT_USER)
                holder.getAutorComentario().setText("You");
            else
                holder.getAutorComentario().setText(comentario.getUsuario().getNombre() );
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder { //USADO PARA LAS NOTAS NORMALES
        private TextView textoComentario;
        private TextView autorComentario;

        public ViewHolder(View itemView) { // aqui irian todos los elementos del layout
            super(itemView);
            textoComentario = (TextView) itemView.findViewById(R.id.textoComentario);
            autorComentario = (TextView) itemView.findViewById(R.id.autorComentario);
        }

        public TextView getTextoComentario() {
            return textoComentario;
        }

        public TextView getAutorComentario() {
            return autorComentario;
        }
    }

    public List<Comentario> getLista() {
        return lista;
    }

    public void nuevoComentario(Comentario c) {
        lista.add(0,c);
        this.notifyItemInserted(0);
    }
}
