package com.recetas.aplicacion.aplicacionrecetas.Dialogos;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;

import com.recetas.aplicacion.aplicacionrecetas.Dialogos.Interfaces.imagenDialogListener;
import com.recetas.aplicacion.aplicacionrecetas.R;

/**
 * Created by anton on 09/05/2017.
 */

public class DialogoImagen extends DialogFragment implements View.OnClickListener {

    // Interfaz de comunicación
    imagenDialogListener listener;

    public DialogoImagen() {
    }

    public static DialogoImagen newInstance() {
        DialogoImagen fragment = new DialogoImagen();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /*@NonNull o onCreateDialog o OncreateView, pero no ambos
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return createDialogoImagen();
    }

    public AlertDialog createDialogoImagen() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.dialogo_imagenes, null));

        return builder.create();
    }*/

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.dialogo_imagenes,null);
        Button botonCamara = (Button) v.findViewById(R.id.dialogCamera);
        botonCamara.setOnClickListener(this);


        Button botonGaleria = (Button) v.findViewById(R.id.dialogGalery);
        botonGaleria.setOnClickListener(this);

        Button botonCancel = (Button) v.findViewById(R.id.dialogCancel);
        botonCancel.setOnClickListener(this);

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (imagenDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(
                    context.toString() +
                            " no implementó imagenDialogListener");
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialogCamera:
                listener.imagenDialogCamara();
                dismiss();
                break;
            case R.id.dialogCancel:
                dismiss();
                break;
            case R.id.dialogGalery:
                listener.imagenDialogGaleria();
                dismiss();
                break;
            default:
                break;
        }
    }
}


