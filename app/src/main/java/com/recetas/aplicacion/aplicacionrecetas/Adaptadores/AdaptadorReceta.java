package com.recetas.aplicacion.aplicacionrecetas.Adaptadores;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.recetas.aplicacion.aplicacionrecetas.Pojo.Receta;
import com.recetas.aplicacion.aplicacionrecetas.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anton on 19/05/2017.
 */

public class AdaptadorReceta extends RecyclerView.Adapter<AdaptadorReceta.ViewHolder> implements View.OnClickListener {


    private List<Receta> lista;
    private View.OnClickListener clickListener;

    public AdaptadorReceta() {
            lista = new ArrayList<Receta>();
    }

    public AdaptadorReceta(List<Receta> lista) {
        if (lista == null)
            lista = new ArrayList<Receta>();
        this.lista = lista;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflador = LayoutInflater.from(parent.getContext());
        View vista = inflador.inflate(R.layout.item_recetas,parent,false);
        return new ViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Receta r = lista.get(position);
        holder.getTituloReceta().setText(r.getTitulo());
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

    public class ViewHolder extends  RecyclerView.ViewHolder { //USADO PARA LAS NOTAS NORMALES
        private TextView tituloReceta;

        public ViewHolder(View itemView) { // aqui irian todos los elementos del layout
            super(itemView);
            tituloReceta = (TextView) itemView.findViewById(R.id.recetaTituloMain);
        }

        public TextView getTituloReceta() {
            return tituloReceta;
        }
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
}
