package com.recetas.aplicacion.aplicacionrecetas.Dialogos;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
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

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return createDialogoImagen();
    }

    public AlertDialog createDialogoImagen() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.dialogo_imagenes, null));

        return builder.create();
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
                    System.out.println("camara");
                break;
            default:
                break;
        }
    }
}


