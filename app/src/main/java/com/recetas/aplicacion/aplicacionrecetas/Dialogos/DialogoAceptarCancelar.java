package com.recetas.aplicacion.aplicacionrecetas.Dialogos;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.recetas.aplicacion.aplicacionrecetas.Dialogos.Interfaces.AcceptOrCancelDialogListener;
import com.recetas.aplicacion.aplicacionrecetas.Dialogos.Interfaces.imagenDialogListener;
import com.recetas.aplicacion.aplicacionrecetas.R;


/**
 * Created by anton on 16/06/2017.
 */

public class DialogoAceptarCancelar extends DialogFragment {

    AcceptOrCancelDialogListener listener;
    String titulo;
    String texto;

    public DialogoAceptarCancelar() {
    }
    public static DialogoAceptarCancelar newInstance(String titulo, String texto) {
        DialogoAceptarCancelar fragment = new DialogoAceptarCancelar();
        Bundle args = new Bundle();

        System.out.println("sout 1 : " + titulo + texto);
        args.putString("titulo" , titulo);
        args.putString("texto" , texto);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            titulo = getArguments().getString("titulo");
            texto = getArguments().getString("texto");
        }

        System.out.println("sout 2 : " + titulo + texto);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        System.out.println("sout 3 : " + titulo + texto);
        builder.setTitle(titulo);
        builder.setMessage(texto);

        builder.setPositiveButton(R.string.acept , new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                listener.onAcceptDialog(null);
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                listener.onCancelDialog();
            }
        });
        AlertDialog alertBorrar = builder.create();
        return alertBorrar;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (AcceptOrCancelDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(
                    context.toString() +
                            " no implementó imagenDialogListener");
        }
    }
}
